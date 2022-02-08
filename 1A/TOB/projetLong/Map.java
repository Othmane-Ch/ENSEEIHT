package projetLong;

public interface Map {
	public int DIM_X = 20; // la dimension (abscisse) de la map
    public int DIM_Y = 35; // la dimension (ordonnee) de la map

    //private Case[][] map;  // la map avec toutes les cellules

    public Case obtenirInfoCase(int x, int y);   // renvoie la cellule de coordonnees (x,y)

    public void miseAjourCase(Case cel);    // met a jour la carte avec la cellule rentree en parametre
}
