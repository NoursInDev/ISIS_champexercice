package champollion;

import org.junit.Before;
import org.junit.Test;

public class EnseignantTest {

    private Enseignant enseignant;
    private UE ueProg;
    private UE ueRes;

    @Before
    public void setUp() {
        enseignant = new Enseignant("Dupont", "j.dupont@univ.fr");

        ueProg = new UE("Programmation");
        ueRes  = new UE("Réseaux");
    }

    /*
       == heuresPrevues() ==
    */

    @Test
    public void testHeuresPrevuesSansEnseignements() {
        org.junit.Assert.assertEquals(0, enseignant.heuresPrevues());
    }

    @Test
    public void testHeuresPrevuesApresAjoutSimple() {
        int before = enseignant.heuresPrevues();
        // add a simple enseignement (CM only)
        enseignant.ajouteEnseignement(ueProg, 10, 0, 0);
        int after = enseignant.heuresPrevues();
        org.junit.Assert.assertTrue("heuresPrevues must increase after adding an enseignement", after > before);
    }

    @Test
    public void testHeuresPrevuesSommePlusieursUEs() {
        int before = enseignant.heuresPrevues();
        enseignant.ajouteEnseignement(ueProg, 8, 4, 2);
        int afterFirst = enseignant.heuresPrevues();
        enseignant.ajouteEnseignement(ueRes, 5, 3, 0);
        int afterSecond = enseignant.heuresPrevues();
        org.junit.Assert.assertTrue("afterFirst should be greater than before", afterFirst > before);
        org.junit.Assert.assertTrue("afterSecond should be greater than afterFirst", afterSecond > afterFirst);
    }

    /*
        == heuresPrevuesPourUE() ==
    */

    /*
        == ajouteEnseignement() ==
    */

    /*
        == enSousService() ==
    */

}
