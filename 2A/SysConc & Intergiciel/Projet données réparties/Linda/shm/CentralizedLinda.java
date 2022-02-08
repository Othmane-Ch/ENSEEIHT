package linda.shm;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {

    private final int CAPACITE = 10;

    protected Lock moniteur = new ReentrantLock();
    private ArrayList<Tuple> listeTuple = new ArrayList<>();

    private List<Reveil> listeAttenteTake = new ArrayList<Reveil>();
    private List<Reveil> listeAttenteRead = new ArrayList<Reveil>();

    private Map<Tuple, BlockingQueue<Callback>> attente_readCallbacks = new HashMap<>();
    private Map<Tuple, BlockingQueue<Callback>> attente_takeCallbacks = new HashMap<>();

    public CentralizedLinda() {
    }

    /**
     * Ajouter un tuple à la liste de tuple
     *
     * @param tuple à ajouter
     */
    @Override
    public void write(Tuple tuple) {
        try {
            if (tuple == null) {
                new NullPointerException();
            } else {
                // Exclusion
                moniteur.lock();

                listeTuple.add(tuple.deepclone());

                for (Reveil elementCourantRead : listeAttenteRead) {
                    if (tuple.matches(elementCourantRead.getMotif())) {
                        Condition conditionReveil = elementCourantRead.getCondition();
                        conditionReveil.signal();
                    }
                }

                for (Reveil elementCourantTake : listeAttenteTake) {
                    if (tuple.matches(elementCourantTake.getMotif())) {
                        Condition conditionReveil = elementCourantTake.getCondition();
                        conditionReveil.signal();
                        break;
                    }
                }

                moniteur.unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Extrait de l’espace partagé un tuple correspondant au motif précisé en
     * paramètre en étant bloquant
     *
     * @param template : motif d'un tuple que l'on recherche
     * @return : retourne le tuple
     */
    @Override
    public Tuple take(Tuple template) {

        Tuple tupleRetourne = null;

            if (template == null) {
               throw new NullPointerException();
            } else {
                moniteur.lock();

                while ((tupleRetourne = tryTake(template)) == null) {
                //if (tupleRetourne == null) {
                    Condition conditionAttente = moniteur.newCondition();
                    Reveil reveil = new Reveil(template, conditionAttente);
                    listeAttenteTake.add(reveil);

                    try {
                        conditionAttente.await();
                    } catch (InterruptedException e ) {
                        e.printStackTrace();
                    }

                }
                moniteur.unlock();

            }

        return tupleRetourne;

    }

    /**
     * Recherche (sans l’extraire) dans l’espace partagé un tuple correspondant
     * au motif fourni en paramètre en étant bloquant.
     *
     * @param template : motif d'un tuple que l'on recherche
     * @return : retourne le tuple
     */
    @Override
    public Tuple read(Tuple template) {

        Tuple tupleRetourne = null;

        if (template == null) {
            throw new NullPointerException();
        } else {
            moniteur.lock();

            while ((tupleRetourne = tryRead(template)) == null) {
                //if (tupleRetourne == null) {
                Condition conditionAttente = moniteur.newCondition();
                Reveil reveil = new Reveil(template, conditionAttente);
                listeAttenteTake.add(reveil);

                try {
                    conditionAttente.await();
                } catch (InterruptedException e ) {
                    e.printStackTrace();
                }

            }
            moniteur.unlock();

        }

        return tupleRetourne;
    }

    /**
     * Extrait de l’espace partagé un tuple correspondant au motif précisé en
     * paramètre sans être bloquant
     * @param template : motif d'un tuple que l'on recherche
     * @return : retourne le tuple si on en trouve un correspondant sinon null
     */
    @Override
    public Tuple tryTake(Tuple template) {



        if (template == null) {
            throw new NullPointerException();
        } else {
            moniteur.lock();
            Tuple copie = null;
            copie = tryRead(template);
            // Vérification que le motif recherché est présent puis suppression
            // Sinon on ne fait rien
            if (copie != null) {
                listeTuple.remove(copie);
            }

            moniteur.unlock();
            return copie;
        }

    }

    /**
     * Recherche (sans l’extraire) dans l’espace partagé un tuple correspondant
     * au motif fourni en paramètre sans être bloquant.
     *
     * @param template : motif d'un tuple que l'on recherche
     * @return : retourne le tuple si on en trouve un correspondant sinon null
     */
    @Override
    public Tuple tryRead(Tuple template) {

        if (template == null) {
           throw new NullPointerException();
        } else {
            Tuple copie = null;
            moniteur.lock();

            for (Tuple tupleL : listeTuple) {
                if (tupleL.matches(template)) {
                    copie = tupleL.deepclone();
                }
            }
            moniteur.unlock();
            return copie;
        }


    }

    /**
     * Cherche tous les tuples correspondant au motif et les retire de la liste, non
     * bloquante.
     *
     * @param template : motif recherché
     * @return : renvoient la collection de tous les tuples qui correspondent au
     *         motif.
     *         Si aucun tuple ne correspond, ces opérations renvoient une collection
     *         vide
     */
    @Override
    public Collection<Tuple> takeAll(Tuple template) {

        Collection<Tuple> tupleListe_Correspond = new ArrayList<>();

        if (template == null) {
            System.out.println("Motif vide");
        } else {
            Tuple retourRead = tryRead(template);
            while (retourRead != null) {
                tupleListe_Correspond.add(retourRead);
                listeTuple.remove((retourRead));
                retourRead = tryTake(template);
            }

        }

        return tupleListe_Correspond;
    }

    /**
     * Cherche tous les tuples correspondant au motif, non bloquante.
     *
     * @param template : motif recherché
     * @return : renvoient la collection de tous les tuples qui correspondent au
     *         motif.
     *         Si aucun tuple ne correspond, ces opérations renvoient une collection
     *         vide
     */
    @Override
    public Collection<Tuple> readAll(Tuple template) {

        Collection<Tuple> tupleListe_Correspond = new ArrayList<>();

        if (template == null) {
            System.out.println("Tuple à rechercher est vide.");
        } else {

            moniteur.lock();

            for (Tuple tupleL : listeTuple) {
                if (tupleL.matches(template)) {
                    tupleListe_Correspond.add(tupleL);
                }
            }

            moniteur.unlock();
        }

        return tupleListe_Correspond;

    }

    @Override
    public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
        if (template == null) {
            throw new NullPointerException();
        } else {
            new Thread(() -> {
                Tuple t = null;
                if (timing == eventTiming.IMMEDIATE && mode == eventMode.READ) {
                    t = read(template);
                    callback.call(t);

                } else if ( timing == eventTiming.IMMEDIATE && mode == eventMode.TAKE ) {
                    t = take(template);
                    callback.call(t);

                } else if (mode == eventMode.READ && timing == eventTiming.FUTURE) {
                    attente_readCallbacks.putIfAbsent(template, new ArrayBlockingQueue<>(CAPACITE, true));
                    attente_readCallbacks.get(template).add(callback);

                } else if (mode == eventMode.TAKE && timing == eventTiming.FUTURE) {
                    attente_takeCallbacks.putIfAbsent(template, new ArrayBlockingQueue<>(CAPACITE, true));
                    attente_takeCallbacks.get(template).add(callback);
                }

            }
            ).start();
        }
    }

    @Override
    public void debug(String prefix) {

    }

    // TO BE COMPLETED

}
	/**
	private final int CAPACITE = 10;
	
    protected Lock moniteur = new ReentrantLock();

	private ArrayList<Tuple> listeTuple = new ArrayList<>(); 
	
	private Map<Tuple, BlockingQueue<Condition>> attente_read  = new HashMap<>();
	private Map<Tuple, BlockingQueue<Condition>> attente_take  = new HashMap<>();

	private Map<Tuple, BlockingQueue<Callback>> attente_readCallbacks = new HashMap<>();
	private Map<Tuple, BlockingQueue<Callback>> attente_takeCallbacks = new HashMap<>();
	
    public CentralizedLinda() {
    }

	@Override
	public void write(Tuple t) {
		try {
			if (t == null) {
				throw new NullPointerException();
			} else {
				moniteur.lock();
				listeTuple.add(t.deepclone());
				moniteur.unlock();
			}
		} catch(NullPointerException e) {}
		
       		
	}

	@Override
	public Tuple take(Tuple template) {
		Tuple tuple_retour;
		Condition c = moniteur.newCondition();
		if (template == null) {
			throw new NullPointerException();
		}
		else {
			moniteur.lock();
			while ((tuple_retour  = tryTake(template)) == null) {
				attente_take.putIfAbsent(template, new ArrayBlockingQueue<>(CAPACITE, true));
				attente_take.get(template).add(c);
				try {
					c.await();
				} catch (InterruptedException e) {}	
			}
			moniteur.unlock();
			return tuple_retour;
		}
		
	}

	@Override
	public Tuple read(Tuple template) {
		Tuple tuple_retour;
		Condition c = moniteur.newCondition();
		if (template == null) {
			throw new NullPointerException();
		}
		else {
			moniteur.lock();
			while ((tuple_retour  = tryRead(template)) == null) {
				attente_read.putIfAbsent(template, new ArrayBlockingQueue<>(CAPACITE, true));
				attente_read.get(template).add(c);
				try {
					c.await();
				} catch (InterruptedException e) {}	
			}
			moniteur.unlock();
			return tuple_retour;
		}
	}

	@Override
	public Tuple tryTake(Tuple template) {
		Tuple copie = null;
		if(template == null) {
			throw new NullPointerException();
		} else {
			moniteur.lock();
			copie = tryRead(template);
			if (copie != null) {
				listeTuple.remove(copie);	
			}
			moniteur.unlock();
		}
		return copie;
	}

	@Override
	public Tuple tryRead(Tuple template) {
		Tuple copie = null;
		if (template == null) {
			throw new NullPointerException();
		} else {
			moniteur.lock();
			for(Tuple t : listeTuple) {
				if(t.matches(template)) {
					copie = t.deepclone();
				}
			}
			moniteur.unlock();
		}
		return copie;
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) {
		Collection<Tuple> tupleListe_Correspond = new ArrayList<>();
		Tuple tuple = tryTake(template);
		while (tuple != null) {
			tupleListe_Correspond.add(tuple);
			tuple = tryTake(template);
		}
		return tupleListe_Correspond;
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) {
		Collection<Tuple> tupleListe_Correspond = new ArrayList<>();
		
		if(template == null){
    		throw new NullPointerException("Tuple à rechercher est vide.");
    	}
		else {
			moniteur.lock();
			for(Tuple t : listeTuple) {
				if(t.matches(template)) {
					tupleListe_Correspond.add(t);
				}
			}
			moniteur.unlock();
		}
		return tupleListe_Correspond;
	}

	 /** Registers a callback which will be called when a tuple matching the template appears.
     * If the mode is Take, the found tuple is removed from the tuplespace.
     * The callback is fired once. It may re-register itself if necessary.
     * If timing is immediate, the callback may immediately fire if a matching tuple is already present; if timing is future, current tuples are ignored.
     * Beware: a callback should never block as the calling context may be the one of the writer (see also {@link AsynchronousCallback} class).
     * Callbacks are not ordered: if more than one may be fired, the chosen one is arbitrary.
     * Beware of loop with a READ/IMMEDIATE re-registering callback !
     *
     * @param mode read or take mode.
     * @param timing (potentially) immediate or only future firing.
     * @param template the filtering template.
     * @param callback the callback to call if a matching tuple appears.
     
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		if (template == null) {
			throw new NullPointerException();
		} else {
			new Thread (() -> {
				Tuple t = null;
				if (timing == eventTiming.IMMEDIATE) {
					if(mode == eventMode.READ) {
						t = tryRead(template);
						callback.call(t);
					} else if(mode == eventMode.TAKE) {
						t = take(template);
						callback.call(t);
					}
				} else if(timing == eventTiming.FUTURE) {
					if(mode == eventMode.READ) {
						attente_readCallbacks.putIfAbsent(template, new ArrayBlockingQueue<>(CAPACITE, true));
						attente_readCallbacks.get(template).add(callback);
					} else if(mode == eventMode.TAKE)  {
						attente_takeCallbacks.putIfAbsent(template, new ArrayBlockingQueue<>(CAPACITE, true));
						attente_takeCallbacks.get(template).add(callback);
					}
				}
			}).start();
			
		}
		
		
	}

	@Override
	public void debug(String prefix) {
		for(Tuple t : listeTuple){
			System.out.println(prefix + t.toString());
		}
		
	}



}
**/