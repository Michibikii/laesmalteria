import logica.Controladora;
import logica.Servicio;

import java.util.List;

public class Laesmalteria {

    public static void main(String[] args) {

        Controladora control = new Controladora();

        Servicio serv = new Servicio();

        serv.setNombre("Kapping");
        serv.setPrecio(10000.0);
        serv.setCategoria("Uñas");
        serv.setDuracion(60);

        control.guardarServicio(serv);

        System.out.println("Servicio guardado");

        List<Servicio> listaServicios = control.traerServicios();

        for (Servicio s : listaServicios) {

            System.out.println(
                    s.getId() + " - " +
                            s.getNombre() + " - $" +
                            s.getPrecio()
            );

        }
        // Busqueda del servicio
        Servicio encontrado = control.buscarServicio(1L);

        if (encontrado != null) {

            System.out.println("El servicio fue encontrado:");
            System.out.println(encontrado.getNombre());

        } else {

            System.out.println("El servicio no pudo ser encontrado");

        }
        // Edicion del servicio
        Servicio servicioEditar = control.buscarServicio(1L);
        if(servicioEditar != null){

            servicioEditar.setPrecio(20000.0);

            control.editarServicio(servicioEditar);

            System.out.println("Servicio editado correctamente");

        }
        // actualizacion del precio
        Servicio verificado = control.buscarServicio(1L);

        System.out.println(
                "Nuevo precio: " +
                        verificado.getPrecio()
        );
        control.eliminarServicio(1L);

        System.out.println("El servicio ha sido eliminado");

        Servicio eliminado = control.buscarServicio(1L);

        if (eliminado == null){

            System.out.println("El servicio ya no existe");

        }
    }
}