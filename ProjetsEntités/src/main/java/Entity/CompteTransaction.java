package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Compte_Transaction")
public class CompteTransaction implements Serializable {

/*
    Entité CompteTransaction : représente la table de jointure entre la table Compte et la table Transaction
*/

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( name="IDTransaction_NumCompte" )
    private int idTransactionNumCompte;

    @Column( name="IDTransaction" )
    private int idTransaction;

    @Column( name="NumCompte" )
    private int numCompte;

    public CompteTransaction(){}

    public CompteTransaction(int idTransaction, int numCompte){
        this.idTransaction = idTransaction;
        this.numCompte = numCompte;
    }

    public int getIdTransactionNumCompte() {
        return idTransactionNumCompte;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public int getNumCompte() {
        return numCompte;
    }

    public void setIdTransactionNumCompte(int idTransactionNumCompte) {
        this.idTransactionNumCompte = idTransactionNumCompte;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }
}
