package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.Servicio;

public class ControladoraPersistencia {

    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("LaEsmalteriaPU");

    EntityManager em = emf.createEntityManager();

    public void guardarServicio(Servicio serv) {

        em.getTransaction().begin();

        em.persist(serv);

        em.getTransaction().commit();

    }
}