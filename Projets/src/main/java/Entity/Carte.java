package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Carte")
public class Carte implements Serializable {

/*
    Entité Carte : représente la carte bancaire lié à un compte en banque
*/

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( name="IDCarte" )
    private int idCarte;

    @Column( name="CodeCarte" )
    private String codeCarte;

    public Carte(){
        //Générer un code à 4 chiffres aléatoirement
        this.codeCarte = String.valueOf((int) (Math.random() * ( 10 - 0 )));
        for(int i = 0; i < 3; i++){
            this.codeCarte += String.valueOf((int)(Math.random() * ( 10 - 0 )));
        }
    }

    public int getIdCarte() {
        return idCarte;
    }

    public String getCodeCarte() {
        return codeCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public void setCodeCarte(String codeCarte) {
        this.codeCarte = codeCarte;
    }
}
