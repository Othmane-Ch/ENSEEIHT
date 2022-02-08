package projetLong;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Random;

import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* La map qui est connue par les robots et par l'utilisateur de l'application, elle est mise a jour en
*  temps reel par les robots lorsqu'il decouvre de nouvelles informations.
*/
public class MapRobot extends Observable implements Map {

	private Case[][] map;

    public MapRobot() {
    	this.map = new Case[DIM_X][DIM_Y];
        for ( int i = 0; i < DIM_X; i++ ) {
            for ( int j = 0; j < DIM_Y; j++ ) {
                this.map[i][j] = new Case(i,j);
            }
        }
    }
    
    public MapRobot(Map map) {
    	this.map = new Case[DIM_X][DIM_Y];
        for ( int i = 0; i < DIM_X; i++ ) {
            for ( int j = 0; j < DIM_Y; j++ ) {
                this.map[i][j] = map.obtenirInfoCase(i, j);
            }
        }
    }
    public Case[][] getMap() {
		return this.map;
	}
    
    public Case obtenirInfoCase(int x, int y) {
        return this.map[x][y];
    }

    public void miseAjourCase(Case cel) {
        this.map[cel.getX()][cel.getY()] = cel;
        //notifieChangement();
    }
    
    public void notifieChangement() {
    	setChanged();
		notifyObservers();
    }
    
    public void mapGenAleat() { // Uniquement utile pour les tests d'affichage.
    	for ( int i = 0; i < DIM_X; i++ ) {
            for ( int j = 0; j < DIM_Y; j++ ) {
            	int r = new Random().nextInt(5);
            	
                if (r == 0) {
                	this.map[i][j] = new Case(i,j, EtatCase.normal);
                }
                else if (r == 1) {
                    this.map[i][j] = new Case(i,j, EtatCase.feu);
                }
                else if (r == 2) {
                    this.map[i][j] = new Case(i,j, EtatCase.rescape);
                }
                else if (r == 3) {
                    this.map[i][j] = new Case(i,j, EtatCase.inaccessible);
                }
                else if (r == 4){
                	this.map[i][j] = new Case(i,j, EtatCase.inconnue);
                }
                
            }
        }
    	notifieChangement();
    }

    /* Renvoie un JPanel avec l'affichage de la carte connue jusqu'alors (surtout concernant les informations
    * importantes comme l'accessibilite ou la presence d'elements consideres comme importants)
    */
    public JPanel afficherMap() {
        JPanel VueMap = new JPanel();
        VueMap.setLayout(new GridLayout(DIM_X, DIM_Y));
        for ( int i = 0; i < DIM_X; i++ ) { 
            for ( Case cel : map[i] ) {
                //JLabel caz = new JLabel();
            	JPanelImg caz = new JPanelImg();
            	
            	try {
					if(cel.getAccesVoisin().getAccesEst() == 0) {
						caz.addImg(ImageIO.read(new File("imageProjet/murEst.png")));
					}
					if(cel.getAccesVoisin().getAccesOuest() == 0) {
						caz.addImg(ImageIO.read(new File("imageProjet/murOuest.png")));
					}
					if(cel.getAccesVoisin().getAccesNord() == 0) {
						caz.addImg(ImageIO.read(new File("imageProjet/murNord.png")));
					}
					if(cel.getAccesVoisin().getAccesSud() == 0) {
						caz.addImg(ImageIO.read(new File("imageProjet/murSud.png")));
					}
					if(cel.robotPresent()) {
						caz.addImg(ImageIO.read(new File("imageProjet/robotTete.png")));
					}
				} catch (IOException e) {
					System.out.print("Aucun image trouvé");
				}
                switch (cel.getEtat()) {
                    case normal : 
                        caz.setBackground(Color.WHITE);
                        break;
                    case feu :
                        caz.setBackground(Color.RED);
                        break;
                    case rescape :
                        caz.setBackground(Color.CYAN);
                        break;
                    case inaccessible :
                        caz.setBackground(Color.DARK_GRAY);
                        break;
                    case inconnue :
                        caz.setBackground(Color.BLACK);
                        break;
                    default :
                        caz.setBackground(Color.BLACK);
                }
                caz.setOpaque(true);
                VueMap.add(caz);
            } 
        } 
        VueMap.setSize(500, 500);
        return VueMap;
    }
}