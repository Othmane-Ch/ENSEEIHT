package linda.test;

import static org.junit.Assert.*;
import org.junit.*;
import linda.Linda;
import linda.Tuple;

import linda.shm.CentralizedLinda;
import linda.server.*;

public class TestRead {
    //private final Linda linda = new CentralizedLinda() ;
    private final Linda linda = new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple, tuple2, motif1, motif2, vide;

    @BeforeClass
    public static void Initialisation() {
        motif1 = new Tuple(Integer.class, Character.class, String.class);
        motif2 = new Tuple(Integer.class, Integer.class, String.class);
        tuple = new Tuple(1, 'a', "ab");
        tuple2 = new Tuple(1, 2, "ab");
        vide = new Tuple();
    }


    @Test
    public void TestMotif() {
        linda.write(tuple);
        Tuple retour = linda.read(motif1);
        System.out.println(tuple);
        System.out.println(retour);
        assertEquals(tuple, retour);
        linda.take(tuple);
    }

    /**
     * On recherche un tuple vide car take est bloquant
     * provoquant la non fin du test.
     */
    @Test
    public void TestVide() {
        linda.write(vide);
        assertEquals(linda.read(vide), vide);
        linda.take(vide);
    }

    /**
     * Le motif n'étant pas présent, le test ne se terminera jamais car
     * read est bloquant. Donc on crée un Threat qui va exécuter read
     * pendant que l'autre s'endort pendant 5 secondes. Si read fonctionne
     * correctement à la fin de ces 5 secondes, le Threat doit toujours être en
     * cours. On vérifie donc l'état du Threat.
     * @throws InterruptedException
     */
    @Test
    public void TestMotifNonPresent() throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                //linda.write(tuple);
                linda.read(motif2);
            }
        };
        thread.start();
        Thread.sleep(1000);
        assertEquals(Thread.State.RUNNABLE, thread.getState());
        thread.interrupt();
    }

    @Test
    public void TestMotifNonPresentDeb() throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                linda.write(tuple);
                linda.read(motif2);
            }
        };
        thread.start();
        linda.write(tuple2);
        Thread.sleep(1000);
        assertEquals(thread.getState(),Thread.State.TERMINATED);
        linda.take(tuple);
        linda.take(tuple2);
        thread.interrupt();
    }

    @Test
    public void TestSupression() {
        linda.write(tuple);
        linda.read(tuple);
        assertNotNull(linda.tryRead(tuple));
        linda.take(tuple);
    }
}
