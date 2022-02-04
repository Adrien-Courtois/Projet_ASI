package Entity;

import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface ConseillerManagerRemote {
    Conseiller ajouterConseiller(Conseiller conseiller);

    Collection<Conseiller> listerConseiller();

    Conseiller modifMDP(Integer idConseiller, String oldMdp, String newMdp);

    public Conseiller getConseillerParLoginMdp(String login, String mdp);
}
