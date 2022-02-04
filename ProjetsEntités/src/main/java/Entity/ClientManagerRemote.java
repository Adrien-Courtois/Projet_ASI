package Entity;

import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface ClientManagerRemote {
    Client ajouterClient(Client client);

    Collection<Client> listerClient();

    Client modifMDP(Integer idClient, String oldMdp, String newMdp);

    Client getClientParLoginMdp(String login, String mdp);

    Client getClient(Integer idClient);

    Client setConseiller(Client client, Integer idConseiller);
}
