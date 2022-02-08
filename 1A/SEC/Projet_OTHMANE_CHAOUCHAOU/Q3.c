#include <unistd.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <stdio.h>
#include <errno.h>
#include <signal.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "readcmd.h"
#include "readcmd.c"
#include "listProcessus.h"
#include "listProcessus.c"
#define taille 500

#define RED  "\033[0;31m"
#define NONE "\033[00m"

listProcessus *LesProcessus;
int n=1;
pid_t processusCourant=0;
bool ctrl_z=false;
bool ctrl_c=false;

            void handler_SIGINT()  {
                        if (processusCourant != 0) {
                            kill(processusCourant,SIGKILL);
                            printf("\n");
                            printf("[%d]+ KILLED\n", processusCourant);
                            SupprimerProcessus(LesProcessus,processusCourant);
                            ctrl_c = true;
                        } else {
                            fflush(stdin);
                        }
            }

              void handler_SIGTSTP(){
                  if (processusCourant != 0) {
                        kill(processusCourant,SIGSTOP);
                        printf("\n");
                        printf("[%d]+ KILLED\n", processusCourant);
                        int i = positionProcessus(LesProcessus,processusCourant);
                        strcpy(LesProcessus[i]->state ,"SUSPENDED");
                        ctrl_z = true;
                   } else {
                        fflush(stdin);
                  }
               }

            void handler_SIGCHLD(){
                int wstatus, pid_child;
                    do {
                        pid_child = (int) waitpid(-1, &wstatus, WNOHANG | WUNTRACED | WCONTINUED);
                        if ((pid_child == -1) && (errno != ECHILD)) {
                            perror("waitpid");
                            exit(-1);
                        } else if (pid_child > 0) {
                            if (WIFSTOPPED(wstatus)) {
                                if (!(ctrl_z ) ){
                                    int i = positionProcessus(LesProcessus,pid_child);
                                    strcpy(LesProcessus[i]->state ,"SUSPENDED");
                                }
                            } else if (WIFCONTINUED(wstatus)) {
                                int i= positionProcessus(LesProcessus,pid_child);
                                strcpy(LesProcessus[i]->state ,"ACTIVE");
                            } else if (WIFEXITED(wstatus) || WIFSIGNALED(wstatus)) {
                                SupprimerProcessus(LesProcessus,pid_child);
                                n=n-1;
                                processusCourant= 0;
                            }
                        }
                    } while (pid_child > 0);
             }


int main(int argc, char *argv[]) {

    signal(SIGCHLD,handler_SIGCHLD);
    signal(SIGTSTP,handler_SIGTSTP);
    signal(SIGINT,handler_SIGINT);

    char cwd[250];
    int retour;
    int pipe1[2], pipe2[2];
    int n = 0;
    extern listProcessus *LesProcessus;
    LesProcessus =  malloc(200);
    struct cmdline *ligneCommande;


    /*Boucle infinie gere les commandes.*/
    while(1) {
        getcwd(cwd,sizeof(cwd));
        printf(RED "oth_shell:%s  " NONE, getcwd(cwd,250));
        processusCourant = 0;
        ctrl_z = false;
        ctrl_c = false;

        ligneCommande = readcmd();

        signal(SIGCHLD,handler_SIGCHLD);
        signal(SIGTSTP,handler_SIGTSTP);
        signal(SIGINT,handler_SIGINT);

        if (ligneCommande->seq[0] != NULL) {

            if (strcmp(ligneCommande->seq[0][0], "exit") == 0) {
                      exit(0);
            }
            else if (strcmp(ligneCommande->seq[0][0], "cd") == 0){
                chdir(ligneCommande->seq[0][1]);
            }
            else if (!strcmp(ligneCommande->seq[0][0],"list") || !strcmp(ligneCommande->seq[0][0],"jobs")){
                   afficherListeProcessus(LesProcessus);
            }
            else if (!strcmp(ligneCommande->seq[0][0],"STOP")) {
                if (!(ligneCommande->seq[0][1] == NULL)){
                    pid_t PID = atoi(ligneCommande->seq[0][1]);
                    if (exiteProcessus(LesProcessus,PID)){
                        kill(PID,SIGSTOP);
                        printf( "Error : Processus is Suspended \n" ,PID);
                    } else {
                        printf( "Error : Processus introuvable !. \n" ,atoi(ligneCommande->seq[0][1]));
                    }
                }
                processusCourant = 0;
            }

            else if  (strcmp(ligneCommande->seq[0][0], "bg") == 0) {
              if (!(ligneCommande->seq[0][1] == NULL)){
                  pid_t PID = atoi(ligneCommande->seq[0][1]);
                  if (exiteProcessus(LesProcessus,PID)) {
                      kill(PID,SIGCONT);
                      printf("Reprise du processus en arrière-plant !\n", PID);
                  } else {
                      printf( "Error : Processus introuvable !\n" ,PID);
                  }
              } else {
                  printf( "Vous devez entrer le PID du processus\n" );
              }
              processusCourant = 0;

            }

            else if (strcmp(ligneCommande->seq[0][0], "fg") == 0){
              if (!(ligneCommande->seq[0][1] == NULL)){
                  pid_t PID = atoi(ligneCommande->seq[0][1]);
                  if (exiteProcessus(LesProcessus,PID)) {
                      ctrl_z=false;
                      ctrl_c=false;
                      kill(PID,SIGCONT);
                      printf("Reprise du processus en avant-plant !\n" ,  PID);
                      processusCourant = PID;

                      while((processusCourant==PID) && !(ctrl_c) && !(ctrl_z)) {
                              sleep(0);
                      }
                  } else {
                      printf( "Error : Processus introuvable !\n",PID);
                  }
              } else {
                  printf( "Vous devez entrer le PID de processus\n" );
              }
              processusCourant = 0;

            }


            else {
                retour = fork();

                if (retour < 0) {
                    printf("Erreur fork\n");
                    exit(1);
                }
                if (retour == 0) {

                  /*Q9. tupes simples, exécution de deux commande.*/
                  if (ligneCommande->seq[1]!= NULL) {


                      if (pipe(pipe1) == -1) {
                          printf("Error pipe\n") ;
                          exit(1) ;
                      }
                       /*On definit le petit fils.*/
                      int p_fils = fork();
                      /* Bonne pratique : tester systématiquement le retour des appels système */
                      if (p_fils < 0) {
                          printf("Error fork\n") ;
                          exit(1) ;
                      }
                      if (p_fils == 0) {
                        close(pipe1[0]);
                        if(dup2(pipe1[1],1)==-1){
                          printf("Error dup2");
                        }
                        close(pipe1[1]);
                        execlp((ligneCommande->seq)[0][0],(ligneCommande->seq)[0][0],NULL) ;
                      } else {
                            close(pipe1[1]) ;
                            if (dup2(pipe1[0], 0)  == -1) {
                               printf("Error dup2\n") ;
                               exit(1) ;
                            }
                            close(pipe1[0]);
                            execlp((ligneCommande->seq)[1][0],(ligneCommande->seq)[1][0], (ligneCommande->seq)[1][1], NULL) ;
                            perror((ligneCommande->seq)[1][0]) ;
                            exit(1) ;
                      }
                  }
                  /*Q10*/

                     if (ligneCommande->seq[2]!=NULL ) {
                         int child1,child2;


                          /* Bonne pratique : tester systématiquement le retour des appels système */
                          if (pipe(pipe2) == -1) {
                             printf("Error pipe\n") ;
                              exit(1) ;
                          }
			  /*On définit le petit fils 1.*/
                          child1 = fork() ;
                         /* Bonne pratique : tester systématiquement le retour des appels système */
                          if (child1 < 0) {
                             printf("Error fork\n") ;
                              exit(1) ;
                          }
                         /* fils */
                          if (child1 == 0) {
                                close(pipe2[0]) ;
                                dup2(pipe2[1],1);
                                close(pipe2[1]);

                                /* Q10 */
                                if (pipe(pipe1) == -1) {
                                     printf("Error pipe\n") ;
                                      exit(1) ;
                                  }
                                  /*On définit le petit fils 2.*/

                                    child2 =fork();
                                    if (child2 < 0) {
                                         printf("Erreur fork\n") ;
                                          exit(1) ;
                                      }
                                    if(child2==0){
                                        close(pipe1[0]);
                                        if(dup2(pipe1[1],1)==-1){
                                          printf("Error dup2");
                                        }
                                        close(pipe1[1]);
                                        execlp(ligneCommande->seq[0][0],ligneCommande->seq[0][0],NULL);
                                        exit(1);
                                     } else{
                                       close(pipe1[1]);
                                        if(dup2(pipe1[0],0) == -1){
                                          printf("Erreur dup2");
                                        }
                                        close(pipe1[1]);
                                        execlp(ligneCommande->seq[1][0],ligneCommande->seq[1][0],ligneCommande->seq[1][1],NULL);
                                        exit(2);
                                    }
                                }
                              else{
                                    close(pipe2[1]);
                                    if(dup2(pipe2[0],0) == -1){
                                      printf("Error dup3");
                                    }
                                    close(pipe2[0]);
                                    execlp(ligneCommande->seq[2][0],ligneCommande->seq[2][0],ligneCommande->seq[2][1], NULL);
                                    exit(3);
                               }
                   }

                }
                /*On masque les signaux sigint et sigstp pour les
                      processus on arriere plan. */
                     if (!(ligneCommande->backgrounded == NULL)){
                        sigset_t pbg;
                        sigemptyset(&pbg);
                        sigaddset(&pbg,SIGTSTP);
                        sigaddset(&pbg,SIGINT);
                        sigprocmask(SIG_BLOCK,&pbg,NULL);
                     }
		        if (ligneCommande->in  ==NULL && ligneCommande->out ==NULL){
                            if (execvp(ligneCommande->seq[0][0],ligneCommande->seq[0]) ==-1){
                                printf("La commande saisi est invalide.\n" );
                                exit(1);
                            }
		        }
                else if (ligneCommande->in !=NULL && ligneCommande->out != NULL) {
                   dup2(open(ligneCommande->in,O_RDONLY),1);
                   close(open(ligneCommande->out, O_WRONLY | O_CREAT, 0644));
                   dup2(open(ligneCommande->in,O_RDONLY),0);
                   close(open(ligneCommande->in,O_RDONLY));
                   execlp(ligneCommande->seq[0][0],ligneCommande->seq[0][0],ligneCommande->seq[0][1],NULL);
               }
                 else if (ligneCommande->in ==NULL && ligneCommande->out != NULL) {
                       dup2(open(ligneCommande->out, O_WRONLY | O_CREAT, 0644),1);
                       close(open(ligneCommande->out, O_WRONLY | O_CREAT, 0644));
                       execlp(ligneCommande->seq[0][0],ligneCommande->seq[0][0],ligneCommande->seq[0][1],NULL);
                }
                   else if (  ligneCommande->in !=NULL && ligneCommande->out == NULL) {
                       dup2(open(ligneCommande->in,O_RDONLY),0);
                       close(open(ligneCommande->in,O_RDONLY));
                       execlp(ligneCommande->seq[0][0],ligneCommande->seq[0][0],ligneCommande->in,NULL);
                       exit(0);
                } else {   /* Q5 */ /* père */
                    int indice = 0;
                    char commande[250] =" " ;
                    while (!(ligneCommande->seq[0][indice] == NULL)) {
                        strcat(commande,ligneCommande->seq[0][indice]);
                        strcat(commande," ");
                        indice++;
                    }
                     addProcessus(LesProcessus,n,retour,commande);
                     n=n+1;
                      /* Q5 */
                    if (ligneCommande->backgrounded == NULL) {
                        int wstatus;
                        processusCourant = retour;
                        waitpid(retour,&wstatus,WUNTRACED);
                        /*On supprime le processus car on a terminé son traitement.*/
                        if (!WIFSTOPPED(wstatus)) {
                               SupprimerProcessus(LesProcessus,retour);
                               n=n-1;
                        }
                        processusCourant= 0;
                    }
                }
            }
        }
    }
    exit(EXIT_SUCCESS);
}
