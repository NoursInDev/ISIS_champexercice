package champollion;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterventionTest {
    @Test
    public void annuler_isIdempotent() {
        Intervention iv = new Intervention(
            new Salle("1", 10),
            null,
            2,
            TypeIntervention.CM,
            null,
            null
        );

        iv.annuler();
        assertTrue(iv.isAnnulee());
        iv.annuler();
        assertTrue(iv.isAnnulee());
    }
}
