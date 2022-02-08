#include <stdlib.h> 
#include <stdio.h>
#include <assert.h>
#include <stdbool.h>
#define CAPACITE 5
// Definition du type monnaie

struct monnaie {
    float valeur;
    char devise;
};
typedef struct monnaie monnaie;

/**
 * \brief Initialiser une monnaie 
 * \param[out] monnaie à initialiser
 * \param[in] valeur : la valeur de la monnaie
 * \param[in] devise : la devise de la monnaie
 * \pre valeur > 0
 * // TODO
 */ 
void initialiser(monnaie* m,float valeur,char devise) {
    assert(valeur>0);
    m->valeur = valeur;
    m->devise = devise;
}


/**
 * \brief Ajouter une monnaie m2 à une monnaie m1 
 * \param[in] m1 : la monnaie à ajouter 
 * \param[in out] m2 : monnaie somme
 * // TODO
 */ 
bool ajouter(monnaie* m1, monnaie* m2) {
    if (m1->devise == m2->devise) {
        m2->valeur += m1->valeur;
        return true;
    } else {
        return false;
    }
}


/**
 * \brief Tester Initialiser 
 * \param[]
 * // TODO
 */ 
void tester_initialiser(){
    monnaie* m1 = NULL;
    monnaie* m2 = NULL;
    initialiser(m1,5.78,'$');
    initialiser(m2,5,'£');
    assert(m1->valeur == 5.78);
    assert(m1->devise == '$');
    assert(m2->valeur == 5);
    assert(m2->devise == '£');
}

/**
 * \brief Tester Ajouter 
 * \param[]
 * // TODO
 */ 
void tester_ajouter(){
    monnaie* m1 = NULL;
    monnaie* m2 = NULL;
    m1->valeur = 15;
    m1->devise = '$';
    m2->valeur = 5;
    m2->devise = '$';
    ajouter(m1,m2);
    assert(m1->valeur == 5);
    assert(m2->valeur == 20);
    
}



int main(void){
    // Un tableau de 5 monnaies
    typedef monnaie wallet [CAPACITE];
    wallet tab;
    //Initialiser les monnaies
    char devise;
    float valeur;
    for(int i=0; i<CAPACITE; i++) {
        printf("\nEntrer la valeur de la monnaie %d : ",i+1);
        scanf("%f",&valeur);
        printf("\nEntrer la devise de la monnaie %d : ",i+1);
        scanf(" %c",&devise);
        initialiser(&tab[i],valeur,devise);
    }
 
    // Afficher la somme des toutes les monnaies qui sont dans une devise entrée par l'utilisateur.
    char d;
    monnaie somme;
    printf("\nEntrer la devise des monnaies :");
    scanf("%s",&d);
    somme.valeur = 0.0;
    somme.devise = d;
    for(int i=0; i<CAPACITE; i++) {
    	if (tab[i].devise == d) {
	    ajouter(&tab[i],&somme);   	
    	}
    }
    printf("La somme des toutes les monnaies ayant la devise %c est : %f",devise,somme.valeur);
    return EXIT_SUCCESS;
}
