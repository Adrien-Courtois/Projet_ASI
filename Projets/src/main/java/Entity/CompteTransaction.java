package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Compte_Transaction")
public class CompteTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( name="IDTransaction_NumCommande" )
    private int idTransactionNumCommande;

    @Column( name="IDTransaction" )
    private int idTransaction;

    @Column( name="NumCompte" )
    private int numCompte;

    public CompteTransaction(){}

    public CompteTransaction(int idTransaction, int numCompte){
        this.idTransaction = idTransaction;
        this.numCompte = numCompte;
    }

    public int getIdTransactionNumCommande() {
        return idTransactionNumCommande;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public int getNumCompte() {
        return numCompte;
    }

    public void setIdTransactionNumCommande(int idTransactionNumCommande) {
        this.idTransactionNumCommande = idTransactionNumCommande;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }
}
