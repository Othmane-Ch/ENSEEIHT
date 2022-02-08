package linda.test;

import linda.Linda;
import linda.Tuple;
import org.junit.*;
import linda.shm.CentralizedLinda;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import linda.server.*;


public class TestWrite {

    //private final Linda linda = new CentralizedLinda() ;
    private final Linda linda =  new LindaClient("//localhost:4000/LindaServer");
    private static Tuple tuple1, tuple2;
    private static Tuple motif1;

    @BeforeClass
    public static void Initialisation() {
        motif1 = new Tuple(Integer.class, Character.class, String.class);
        tuple1 = new Tuple(1, 'a', "ab");
        tuple2 = new Tuple(2, 'b', "ab");
    }


    @Test
    public void TestEcriture() {
        linda.write(tuple1);
        assertEquals(linda.tryRead(motif1), tuple1);
    }

    @Test
    public void TestEcriture2() {
        linda.write(tuple1);
        linda.write(tuple2);
        Collection<Tuple> retour = linda.readAll(motif1);
        assertEquals(retour.size(), 2);
    }


}
