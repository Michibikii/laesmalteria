package logica;

import persistencia.ControladoraPersistencia;

import java.util.List;

public class Controladora {

    // Instancia de la clase que maneja la base de datos (Persistencia)
    ControladoraPersistencia controlPers = new ControladoraPersistencia();

    // ==========================================
    // SECCIÓN DE SERVICIOS (Catálogo)
    // ==========================================

    // Recibe un servicio desde la interfaz visual y se lo pasa a la base de datos para guardarlo
    public void guardarServicio(Servicio serv) {
        controlPers.guardarServicio(serv);
    }

    // Pide a la base de datos todos los servicios guardados y los devuelve en forma de Lista
    public List<Servicio> traerServicios() {
        return controlPers.traerServicios();
    }

    // Busca un servicio específico en la base de datos usando su ID
    public Servicio buscarServicio(long id){
        return controlPers.buscarServicio(id);
    }

    // Recibe un servicio modificado y le avisa a la base de datos que actualice los datos
    public void editarServicio(Servicio serv) {
        controlPers.editarServicio(serv);
    }

    // Recibe un ID y le indica a la base de datos que elimine ese servicio
    public void eliminarServicio(Long id){
        controlPers.eliminarServicio(id);
    }

    // ==========================================
    // SECCIÓN DE TURNOS (Agenda)
    // ==========================================

    // Recibe un turno nuevo desde la interfaz visual y lo guarda en la base de datos
    public void guardarTurno(Turno turno) {
        controlPers.guardarTurno(turno);
    }

    // Pide a la base de datos todos los turnos agendados y los devuelve en una Lista
    public List<Turno> traerTurnos() {
        return controlPers.traerTurnos();
    }

    // Busca un turno específico usando su ID (útil para cuando queremos editar o cobrar un turno)
    public Turno buscarTurno(long id) {
        return controlPers.buscarTurno(id);
    }

    // Recibe un turno modificado (por ejemplo, cuando se cambia su estado a "Pagado") y actualiza la BD
    public void editarTurno(Turno turno) {
        controlPers.editarTurno(turno);
    }

    // Elimina permanentemente un turno de la base de datos usando su ID
    public void eliminarTurno(Long id) {
        controlPers.eliminarTurno(id);
    }
}
