import java.awt.Color;
import org.junit.*;
import static org.junit.Assert.*;

/**
  * Classe de test des exigences E8 et E11 pour la classe Cercle.
  * @author	Othmane Chaouchaou
  */
public class ComplementsCercleTest {

	// Les points du sujet
	private Point A, B, C;

	// Les cercles du sujet
	private Cercle C1, C2, C3, C4;

	@Before public void setUp() {
		// Construire les points
		A = new Point(7, 1);
		B = new Point(0, 4);
		C = new Point(-3, 2);

		// Construire les cercles
        C1 = new Cercle(A, 3);
        C2 = new Cercle(A, B);
        C3 = new Cercle(A, C, Color.cyan);
        C4 = Cercle.creerCercle(B, C);
	}
    @Test public void testerE11() {
        assert(C1.contient(A));
    }

    @Test public void testerE8() {
        assert(C1.getCouleur() != null);
        assert(C2.getCouleur() != null);
        assert(C3.getCouleur() != null);
        assert(C4.getCouleur() != null);
    }

}
