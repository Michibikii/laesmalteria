package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.Servicio;
import logica.Turno;

import jakarta.persistence.TypedQuery;
import java.util.List;


public class ControladoraPersistencia {

    // Configura la conexión a la base de datos usando JPA/Hibernate (lee el archivo persistence.xml)
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("LaEsmalteriaPU");
    // Objeto principal que ejecuta las acciones (guardar, borrar, buscar) en la BD
    EntityManager em = emf.createEntityManager();

    // ==========================================
    // OPERACIONES CON SERVICIOS
    // ==========================================

    // Guarda un servicio nuevo en la base de datos
    public void guardarServicio(Servicio serv) {
        em.getTransaction().begin(); // Inicia la transacción
        em.persist(serv); // Inserta el objeto en la BD
        em.getTransaction().commit(); // Confirma y guarda los cambios
    }

    // Obtiene todos los servicios de la base de datos y los devuelve en una lista
    public List<Servicio> traerServicios() {
        // Limpiamos el caché de EntityManager para forzar la lectura real desde la BD (evita datos fantasmas tras un editar)
        em.clear();
        // Consulta JPQL (Java Persistence Query Language) para traer todos los servicios
        TypedQuery<Servicio> query = em.createQuery("SELECT s FROM Servicio s", Servicio.class);
        // Ejecuta la consulta y devuelve los registros encontrados
        return query.getResultList();
    }

    // Busca un servicio específico por su ID
    public Servicio buscarServicio(long id){
        return em.find(Servicio.class, id);
    }

    // Edita y actualiza un servicio existente
    public void editarServicio(Servicio serv){
        em.getTransaction().begin();
        // merge() toma el objeto modificado y sincroniza sus datos con los de la base de datos
        em.merge(serv);
        em.getTransaction().commit();
    }

    // Busca un servicio por su ID y lo elimina de forma definitiva de la base de datos
    public void eliminarServicio(Long id){
        em.getTransaction().begin();
        Servicio serv = em.find(Servicio.class, id); // Primero lo busca
        if (serv != null){
            em.remove(serv); // Si existe, lo elimina
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

    // ==========================================
    // OPERACIONES CON TURNOS
    // ==========================================

    // Guarda un nuevo turno en la base de datos
    public void guardarTurno(Turno turno) {
        em.getTransaction().begin();
        em.persist(turno);
        em.getTransaction().commit();
    }

    // Obtiene todos los turnos registrados
    public List<Turno> traerTurnos() {
        // Limpiamos el caché para evitar traer estados viejos (por ejemplo, si acabamos de cambiar un turno a "Pagado")
        em.clear();
        TypedQuery<Turno> query = em.createQuery("SELECT t FROM Turno t", Turno.class);
        return query.getResultList();
    }

    // Busca un turno específico por su ID
    public Turno buscarTurno(long id) {
        return em.find(Turno.class, id);
    }

    // Actualiza los datos de un turno existente (útil para cambiar fechas o registrar pagos)
    public void editarTurno(Turno turno) {
        em.getTransaction().begin();
        em.merge(turno);
        em.getTransaction().commit();
    }

    // Busca un turno por su ID y lo elimina
    public void eliminarTurno(Long id) {
        em.getTransaction().begin();
        Turno turno = em.find(Turno.class, id);
        if (turno != null) {
            em.remove(turno);
        }
        em.getTransaction().commit();
    }
}