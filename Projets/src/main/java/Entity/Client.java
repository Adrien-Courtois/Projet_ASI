package Entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="Client")
public class Client implements Serializable{
    @Id
    @GeneratedValue (strategy=GenerationType.AUTO)
    @Column( name="IDClient" )
    private int idClient;

    @Column( name="LoginClient" )
    private String loginClient;

    @Column( name="MDPClient" )
    private String mdpClient;

    @Column( name="IDConseiller" )
    private int idConseiller;

    private static final long serialVersionUID = 1L;

    public Client(){}

    public Client(String login, String mdp, int conseiller) {
        super();
        this.setLoginClient(login);
        this.setMdpClient(mdp);
        this.setIdConseiller(conseiller);
    }

    public Client(String login, String mdp) {
        super();
        this.setLoginClient(login);
        this.setMdpClient(mdp);
        this.setIdConseiller(1);
    }

    public int getIdClient() {
        return idClient;
    }

    public String getLoginClient() {
        return loginClient;
    }

    public String getMdpClient() {
        return mdpClient;
    }

    public int getIdConseiller() { return idConseiller; }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }

    public void setMdpClient(String mdpClient) {
        this.mdpClient = mdpClient;
    }

    public void setIdConseiller(int idConseiller) {
        this.idConseiller = idConseiller;
    }
}
