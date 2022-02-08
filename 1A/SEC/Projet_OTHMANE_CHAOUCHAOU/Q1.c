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

    signal(SIGCHLD,handler_SIGCHLD);
    signal(SIGTSTP,handler_SIGTSTP);
    signal(SIGINT,handler_SIGINT);

    
    int retour;
    
    int n = 0;
   

    /*Boucle infinie gere les commandes.*/
    while(1) {
        getcwd(cwd,sizeof(cwd));
        printf(RED "oth_shell:%s  " NONE, getcwd(cwd,250));
        processusCourant = 0;
       
        signal(SIGCHLD,handler_SIGCHLD);
        signal(SIGTSTP,handler_SIGTSTP);
        signal(SIGINT,handler_SIGINT);
    }    
    exit(EXIT_SUCCESS);
}
