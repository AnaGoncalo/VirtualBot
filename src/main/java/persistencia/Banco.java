package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @version 1.0
 * Classe de acesso ao banco de dados da aplicação.
 *
 */
public class Banco {

    private static final Banco instanciaDeBanco = new Banco();
    private static EntityManager manager;

    private Banco() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("virtualbot");
        manager = factory.createEntityManager();
    }

    /**
     *
     * @return Instância de Banco de Dados.
     */
    public static Banco getInstance() {
        return instanciaDeBanco;
    }

    /**
     *
     * @return Genrênciador de entidades JPAs
     */
    public EntityManager getEntityManager() {
        return manager;
    }
}
