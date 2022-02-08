/**Implantation de la classe Cercle Un cercle est une courbe plane fermée
 * constituée des points situés à égale distance d’un point nommé centre, La
 * valeur de cette distance est appelée rayon du cercle, On appelle diamètre la
 * distance entre deux points diamétralement opposés.
 * @author Othmane CHAOUCHAOU
 */
import java.awt.Color;

public class Cercle implements Mesurable2D {
	/**Rayon du cercle.*/	
	private double rayon;
	/**Centre du cercle.*/
	private Point centre;
	/**Couleur du cercle.*/
	private Color couleur;
        /**Constante PI = 3.14.*/
	public final static double PI = Math.PI;	
    /** Construire un cercle à partir d'un point et d'un rayon
     *  @param C Centre du cercle
     *  @param R Rayon du cercle
     */	
	public Cercle(Point C,double R) {
		assert(C !=null && R >0);
		this.centre= new Point(C.getX(),C.getY());;
		this.rayon= R;
		this.couleur= Color.blue;
	}
    /**Construire un cercle à partir de deux points diamètralement opposés
     * @param A Premier point
     * @param B Deuxième point
     */	
	public Cercle(Point A,Point B) {
		assert(A!=null && B!=null);
		assert(A.distance(B)>0);
		this.rayon=A.distance(B)*0.5;
		this.centre= new Point((A.getX()+B.getX())*0.5,(A.getY()+B.getY())*0.5);
		this.couleur= Color.blue;
	}
    /**Construire un cercle à partir de deux points diamètralement opposés
     * @param A Premier point
     * @param B Deuxième point
     * @param nouvellecouleur couleur du cercle
     */	
    public Cercle(Point A, Point B, Color nouvellecouleur) {
        this(A,B);
        assert nouvellecouleur != null;
	    this.setCouleur(nouvellecouleur);
	}    
    /**Construire un cercle à partir d'un centre et d'un point  appartenant à la circonférence du cercle
     * @param A centre du cercle
     * @param B point appartenant à la circonférence du cercle
     * @return Cercle
     */	
	public static Cercle creerCercle(Point A, Point B) {
		assert(A!=null && B!=null);
        assert(A.getX() != B.getX() || A.getY()!=B.getY());
        return new Cercle(A,B.distance(A));
	}	
    /**Obtenir le rayon d'un cercle
     * @return Rayon du cercle
     */    
	public double getRayon() {
		return this.rayon;
	}
    /**Obtenir le diamètre d'un cercle
     * @return Diamètre du cercle
     */	
	public double getDiametre() {
		return 2*this.rayon;
	}	
    /**Obtenir le centre d'un cercle
     * @return Centre du cercle
     */	
	public Point getCentre() {
		Point C = new Point(this.centre.getX(),this.centre.getY()); 
		return C;
	}
    /**Obtenir la couleur d'un cercle
     * @return Couleur la couleur du cercle
     */	
	public Color getCouleur() {
		return this.couleur;
	}
    /**Savoir si un point est à l'intérieur (au sens large) d'un cercle
     * @param P Point dont on veut tester l'appartenance 
     * @return si le point est à l'intérieur ou non
     */	
	public boolean contient(Point P) {
        assert(P != null);
		return (this.centre.distance(P) <= this.rayon); 
	}
    /**Changer le rayon d'un cercle
     * @param r le nouveau rayon du cercle
     */
	public void setRayon(double r) {
		assert(r>0);
		this.rayon=r;
	}
    /**Changer le diamètre d'un cercle
     * @param d le nouveau diamètre du cercle
     */	
	public void setDiametre(double d) {
		assert(d>0);
		this.rayon= d*0.5;
	}
    /**Changer la couleur d'un cercle
     * @param nv_Color la nouvelle couleur du cercle
     */
	public void setCouleur(Color nv_Color) {
        assert(nv_Color != null);
		this.couleur = nv_Color;
	}	
    /**retourne le cercle sous-forme string Cr@(centre.getX(),centre.getY())
     * @param 
     */
	public String toString() {
		return "C"+this.rayon+"@"+this.centre.toString();
	}
	/** Afficher le cercle. */	
    public void afficher(){
        System.out.print(this);
    }
    /**Obtenir le périmètre d'un cercle
     * @return Perimètre du cercle
     */    		
	public double perimetre() {
		return this.rayon*PI*2;
	}
    /**Obtenir l'aire d'un cercle
     * @return Aire du cercle
     */	
	public double aire() {
		return PI*Math.pow(this.rayon,2);
	}
    /**Translater un cercle
     * @param dx déplacement en abscisse du centre
     * @param dy déplacement en ordonnée du centre
     */	
	public void translater(double dx,double dy) {
		this.centre.translater(dx,dy); 
	}
}
