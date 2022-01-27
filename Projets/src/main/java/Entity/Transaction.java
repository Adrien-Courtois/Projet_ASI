package Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="Transaction")
public class Transaction implements Serializable {

/*
    Entité Transaction : représente une transaction d'un compte à un autre
*/

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( name="IDTransaction" )
    private int idTransaction;

    @Column( name="NumCompteExp" )
    private int numCompteExp;

    @Column( name="NumCompteDest" )
    private int numCompteDest;

    @Column( name="Montant" )
    private float montant;

    @Column( name="Date" )
    private String date;

    public Transaction(){}

    public Transaction(int numCompteExp, int numCompteDest, float montant){
        this.numCompteExp = numCompteExp;
        this.numCompteDest = numCompteDest;
        this.montant = montant;
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        Date dateObj = calendar.getTime();
        this.date = dtf.format(dateObj);
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public int getNumCompteExp() {
        return numCompteExp;
    }

    public int getNumCompteDest() {
        return numCompteDest;
    }

    public float getMontant() {
        return montant;
    }

    public String getDate() {
        return date;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void setNumCompteExp(int numCompteExp) {
        this.numCompteExp = numCompteExp;
    }

    public void setNumCompteDest(int numCompteDest) {
        this.numCompteDest = numCompteDest;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
