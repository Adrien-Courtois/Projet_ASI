package Entity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Collection;

@Stateless
@LocalBean
public class CompteManager implements CompteManagerRemote{
    @PersistenceContext
    EntityManager em;

    public Compte ajouterCompte(int epargne, Float solde, Float plafond, Integer idClient) {

        //On récupère l'entity manager de notre base
        //On commence une transaction
        EntityTransaction trans = em.getTransaction();
        trans.begin();

        //On initialise la variable compte
        Compte compte = null;

        //On regarde si le client veut créer un compte épargne ou non
        if (epargne == 1){

            //Si c'est un compte épargne alors on vérifie que le plafond n'est pas inférieur au solde et qu'il soit supérieur à 0
            if(plafond > 0 && solde < plafond) {

                //Si tout est bon alors nous créons le compte
                compte = new Compte(solde, idClient, plafond);
            }
        }else{

            //Si ce n'est pas un compte épargne alors on peut directement créer le comtpe en banque sans plafond
            compte = new Compte(solde, idClient, null);
        }

        //On vérifie que le compte a était créer avant de l'enregistrer
        if(compte != null) {

            //On enregistre le compte dans la base de donnée
            em.persist(compte);
        }

        //On applique la transaction
        trans.commit();

        return compte;
    }

    public Collection<Compte> listerCompte() {
        // Vous pouvez aussi utiliser une named query définie dans l’entité
        return em.createQuery("SELECT c FROM Compte c").getResultList();
    }

    public Compte getCompte(Integer idCompte){
        return em.createQuery("select c from Compte c where c.numCompte = '" + idCompte + "'", Compte.class).getSingleResult();
    }

    public Compte setCarte(Carte carte, Compte compte){
        //On débute une nouvelle transaction
        EntityTransaction trans = em.getTransaction();
        trans.begin();

        //On lie la carte au compte
        compte.setIdCarte(carte.getIdCarte());
        em.flush();

        //On applique les changements fait durant la transaction
        trans.commit();

        return compte;
    }
}
