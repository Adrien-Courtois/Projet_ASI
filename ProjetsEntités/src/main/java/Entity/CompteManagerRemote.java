package Entity;

import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface CompteManagerRemote {

    Collection<Compte> listerCompte();

    Compte ajouterCompte(int epargne, Float solde, Float plafond, Integer idClient);

    Compte getCompte(Integer idCompte);

    Compte setCarte(Carte carte, Compte compte);
}
