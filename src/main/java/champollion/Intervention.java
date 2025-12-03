package champollion;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Intervention {

    @Getter
    private Salle lieu;

    @Getter
    private UE matiere;

    @Getter
    private Enseignant intervenant;

    @Getter
    @Setter
    private Date debut;

    @Getter
    @Setter
    private int duree;

    @Getter
    private boolean annulee;

    @Getter
    private TypeIntervention type;

    public Intervention(
        Salle lieu,
        Date debut,
        int duree,
        TypeIntervention type,
        UE matiere,
        Enseignant intervenant
        ) {
        this.lieu = lieu;
        this.debut = debut;
        this.duree = duree;
        this.type = type;
        this.matiere = matiere;
        this.intervenant = intervenant;
        this.annulee = false;
    }

    public void annuler() {
        this.annulee = true;
    }
}
