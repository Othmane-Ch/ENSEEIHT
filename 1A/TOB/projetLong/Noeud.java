package projetLong;

/**
 * Cette classe est utile pour l'algorithme de pathfinding
 * @author tmaisonh
 *
 */
public class Noeud {
	private int x;
	private int y;
	private int distInit;
	private int distFin;
	
	public Noeud(int _x, int _y, int d_init, int d_fin) {
		this.x = _x;
		this.y = _y;
		this.distInit = d_init;
		this.distFin = d_fin;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getDistInit() {
		return this.distInit;
	}
	public int getDistFin() {
		return this.distFin;
	}
	public int getHeuristique() {
		return this.distInit + this.distFin;
	}
	
	public void setX(int _x) {
		this.x = _x;
	}
	public void setY(int _y) {
		this.y = _y;
	}
	public void setDistInit(int d) {
		this.distInit = d;
	}
	public void setDistFin(int d) {
		this.distFin = d;
	}
}
