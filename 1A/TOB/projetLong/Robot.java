package projetLong;

import java.awt.Component;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;




/**classe representant un robot */
public abstract class Robot {
	/* Image modélisant le robot dans la map*/
	private final String imageLocation = "RobotLogo.png/";
	/**position du robot*/
	Point point;
	/**direction  du robot*/
	private Direction direction;
	/**etat du robot*/
	private EtatRobot etat;	

	/**lien vers le terrain dans lequel se trouve le robot*/
	private MapReel map;
	/**taille du terrain*/
	private int taille;
	/**position de survivant*/
	private static Case posSurvivant ;


	/**objet necessaire pour le tirage aleatoire de la prochaine direction*/
	private Random hasard;
	
	private Tache tache;
	/* identifiant du Robot */
	private int id;
	
	private boolean on;
	
	
	
	/**construit un robot
	 * @param _x coordonee x initiale du robot
	 * @param _y coordonee y initiale du robot
	 * @param _terrain terrain ou se trouve le robot
	 */
	public Robot(int _x, int _y, MapReel _map, int num)
	{
		this.on=true;
		point = new Point(_x, _y);
		map=_map;
		//taille = case.getTaille();
		direction = Direction.randomDirection();
		etat = EtatRobot.CHERCHER;
		hasard = new Random();
		this.id = num;
		this.tache = new Tache();
	}
	
	public EtatRobot getEtatRobot() {
		return this.etat;
	}
	
	public void setEtatRobot(EtatRobot etat) {
		this.etat=etat;
	}
	
	public void remplacerTache(Tache tache) {
		this.tache=tache;
	}

	public Tache getTache() {
		return this.tache;
	}

	
	/**
	 * Ces taches ne doivent pas être vides, il faut le vérifier avant d'exécuté cette fonction
	 */
	public void faireAction(MapRobot mapRobot) {
		Commande commande = this.tache.prendrePremier();
		switch(commande) {
		case avancerNord:
			//System.out.println("nord");
			if(point.y - 1 >= 0 && map.obtenirInfoCase(point.x, point.y-1).getEtat().estAccessible() && map.obtenirInfoCase(point.x, point.y).getAccesVoisin().getAccesNord() > 0) {

				Case cel = mapRobot.obtenirInfoCase(point.x, point.y);
				cel.setRobot(false);
				mapRobot.miseAjourCase(cel);
				
				point.y = point.y - 1;
				
				Case newCel = mapRobot.obtenirInfoCase(point.x, point.y);
				newCel.setRobot(true);
				mapRobot.miseAjourCase(newCel);
			}else {
				this.tache = new Tache();
			}
			break;
		case avancerSud:
			//System.out.println("sud");
			//on verifie s'il peut
			if(point.y + 1 < Map.DIM_Y && map.obtenirInfoCase(point.x, point.y+1).getEtat().estAccessible() && map.obtenirInfoCase(point.x, point.y).getAccesVoisin().getAccesSud() > 0) {
				Case cel = mapRobot.obtenirInfoCase(point.x, point.y);
				cel.setRobot(false);
				mapRobot.miseAjourCase(cel);
				
				point.y = point.y + 1;
				
				Case newCel = mapRobot.obtenirInfoCase(point.x, point.y);
				newCel.setRobot(true);
				mapRobot.miseAjourCase(newCel);
			}else {
				this.tache = new Tache();
			}
			break;
		case avancerOuest:
			//System.out.println("ouest");
			//on verifie s'il peut
			if(point.x - 1 >= 0 && map.obtenirInfoCase(point.x-1, point.y).getEtat().estAccessible() && map.obtenirInfoCase(point.x, point.y).getAccesVoisin().getAccesOuest() > 0) {
				Case cel = mapRobot.obtenirInfoCase(point.x, point.y);
				cel.setRobot(false);
				mapRobot.miseAjourCase(cel);
				
				point.x = point.x - 1;
				
				Case newCel = mapRobot.obtenirInfoCase(point.x, point.y);
				newCel.setRobot(true);
				mapRobot.miseAjourCase(newCel);
			}else {
				this.tache = new Tache();
			}
			break;
		case avancerEst:
			//System.out.println("est");
			//on verifie s'il peut
			if(point.x + 1 < Map.DIM_X && map.obtenirInfoCase(point.x+1, point.y).getEtat().estAccessible() && map.obtenirInfoCase(point.x, point.y).getAccesVoisin().getAccesEst() > 0) {
				Case cel = mapRobot.obtenirInfoCase(point.x, point.y);
				cel.setRobot(false);
				mapRobot.miseAjourCase(cel);
				
				point.x = point.x + 1;
				
				Case newCel = mapRobot.obtenirInfoCase(point.x, point.y);
				newCel.setRobot(true);
				mapRobot.miseAjourCase(newCel);
			}else {
				this.tache = new Tache();
			}
			break;
		default:
				//il attend
			break;
		}
	}


	/**active les actions du robot selon son etat*/
	public void avancer()
	{
		switch(etat)
		{
		case CHERCHER: //recherche de survivant
			direction = getBestDirection(); //s'orienter vers la direction des survivant, sinon au hasard devant
			bougerVersDirection();
			break;
		case PISTER:
			break;
		}
	}


	/**recherche la direction a prendre.. 
	 * SI aucun survivant n est detecte, pour un comportement "realiste" le robot a un rayon de braquage de 45degres 
	 * @return si un survivant a ete detecte, retourne la direction vers le survivant, sinon  retourne une des trois directions devant le robot
	 * */
	private Direction getBestDirection()
	{
		Direction bestDirection = direction;
		Case maPosSurvivant = detecterSurvivant();
		if(maPosSurvivant != null &&  maPosSurvivant !=posSurvivant) posSurvivant = maPosSurvivant ;
		Direction []dirAlentours = null;
		if (posSurvivant!=null) 
		{
			bestDirection = Direction.getDirectionFromVector((int)(posSurvivant.getX()-point.getX()), (int)(posSurvivant.getY()-point.getY()));
			if(bestDirection==null) return null;
			dirAlentours = bestDirection.get3Dir();		
		}
		else
		{
			dirAlentours = direction.get3Dir();
		}
			ArrayList<Direction> listeDir = possibleNextDirections(dirAlentours);			
			if(!listeDir.isEmpty())
			{
				if (posSurvivant==null || (posSurvivant!=null  && listeDir.size()<3))
				{
					int i = hasard.nextInt(listeDir.size());
					bestDirection = listeDir.get(i); 
				}
				else if (posSurvivant!=null) bestDirection = listeDir.get(1);
			}
			else // si pas possible, faire demi-tour
				bestDirection = direction.getInverse();	
		
		return bestDirection;
	}

	/**cherche si un survivant est dans le voisinage
	 * @return la cellule du voisinage ou se trouve le survivant, null sinon*/
	private Case detecterSurvivant() {
		Case posSurvivant = null;
		Case[][] grille = map.getMap();
		int i,j=0;
		for( i=-3; i<=3; i++)
		{
			int xi = point.x+i;
			if(xi<0 || xi>=taille) continue;
			for( j=-3; j<=3; j++)
			{
				int yi = point.y+j;
				if(yi<0 || yi>=taille) continue;
				if(grille[xi][yi].isSurvivant())
				{
					posSurvivant = grille[xi][yi];
					break;
				}
			}			
		}
		if(posSurvivant!=null && posSurvivant.getX()==point.getX() && posSurvivant.getY()==point.getY() )
		{
			System.out.println("PERDU !!!");
			 
		}
		return posSurvivant;
	}


	/**retourne une liste de directions possibles vers des cases vides dans les directions donnees
	 * @param directions tableaux des directions dans lesquelles il faut tester si les cellules sont vides de robots
	 * @return une liste de directions possibles vers des cases vides de robots*/
	private ArrayList<Direction> possibleNextDirections(Direction []directions)
	{
		ArrayList<Direction> liste = new ArrayList<Direction>();
		for(Direction dir:directions)
		{
			Case cell = getNextCellule(dir);
			if(cell != null && !cell.isEmpty() && !cell.isObstacle()  )
			{
				if(dir.ordinal()%2!=0)
				{
					Direction[] dir3 = dir.get3Dir();
					boolean ok = true;
					for(int i=0; i<3;i+=2)
					{
						cell = getNextCellule(dir3[i]);
						ok = ok && (cell != null && !cell.isEmpty() && !cell.isObstacle() );
					}
					if(ok) liste.add(dir);
				}
				else	liste.add(dir);
			}
				
		}
		return liste;		
	}





	/**fait avancer le robot dans sa direction si la case devant existe et est non occupee*/
	private void bougerVersDirection()
	{
		Case cell = getNextCellule(direction);
		if(cell!=null && !cell.isEmpty()  && !cell.isObstacle()) 
		{
			Case[][] grille = map.getMap();
			grille[point.x][point.y].setRobot(false);
			point.x = cell.getX();
			point.y = cell.getY();
			// AppliLaby.moveRobot(this,  point);
			//			dessin.setCenterX((point.x+1) * pas);
			//			dessin.setCenterY((point.y+2) * pas);
			cell.setRobot(true);
			if(posSurvivant!=null && posSurvivant.getX()==point.getX() && posSurvivant.getY()==point.getY() )
			{
				if(cell.isSurvivant())
				{
					System.out.println("PERDU !!!");
					
					//AppliLaby.littleCycle.stop();					
				}
				else posSurvivant=null;
			}
		}
	}




	/**donne la prochaine case dans la direction donnée
	 * @param dir la direction
	 * @return la cellule voisine dans la direction donnée, null si aucune cellule*/
	private Case getNextCellule(Direction dir)
	{
		Case cell = null;
		if(dir!=null)
		{
			Point newPoint = dir.getNextPoint(point);
			if ((newPoint.x>=0 && newPoint.x < taille) && (newPoint.y>=0 && newPoint.y<taille))
			{
				Case[][] grille = map.getMap();
				cell = grille[newPoint.x][newPoint.y];
			}
		}
		return cell;
	}	


	/**
	 * 
	 * @return Image du robot dessin
	 */
	public  void ImageRobot() {
		BufferedImage image = null;

		try{
		    image = ImageIO.read(new File(imageLocation));
		} catch(Exception e){e.printStackTrace();}
		 
	}

	 

	public Point getPoint() {
		return point;
	}

	public void remplacerTache1(Tache tache2) {
		
		
	}

	public Tache getTache1() {
		return this.tache;
	}

	
	/**
     * Faire apparaitre une fenetre avec les informations relatives au robot
     */
     public void AfficherInfoRobot() {
        JFrame fenetre = new JFrame();
        JLabel title = new JLabel("Infos",SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel pan = new JPanel();
        pan.setLayout(new BoxLayout(pan,BoxLayout.Y_AXIS));
        pan.add(title);
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
        info.add(new JLabel("identifiant : " +this.id));
        info.add(new JLabel("Etat : " + (this.on ? "On" : "Off")));
        info.add(new JLabel("Coordonnees : " + this.point.x + ";" +this.point.y));
        JTextArea task = new JTextArea(3,20);
        task.setEditable(false);
        Tache tache_actuel = this.getTache();
        while ( !tache_actuel.estVide() ) {
            task.append(tache_actuel.prendrePremier().toString());
            task.append("\n");
        }
        JScrollPane scroll = new JScrollPane(task);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        info.add(scroll,Component.CENTER_ALIGNMENT);
        JPanel bout = new JPanel();
        JButton boutQuit = new JButton("Quit");
        boutQuit.addActionListener(e -> { 
            fenetre.dispose();
        });
        bout.add(boutQuit);
        bout.setAlignmentX(Component.CENTER_ALIGNMENT);
        pan.add(bout);
        fenetre.add(pan);
        fenetre.pack();
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     }
     
     public int getId() {
    	 return this.id;
     }


	

}