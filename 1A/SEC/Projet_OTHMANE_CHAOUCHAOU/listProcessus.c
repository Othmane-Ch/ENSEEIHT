#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "listProcessus.h"

#define taille 500





int length(listProcessus *LesProcessus){
    int i = 0;
    while (!(LesProcessus[i] == NULL)){
            i++;
    }
    return i;
}


int positionProcessus(listProcessus *LesProcessus, pid_t pid) {
    int i = 0;
    int position = -1;
    while(!(LesProcessus[i] == NULL)){
        if (LesProcessus[i]->pidProcessus == pid) {
            position = i;
            break;
        }
        i++;
      }
      return position;
    }


bool exiteProcessus(listProcessus *LesProcessus,pid_t pid) {
        return !(positionProcessus(LesProcessus,pid) == -1);
}


void addProcessus(listProcessus *LesProcessus, int id , pid_t pid, char commande[200]) {
    if (!exiteProcessus(LesProcessus,pid)) {
        int n = length(LesProcessus);
        LesProcessus[n] = malloc(taille);
        LesProcessus[n]->idProcessus = id;
        LesProcessus[n]->pidProcessus = pid;
        strcpy(LesProcessus[n]->state, "ACTIF");
        strcpy(LesProcessus[n]->commande, commande);
    }
}




void SupprimerProcessus(listProcessus *LesProcessus, pid_t pid) {
    if(exiteProcessus(LesProcessus,pid)){
        int n = length(LesProcessus);
        int p = positionProcessus(LesProcessus, pid);
        for (int i = p+1; i < n; i++) {
            LesProcessus[i] = LesProcessus[i + 1];
        }
            free(LesProcessus[n-1]);
            LesProcessus[n-1] = NULL;
            LesProcessus = calloc(n-1,sizeof(LesProcessus));

    }
}



void afficherListeProcessus(listProcessus *LesProcessus){
    int n = length(LesProcessus);
    if (n>0) {
      printf("Id     PID       STAT          COMMAND\n");
      for (int i=0 ; i < n ; i++) {
          if(!strcmp(LesProcessus[i]->state,"ACTIF")){
                  printf("%d      %d     %s       %s\n",LesProcessus[i]->idProcessus,LesProcessus[i]->pidProcessus,
                  LesProcessus[i]->state,LesProcessus[i]->commande);}
          else{printf("%d      %d     %s    %s\n",LesProcessus[i]->idProcessus,LesProcessus[i]->pidProcessus,
                  LesProcessus[i]->state,LesProcessus[i]->commande);}
      }
    } else {
      printf("La liste est vide !\n");
    }
}
