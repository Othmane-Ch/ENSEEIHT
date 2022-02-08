package projetLong;

public class MapReel implements Map{

    private Case[][] map;

	public MapReel() {
		this.map = new Case[DIM_X][DIM_Y];
        for ( int i = 0; i < DIM_X; i++ ) {
            for ( int j = 0; j < DIM_Y; j++ ) {
            	this.map[i][j] = new Case(i,j);
            }
        }
    }

    public Case obtenirInfoCase(int x, int y) {
        return this.map[x][y];
    }

    public void miseAjourCase(Case cel) {
        this.map[cel.getX()][cel.getY()] = cel;
    }
    public Case[][] getMap() {
		return this.map;
	}
}
