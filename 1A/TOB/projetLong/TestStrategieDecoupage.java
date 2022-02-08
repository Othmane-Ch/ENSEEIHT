package projetLong;

import java.util.*;

public class TestStrategieDecoupage {
	public static void main(String[] args) {
		MapRobot map = new MapRobot();
		
		Case caseMur= new Case(0,10, EtatCase.inaccessible);
		for (int k = 0; k < 30; k++) {
			caseMur.setX(k);
			map.miseAjourCase(caseMur);
		}
		
		StrategieControler str = new StrategieControler();
		int[][] pointsDebutExploreRobots = str.analyseMapInitiale(map, 5, 5, 3);
		int[][] mapDistance = str.getMapDistance();
		for(int i = 0; i < Map.DIM_X; i++){
			System.out.print("|  ");
			for(int j = 0; j < Map.DIM_Y; j++) {
				System.out.print(mapDistance[i][j]);
				System.out.print("\t");
			}
			System.out.println("|");
		}
		for(int k = 0; k < pointsDebutExploreRobots.length; k++) {
			System.out.print("x pos : " + pointsDebutExploreRobots[k][0] + "\t");
			System.out.println("y pos : " + pointsDebutExploreRobots[k][1]);
		}
		
		str.creation_reattribution_mapExploration(map, 5, 5, 3, null);
		int[][] matriceExploration = str.getMatriceExploration();
		for(int i = 0; i < Map.DIM_X; i++){
			System.out.print("|  ");
			for(int j = 0; j < Map.DIM_Y; j++) {
				System.out.print(matriceExploration[i][j]);
				System.out.print("\t");
			}
			System.out.println("|");
		}
		
		
	}
	
	
}
