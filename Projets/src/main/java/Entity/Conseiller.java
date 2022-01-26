package Entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Conseiller")
public class Conseiller implements Serializable{
    @Id
    @GeneratedValue (strategy=GenerationType.AUTO)
    @Column( name="IDConseiller" )
    private int idConseiller;

    @Column( name="LoginConseiller" )
    private String loginConseiller;

    @Column( name="MDPConseiller" )
    private String mdpConseiller;

    private static final long serialVersionUID = 1L;

    public Conseiller(){}

    public Conseiller(String login, String mdp) {

        super();
        this.setLoginConseiller(login);
        this.setMdpConseiller(mdp);

    }

    public int getIdConseiller() {
        return idConseiller;
    }

    public String getLoginConseiller() {
        return loginConseiller;
    }

    public String getMdpConseiller() {
        return mdpConseiller;
    }

    public void setIdConseiller(int idConseiller) {
        this.idConseiller = idConseiller;
    }

    public void setLoginConseiller(String loginConseiller) {
        this.loginConseiller = loginConseiller;
    }

    public void setMdpConseiller(String mdpConseiller) {
        this.mdpConseiller = mdpConseiller;
    }
}
