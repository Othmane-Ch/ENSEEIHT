package projetLong;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.ArrayList;

public class Menu {

	private boolean ok;
    private Map map;
    private int nb_robot;
    private MapRobot mapR;
    private boolean debut;
    private Logs log;
    private ArrayList<Robot> robots;

    public Menu(MapRobot mapRob, Logs log, ArrayList<Robot> robots) {
        //this.ok = false;  // pour une utilisation reelle, on doit attendre que la map soit chargee pour pouvoir lancer la simulation
        this.ok = true;
        this.mapR = mapRob;
        this.nb_robot = 4;
        this.debut = false;
        this.log = log;
        this.robots = robots;
    }

    public void AfficherMenu1() {
        JFrame fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());
        JButton boutMap = new JButton("Importer Map");
        boutMap.setFont(new Font("Arial", Font.PLAIN, 20));
        boutMap.addActionListener(e -> {
            // Ajouter ici l'action pour pouvoir enregistrer differentes map
        });
        JPanel bMap = new JPanel();
        bMap.add(boutMap);
        fenetre.add(bMap, BorderLayout.NORTH);
        JButton boutStart = new JButton("Lancer la simulation");
        boutStart.setFont(new Font("Arial", Font.PLAIN, 20));
        boutStart.addActionListener(e -> {
            fenetre.dispose();
            this.AfficherMenu2();
        });
        JPanel bStart = new JPanel();
        bStart.add(boutStart);
        fenetre.add(bStart,BorderLayout.CENTER);
        JButton boutQuit = new JButton("Quitter");
        boutQuit.setFont(new Font("Arial", Font.PLAIN, 20));
        boutQuit.addActionListener(e -> {
            fenetre.dispose();
        });
        JPanel bQuit = new JPanel();
        bQuit.add(boutQuit);
        fenetre.add(bQuit,BorderLayout.SOUTH);
        fenetre.setLocation(100,200);
        fenetre.pack();
        fenetre.setSize(500,300);
        fenetre.setVisible(true);
    }

    public void AfficherMenu2() {
        JFrame fenetre = new JFrame();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());
        JButton boutRetour = new JButton("Retour");
        boutRetour.setFont(new Font("Arial", Font.PLAIN, 20));
        boutRetour.addActionListener(e -> {
            fenetre.dispose();
            this.AfficherMenu1();
        });
        JSlider slider = new JSlider(1,20,4);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);  
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                nb_robot = slider.getValue();
            }
        });
        JButton boutOk = new JButton("OK");
        boutOk.setFont(new Font("Arial", Font.PLAIN, 20));
        boutOk.addActionListener(e -> {
            fenetre.dispose();
            // MapRobot mapR = new MapRobot(this.map);     // On doit convertir la map en map robot en tant normal mais ici on emploiera une map test
            InterfaceUtilisateur inter = new InterfaceUtilisateur(mapR, nb_robot);
            debut = true;
            inter.setLog(this.log);
            inter.setRobots(this.robots);
	    	inter.setSize(1600, 850);
		    inter.setVisible(true);
	    	inter.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
        JLabel title = new JLabel("Veuillez rentrer le nombre de robots :");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setAlignmentX(SwingConstants.CENTER);
        fenetre.add(title,BorderLayout.NORTH);
        JPanel choixRobot = new JPanel();
        choixRobot.add(slider);
        choixRobot.add(boutOk);
        fenetre.add(choixRobot, BorderLayout.CENTER);
        fenetre.add(boutRetour,BorderLayout.SOUTH);
        fenetre.setSize(500,300);
        fenetre.setLocation(100,200);
        fenetre.setVisible(true);
    }
    
    public int getNbRobot() {
    	return nb_robot;
    }
    public boolean debutEstLance() {
    	return debut;
    }


}
