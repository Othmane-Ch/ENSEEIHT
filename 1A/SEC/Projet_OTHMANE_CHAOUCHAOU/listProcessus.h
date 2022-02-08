#ifndef _LISTEPROCESSUS_H
#define _LISTEPROCESSUS_H
#include <stdbool.h>



struct listProcessus {
    int idProcessus;        // Identifiant propre au minishell du processus.
    pid_t pidProcessus;     // Pid du processus.
    char state[10];           // Etat du processus.
    char commande[200];      // La commande lancée.
};

typedef struct listProcessus *listProcessus;

//Fonctions utilisées.

int positionProcessus(listProcessus *LesProcessus, pid_t pid);

bool exiteProcessus(listProcessus *LesProcessus,pid_t pid);

void addProcessus(listProcessus *LesProcessus, int id , pid_t pid, char commande[200]);

void SupprimerProcessus(listProcessus *LesProcessus, pid_t pid) ;

void afficherListeProcessus(listProcessus *LesProcessus);

int length(listProcessus *LesProcessus);

#endif
