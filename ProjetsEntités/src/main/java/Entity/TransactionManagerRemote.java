package Entity;

import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface TransactionManagerRemote {
    Transaction ajouterTransaction(Transaction transaction);

    Collection<Transaction> listerTransaction();

    Transaction ajouterTransaction(Compte compteExp, Compte compteDest, Float montant);
}
