package projetLong;

import java.util.*;
import java.awt.Point;

public class StrategieControler implements Strategie {

	
	
	/**nom de la strategie	 */
	String str; 
	
	/** Choix de l'utilisateur */
	private ChoixUser choix;
   
	private int[][] mapDistance;
	
	private int[][] matriceExploration;

	private Logs logs;
	
	public StrategieControler() {
		this.str = "controler";
	}
	
	/**
	 * 
	 * @param controleur
	 * @param robot
	 */
	public void controler(ControleurRobot controleur,Robot robot) {
		String prop = "";
		assert(controleur == null && robot == null);
		while (true) {
			switch (prop) {
			case "etat" :
				controleur.obtenirEtat(robot);
			    break;
			case "commander" :
				controleur.commander(robot);
                break;
			case "commande" :

                String commande="";
				 
				//controleur.ajouterCommande(robot, commande);
                break;
			}
			
		}
	}
	

	public void demanderChoix(ControleurRobot controleur,Robot robot) {
		boolean clic=false;
		assert(controleur == null && robot == null);
		System.out.println("L'etat du robot :" + controleur.obtenirEtat(robot));		
		System.out.println(" Que doit faire le robot ?");
		System.out.println(" Veuillez choisir parmi les propositions suivantes :");// affichage du menu propositions
		while(!clic) {
			
		 switch (this.choix) {
		 case Continuer :
			 clic=true;
			 robot.setEtatRobot(EtatRobot.CHERCHER);
			 robot.avancer();
		     break;
		 case  Attendre :
			 clic=true;
			 robot.setEtatRobot(EtatRobot.PISTER);
             break;
		 case  DemanderAssistance :
			 clic=true;
			 System.out.println(" Vous avez la main ");
			 controleur.commander(robot);
             break;
         default :
        	    clic=false;
            	System.out.println("Tu n'as pas cliqué, tu dois faire un choix");
		 }	
		
		}
	}
	
	public void repartitionTache(Robot robot) {
		
	}
	
	public void miseAJourMapRobot(MapRobot mapRobot, MapReel mapReel, int p_x, int p_y,  int nbRobot, int idRobot, Logs log) {
		boolean aucuneModifImportante = true;
		for(int i = -1; i <= 1; i ++) {
			for(int j = -1; j <= 1; j++) {
				if(p_x + i >= 0 && p_x + i < Map.DIM_X && p_y + j >= 0 && p_y + j < Map.DIM_Y) {
					Case caseReel = mapReel.obtenirInfoCase(p_x + i, p_y + j);
					Case caseRobot = mapRobot.obtenirInfoCase(p_x + i, p_y + j);
					
					aucuneModifImportante = aucuneModifImportante && (caseReel.getEtat().estAccessible() == caseRobot.getEtat().estAccessible());
					AccesVoisin avReel = caseReel.getAccesVoisin();
					AccesVoisin avRobot = caseRobot.getAccesVoisin();
					aucuneModifImportante = aucuneModifImportante && (avReel.getAccesEst() > 0 == avRobot.getAccesEst() > 0);
					aucuneModifImportante = aucuneModifImportante && (avReel.getAccesOuest() > 0 == avRobot.getAccesOuest() > 0);
					aucuneModifImportante = aucuneModifImportante && (avReel.getAccesNord() > 0 == avRobot.getAccesNord() > 0);
					aucuneModifImportante = aucuneModifImportante && (avReel.getAccesSud() > 0 == avRobot.getAccesSud() > 0);
					
					
					InfoText infTxt;
					if(caseReel.getEtat() != caseRobot.getEtat()) {
						switch(caseReel.getEtat()) {
						case feu:
							infTxt = new InfoText(idRobot, "Un feu a été découvert à la position : " + (p_x + i) + "; " + (p_y + j));
							break;
						case inaccessible:
							infTxt = new InfoText(idRobot, "Impossible d'accéder à la position : " + (p_x + i) + "; " + (p_y + j));
							break;
						case rescape:
							infTxt = new InfoText(idRobot, "Un individu a été trouvé à la position : " + (p_x + i) + "; " + (p_y + j));
							break;
						default:
							infTxt = null;
						}
						if(infTxt != null) {
							log.ajouter(infTxt);
						}
					}
					
					mapRobot.miseAjourCase(caseReel);
<<<<<<< .mine
					if(this.matriceExploration[p_x + i][p_y + j] > 0) {
						this.matriceExploration[p_x + i][p_y + j] = 0;
					}
||||||| .r235564
=======
					if (caseReel != caseRobot) {
							InfoText info;
						switch (caseReel.getEtat) {
						case feu :
							info = new InfoText(nbRobot,"Un incendie est declare en : " + p_x + i"," + p_y + j + " !",timer);
							break;
						case rescape :
							info = new InfoText(nbRobot,"Un individus a ete trouve en : " + p_x + i"," + p_y + j + " !",timer);	
							break;
						case inaccessible :
							info = new InfoText(nbRobot,"Cet endroit est desormais inaccessible : " + p_x + i"," + p_y + j + " !",timer);
							break;
						default :
							break;
						}
						Logs.ajouter(info);
					}
					
>>>>>>> .r235896
				}
			}
		}
		
		if(!aucuneModifImportante) {
			double[][] sommesCoord = new double[nbRobot][2];
			int[] nbCase_exploRob = new int[nbRobot];
			for(int i = 0; i < Map.DIM_X; i ++) {
				for(int j = 0; j < Map.DIM_Y; j ++) {
					if(matriceExploration[i][j] >= 1) {
						nbCase_exploRob[matriceExploration[i][j] - 1] ++;
						sommesCoord[matriceExploration[i][j] - 1][0] += i;
						sommesCoord[matriceExploration[i][j] - 1][1] += j;
					}
				}
			}
			int[][] positionDepartZone = new int[nbRobot][2];
			for(int k = 0; k < nbRobot; k ++) {
				positionDepartZone[k][0] = (int) (sommesCoord[k][0]/nbCase_exploRob[k]);
				positionDepartZone[k][1] = (int) (sommesCoord[k][1]/nbCase_exploRob[k]);
				int taille = 1;
				while(!mapRobot.obtenirInfoCase(positionDepartZone[k][0], positionDepartZone[k][1]).getEtat().estAccessible()) {
					for(int i = -taille; i <= taille; i ++) {
						for(int j = -taille; j <= taille; j ++) {
							int x = positionDepartZone[k][0];
							int y = positionDepartZone[k][1];
							if (x + i >= 0 && x + i < Map.DIM_X && y + j >= 0 && y + j < Map.DIM_Y) {
								if (mapRobot.obtenirInfoCase(x+i, y+j).getEtat().estAccessible()) {
									positionDepartZone[k][0] = x + i;
									positionDepartZone[k][1] = x + j;
								}
							}
						}
					}
					taille ++;
				}
				//donc en sortant de là, les positions de departDeZone_k devrait être a priori accessible
			}
			
			creation_reattribution_mapExploration(mapRobot, 0, 0,nbRobot, positionDepartZone);
		}
		
		
	}
	
	//Cette matrice va être appeler par creation_reattribution_mapExploration si le dernier paramètre est non null
	private void miseAJourMatriceExploration(MapRobot mapRobot) {
		for(int i = 0; i < Map.DIM_X; i ++) {
			for(int j = 0 ; j < Map.DIM_Y; j ++) {
				if(matriceExploration[i][j] != -1 && !mapRobot.obtenirInfoCase(i, j).getEtat().estAccessible()) {
					matriceExploration[i][j] = -1;
				}
				if(matriceExploration[i][j] == -1 && mapRobot.obtenirInfoCase(i, j).getEtat().estAccessible()) {
					//En effet une différence ici ne peut s'expliquer que si on a obtenus une nouvelle info sur la mapReel
					//Et ceci implique forcément que cette case a été explorer.
					matriceExploration[i][j] = 0;
				}
			}
		}
	}
	/**
	 * Cette fonction sert à initialiser la matriceD'exploration au début alors que les robots ne sont pas placé,
	 * mais elle est également utile lorsque on considère déjà la position des robots et qu'on cherche simplement a
	 * redécouper la zone d'exploration en utilisant des barycentres d'exploration, souvent égale à la position des robots
	 * @param map
	 * @param posBase_X
	 * @param posBase_Y
	 * @param nbRobot
	 * @param barycentresZonesExplor : ce paramètre doit être nul si on veut initaliser la matrice d'exploration,
	 * Sinon il doit prendre la position des points de bases d'explorations, souvent égale à la position des robots.
	 */
	public void creation_reattribution_mapExploration(MapRobot map, int posBase_X, int posBase_Y, int nbRobot, int[][] barycentresZonesExplor) {
		int[][] pointsDebutExploreRobots;
		if(barycentresZonesExplor == null) {
			pointsDebutExploreRobots = analyseMapInitiale(map, posBase_X, posBase_Y, nbRobot);
			//à partir de ce moment là, mapDistance est déjà initialisé par analyseMap.
			
			//on part de la matrice distance de base qui a déjà répertorié les cases non accessibles
			matriceExploration = mapDistance.clone();
			
			for(int i = 0; i < Map.DIM_X; i++) {
				for(int j = 0; j < Map.DIM_Y; j++) {
					if(matriceExploration[i][j] != 0) {
						matriceExploration[i][j] = 0;
					}else {
						matriceExploration[i][j] = -1;
					}
				}
			}
		}else {
			pointsDebutExploreRobots = barycentresZonesExplor;
			//on remet simplement à jour la matriceD'exploration
			miseAJourMatriceExploration(map);
			
			//on remet la matrice a zero pour les valeurs où c'est explorable
			for(int i = 0; i < Map.DIM_X; i++) {
				for(int j = 0; j < Map.DIM_Y; j++) {
					if(matriceExploration[i][j] != -1) {
						matriceExploration[i][j] = 0;
					}
				}
			}
		}
			
		//maintenant matriceExploration prend la valeur -1 quand une zone est inaccessible, on va modifier pour que à la place
		// il prenne comme valeur un entier non nul k qui indique quel robot va se diriger vers cette endroit. 
		//Et enfin 0 dans les autres cas.
		
		
		
		boolean finDecoupage = false;
		List<List<int[]>> ptsAccessibleZone = new ArrayList<List<int[]>>();
		// ptsAccessibleZone.get(k) est une liste des points accessibles depuis la zone robot k (qui ne sont pas dans la zone);
		//Ces points sont triés, le premier élément est le plus favorable à rajouter à la liste de l'exploration.
		
		for(int k = 0; k < nbRobot; k ++) {
			ptsAccessibleZone.add(0, new ArrayList<int[]>());
			ptsAccessibleZone.get(0).add(pointsDebutExploreRobots[k]);
		}
		
		//pour l'instant 0 est la valeur de base, après la boucle suivante, il n'y aura plus de 0, chaque valeur d'une case
		//correspondra au numéro du robot qui va l'explorer. Et plus tard 0 sera la valeur associé au case déjà explorer.
		while (!finDecoupage) {
			int compteurBlocage = 0;
			for(int k = 0; k < nbRobot; k ++) {
				boolean notDone = true;
				
				while(notDone && (ptsAccessibleZone.get(k).size() > 0)) {
					int[] nouvPoint = ptsAccessibleZone.get(k).get(0);
					ptsAccessibleZone.get(k).remove(0);
					if(matriceExploration[nouvPoint[0]][nouvPoint[1]] == 0) {
						notDone = false;
						matriceExploration[nouvPoint[0]][nouvPoint[1]] = k+1;
						
						//on cherche les voisins accessibles du point au dessus.
						List<int[]> voisinsAccessible = casesProcheAccessible(nouvPoint[0], nouvPoint[1], map);
						for(int[] voisin : voisinsAccessible) {
							ptsAccessibleZone.get(k).add(voisin);
						}
					}
				}
				if(notDone) {
					//on a pas réussit à add des trucs à la zone k
					compteurBlocage ++;
				}
			}//fin boucle for
			
			//(compteurBlocage == nbRobot) => aucune expansion de zone n'a été possible, l'algo est donc finit
			finDecoupage = (compteurBlocage == nbRobot); 
		}
	}

	
	/**
	 * fonction qui rééquilibre les zones d'explorations, elle n'augmente que d'une case max la zone d'exploration d'un robot
	 * (mais elle peut être appeler plusieurs fois de suite)
	 * La matrice d'exploration doit déjà avoir été initialiser pour que cette algorithme fonctionne.
	 * @param nbRobot
	 */
	public void equilibre_matriceExploration(int nbRobot, MapRobot mapRobot) {
		int[] nbCaseNum = new int[nbRobot]; // nombre de case attribuer pour les différents robots
		
		double[][] sommesCoordRbCase = new double[nbRobot][2]; //pour chaque robot prend la somme des abscisses et 
		//des ordonnées des cases qu'il doit explorer, permet de calculer le barycentre

		int nbMoyCaseExplorable = 0;
		for(int i = 0; i < Map.DIM_X; i ++) {
			for(int j = 0; j < Map.DIM_Y; j ++) {
				if(matriceExploration[i][j] > 0) {
					nbCaseNum[matriceExploration[i][j] - 1] ++;
					nbMoyCaseExplorable ++;
					sommesCoordRbCase[matriceExploration[i][j] - 1][0] += i;
					sommesCoordRbCase[matriceExploration[i][j] - 1][1] += j;
				}
			}
		}
		nbMoyCaseExplorable = nbMoyCaseExplorable / nbRobot;
		for(int k = 0; k < nbRobot; k++) {
			List<int[]> casesPrenable = new ArrayList<int[]>(); //cases dont l'exploration peut changer de responsabilité
			for(int i = 0; i < Map.DIM_X; i ++) {
				for(int j = 0; j < Map.DIM_Y; j++) {
					if(matriceExploration[i][j] == k+1) {
						List<int[]> casesPotentiellementPrenable = casesProcheAccessible(i, j, mapRobot);

						//On retire les éléments qui ne peuvent pas être récupérer.
						int cpt = 0;
						while(cpt < casesPotentiellementPrenable.size()) {
							int p_X = casesPotentiellementPrenable.get(cpt)[0];
							int p_Y = casesPotentiellementPrenable.get(cpt)[1];
							if(matriceExploration[p_X][p_Y] == k+1 || matriceExploration[p_X][p_Y] <= 0) {
								casesPotentiellementPrenable.remove(cpt);
							}else {
								cpt ++;
							}
						}
						casesPrenable.addAll(casesPotentiellementPrenable);
					}
				}
			}// à la fin de cette boucle for, la liste des casesPrenable est complète

			int[] robotIsPrenable = new int[nbRobot]; //nb de case qu'il peut prendre au robot
			for(int cpt = 0; cpt < nbRobot; cpt ++) {
				robotIsPrenable[cpt] = 0;
			}
			for(int[] voisinsPrenable : casesPrenable) {
				int numRob = matriceExploration[voisinsPrenable[0]][voisinsPrenable[1]];
				robotIsPrenable[numRob -1] ++;
			}
			
			int robotMaxCase = -1;
			int nbCasesRobTarget = 0;
			for(int m = 0; m < nbRobot; m ++) {
				if(robotIsPrenable[m] > 0 && nbCaseNum[m] > nbCasesRobTarget && nbCaseNum[m] - 1 > nbCaseNum[k]) {
					robotMaxCase = m;
					nbCasesRobTarget = nbCaseNum[m];
				}
			}
			
			//Si on doit recup une case à un robot alors robotMaxCase est différent de -1
			if(robotMaxCase != -1) {
				//on cherche la meilleure case à récupérer
				//pour cela on se débarasse des cases prenable qui ne sont pas de ce type.
				int cpt = 0;
				while(cpt < casesPrenable.size()) {
					int p_X = casesPrenable.get(cpt)[0];
					int p_Y = casesPrenable.get(cpt)[1];
					if(matriceExploration[p_X][p_Y] != robotMaxCase + 1) {
						casesPrenable.remove(cpt);
					}else {
						cpt ++;
					}
				}
				
				//on calcul le barycentre
				double bary_x = sommesCoordRbCase[k][0] / nbCaseNum[k];
				double bary_y = sommesCoordRbCase[k][1] / nbCaseNum[k];
				
				//on fait le choix de prendre la case qui minimise l'éparpillement de l'exploration du robot qui recup la case.
				double minDistBary = Math.sqrt(Math.pow(casesPrenable.get(0)[0] - bary_x, 2) + Math.pow(casesPrenable.get(0)[1] - bary_y, 2));
				int[] posMinDist = new int[2];
				for(int[] voisin : casesPrenable) {
					double distBary = Math.sqrt(Math.pow(voisin[0] - bary_x, 2) + Math.pow(voisin[1] - bary_y, 2));
					if(distBary < minDistBary) {
						posMinDist[0] = voisin[0];
						posMinDist[1] = voisin[1];
						minDistBary = distBary;
					}
				}
				matriceExploration[posMinDist[0]][posMinDist[1]] = k+1;
				
			}
		}
	}
	
	public int[][] getMatriceExploration(){
		return this.matriceExploration;
	}
	
	/**
	 * Cette algorithme ne fonctionne que si matriceExploration est à jour !
	 * @param p_X
	 * @param p_Y
	 * @return
	 */
	private List<int[]> casesProcheAccessible(int p_X, int p_Y, MapRobot mapRobot){
		List<int[]> voisinCase = new ArrayList<int[]>();
		if(p_X-1 >= 0 && matriceExploration[p_X-1][p_Y] != -1 && mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesOuest() > 0) {
			voisinCase.add(new int[] {p_X - 1, p_Y});
		}
		if(p_X+1 < Map.DIM_X && matriceExploration[p_X+1][p_Y] != -1 && mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesEst() > 0) {
			voisinCase.add(new int[] {p_X + 1, p_Y});
		}
		if(p_Y - 1 >= 0 && matriceExploration[p_X][p_Y-1] != -1 && mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesNord() > 0) {
			voisinCase.add(new int[] {p_X, p_Y - 1});
		}
		if(p_Y + 1 < Map.DIM_Y && matriceExploration[p_X][p_Y+1] != -1 && mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesSud() > 0) {
			voisinCase.add(new int[] {p_X, p_Y + 1});
		}
		
		return voisinCase;
	}
	
	//Cette procédure n'est appelé que par la fonction analyseRobot
	private void miseAZeroMapDistance() {
		int maxX = Map.DIM_X;
		int maxY = Map.DIM_Y;
		this.mapDistance = new int[maxX][maxY];
		for (int i = 0; i < maxX; i++ ) {
			for (int j = 0; j < maxY; j++ ) {
				this.mapDistance[i][j] = 0;
			}
		}
	}
	
	/**
	 * Cette algorithme analyse la mapRobot, elle en déduit les lieux qui sont connectés entre eux 
	 * et connecté avec un bord de la map. Si 2 cases sont atteignables depuis un bord mais pas depuis
	 * le même alors la valeur dans mapExplorer sera différent.
	 * Une valeur égale à 0 veut dire que la case n'est pas accessible.
	 * Cette procédure devra être appelé lorsque nous obtenons de nouvelle infos sur la mapRobot
	 * @param mapRobot
	 */
	public int[][] analyseMapInitiale(MapRobot mapRobot, int posBase_X, int posBase_Y, int nbRobot) {
		miseAZeroMapDistance();
		int valeur = 1;
		int[][] pointsDebutExploreRobots = new int[nbRobot][2];
		
		pointsDebutExploreRobots[0][0] = posBase_X;
		pointsDebutExploreRobots[0][1] = posBase_Y;
		this.mapDistance[posBase_X][posBase_Y] = -1;
		//on suppose que la case rpz par posBase_X et posBase_Y est libre et non bloquante.
		List<int[]> pointsNonTraites = new ArrayList<int[]>();
		pointsNonTraites.add(new int[] {posBase_X, posBase_Y});
		
		
		for (int compteur = 1; compteur <= nbRobot; compteur ++) {
			do { //cette boucle permet d'éviter un stack overflow dû à un algorithme récursif trop profond
				List<int[]> pointsNonTraitesNew = new ArrayList<int[]>();
				for(int[] point : pointsNonTraites) {
					int pX = point[0];
					int pY = point[1];
					if(mapDistance[pX][pY] == -1) {
						valeur = 1;
					}else {
						valeur = mapDistance[pX][pY] + 1;
					}
					pointsNonTraitesNew.addAll(majMapDistance(mapRobot, pX, pY, valeur, 10));
				}
				pointsNonTraites = pointsNonTraitesNew;
				
			}while(!pointsNonTraites.isEmpty());
			
			if(compteur < nbRobot) {
				int[] posLoin = caseLoinMapDistance();
				pointsNonTraites.add(new int[] {posLoin[0], posLoin[1]});
				this.mapDistance[posLoin[0]][posLoin[1]] = -1;
				pointsDebutExploreRobots[compteur][0] = posLoin[0];
				pointsDebutExploreRobots[compteur][1] = posLoin[1];
			}
			
		}
		return pointsDebutExploreRobots;
		
	}
	
	private int[] caseLoinMapDistance(){
		int maxDist;
		int[] pos_max = new int[] {0, 0};
		maxDist = mapDistance[pos_max[0]][pos_max[1]];
		for(int i = 0; i < Map.DIM_X; i++) {
			for(int j = 0; j < Map.DIM_Y; j++) {
				if(maxDist < mapDistance[i][j]) {
					pos_max[0] = i;
					pos_max[1] = j;
					maxDist = mapDistance[i][j];
				}
			}
		}
		return pos_max;
	}
	
	/**
	 * procedure qui sera appelé de manière récursive et qui permet de réaliser la procédure analyseMap;
	 * Elle prend une case en entré et enregistre toutes les cases non encore enregistrer dans mapExplorable
	 * qui sont relié à une certaine case
	 * @param mapRobot
	 * @param pos_X
	 * @param pos_Y
	 * @param valeur
	 */
	private List<int[]> majMapDistance(MapRobot mapRobot, int pos_X, int pos_Y, int valeur, int maxIt) {
		List<int[]> pointsNonTraites = new ArrayList<int[]>();
		if(maxIt > 0) {
			List<int[]> pointsOuest = null;
			List<int[]> pointsEst = null;
			List<int[]> pointsNord = null;
			List<int[]> pointsSud = null;
			if(pos_X - 1 >= 0 && (this.mapDistance[pos_X - 1][pos_Y] == 0 || this.mapDistance[pos_X - 1][pos_Y] > valeur) ) {
				if(mapRobot.obtenirInfoCase(pos_X - 1, pos_Y).getEtat().estAccessible() && mapRobot.obtenirInfoCase(pos_X, pos_Y).getAccesVoisin().getAccesOuest() > 0) {
					this.mapDistance[pos_X - 1][pos_Y] = valeur;
					pointsOuest = majMapDistance(mapRobot, pos_X - 1, pos_Y, valeur + 1, maxIt-1);
				}
				
			}
			if(pos_X + 1 < Map.DIM_X && (this.mapDistance[pos_X + 1][pos_Y] == 0 || this.mapDistance[pos_X + 1][pos_Y] > valeur) ) {
				if(mapRobot.obtenirInfoCase(pos_X + 1, pos_Y).getEtat().estAccessible() && mapRobot.obtenirInfoCase(pos_X, pos_Y).getAccesVoisin().getAccesEst() > 0) {
					this.mapDistance[pos_X + 1][pos_Y] = valeur;
					pointsEst = majMapDistance(mapRobot, pos_X + 1, pos_Y, valeur + 1, maxIt-1);
				}
			}
			if(pos_Y - 1 >= 0 && (this.mapDistance[pos_X][pos_Y - 1] == 0 || this.mapDistance[pos_X][pos_Y - 1] > valeur) ) {
				if(mapRobot.obtenirInfoCase(pos_X, pos_Y - 1).getEtat().estAccessible() && mapRobot.obtenirInfoCase(pos_X, pos_Y).getAccesVoisin().getAccesNord() > 0) {
					this.mapDistance[pos_X][pos_Y - 1] = valeur;
					pointsNord = majMapDistance(mapRobot, pos_X, pos_Y - 1, valeur + 1, maxIt-1);
				}
				
			}
			if(pos_Y + 1 < Map.DIM_Y && (this.mapDistance[pos_X][pos_Y + 1] == 0 || this.mapDistance[pos_X][pos_Y + 1] > valeur) ) {
				if(mapRobot.obtenirInfoCase(pos_X, pos_Y + 1).getEtat().estAccessible()  && mapRobot.obtenirInfoCase(pos_X, pos_Y).getAccesVoisin().getAccesSud() > 0) {
					this.mapDistance[pos_X][pos_Y + 1] = valeur;
					pointsSud = majMapDistance(mapRobot, pos_X, pos_Y + 1, valeur + 1, maxIt-1);
				}
				
			}
			if(pointsOuest != null) {
				pointsNonTraites.addAll(pointsOuest);
			}
			if(pointsEst != null) {
				pointsNonTraites.addAll(pointsEst);
			}
			if(pointsNord != null) {
				pointsNonTraites.addAll(pointsNord);
			}
			if(pointsSud != null) {
				pointsNonTraites.addAll(pointsSud);
			}
		}else {
			pointsNonTraites.add(new int[] {pos_X, pos_Y});
		}
		
		return pointsNonTraites;
	}
	
	public int[][] getMapDistance(){
		return this.mapDistance;
	}
	
	/**
	 * 
	 * @param mapRobot
	 * @param pos_X : position X initiale
	 * @param pos_Y : position X initiale
	 * @param target_X : coordonnée X vers laquelle on souhaite se rendre
	 * @param target_Y : coordonnée Y vers laquelle on souhaite se rendre
	 * @return : suite de direction que l'on doit suivre
	 */
	public List<Commande> pathfinding(MapRobot mapRobot, int pos_X, int pos_Y, int target_X, int target_Y){
		List<Commande> commandes = new ArrayList<Commande>();
		List<Direction> chemin = new ArrayList<Direction>();
		List<Noeud> openList = new ArrayList<Noeud>();
		List<Noeud> closeList = new ArrayList<Noeud>();
		Noeud noeudInit = new Noeud(pos_X, pos_Y, 0, Math.abs(target_X - pos_X) + Math.abs(target_Y - pos_Y));
		openList.add(noeudInit);
		boolean cheminTrouve = false;
		int nbIter = 0;
		while(!cheminTrouve && openList.size() > 0) {
			nbIter ++;
			Noeud ndTarget = openList.get(0);
			openList.remove(0);
			closeList.add(ndTarget);
			
			Noeud ndNord = null;
			Noeud ndSud = null;
			Noeud ndOuest = null;
			Noeud ndEst = null;
			for(int cpt = 0; cpt < 4; cpt ++) {
				
				int p_X = ndTarget.getX();
				int p_Y = ndTarget.getY();
				boolean estAccessible = false;;
				switch(cpt) {
					case 0:
						//cas Nord
						estAccessible = mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesNord() > 0;
						p_Y = p_Y - 1;
						
						break;
					case 1:
						//cas Sud
						estAccessible = mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesSud() > 0;
						p_Y = p_Y + 1;
						break;
					case 2:
						//cas Ouest
						estAccessible = mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesOuest() > 0;
						p_X = p_X - 1;
						break;
					case 3:
						//cas Est
						estAccessible = mapRobot.obtenirInfoCase(p_X, p_Y).getAccesVoisin().getAccesEst() > 0;
						p_X = p_X + 1;
						break;
				}

				
				if(p_X >= 0 && p_X < Map.DIM_X && p_Y >= 0 && p_Y < Map.DIM_Y) {
					if(mapRobot.obtenirInfoCase(p_X, p_Y).getEtat().estAccessible() && estAccessible) {
						int distanceDebut = ndTarget.getDistInit() + 1;
						int distanceFin = Math.abs(target_X - p_X) + Math.abs(target_Y - p_Y);
						
						//on regarde si le noeud est déjà dans closeList
						boolean estDansClose = false;
						int indexPresent_close = -1; //s'il est déjà dans close, cette argument indique quel est son index dans la liste
						for (int k = 0; k < closeList.size(); k++) {
							Noeud n_k = closeList.get(k);
							if(p_X == n_k.getX() && p_Y == n_k.getY()) {
								indexPresent_close = k;
								estDansClose = true;
							}
						}
						if(estDansClose) {
							//on verifier qu'on ne puisse pas améliorer le noeud.
							if(distanceDebut < closeList.get(indexPresent_close).getDistInit()) {
								closeList.get(indexPresent_close).setDistInit(distanceDebut);
							}
						}
						else {
							//on regarde si le noeud est déjà dans openList
							boolean estDansOpen = false;
							int indexPresent_open = -1; //s'il est déjà dans open, cette argument indique quel est son index dans la liste
							for (int k = 0; k < openList.size(); k++) {
								Noeud n_k = openList.get(k);
								if(p_X == n_k.getX() && p_Y == n_k.getY()) {
									indexPresent_open = k;
									estDansOpen = true;
								}
								
							}
							if(estDansOpen) {
								//on verifier qu'on ne puisse pas améliorer le noeud.
								if(distanceDebut < openList.get(indexPresent_open).getDistInit()) {
									openList.get(indexPresent_open).setDistInit(distanceDebut);
								}
							}
							else {
								//on construit l'un des points proches. Ce point sera plus tard ajouter à openList
								switch(cpt) {
									case 0:
										//cas Nord
										ndNord = new Noeud(p_X, p_Y, distanceDebut, distanceFin);
										break;
									case 1:
										//cas Sud
										ndSud = new Noeud(p_X, p_Y, distanceDebut, distanceFin);
										break;
									case 2:
										//cas Ouest
										ndOuest = new Noeud(p_X, p_Y, distanceDebut, distanceFin);
										break;
									case 3:
										//cas Est
										ndEst = new Noeud(p_X, p_Y, distanceDebut, distanceFin);
										break;
								}
								
							}//fin cas !estDansOpen 
							
						}//fin cas !estDansClose
						
					}
				}
			}// fin de la boucle for, après cela les noeuds proches qui devaient être construit ont été construit.
			
			if(ndNord != null) {
				openList = majOpenList(ndNord, openList);
			}
			if(ndSud != null) {
				openList = majOpenList(ndSud, openList);
			}
			if(ndOuest != null) {
				openList = majOpenList(ndOuest, openList);
			}
			if(ndEst != null) {
				openList = majOpenList(ndEst, openList);
			}
			
			if(openList.size() > 0) {
				cheminTrouve = (openList.get(0).getX() == target_X && openList.get(0).getY() == target_Y);
			}
			
		}
		
		if(cheminTrouve) {
			//on va retracer le chemin en le remontant
			Noeud noeudActuel = openList.get(0);
			int pt_X = noeudActuel.getX();
			int pt_Y = noeudActuel.getY();
			while(pt_X != pos_X || pt_Y != pos_Y) {
				boolean prochainTrouve = false;

				int i = 0;
				while(i < closeList.size() && !prochainTrouve) {
					Noeud nd = closeList.get(i);
					int distance = Math.abs(pt_X - nd.getX()) + Math.abs(pt_Y - nd.getY());
					if (distance == 1) {
						if(nd.getDistInit() < noeudActuel.getDistInit()) {
							switch(pt_X - nd.getX()) {
							case 1:
								//ouest
								if(mapRobot.obtenirInfoCase(pt_X, pt_Y).getAccesVoisin().getAccesOuest() > 0) {
									noeudActuel = nd;
									prochainTrouve = true;
								}
								break;
							case -1:
								//est
								if(mapRobot.obtenirInfoCase(pt_X, pt_Y).getAccesVoisin().getAccesEst() > 0) {
									noeudActuel = nd;
									prochainTrouve = true;
								}
								break;
							default:
								if(pt_Y - nd.getY() > 0) {
									if(mapRobot.obtenirInfoCase(pt_X, pt_Y).getAccesVoisin().getAccesNord() > 0) {
										noeudActuel = nd;
										prochainTrouve = true;
									}
								}else {
									if(mapRobot.obtenirInfoCase(pt_X, pt_Y).getAccesVoisin().getAccesSud() > 0) {
										noeudActuel = nd;
										prochainTrouve = true;
									}
								}
								break;
								
							}
							/*
							noeudActuel = nd;
							prochainTrouve = true;*/
						}
					}
					i++;
				}
				//en arriviant ici, prochainTrouve a forcément été vrai si l'algorithme a bien fonctionné.
				Direction dir;
				switch(pt_X - noeudActuel.getX()) {
				case 0:
					if(pt_Y - noeudActuel.getY() > 0) {
						dir = Direction.SUD;
					}else {
						dir = Direction.NORD;
					}
					break;
				case 1:
					dir = Direction.EST;
					break;
				case -1:
					dir = Direction.OUEST;
					break;
				default :
					//ce cas ne devrait pas arriver
					System.out.println("Erreur dans le pathfindind : direction correct non detecté");
					dir = Direction.OUEST;
				}
				chemin.add(0,dir);
				pt_X = noeudActuel.getX();
				pt_Y = noeudActuel.getY();
				
			}
		}else {
			System.out.println("Aucun chemin pathfinding trouvé");
		}
		
		//on traduit les directions en commandes.
		for(Direction dir : chemin) {
			switch (dir) {
			case NORD :
				commandes.add(Commande.avancerNord);
				break;
			case SUD :
				commandes.add(Commande.avancerSud);
				break;
			case EST :
				commandes.add(Commande.avancerEst);
				break;
			case OUEST :
				commandes.add(Commande.avancerOuest);
				break;
			default:
				System.out.println("Direction incomprise");
				commandes.add(Commande.attendre);
				break;
			}
		}
		return commandes;
	}
	
	/**
	 * uniquement utile et utilisable par le pathfinding
	 * @param noeud
	 * @param openList
	 * @return
	 */
	private List<Noeud> majOpenList(Noeud noeud, List<Noeud> openList){
		int index = 0;
		while(index < openList.size() && noeud.getHeuristique() > openList.get(index).getHeuristique()) {
			index += 1;
		}
		openList.add(index, noeud);
		return openList;
	}
	


	
}
