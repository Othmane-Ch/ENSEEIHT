package projetLong;

import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class TestVue {
	public static void main(String[] args) throws InterruptedException{
		
		MapRobot mapR = new MapRobot();
		//mapR.mapGenAleat();

		
		for(int i = 0 ; i < Map.DIM_X; i++) {
			for(int j = 0 ; j < Map.DIM_Y; j++) {
				Case cel;
				cel = mapR.obtenirInfoCase(i, j);
				cel.setAccesNord(0);
				
				mapR.miseAjourCase(cel);
			}
		}
		InterfaceUtilisateur inter = new InterfaceUtilisateur(mapR, 3);
		inter.setSize(1600, 900);
		inter.setVisible(true);
		inter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//modification aléatoire
		/*
		TimeUnit.SECONDS.sleep(1);
		mapR.mapGenAleat();
		TimeUnit.SECONDS.sleep(1);
		mapR.mapGenAleat();
		*/
		
	}
	
}
