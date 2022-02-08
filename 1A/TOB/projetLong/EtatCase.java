package projetLong;

public enum EtatCase {
	normal, feu, rescape, inaccessible, inconnue;
	
	/**
	 * 
	 * @return vrai si la case est accessible par un robot
	 */
	public boolean estAccessible() {
		
		return (this == EtatCase.normal || this == EtatCase.rescape);
	}
}
