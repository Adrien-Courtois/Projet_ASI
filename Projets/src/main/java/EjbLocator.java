import Entity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class EjbLocator {
    private static Context ctx;
    private static final EjbLocator instance = new EjbLocator();
    private EjbLocator() {
    }
    public static EjbLocator getLocator() {
        return instance;
    }

    private <T> T getEjb(Class<T> ejbClass, String beanName) {
        try {
            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            final Context context = new InitialContext(jndiProperties);
            final String appName = "ProjetWebContact";
            String lat="java:global/"+ appName + "/" + beanName + "!" + ejbClass.getName();
            System.out.println(lat);
            return (T) context.lookup("java:global/" + appName + "/" + beanName + "!" + ejbClass.getName());
        } catch (NamingException e) {
            return null;
        }
    }
    public CarteManagerRemote getContactManager() {
        return getEjb(CarteManagerRemote.class, "CarteManager");
    }

    public ClientManagerRemote getClientManager() {
        return getEjb(ClientManagerRemote.class, "ClientManager");
    }

    public CompteManagerRemote getCompteManager() {
        return getEjb(CompteManagerRemote.class, "CompteManager");
    }

    public ConseillerManagerRemote getConseillerManager() {
        return getEjb(ConseillerManagerRemote.class, "ConseillerManager");
    }

    public TransactionManagerRemote getTransactionManager() {
        return getEjb(TransactionManagerRemote.class, "TransactionManager");
    }

    public CarteManagerRemote getCarteManager(){
        return getEjb(CarteManagerRemote.class, "CarteManagerRemote");
    }
}


