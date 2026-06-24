package logica;

import persistencia.ControladoraPersistencia;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    // ==========================================
    // REPORTES Y ESTADÍSTICAS
    // ==========================================

    // Reporte: obtener turnos según una fecha específica
    public List<Turno> reportePorFecha(String fecha) {
        List<Turno> turnos = traerTurnos();
        List<Turno> resultado = new ArrayList<>();
        for (Turno t : turnos) {
            if (t.getFecha().equals(fecha)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    // Reporte: calcular ingresos totales de turnos completados/pagados
    public double reporteIngresos() {
        List<Turno> turnos = traerTurnos();
        double total = 0;
        for (Turno t : turnos) {
            // Ajustamos para que tome "Completado" o "Pagado"
            if (t.getEstado() != null && (t.getEstado().equalsIgnoreCase("completado") || t.getEstado().contains("Pagado"))) {
                if (t.getServicio() != null) {
                    total += t.getServicio().getPrecio();
                }
            }
        }
        return total;
    }

    // Reporte: contar cuántas veces fue solicitado cada servicio
    public Map<String, Integer> reporteServiciosMasSolicitados() {
        List<Turno> turnos = traerTurnos();
        Map<String, Integer> reporte = new HashMap<>();
        for (Turno t : turnos) {
            if (t.getServicio() != null) {
                String servicio = t.getServicio().getNombre();
                reporte.put(servicio, reporte.getOrDefault(servicio, 0) + 1);
            }
        }
        return reporte;
    }

    // Reporte: obtener todos los turnos cancelados
    public List<Turno> reporteCancelados() {
        List<Turno> turnos = traerTurnos();
        List<Turno> cancelados = new ArrayList<>();
        for (Turno t : turnos) {
            if (t.getEstado() != null && t.getEstado().equalsIgnoreCase("cancelado")) {
                cancelados.add(t);
            }
        }
        return cancelados;
    }
}
