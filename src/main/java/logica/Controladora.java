 package logica;

import persistencia.ControladoraPersistencia;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controladora {

    ControladoraPersistencia controlPers =
            new ControladoraPersistencia();
    // Guardar Servicio
    public void guardarServicio(Servicio serv) {

        controlPers.guardarServicio(serv);

    }
    // Traer Servicios
    public List<Servicio> traerServicios() {

        return controlPers.traerServicios();

    }
// Buscar Servicio
    public Servicio buscarServicio(long id){

        return  controlPers.buscarServicio(id);
    }
// Editar Servicio
    public void editarServicio(Servicio serv){

        controlPers.editarServicio(serv);
    }

    // Eliminar servicio
    public void eliminarServicio(Long id){

        controlPers.eliminarServicio(id);

    }
    // Obtener todos los turnos desde persistencia
    public List<Turno> traerTurnos() {
        return controlPers.traerTurnos();
    }

    // Reporte: obtener turnos según una fecha específica
    public List<Turno> reportePorFecha(String fecha) {

        // Trae todos los turnos
        List<Turno> turnos = traerTurnos();

        // Lista donde se guardarán los turnos filtrados
        List<Turno> resultado = new ArrayList<>();

        // Recorre todos los turnos
        for (Turno t : turnos) {

            // Si la fecha coincide, lo agrega al reporte
            if (t.getFecha().equals(fecha)) {
                resultado.add(t);
            }
        }

        // Devuelve la lista filtrada
        return resultado;
    }

    // Reporte: calcular ingresos totales de turnos completados
    public double reporteIngresos() {

        // Traer todos los turnos
        List<Turno> turnos = traerTurnos();

        // Variable acumuladora
        double total = 0;

        // Recorre todos los turnos
        for (Turno t : turnos) {

            // Solo suma los turnos completados
            if (t.getEstado().equalsIgnoreCase("completado")) {
                total += t.getServicio().getPrecio();
            }
        }

        // Devuelve el total recaudado
        return total;
    }

    // Reporte: contar cuántas veces fue solicitado cada servicio
    public Map<String, Integer> reporteServiciosMasSolicitados() {

        // Traer todos los turnos
        List<Turno> turnos = traerTurnos();

        // Mapa para guardar nombre del servicio y cantidad
        Map<String, Integer> reporte = new HashMap<>();

        // Recorre los turnos
        for (Turno t : turnos) {

            // Obtiene el nombre del servicio
            String servicio = t.getServicio().getNombre();

            // Cuenta cuántas veces aparece
            reporte.put(
                    servicio,
                    reporte.getOrDefault(servicio, 0) + 1
            );
        }

        // Devuelve el reporte final
        return reporte;
    }

    // Reporte: obtener todos los turnos cancelados
    public List<Turno> reporteCancelados() {

        // Traer todos los turnos
        List<Turno> turnos = traerTurnos();

        // Lista para guardar cancelados
        List<Turno> cancelados = new ArrayList<>();

        // Recorre todos los turnos
        for (Turno t : turnos) {

            // Filtra por estado cancelado
            if (t.getEstado().equalsIgnoreCase("cancelado")) {
                cancelados.add(t);
            }
        }

        // Devuelve la lista de cancelados
        return cancelados;
    }

}
