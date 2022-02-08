package linda.application;

import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;
import java.util.*;

public class SequentielleEratosthene implements Eratosthene {
	
	private int borne; 
	private CentralizedLinda linda;
	
	
	
	public SequentielleEratosthene(CentralizedLinda l,int K) {
		this.borne = K;
		this.linda = l;
	}

	@Override
	public void remplirLinda() {
		for(int i=2; i<=this.borne;i++) { 
			this.linda.write(new Tuple(i));
		}
	}

	@Override
	public CentralizedLinda supprimerNonPremier() {
		for(int i = 2; i < Math.sqrt(this.borne);i++) {
			if( this.linda.tryRead(new Tuple(i)) != null) {
				int premier = (Integer) this.linda.tryRead(new Tuple(i)).element();
				for(int j = 2*premier; j < this.borne + 1;j+= premier) {
					linda.tryTake(new Tuple(j));
				}
			}
		}
		return this.linda;
	}
	
	
	public static void main(String args[]) {
		CentralizedLinda l = new CentralizedLinda();
		Eratosthene eratostheneSq = new SequentielleEratosthene(l,Integer.parseInt(args[0]));
		eratostheneSq.remplirLinda();
		eratostheneSq.supprimerNonPremier();
    Collection<Tuple> tuples = l.readAll(Tuple.valueOf("[ ?Integer ]"));
    for (Tuple tuple : tuples) {
            System.out.print(tuple.toString()+";");
    }
    System.out.println("\n");
	}

}
