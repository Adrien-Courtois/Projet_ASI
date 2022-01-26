package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Compte")
public class Compte implements Serializable {
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

    public int getNumCompte() {
        return numCompte;
    }

    public float getSolde() {
        return solde;
    }

    public int getIdClient() {
        return idClient;
    }

    public float getPlafond() {
        return plafond;
    }

    public int getIdCarte() {
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
