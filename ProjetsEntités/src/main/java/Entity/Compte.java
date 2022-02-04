package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Compte")
public class Compte implements Serializable {

/*
    Entité Compte : représente un compte bancaire détenu par un client,
    ce compte peut être un compte courant (sans plafond)
    ou un compte épargne (avec plafond)
*/

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( name="NumCompte" )
    private int numCompte;

    @Column( name="Solde" )
    private float solde;

    @Column( name="IDClient" )
    private int idClient;

    @Column( name="plafond" )
    private Float plafond;

    @Column( name="IDCarte" )
    private Integer idCarte;

    private static final long serialVersionUID = 1L;

    public Compte(){}

    //Constructeur
    public Compte(float solde, int idClient, Float plafond){
        this.solde = solde;
        this.idClient = idClient;
        this.plafond = plafond;
    }

    public int getNumCompte() {
        return numCompte;
    }

    public float getSolde() {
        return solde;
    }

    public int getIdClient() {
        return idClient;
    }

    public Float getPlafond() {
        return plafond;
    }

    public Integer getIdCarte() {
        return idCarte;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setPlafond(Float plafond) {
        this.plafond = plafond;
    }

    public void setIdCarte(Integer idCarte) {
        this.idCarte = idCarte;
    }
}
