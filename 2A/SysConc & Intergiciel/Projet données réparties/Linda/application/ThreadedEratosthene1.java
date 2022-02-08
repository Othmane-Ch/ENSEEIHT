package linda.application;

import java.util.Collection;

import linda.Tuple;
import linda.shm.CentralizedLinda;

public class ThreadedEratosthene1 implements Eratosthene {
	
	private int nThread;
	private int borne; 
	private CentralizedLinda linda;

  public ThreadedEratosthene1(CentralizedLinda l,int K) {
		this.borne = K;
		this.linda = l;
		this.nThread = 10;
	}


	public ThreadedEratosthene1(CentralizedLinda l,int K,int nThread) {
		this.borne = K;
		this.linda = l;
		this.nThread = nThread;
	}



	@Override
	public void remplirLinda() {
		for(int i=2; i<this.borne + 1;i++) {
			this.linda.write(new Tuple(i));
		}
	}


  @Override
	public CentralizedLinda supprimerNonPremier() {
		// je slice linda sur le nombre de thread qu'on a
		for(int k = 2; k < this.borne + 1;k += this.borne/this.nThread) {
		// je cree pour chaque slice un thread
				new Thread(  () -> {
						for(int i = 2; i < Math.sqrt(this.borne);i++) {
							if( this.linda.tryRead(new Tuple(i)) != null) {
								int premier = (Integer) this.linda.tryRead(new Tuple(i)).element();
								for(int j = 2*premier; j < this.borne + 1;j+= premier) {
									linda.tryTake(new Tuple(j));
								}
							}
						}
					}).start();

		}
		return this.linda;
	}

  public static void main(String args[]) {
		CentralizedLinda l = new CentralizedLinda();
		Eratosthene eratostheneTh = new ThreadedEratosthene1(l,Integer.parseInt(args[0]),Integer.parseInt(args[0]));
		eratostheneTh.remplirLinda();
		eratostheneTh.supprimerNonPremier();
	    //System.out.println(l);
	    Collection<Tuple> tuples = l.readAll(Tuple.valueOf("[ ?Integer ]"));
	    for (Tuple tuple : tuples) {
	                System.out.println(tuple.toString());
	    }

	}


}
