package champollion;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Salle {
    @Getter
    private int capacite;

    @Getter
    private String intitule;

    @Setter
    @Getter
    private List<Intervention> occupations;

    public Salle(String intitule, int capacite) {
        this.intitule = intitule;
        this.capacite = capacite;
        this.occupations = new ArrayList<Intervention>();
    }
}
