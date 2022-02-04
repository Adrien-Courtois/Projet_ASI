package Entity;

import com.google.common.hash.Hashing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

public class ConseillerManager implements ConseillerManagerRemote{

    @PersistenceContext
    EntityManager em;

    public Conseiller getConseillerParLoginMdp(String login, String mdp){
        //On récupère la liste des conseillers dont le login est le même que celui indiqué dans le formulaire
        List<Conseiller> conseillers = em.createQuery("select c from Conseiller c where c.loginConseiller = '" + login + "'", Conseiller.class).getResultList();

        //On boucle sur la liste afin de trouver un ensemble login/mot de passe qui correspond avec ce qui a été entré dans le formulaire
        for (Conseiller conseiller : conseillers) {

            //On hash le mot de passe entré dans le formulaire car les mot de passe sont hashés dans la base pour une question de sécurité
            if (conseiller.getMdpConseiller().equals(Hashing.sha256().hashString(mdp, StandardCharsets.UTF_8).toString())) {

                //Si on trouve une correspondance alors on retourne le conseiller
                return conseiller;
            }
        }

        return null;
    }

    public Conseiller ajouterConseiller(Conseiller conseiller) {
        em.persist(conseiller);
        return conseiller;
    }
    public Collection<Conseiller> listerConseiller() {
        // Vous pouvez aussi utiliser une named query définie dans l’entité
        return em.createQuery("SELECT c FROM Conseiller c").getResultList();
    }

    public Conseiller modifMDP(Integer idConseiller, String oldMdp, String newMdp) {

        //On récupère les informations du client
        Conseiller conseiller = em.createQuery("select c from Client c where c.idConseiller = '" + idConseiller + "'", Conseiller.class).getSingleResult();

        if (conseiller.getMdpConseiller().equals(Hashing.sha256().hashString(oldMdp, StandardCharsets.UTF_8).toString())) {

            //on remplace le mot de passe du client par le hashé du nouveau mot de passe entré
            conseiller.setMdpConseiller(Hashing.sha256().hashString(newMdp, StandardCharsets.UTF_8).toString());
        }

        return conseiller;
    }
}
