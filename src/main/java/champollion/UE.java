package champollion;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class UE {
    @Getter
    private final String intitule;

    @Getter
    private final List<ServicePrevu> services = new ArrayList<>();


    public UE(String intitule) {
        this(intitule, 0, 0, 0);
    }

    public UE(String intitule, int heuresCM, int heuresTD, int heuresTP) {
        this.intitule = intitule;
        this.heuresCM = heuresCM;
        this.heuresTD = heuresTD;
        this.heuresTP = heuresTP;
    }

    @Getter
    @Setter
    private int heuresCM;

    @Getter
    @Setter
    private int heuresTD;

    @Getter
    @Setter
    private int heuresTP;
}
