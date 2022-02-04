package Entity;
import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface CarteManagerRemote {
    Carte ajouterCarte();

    Collection<Carte> listerCarte();

}
