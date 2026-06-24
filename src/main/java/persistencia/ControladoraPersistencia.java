package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.Servicio;
import logica.Turno;

import jakarta.persistence.TypedQuery;
import java.util.List;


public class ControladoraPersistencia {

    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("LaEsmalteriaPU");

    EntityManager em = emf.createEntityManager();

    //Guarda un servicio en la base de dato
    public void guardarServicio(Servicio serv) {

        em.getTransaction().begin();

        em.persist(serv);

        em.getTransaction().commit();

    }
    // Obtener todos los servicios de la base de datos y devolver en una lista
    public List<Servicio> traerServicios() {

        //consulta JPQL
        TypedQuery<Servicio> query =
                em.createQuery("SELECT s FROM Servicio s", Servicio.class);

        //ejecucion de la consulta para devolver todos los registros encontrados
        return query.getResultList();
    }

    public Servicio buscarServicio(long id){

        return em.find(Servicio.class, id);
    }
    // editar el servicio
    public void editarServicio(Servicio serv){
        em.getTransaction().begin();
        //tomar el objeto y sincronizar con la base de datos
        em.merge(serv);
        em.getTransaction().commit();
    }

    // busca un servicio por id y lo elimina de la base de datos

    public void eliminarServicio(Long id){

        em.getTransaction().begin();

        Servicio serv = em.find(Servicio.class, id);

        if (serv != null){

            em.remove(serv);

        }
        em.getTransaction().commit();
    }
    // Obtener todos los turnos registrados en la base de datos
    public List<Turno> traerTurnos() {

        // Consulta JPQL para traer todos los turnos
        TypedQuery<Turno> query =
                em.createQuery("SELECT t FROM Turno t", Turno.class);

        // Devuelve la lista de turnos encontrados
        return query.getResultList();
    }

    // Guardar un nuevo turno en la base de datos
    public void guardarTurno(Turno turno) {

        // Inicia la transacción
        em.getTransaction().begin();

        // Guarda el turno
        em.persist(turno);

        // Confirma los cambios
        em.getTransaction().commit();
    }

}