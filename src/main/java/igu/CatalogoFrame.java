package igu;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import logica.Controladora;
import logica.Servicio;

public class CatalogoFrame extends JFrame {

    private Controladora control = new Controladora(); // Conexión a la base de datos a través de la lógica
    private JPanel contenedor;

    public CatalogoFrame() {

        setTitle("Nuestra Carta");
        setSize(800, 700);
        setLocationRelativeTo(null);
        // Usamos DISPOSE_ON_CLOSE para que al cerrar esta ventana no se apague todo el programa (sólo se cierra esta ventana)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Contenedor principal donde se van a apilar las "tarjetas" de cada servicio verticalmente
        contenedor = new JPanel();
        contenedor.setBackground(Color.WHITE);
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

        // Panel Superior: Contiene los títulos y el botón para Agregar un nuevo servicio
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(252, 228, 236)); // Rosa pastel marca
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Nuestra Carta", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.ITALIC, 36));
        
        JLabel subtitulo = new JLabel("SERVICIOS & PRECIOS", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JPanel titulosPanel = new JPanel();
        titulosPanel.setLayout(new BoxLayout(titulosPanel, BoxLayout.Y_AXIS));
        titulosPanel.setBackground(new Color(252, 228, 236));
        
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulosPanel.add(titulo);
        titulosPanel.add(subtitulo);

        // Botón "Agregar Servicio" estilizado
        JButton btnAgregar = new JButton("Agregar Servicio");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(40, 40, 40));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(e -> {
            // Abre el formulario en "Modo Alta" (vacío, sin pasarle ningún ID)
            FormularioServicioFrame form = new FormularioServicioFrame();
            form.setVisible(true);
            // Agregamos un oyente: Cuando se cierra el formulario, volvemos a cargar la pantalla para ver el nuevo servicio agregado
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    cargarServicios(); 
                }
            });
        });

        topPanel.add(titulosPanel, BorderLayout.CENTER);
        topPanel.add(btnAgregar, BorderLayout.EAST); // Acomoda el botón a la derecha del título

        add(topPanel, BorderLayout.NORTH);

        // ScrollPane para permitir deslizar la pantalla si se agregan muchos servicios
        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.getVerticalScrollBar().setUnitIncrement(16); // Hace que la rueda del mouse baje más rápido
        add(scroll, BorderLayout.CENTER);

        // Llama al método que busca los servicios en la BD y dibuja las tarjetas al abrir la ventana
        cargarServicios();
    }

    // Método principal que actualiza la lista de servicios en la pantalla
    private void cargarServicios() {
        contenedor.removeAll(); // Borra todas las tarjetas viejas que había en la pantalla
        contenedor.add(Box.createVerticalStrut(20)); // Añade un espacio en blanco arriba

        // Pide la lista de servicios reales a la Base de Datos
        List<Servicio> listaServicios = control.traerServicios();

        if (listaServicios != null && !listaServicios.isEmpty()) {
            // Por cada servicio guardado en la base de datos, crea y agrega una Tarjeta visual
            for (Servicio serv : listaServicios) {
                contenedor.add(crearServicioCard(serv));
                contenedor.add(Box.createVerticalStrut(10)); // Espacio entre tarjetas
            }
        } else {
            JLabel lblVacio = new JLabel("Aún no hay servicios cargados.");
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenedor.add(lblVacio);
        }

        // Obliga a la ventana a redibujarse para mostrar los nuevos elementos cargados
        contenedor.revalidate();
        contenedor.repaint();
    }

    // Método que diseña la "Tarjeta" visual (Card) de un servicio específico
    private JPanel crearServicioCard(Servicio serv) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(700, 100));
        card.setBackground(Color.WHITE);
        // Le pone un borde redondeado gris clarito simulando una tarjeta moderna
        card.setBorder(new CompoundBorder(
                new EmptyBorder(10, 20, 10, 20),
                new LineBorder(new Color(230, 230, 230), 1, true)
        ));

        // === Lado Izquierdo de la Tarjeta: Nombre, Categoría y Duración ===
        JPanel izquierda = new JPanel();
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        izquierda.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel(serv.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        String descripcion = serv.getCategoria() + " (" + serv.getDuracion() + " min)";
        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setForeground(Color.GRAY);

        izquierda.add(lblNombre);
        izquierda.add(lblDesc);

        // === Lado Derecho de la Tarjeta: Precio y Botones de Editar/Borrar ===
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        derecha.setBackground(Color.WHITE);

        JLabel lblPrecio = new JLabel("$" + serv.getPrecio());
        lblPrecio.setFont(new Font("Serif", Font.PLAIN, 24));

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> {
            // Abre el formulario pero pasándole el ID del servicio, así la ventana sabe que es "Modo Edición" y precarga los datos
            FormularioServicioFrame form = new FormularioServicioFrame(serv.getId());
            form.setVisible(true);
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    cargarServicios(); // Refresca la pantalla al terminar de editar
                }
            });
        });

        JButton btnEliminar = new JButton("X");
        btnEliminar.setForeground(Color.RED);
        btnEliminar.setToolTipText("Eliminar este servicio");
        btnEliminar.addActionListener(e -> {
            // Cuadro de diálogo para confirmar que realmente quiere borrarlo
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea eliminar el servicio '" + serv.getNombre() + "'?", 
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                control.eliminarServicio(serv.getId()); // Lo borra de la BD
                cargarServicios(); // Refresca la pantalla inmediatamente
            }
        });

        derecha.add(lblPrecio);
        derecha.add(btnEditar);
        derecha.add(btnEliminar);

        // Ensamblamos ambos lados (izquierdo y derecho) en la tarjeta principal
        card.add(izquierda, BorderLayout.WEST);
        card.add(derecha, BorderLayout.EAST);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        return card;
    }
}
