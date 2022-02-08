package projetLong;

public class Case {
	private int x;
	private int y;
	private EtatCase etat;
	private AccesVoisin accesVoisin;
	private boolean robot;
	

	//constructeurs _______________________________________________________________
	public Case(int pos_x, int pos_y) {
		this.x = pos_x;
		this.y = pos_y;
		this.etat = EtatCase.normal;
		this.accesVoisin = new AccesVoisin();
	}
	public Case(int pos_x, int pos_y, EtatCase etatCase) {
		this.x = pos_x;
		this.y = pos_y;
		this.etat = etatCase;
		this.accesVoisin = new AccesVoisin();
	}
	public Case(int pos_x, int pos_y, AccesVoisin acces) {
		this.x = pos_x;
		this.y = pos_y;
		this.etat = EtatCase.normal;
		this.accesVoisin = acces.copy();
	}
	public Case(int pos_x, int pos_y, EtatCase etatCase, AccesVoisin acces) {
		this.x = pos_x;
		this.y = pos_y;
		this.etat = etatCase;
		this.accesVoisin = acces.copy();
	}
	//fin constructeurs __________________________________________________________________
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public EtatCase getEtat() {
		return this.etat;
	}
	public AccesVoisin getAccesVoisin() {
		return this.accesVoisin; //faire une copie ?
	}
	

	public boolean robotPresent() {
		return this.robot;
	}
	
	public void setX(int pos_x) {
		this.x = pos_x;
	}
	public void setY(int pos_y) {
		this.y = pos_y;
	}
	public void setEtat(EtatCase etatCase) {
		this.etat = etatCase;
	}
	
	public void setAccesNord(double val) {
		this.accesVoisin.setAccesNord(val);
	}
	public void setAccesSud(double val) {
		this.accesVoisin.setAccesSud(val);
	}
	public void setAccesEst(double val) {
		this.accesVoisin.setAccesEst(val);
	}
	public void setAccesOuest(double val) {
		this.accesVoisin.setAccesOuest(val);
	}
	public boolean isEmpty() {
		if (this.etat == EtatCase.inaccessible) {
			return false;
		} else {
			return true;
		}
		
	}
	public boolean isObstacle() {
		if (this.etat == EtatCase.inaccessible) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isSurvivant() {
		if (this.etat == EtatCase.rescape) {
			return true;
		} else {
			return false;
		}
	}
	public void setRobot(boolean b) {
		this.robot = b;
	}
}
