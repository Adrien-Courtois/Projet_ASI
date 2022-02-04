package Entity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
@LocalBean
public class CarteManager implements CarteManagerRemote{
    @PersistenceContext
    EntityManager em;

    public Carte ajouterCarte() {
        Carte carte = new Carte();
        em.persist(carte);
        return carte;
    }
    public Collection<Carte> listerCarte() {
        // Vous pouvez aussi utiliser une named query définie dans l’entité
        return em.createQuery("SELECT c FROM Carte c").getResultList();
    }
}
