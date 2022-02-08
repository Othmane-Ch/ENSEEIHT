package projetLong;
public interface Strategie {
	
	
	/**
	 * 
	 * @param controleur
	 * @param robot
	 */
	void controler(ControleurRobot controleur,Robot robot);
	
	void demanderChoix(ControleurRobot controleur,Robot robot);
	 
	void repartitionTache(Robot robot);

}
