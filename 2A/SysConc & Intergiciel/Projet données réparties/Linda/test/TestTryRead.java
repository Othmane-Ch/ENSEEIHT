package linda.test;

import static org.junit.Assert.*;

import linda.server.LindaClient;
import org.junit.*;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;
import linda.server.*;


public class TestTryRead {
    //private final Linda linda = new CentralizedLinda();
    private final Linda linda =  new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple, motif1, motif2, vide;

    @BeforeClass
    public static void Initialisation() {

        motif1 = new Tuple(Integer.class, Character.class, String.class);
        motif2 = new Tuple(Integer.class, Integer.class, String.class);
        tuple = new Tuple(1, 'a', "ab");
        vide = new Tuple();
    }

    @Test
    public void TestVide() {
        Tuple retour = linda.tryRead(motif2);
        assertNull(retour);
    }

    @Test
    public void TestMotif() {
        linda.write(tuple);
        Tuple retour = linda.tryRead(motif1);
        assertEquals(tuple, retour);
        linda.take(tuple);
    }



    @Test
    public void TestMotifNonPresent() {
        linda.write(tuple);
        Tuple retour = linda.tryRead(motif2);
        assertNull(retour);
        linda.take(tuple);
    }

    @Test
    public void TestAucuneSupression() {
        linda.write(tuple);
        linda.tryRead(tuple);
        // Si non null alors on relit le même tuple donc on ne l'enlève pas
        assertNotNull(linda.tryRead(tuple));
        linda.take(tuple);
    }
}
