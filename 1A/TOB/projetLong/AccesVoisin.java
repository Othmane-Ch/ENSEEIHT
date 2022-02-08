package projetLong;

public class AccesVoisin {
	//Les 4 attributs correspond au pourcentage de chance de réussir à se rendre 
	//à la case adjacente dans une direciton
	private double accesNord; // y-1
	private double accesSud; // y+1
	private double accesEst; // x+1
	private double accesOuest; // x-1
	
	public AccesVoisin(double nord_acc, double sud_acc, double est_acc, double ouest_acc) {
		this.accesNord = nord_acc;
		this.accesSud = sud_acc;
		this.accesEst = est_acc;
		this.accesOuest = ouest_acc;
	}
	
	public AccesVoisin() {
		this.accesNord = 100;
		this.accesSud = 100;
		this.accesEst = 100;
		this.accesOuest = 100;
	}
	
	public double getAccesNord() {
		return this.accesNord;
	}
	public double getAccesSud() {
		return this.accesSud;
	}
	public double getAccesEst() {
		return this.accesEst;
	}
	public double getAccesOuest() {
		return this.accesOuest;
	}
	
	public AccesVoisin copy() {
		return new AccesVoisin(this.accesNord, this.accesSud, this.accesEst, this.accesOuest);
	}
	
	public void setAccesNord(double val) {
		this.accesNord = val;
	}
	public void setAccesSud(double val) {
		this.accesSud = val;
	}
	public void setAccesEst(double val) {
		this.accesEst = val;
	}
	public void setAccesOuest(double val) {
		this.accesOuest = val;
	}
}
