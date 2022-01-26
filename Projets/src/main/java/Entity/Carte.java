package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Carte")
public class Carte implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( name="IDCarte" )
    private int idCarte;

    @Column( name="CodeCarte" )
    private int codeCarte;

    public Carte(){
        //Générer un code à 4 chiffres aléatoirement
        this.codeCarte = (int) (Math.random() * ( 10 - 0 ));
        for(int i = 0; i < 3; i++){
            this.codeCarte *= 10;
            this.codeCarte = (int) (Math.random() * ( 10 - 0 ));
        }
    }

    public int getIdCarte() {
        return idCarte;
    }

    public int getCodeCarte() {
        return codeCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public void setCodeCarte(int codeCarte) {
        this.codeCarte = codeCarte;
    }
}
