package linda.test;

import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

/**
 * Permet de calculer le temps nécessaire pour accéder à un tuple
 * Ainsi nous pouvons comparer le temps mis par une méthode séquentielle
 * et une méthode de parallélisation ou CentralizedLinda et LindaServer
 */
public class TestPerformance {


    public static void main (String[] args) {
        test(100);
        test(500);
        test(1000);
        test(5000);
        test(100000);
        test(500000);
    }

    public static void test(int nbTuple) {

        //Linda linda = new CentralizedLinda();
        Linda linda = new linda.server.LindaClient("//localhost:4000/LindaServer");

        Tuple tuple1 = new Tuple(1);
        Tuple tuple2 = new Tuple(2,3,'a');
        Tuple tuple3 = new Tuple(4,'b', "abc", "defgh");
        Tuple tuple4 = new Tuple("azerty", 'r', 5, 7, 'r', "aa", 123456);
        Tuple tuple5 = new Tuple(57,'r');

        long debEcriture = System.currentTimeMillis();
        // on recherche le tuple3
        for (int i = 0; i <nbTuple/2; i++) {
            linda.write(tuple1);
            linda.write(tuple2);
            linda.write(tuple4);
            linda.write(tuple5);
        }

        linda.write(tuple3);

        for (int i = 0; i <nbTuple/2; i++) {
            linda.write(tuple1);
            linda.write(tuple2);
            linda.write(tuple4);
            linda.write(tuple5);
        }
        long finEcriture = System.currentTimeMillis();

        long deb = System.currentTimeMillis();
        linda.read(tuple3);
        long fin = System.currentTimeMillis();

        long temps = fin - deb;
        long tempsEcriture = finEcriture - debEcriture;

        System.out.println("---- " + nbTuple + " ----");
        System.out.println("Ecriture : " + tempsEcriture + " ms");
        System.out.println("Trouver le tuple : " + temps + " ms");
        System.out.println(" ");


    }

}
