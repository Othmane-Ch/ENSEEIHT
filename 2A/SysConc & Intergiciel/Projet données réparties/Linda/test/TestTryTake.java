package linda.test;

import static org.junit.Assert.*;
import org.junit.*;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;
import linda.server.*;


public class TestTryTake {
    //private final Linda linda = new CentralizedLinda();
    private final Linda linda =  new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple, tupleVide;
    private static Tuple motif1, motif2;

    @BeforeClass
    public static void Initialisation() {
        motif1 = new Tuple(Integer.class, Character.class, String.class);
        motif2 = new Tuple(Integer.class, Integer.class, String.class);
        tuple = new Tuple(1, 'a', "ab");
        tupleVide = new Tuple();
    }


    @Test
    public void TestMotif() {
    	linda.write(tuple);
        Tuple retour = linda.tryTake(motif1);
        assertEquals(retour, tuple);
    }

    @Test
    public void TestVide() {
    	linda.write(tupleVide);
        Tuple retour1 = linda.tryTake(motif2);
        assertNull(retour1);
        linda.take(tupleVide);
    }

    @Test
    public void TestMotifNonPresent() {
        linda.write(tuple);
        Tuple retour2 = linda.tryTake(motif2);
        //linda.tryTake(motif1);
        assertNull(retour2);
        linda.take(tuple);
    }

    @Test
    public void TestSupression() {
        linda.write(tuple);
        linda.tryTake(tuple);
        // Si null alors on a retiré le tuple avec tryTake
        assertNull(linda.tryRead(tuple));
    }
}
