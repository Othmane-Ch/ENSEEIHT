package linda.test;

import static org.junit.Assert.*;

import linda.server.LindaClient;
import org.junit.*;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

import java.util.Collection;

public class TestReadAll {
    //private final Linda linda =new CentralizedLinda();
    private final Linda linda =  new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple, tuple2, motif1, motif2;

    @BeforeClass
    public static void Initialisation() {
        motif1 = new Tuple(Integer.class, Character.class, String.class);
        motif2 = new Tuple(Integer.class, Integer.class, String.class);
        tuple = new Tuple(1, 'a', "ab");
        tuple2 = new Tuple(2, 'b', "cd");
    }

    @Test
    public void TestVide() {
        Collection<Tuple> retour = linda.readAll(motif1);
        assertTrue(retour.isEmpty());
    }

    @Test
    public void TestMotif() {
        linda.write(tuple);
        Collection<Tuple> retour = linda.readAll(motif1);
        assertTrue(retour.contains(tuple));
        linda.take(tuple);
    }

    @Test
    public void TestMotif2() {
        linda.write(tuple);
        linda.write(tuple2);
        Collection<Tuple> retour = linda.readAll(motif1);
        assertTrue(retour.contains(tuple) && retour.contains(tuple2));
        linda.take(tuple);
        linda.take(tuple2);
    }


    @Test
    public void TestMotifNonPresent() {
        linda.write(tuple);
        Collection<Tuple> retour = linda.readAll(motif2);
        Tuple retour1 = linda.tryTake(motif2);
        assertTrue(retour.isEmpty());
        assertNull(retour1);
        linda.take(tuple);
    }

    @Test
    public void TestAucuneSupression() {
        linda.write(tuple);
        linda.readAll(tuple);
        // Si non null alors on relit le même tuple donc on ne l'enlève pas
        assertNotNull(linda.tryRead(tuple));
        linda.take(tuple);
    }
}
