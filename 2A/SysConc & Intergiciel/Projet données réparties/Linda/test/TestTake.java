package linda.test;

import static org.junit.Assert.*;
import org.junit.*;
import linda.Linda;
import linda.Tuple;
import linda.server.LindaClient;
import linda.shm.CentralizedLinda;

public class TestTake {
    //private final Linda linda = new CentralizedLinda();
    private final Linda linda = new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple, tuple2, tuple3;
    private static Tuple motif1, motif2, motif3;
    private static Tuple vide;

    @BeforeClass
    public static void Initialisation() {
        motif1 = new Tuple(Integer.class, Character.class, String.class);
        motif2 = new Tuple(Integer.class, Integer.class, String.class);
        motif3 = new Tuple(Character.class, Character.class, Character.class);
        tuple = new Tuple(1, 'a', "ab");
        tuple2 = new Tuple(1, 2, "ab");
        tuple3 = new Tuple('a', 'b', 'c');
        vide = new Tuple();
    }


    @Test
    public void TestMotif() {
        linda.write(tuple);
        Tuple retour = linda.take(motif1);
        assertEquals(tuple, retour);
    }

    /**
     * On recherche un tuple vide car take est bloquant
     * provoquant la non fin du test.
     */
    @Test
    public void TestVide() {
        linda.write(vide);
        assertEquals(linda.take(vide), vide);
        linda.tryTake(vide);
    }

    /**
     * Le motif n'étant pas présent, le test ne se terminera jamais car
     * take est bloquant. Donc on crée un Threat qui va exécuter take
     * pendant que l'autre s'endort pendant 5 secondes. Si take fonctionne
     * correctement à la fin de ces 5 secondes, le Threat doit être endormi.
     * On vérifie donc l'état du Threat.
     * @throws InterruptedException
     */
    @Test
    public void TestMotifNonPresent() throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                linda.write(tuple);
                Tuple retour = linda.take(motif2);
            }
        };
        thread.start();
        Thread.sleep(1000);
        assertEquals(Thread.State.RUNNABLE, thread.getState());
        linda.take(tuple);
        thread.interrupt();
    }

    @Test
    public void TestMotifNonPresentDeb() throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                linda.write(tuple);
                Tuple retour = linda.take(motif2);
            }
        };
        thread.start();
        Thread.sleep(1000);
        linda.tryTake(tuple);
        assertEquals(Thread.State.RUNNABLE, thread.getState());
        thread.interrupt();
    }

    @Test
    public void TestSupression() {
        linda.write(tuple);
        linda.take(tuple);
        linda.write(tuple2);
        linda.take(motif2);
        linda.write(tuple3);        
        linda.take(motif3);
        // Si null alors on a retiré le tuple avec tryTake
        assertNull(linda.tryRead(tuple));
        assertNull(linda.tryRead(tuple2));
        assertNull(linda.tryRead(tuple3));
    }
}
