package champollion;

import java.util.ArrayList;
import java.util.List;

/**
 * Un enseignant est caractérisé par les informations suivantes : son nom, son adresse email, et son service prévu,
 * et son emploi du temps.
 */
public class Enseignant extends Personne {

    private final List<ServicePrevu> services = new ArrayList<>();

    private final List<Intervention> interventionsPlanifiees = new ArrayList<>();

    public Enseignant(String nom, String email) {
        super(nom, email);
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        int count = 0;
        for (ServicePrevu service : services) {
            count += service.getVolumeCM() * 3 / 2;
            count += service.getVolumeTD();
            count += service.getVolumeTP() * 3 / 4;
        }
        return count;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        int count = 0;
        for (ServicePrevu service : services) {
            if (service.getEnseignement() == ue) {
                count += service.getVolumeCM() * 3 / 2;
                count += service.getVolumeTD();
                count += service.getVolumeTP() * 3 / 4;
            }
        }
        return count;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magistral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        /*for (ServicePrevu sp : services) {
            if (sp.getEnseignement().equals(ue)) {
                return;
            }
        }*/
        services.add(new ServicePrevu(this, ue, volumeCM, volumeTD, volumeTP));
    }

    public boolean enSousService() { return this.heuresPrevues() > 192;}

    public void ajouteIntervention(Intervention intervention) {
        if (!intervention.getIntervenant().equals(this)) {
            throw new IllegalArgumentException("L'intervention n'est pas pour cet enseignant");
        }

        int reste = resteAPlanifier(intervention.getMatiere(), intervention.getType());
        if (intervention.getDuree() > reste) {
            throw new IllegalArgumentException("L'intervention dépasse le service prévu pour cette UE et ce type");
        }

        interventionsPlanifiees.add(intervention);
    }

    public int resteAPlanifier(UE ue, TypeIntervention type) {
        int prevu = 0;
        for (ServicePrevu sp : services) {
            if (sp.getEnseignement().equals(ue)) {
                switch (type) {
                    case CM:
                        prevu += sp.getVolumeCM();
                        break;
                    case TD:
                        prevu += sp.getVolumeTD();
                        break;
                    case TP:
                        prevu += sp.getVolumeTP();
                        break;
                }
            }
        }

        int planifie = 0;
        for (Intervention i : interventionsPlanifiees) {
            if (i.getType().equals(type) && i.getMatiere().equals(ue)) {
                planifie += i.getDuree();
            }
        }

        return Math.max(0, prevu - planifie);
    }

}
