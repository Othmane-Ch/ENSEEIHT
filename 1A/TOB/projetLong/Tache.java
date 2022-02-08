package projetLong;

import java.util.ArrayList;

/**
 * une Tache est une liste chainée de tache
 * 
 */


public class Tache {
	 
    int sommet;
    Commande commande;
    //Commande[] taches;
    ArrayList<Commande> taches;
 
    Tache(){
    	//taches = new Commande[capacite];   //créer une nouvelle pile vide de taille maximal capacite
    	sommet = -1;  //lorsque tu auras empiler un élément tu l'obtiendras à piles[0]

    	taches = new ArrayList<Commande>();
    }
 
    /*
    Commande getSommet(){
        return taches[sommet];
    }*/
 
    public boolean estVide(){
    	/*
      if(sommet == -1){
        return true;
      }
		return false;
    	 */
    	return taches.isEmpty();
    }
    
    public void ajouter(Commande com) {
    	taches.add(com);
    }
 /*
    public boolean estPleine(){
      if(sommet == taches.length){
        return true;
      }
      return false;
    }

    public void empile(Commande commande2){
      if (estPleine()){
          throw new Error("La pile est pleine impossible d'empiler");
      }
      taches[sommet+1] = commande2;
      sommet++;
    }
 
    public Commande depile(){
      if (estVide()){
        throw new Error("La pile est vide impossible de depiler");
      }
      sommet--;  //inutile de la remplacer par une valeur 0 ou null car c'est avec l'indice sommet que tu y accèdes.
      return taches[sommet+1];      //on retourne la valeur que nous avons depiler
    }*/
    
    public Commande prendrePremier(){
        if (estVide()){
          throw new Error("La pile est vide impossible de depiler");
        }
        Commande retour = taches.get(0);
        taches.remove(0);
        
        /*
        sommet --;
        for (int k = 0; k < sommet; k++) {
        	taches[k]=taches[k+1];
        }
        if(sommet >= 0) {
            taches[sommet] = null;
        }
        */
        
        return retour;      //on retourne la valeur que nous avons depiler
      }
 /*
    public String versChaine(){ //Pour visualiser ta pile
      if(estVide()){
        return "[]";
      }
      if(sommet == 0){
        return "["+getSommet()+"]";
      }
      return "["+taches[0]+" "+getSommet()+"]";
    }*/
}
