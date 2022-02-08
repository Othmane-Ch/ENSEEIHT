package projetLong;

import java.util.List;

import javax.swing.JFrame;

public class TestPathfinding {
	public static void main(String[] args) {
		MapRobot map = new MapRobot();
		
		Case caseMur= new Case(0,10, EtatCase.inaccessible);
		for (int k = 0; k < 5; k++) {
			caseMur.setX(k);
			map.miseAjourCase(caseMur);
		}
		for (int k = 0; k < 5; k++) {
			caseMur.setY(10 + k);
			map.miseAjourCase(caseMur);
		}
		
		StrategieControler str = new StrategieControler();
		
		List<Direction> cheminRapide = str.pathfinding(map, 0, 0, 1, 12);
		for(Direction elem : cheminRapide) {
			if(elem == Direction.NORD) {
				System.out.println("Nord");
			}
			else if(elem == Direction.SUD) {
				System.out.println("Sud");
			}
			else if(elem == Direction.OUEST) {
				System.out.println("Ouest");
			}
			else if(elem == Direction.EST) {
				System.out.println("EST");
			}else {
				System.out.println("erf !! ??");
			}
		}
	}
}
