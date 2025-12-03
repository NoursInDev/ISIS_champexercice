package champollion;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnseignantTest {

    private Enseignant enseignant;
    private UE ueProg;
    private UE ueRes;
    private Salle sl;

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
        assertEquals(0, enseignant.heuresPrevues());
    }

    @Test
    public void testHeuresPrevuesApresAjoutSimple() {
        int before = enseignant.heuresPrevues();
        // add a simple enseignement (CM only)
        enseignant.ajouteEnseignement(ueProg, 10, 0, 0);
        int after = enseignant.heuresPrevues();
        assertTrue("heuresPrevues must increase after adding an enseignement", after > before);
    }

    @Test
    public void testHeuresPrevuesSommePlusieursUEs() {
        int before = enseignant.heuresPrevues();
        enseignant.ajouteEnseignement(ueProg, 8, 4, 2);
        int afterFirst = enseignant.heuresPrevues();
        enseignant.ajouteEnseignement(ueRes, 5, 3, 0);
        int afterSecond = enseignant.heuresPrevues();
        assertTrue("afterFirst should be greater than before", afterFirst > before);
        assertTrue("afterSecond should be greater than afterFirst", afterSecond > afterFirst);
    }

    /*
        == heuresPrevuesPourUE() ==
    */

    @Test
    public void testHeuresPrevuesPourUESansEnseignements() {
        assertEquals(0, enseignant.heuresPrevuesPourUE(ueProg));
        assertEquals(0, enseignant.heuresPrevuesPourUE(ueRes));
    }

    @Test
    public void testHeuresPrevuesPourUEApresAjoutSimpleCM() {
        enseignant.ajouteEnseignement(ueProg, 10, 0, 0);
        // CM -> *3/2 (integer arithmetic)
        int expected = 10 * 3 / 2;
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
        // other UE should remain 0
        assertEquals(0, enseignant.heuresPrevuesPourUE(ueRes));
    }

    @Test
    public void testHeuresPrevuesPourUEApresAjoutSimpleTD() {
        enseignant.ajouteEnseignement(ueProg, 0, 5, 0);
        int expected = 5; // TD -> 1:1
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
    }

    @Test
    public void testHeuresPrevuesPourUEApresAjoutSimpleTP() {
        enseignant.ajouteEnseignement(ueProg, 0, 0, 8);
        int expected = 8 * 3 / 4; // TP -> *3/4
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
    }

    @Test
    public void testHeuresPrevuesPourUESommePlusieursTypes() {
        // CM=3, TD=4, TP=4 -> CM: 3*3/2=4 (int), TD:4, TP:4*3/4=3 => total 11
        enseignant.ajouteEnseignement(ueProg, 3, 4, 4);
        int expected = 3 * 3 / 2 + 4 + 4 * 3 / 4;
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
    }

    @Test
    public void testHeuresPrevuesPourUESimpleMultipleUEs() {
        enseignant.ajouteEnseignement(ueProg, 6, 2, 2);
        enseignant.ajouteEnseignement(ueRes, 5, 3, 1);
        int expectedProg = 6 * 3 / 2 + 2 + 2 * 3 / 4;
        int expectedRes  = 5 * 3 / 2 + 3;
        assertEquals(expectedProg, enseignant.heuresPrevuesPourUE(ueProg));
        assertEquals(expectedRes, enseignant.heuresPrevuesPourUE(ueRes));
    }

    @Test
    public void testHeuresPrevuesPourUEAvecPlusieursAjoutsMemeUE() {
        // Adding same UE twice should sum (current implementation allows duplicates)
        enseignant.ajouteEnseignement(ueProg, 2, 1, 1);
        enseignant.ajouteEnseignement(ueProg, 3, 2, 2);
        int expected = (2 * 3 / 2 + 1) + (3 * 3 / 2 + 2 + 2 * 3 / 4);
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
    }

    @Test
    public void testHeuresPrevuesPourUERoundingBehavior() {
        // Check integer rounding behavior for CM: 1 CM -> 1*3/2 = 1 (integer division)
        enseignant.ajouteEnseignement(ueProg, 1, 0, 0);
        int expected = 3 / 2; // equals 1 with integer arithmetic
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
    }

    /*
        == ajouteEnseignement() ==
    */

    @Test
    public void testAjouteEnseignement_increasesTotalAndForUE() {
        int beforeTotal = enseignant.heuresPrevues();
        int beforeUE = enseignant.heuresPrevuesPourUE(ueProg);

        enseignant.ajouteEnseignement(ueProg, 4, 2, 4);

        int expectedForUE = 4 * 3 / 2 + 2 + 4 * 3 / 4;
        int afterUE = enseignant.heuresPrevuesPourUE(ueProg);
        int afterTotal = enseignant.heuresPrevues();

        assertEquals(expectedForUE, afterUE);
        assertTrue(afterTotal >= beforeTotal);
        assertTrue(afterUE > beforeUE);
    }

    @Test
    public void testAjouteEnseignement_zeroVolumes_noEffect() {
        int beforeTotal = enseignant.heuresPrevues();
        int beforeUE = enseignant.heuresPrevuesPourUE(ueProg);

        enseignant.ajouteEnseignement(ueProg, 0, 0, 0);

        assertEquals(beforeTotal, enseignant.heuresPrevues());
        assertEquals(beforeUE, enseignant.heuresPrevuesPourUE(ueProg));
    }

    @Test
    public void testAjouteEnseignement_duplicateAllowed_sumsVolumes() {
        enseignant.ajouteEnseignement(ueProg, 2, 1, 1);
        int first = enseignant.heuresPrevuesPourUE(ueProg);

        enseignant.ajouteEnseignement(ueProg, 3, 2, 2);
        int after = enseignant.heuresPrevuesPourUE(ueProg);

        int expectedFirst = 2 * 3 / 2 + 1;
        int expectedSecond = 3 * 3 / 2 + 2 + 2 * 3 / 4;
        int expectedTotal = expectedFirst + expectedSecond;

        assertEquals(expectedFirst, first);
        assertEquals(expectedTotal, after);
    }

    @Test
    public void testAjouteEnseignement_differentInstanceSameName_treatedAsDifferentUE() {
        enseignant.ajouteEnseignement(ueProg, 5, 0, 0);
        UE anotherProg = new UE("Programmation");

        assertEquals(0, enseignant.heuresPrevuesPourUE(anotherProg));

        int expected = 5 * 3 / 2;
        assertEquals(expected, enseignant.heuresPrevuesPourUE(ueProg));
    }

    @Test
    public void testAjouteEnseignement_multipleUEs_areIndependent() {
        enseignant.ajouteEnseignement(ueProg, 6, 2, 2);
        enseignant.ajouteEnseignement(ueRes, 5, 3, 1);

        int expectedProg = 6 * 3 / 2 + 2 + 2 * 3 / 4;
        int expectedRes  = 5 * 3 / 2 + 3;

        assertEquals(expectedProg, enseignant.heuresPrevuesPourUE(ueProg));
        assertEquals(expectedRes, enseignant.heuresPrevuesPourUE(ueRes));

        assertEquals(expectedProg + expectedRes, enseignant.heuresPrevues());
    }

    /*
        == enSousService() ==
    */

    @Test
    public void testEnSousService_zeroHours() {
        // no services added -> 0 hours
        assertTrue(!enseignant.enSousService());
        assertEquals(0, enseignant.heuresPrevues());
    }

    @Test
    public void testEnSousService_justBelowThreshold_191() {
        // CM=127 -> 127*3/2 = 190 ; TD=1 -> +1 => 191
        enseignant.ajouteEnseignement(ueProg, 127, 1, 0);
        int expected = 127 * 3 / 2 + 1;
        assertEquals(191, expected);
        assertEquals(expected, enseignant.heuresPrevues());
        assertTrue(!enseignant.enSousService());
    }

    @Test
    public void testEnSousService_exactThreshold_singleService_192() {
        // CM=128 -> 128*3/2 = 192
        enseignant.ajouteEnseignement(ueProg, 128, 0, 0);
        assertEquals(192, enseignant.heuresPrevues());
        assertTrue(!enseignant.enSousService());
    }

    @Test
    public void testEnSousService_exactThreshold_multipleServices_192() {
        // two services CM=64 each -> 64*3/2 = 96 ; total = 96 + 96 = 192
        enseignant.ajouteEnseignement(ueProg, 64, 0, 0);
        enseignant.ajouteEnseignement(ueRes, 64, 0, 0);
        assertEquals(192, enseignant.heuresPrevues());
        assertTrue(!enseignant.enSousService());
    }

    @Test
    public void testEnSousService_justAboveThreshold_193() {
        // CM=129 -> 129*3/2 = 193
        enseignant.ajouteEnseignement(ueProg, 129, 0, 0);
        assertEquals(193, enseignant.heuresPrevues());
        assertTrue(enseignant.enSousService());
    }

    /*

        == ajouteIntervention() ==

     */

    @SneakyThrows
    @Test
    public void testAjouteIntervention_valide_reduitReste() {
        enseignant.ajouteEnseignement(ueProg, 10, 0, 0);
        int before = enseignant.resteAPlanifier(ueProg, TypeIntervention.CM);
        Date debut = new SimpleDateFormat("yyyyMMddHH").parse("2025120104");

        Intervention iv = new Intervention(
            sl,
            debut,
            4,
            TypeIntervention.CM,
            ueProg,
            enseignant
        );

        enseignant.ajouteIntervention(iv);
        int after = enseignant.resteAPlanifier(ueProg, TypeIntervention.CM);
        assertEquals(before - 4, after);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteIntervention_nonIntervenant_lance() {
        enseignant.ajouteEnseignement(ueProg, 2, 0, 0);
        Enseignant autre = new Enseignant("Martin", "m.martin@univ.fr");
        Intervention iv = new Intervention(sl, new Date(), 1, TypeIntervention.CM, ueProg, autre);
        enseignant.ajouteIntervention(iv);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteIntervention_depasse_lance() {
        enseignant.ajouteEnseignement(ueProg, 0, 2, 0);
        Intervention iv = new Intervention(sl, new Date(), 3, TypeIntervention.TD, ueProg, enseignant);
        enseignant.ajouteIntervention(iv);
    }

    @Test
    public void testAjouteIntervention_zeroDuration_allowed() {
        enseignant.ajouteEnseignement(ueProg, 1, 1, 1);
        int before = enseignant.resteAPlanifier(ueProg, TypeIntervention.TP);
        Intervention iv = new Intervention(sl, new Date(), 0, TypeIntervention.TP, ueProg, enseignant);
        enseignant.ajouteIntervention(iv);
        assertEquals(before, enseignant.resteAPlanifier(ueProg, TypeIntervention.TP));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteIntervention_cumulatif_excede_afterMultiple() {
        enseignant.ajouteEnseignement(ueProg, 0, 5, 0);
        Intervention iv1 = new Intervention(sl, new Date(), 2, TypeIntervention.TD, ueProg, enseignant);
        Intervention iv2 = new Intervention(sl, new Date(), 3, TypeIntervention.TD, ueProg, enseignant);
        enseignant.ajouteIntervention(iv1);
        enseignant.ajouteIntervention(iv2);
        // now reste == 0, next intervention must throw
        Intervention iv3 = new Intervention(sl, new Date(), 1, TypeIntervention.TD, ueProg, enseignant);
        enseignant.ajouteIntervention(iv3);
    }

    /*

        == resteAPlanifier() ==

     */

    // already 100% coverage on Enseignant, not implemented

}
