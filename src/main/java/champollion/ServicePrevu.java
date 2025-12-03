package champollion;

import lombok.Getter;

public class ServicePrevu {
    @Getter
    private Enseignant intervenant;

    @Getter
    private UE enseignement;

    @Getter
    private int volumeCM;

    @Getter
    private int volumeTD;

    @Getter
    private int volumeTP;

    public ServicePrevu(Enseignant intervenant, UE enseignement, int volumeCM, int volumeTD, int volumeTP) {
        this.intervenant = intervenant;
        this.enseignement = enseignement;
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
    }
}
