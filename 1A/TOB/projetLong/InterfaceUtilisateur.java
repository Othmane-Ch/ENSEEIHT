package projetLong;
import java.util.ArrayList;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class InterfaceUtilisateur extends JFrame implements Observer{

	private JPanel panelGauche;
	private JPanel panelDroite;

	private ArrayList<Robot> ListRobot;		// la liste des robots en activites
	private int compteur_robot;				// incdice d'une boucle
	private Logs log;						// les logs actuels
	private int nb_robot;					// le nombre de robots totaux
	
	public InterfaceUtilisateur(MapRobot map, int nb) {
		super("Interface");
		this.nb_robot = nb;
		
		map.addObserver(this);

		this.setLayout(new GridLayout(1, 2));
		
		this.panelGauche = new JPanel();
		this.panelGauche.setLayout(new BorderLayout());
		
		this.panelDroite = new JPanel();
		this.panelDroite.setLayout(new BorderLayout());
		this.add(this.panelGauche);
		this.add(this.panelDroite);
		
		
		this.panelGauche.add(map.afficherMap(), BorderLayout.CENTER);
		
		JPanel panelGaucheBas = new JPanel();
		this.panelGauche.add(panelGaucheBas, BorderLayout.SOUTH);
		
		JButton boutonSwitchMap = new JButton("ChangerMap");
		boutonSwitchMap.setFont(new Font("Arial", Font.PLAIN, 20));
		//this.panelGauche.add(boutonSwitchMap, BorderLayout.SOUTH);
		panelGaucheBas.add(boutonSwitchMap);
		
		JButton logsButton = new JButton("Afficher les Logs");
		logsButton.setFont(new Font("Arial", Font.PLAIN, 20));
		logsButton.addActionListener(e -> {
	    	VueLogsFenetre fenetre = new VueLogsFenetre(this.log);
	    	fenetre.setSize(400, 400);
	    	fenetre.setLocation(400,200);
	    	fenetre.setVisible(true);
		});
		
		JPanel panelDroiteRobot = new JPanel();
		//panelDroiteRobot.setLayout(new GridLayout(4,1));
		panelDroiteRobot.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.panelDroite.add(panelDroiteRobot, BorderLayout.CENTER);
		
		for (compteur_robot = 1; compteur_robot <= nb_robot; compteur_robot++) {
			JButton robotButton = new JButton("Robot " + compteur_robot);
			robotButton.setFont(new Font("Arial", Font.PLAIN, 20));
			robotButton.addActionListener(e -> {
				ListRobot.get(compteur_robot).AfficherInfoRobot();
			});
			panelDroiteRobot.add(robotButton, gbc);
			gbc.gridx = 0;
			gbc.gridy = compteur_robot+1;
		}
		
		JPanel panelDroiteLog = new JPanel();
		this.panelDroite.add(panelDroiteLog, BorderLayout.SOUTH);
		panelDroiteLog.add(logsButton);
		
		
	}
	
	@Override
    public void update(Observable o, Object args) {
		//on retire les vieux trucs
		this.panelGauche.removeAll();
		

		//on met les nouveaux trucs
		if(o instanceof MapRobot) {
			MapRobot mapR = (MapRobot) o;
			
			this.panelGauche.add(mapR.afficherMap(), BorderLayout.CENTER);
			JPanel panelGaucheBas = new JPanel();
			this.panelGauche.add(panelGaucheBas, BorderLayout.SOUTH);
			
			JButton boutonSwitchMap = new JButton("ChangerMap");
			boutonSwitchMap.setFont(new Font("Arial", Font.PLAIN, 20));
			panelGaucheBas.add(boutonSwitchMap);
		}
		panelGauche.updateUI();
    }
	public void setLog(Logs log) {
		this.log = log;
	}
	public void setRobots(ArrayList<Robot> robots) {
		this.ListRobot = robots;
	}

	
}