package linda.test;

import linda.application.*;
import linda.Linda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class PerformanceErastosthene {

    public static void main (String[] args) {

        Seq(100);
        Seq(1000);
        Seq(10000);
        Par(100);
        Par(1000);
        Par(10000);
    }

    public static void Seq(int nbLimite) {

        CentralizedLinda l = new CentralizedLinda();
        Eratosthene eratostheneSq = new SequentielleEratosthene(l,nbLimite);
        eratostheneSq.remplirLinda();
        long deb = System.currentTimeMillis();
        eratostheneSq.supprimerNonPremier();
        long fin = System.currentTimeMillis();

        long temps = fin - deb;

        System.out.println("Sequentiel " + nbLimite + " : " + temps + " ms");
    }

    public static void Par(int nbLimite) {

        CentralizedLinda l = new CentralizedLinda();
        Eratosthene eratostheneSq = new ThreadedEratosthene1(l,nbLimite);
        eratostheneSq.remplirLinda();
        long deb = System.currentTimeMillis();
        eratostheneSq.supprimerNonPremier();
        long fin = System.currentTimeMillis();

        long temps = fin - deb;

        System.out.println("Parallélisation " + nbLimite + " : " + temps + " ms");
    }


}
