package projetLong;

import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

public class TestClass {

	
	public static void main(String[] args) throws InterruptedException {
	    Date d1 = new Date(11,30);
	    Info i1 = new InfoText(110,"Bonjour");
	    Logs logs = new Logs();
	    VueLogsFenetre fenetre = new VueLogsFenetre(logs);
	    logs.ajouter(i1);
	    fenetre.setSize(new Dimension(400,300));
	    fenetre.setLocation(400,200);
	    fenetre.setVisible(true);
	    TimeUnit.SECONDS.sleep(4);
	    logs.ajouter(i1);
	}
}
