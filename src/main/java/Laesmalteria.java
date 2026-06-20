import logica.Servicio;
import persistencia.ControladoraPersistencia;

public class Laesmalteria {

    public static void main(String[] args) {

        ControladoraPersistencia control =
                new ControladoraPersistencia();

        Servicio serv = new Servicio();

        serv.setNombre("Kapping");
        serv.setPrecio(10000.0);
        serv.setCategoria("Uñas");
        serv.setDuracion(60);

        control.guardarServicio(serv);

        System.out.println("Servicio guardado");
    }
}