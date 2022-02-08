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


int n=1;
pid_t processusCourant=0;

            void handler_SIGINT()  {
                        if (processusCourant != 0) {
                            kill(processusCourant,SIGKILL);
                            printf("\n");
                            printf("[%d]+ KILLED\n", processusCourant);
                        } else {
                            fflush(stdin);
                        }
            }

              void handler_SIGTSTP(){
                  if (processusCourant != 0) {
                        kill(processusCourant,SIGSTOP);
                        printf("\n");
                        printf("[%d]+ KILLED\n", processusCourant);
                   } else {
                        fflush(stdin);
                  }
               }

            

int main(int argc, char *argv[]) {

    signal(SIGINT,handler_SIGINT);

    char cwd[250];
    int retour;
    int n = 0; 
    struct cmdline *ligneCommande;


    /*Boucle infinie gere les commandes.*/
    while(1) {
        getcwd(cwd,sizeof(cwd));
        printf(RED "oth_shell:%s  " NONE, getcwd(cwd,250));
        processusCourant = 0; 

        ligneCommande = readcmd();

         
        signal(SIGINT,handler_SIGINT);

        if (ligneCommande->seq[0] != NULL) {

            if (strcmp(ligneCommande->seq[0][0], "exit") == 0) {
                      exit(0);
            }
            else if (strcmp(ligneCommande->seq[0][0], "cd") == 0){
                chdir(ligneCommande->seq[0][1]);
            }
             
             
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
