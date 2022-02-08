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


    signal(SIGTSTP,handler_SIGTSTP);
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
        signal(SIGTSTP,handler_SIGTSTP);
        signal(SIGINT,handler_SIGINT);

        if (ligneCommande->seq[0] != NULL) {

            if (strcmp(ligneCommande->seq[0][0], "exit") == 0) {
                      exit(0);
            }
            else (strcmp(ligneCommande->seq[0][0], "cd") == 0){
                chdir(ligneCommande->seq[0][1]);
            }
    exit(EXIT_SUCCESS);
}
