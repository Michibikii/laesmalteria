package logica;

import persistencia.ControladoraPersistencia;

import java.util.List;

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

}
