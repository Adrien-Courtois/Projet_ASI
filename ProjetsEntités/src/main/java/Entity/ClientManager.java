package Entity;

import com.google.common.hash.Hashing;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Stateless
@LocalBean
public class ClientManager implements ClientManagerRemote{

    @PersistenceContext
    EntityManager em;

    public Client ajouterClient(Client client) {
        em.persist(client);
        return client;
    }
    public Collection<Client> listerClient() {
        // Vous pouvez aussi utiliser une named query définie dans l’entité
        return em.createQuery("SELECT c FROM Client c").getResultList();
    }

    public Client modifMDP(Integer idClient, String oldMdp, String newMdp) {

        //On récupère les informations du client
        Client client = em.createQuery("select c from Client c where c.idClient = '" + idClient + "'", Client.class).getSingleResult();

        if (client.getMdpClient().equals(Hashing.sha256().hashString(oldMdp, StandardCharsets.UTF_8).toString())) {
            //on remplace le mot de passe du client par le hashé du nouveau mot de passe entré
            client.setMdpClient(Hashing.sha256().hashString(newMdp, StandardCharsets.UTF_8).toString());
        }

        return client;
    }

    public Client getClientParLoginMdp(String login, String mdp){

        //On recherche tout les clients qui ont pour login le login entrée dans le formulaire
        List<Client> clients = em.createQuery("select c from Client c where c.loginClient = '" + login + "'", Client.class).getResultList();

        //On boucle sur la liste de clients pour trouver une correspondance login/mot de passe
        for (Client client : clients) {

            //On vérifie le hash du mot de passe entré avec le mot de passe enregistré dans la base
            if (client.getMdpClient().equals(Hashing.sha256().hashString(mdp, StandardCharsets.UTF_8).toString())) {

                //Si nous trouvons une correspondance alors nous retournons le client
                return client;
            }
        }

        return null;
    }

    public Client getClient(Integer idClient){
        return em.createQuery("select c from Client c where c.idClient = '" + idClient + "'", Client.class).getSingleResult();
    }

    public Client setConseiller(Client client, Integer idConseiller){
        //On commence une transaction
        EntityTransaction trans = em.getTransaction();
        trans.begin();

        //On lui modifie le conseiller qui lui est associé
        client.setIdConseiller(idConseiller);

        //On applique les changements fais aux entités
        em.flush();

        //On applique les changements fais durant la transaction
        trans.commit();

        return client;
    }
}
