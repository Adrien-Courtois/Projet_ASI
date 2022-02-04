package Entity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
@LocalBean
public class TransactionManager implements TransactionManagerRemote{
    @PersistenceContext
    EntityManager em;

    public Transaction ajouterTransaction(Transaction transaction) {
        em.persist(transaction);
        return transaction;
    }
    public Collection<Transaction> listerTransaction() {
        // Vous pouvez aussi utiliser une named query définie dans l’entité
        return em.createQuery("SELECT c FROM Transaction c").getResultList();
    }

    public Transaction ajouterTransaction(Compte compteExp, Compte compteDest, Float montant){
        if(compteExp.getSolde() >= montant && ((compteDest.getPlafond() != null && (compteDest.getSolde() + montant) <= compteDest.getPlafond()) || compteDest.getPlafond() == null)){
            //Si c'est bon alors on commence une transaction (transaction de base de données)
            EntityTransaction trans = em.getTransaction();
            trans.begin();

            //On créer une transaction (un virement entre compte) entre le compte expéditeur et le compte destinataire
            Transaction newTransac = new Transaction(compteExp.getNumCompte(), compteDest.getNumCompte(), montant);

            //On enregistre la transaction (un virement entre compte)
            em.persist(newTransac);

            //On fais le lien dans la table de jointure
            em.persist(new CompteTransaction(newTransac.getIdTransaction(), compteExp.getNumCompte()));
            em.persist(new CompteTransaction(newTransac.getIdTransaction(), compteDest.getNumCompte()));

            //On modifie le solde des comptes
            compteExp.setSolde(compteExp.getSolde() - montant);
            compteDest.setSolde(compteDest.getSolde() + montant);
            em.flush();

            //On applique les changements fait durant la transaction (transaction de base de données)
            trans.commit();

            return newTransac;
        }
        return null;
    }
}
