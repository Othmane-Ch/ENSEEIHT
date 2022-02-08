package linda.test;

import static org.junit.Assert.*;
import org.junit.*;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;
import linda.server.*;


import java.util.Collection;

public class TestTakeAll {
    //private final Linda linda = new CentralizedLinda() ;
    private final Linda linda = new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple, tuple2;
    private static Tuple motif1, motif2;

    @BeforeClass
    public static void Initialisation() {

        motif1 = new Tuple(Integer.class, Character.class, String.class);
        motif2 = new Tuple(Integer.class, Integer.class, String.class);
        tuple = new Tuple(1, 'a', "ab");
        tuple2 = new Tuple(2, 'b', "cd");
    }

    @Test
    public void TestVide() {
        Collection<Tuple> retour = linda.takeAll(motif1);
        assertTrue(retour.isEmpty());
    }

    @Test
    public void TestMotif() {
        linda.write(tuple);
        Collection<Tuple> retour = linda.takeAll(motif1);
        assertTrue(retour.contains(tuple));
    }

    @Test
    public void TestMotif2() {
        linda.write(tuple);
        linda.write(tuple2);
        Collection<Tuple> retour = linda.takeAll(motif1);
        assertTrue(retour.contains(tuple) && retour.contains(tuple2));
        assertNull(linda.tryRead(tuple));
    }

    @Test
    public void TestMotifNonPresent() {
        linda.write(tuple);
        Collection<Tuple> retour = linda.takeAll(motif2);
        assertTrue(retour.isEmpty());
        linda.take(tuple);
    }

    @Test
    public void TestSupression() {
        linda.write(tuple);
        linda.takeAll(tuple);
        // Si null alors on a retiré le tuple avec takeAll
        assertNull(linda.tryRead(tuple));
    }
}
