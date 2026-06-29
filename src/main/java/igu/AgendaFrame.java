package igu;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import logica.Controladora;
import logica.Turno;

public class AgendaFrame extends JFrame {
    private Controladora control = new Controladora(); // Conexión a la base de datos
    private JPanel contenedor;

    public AgendaFrame() {
        setTitle("Agenda de Turnos");
        setSize(800, 700);
        setLocationRelativeTo(null);
        // DISPOSE_ON_CLOSE asegura que al cerrar la agenda no se cierre el Menú Principal
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Contenedor donde se van a apilar las tarjetas de los turnos verticalmente (Y_AXIS)
        contenedor = new JPanel();
        contenedor.setBackground(Color.WHITE);
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

        // === Panel Superior (Títulos y Botones) ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(252, 228, 236)); // Rosa pastel marca
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Agenda de Turnos", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.ITALIC, 36));
        
        JLabel subtitulo = new JLabel("PRÓXIMAS RESERVAS", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JPanel titulosPanel = new JPanel();
        titulosPanel.setLayout(new BoxLayout(titulosPanel, BoxLayout.Y_AXIS));
        titulosPanel.setBackground(new Color(252, 228, 236));
        
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulosPanel.add(titulo);
        titulosPanel.add(subtitulo);

        // Panel de botones (derecha)
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botonesPanel.setBackground(new Color(252, 228, 236));

        // Botón Exportar
        JButton btnExportar = new JButton("Exportar TXT");
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnExportar.setBackground(new Color(255, 255, 255));
        btnExportar.setForeground(new Color(40, 40, 40));
        btnExportar.setFocusPainted(false);
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.addActionListener(e -> exportarTurnos());

        // Botón para crear un turno nuevo
        JButton btnAgregar = new JButton("Nuevo Turno");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(40, 40, 40));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(e -> {
            // Validación: No se puede agendar un turno si no hay servicios cargados en el Catálogo
            List<logica.Servicio> servicios = control.traerServicios();
            if (servicios == null || servicios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe cargar al menos un servicio en el Catálogo antes de agendar un turno.", "Acción no permitida", JOptionPane.WARNING_MESSAGE);
                return; // Corta la ejecución si no hay servicios
            }

            // Abre la ventana para registrar un turno vacío (Modo Alta)
            FormularioTurnoFrame form = new FormularioTurnoFrame();
            form.setVisible(true);
            // Cuando se cierra el formulario, volvemos a cargar la lista para que aparezca el nuevo turno
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    cargarTurnos();
                }
            });
        });

        botonesPanel.add(btnExportar);
        botonesPanel.add(btnAgregar);

        topPanel.add(titulosPanel, BorderLayout.CENTER);
        topPanel.add(botonesPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ScrollPane para que se pueda bajar con el mouse si hay muchos turnos
        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // Cargamos los turnos al iniciar la ventana
        cargarTurnos();
    }

    // Método que genera un archivo de texto con los turnos
    private void exportarTurnos() {
        List<Turno> listaTurnos = control.traerTurnos();
        if (listaTurnos == null || listaTurnos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay turnos para exportar.", "Agenda vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Agenda");
        fileChooser.setSelectedFile(new File("Agenda_Turnos.txt"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivoToSave = fileChooser.getSelectedFile();
            try (PrintWriter out = new PrintWriter(archivoToSave)) {
                out.println("==========================================");
                out.println("          LA ESMALTERÍA - AGENDA          ");
                out.println("==========================================");
                out.println("");

                for (Turno t : listaTurnos) {
                    out.println("CLIENTE: " + t.getCliente() + " | TEL: " + t.getTelefono());
                    out.println("FECHA: " + t.getFecha() + " | HORA: " + t.getHora() + " hs");
                    String servNombre = t.getServicio() != null ? t.getServicio().getNombre() : "Ninguno";
                    out.println("SERVICIO: " + servNombre);
                    out.println("ESTADO: " + t.getEstado());
                    out.println("------------------------------------------");
                }

                JOptionPane.showMessageDialog(this, "Exportación exitosa:\n" + archivoToSave.getAbsolutePath(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método que actualiza la lista de turnos que se ven en pantalla
    private void cargarTurnos() {
        contenedor.removeAll(); // Borra las tarjetas anteriores
        contenedor.add(Box.createVerticalStrut(20));

        // Solicita todos los turnos a la Base de Datos
        List<Turno> listaTurnos = control.traerTurnos();

        if (listaTurnos != null && !listaTurnos.isEmpty()) {
            for (Turno turno : listaTurnos) {
                // Crea una tarjeta por cada turno encontrado
                contenedor.add(crearTurnoCard(turno));
                contenedor.add(Box.createVerticalStrut(10)); // Espacio entre tarjetas
            }
        } else {
            JLabel lblVacio = new JLabel("No hay turnos agendados.");
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenedor.add(lblVacio);
        }

        // Obliga a la ventana a redibujarse para mostrar los cambios
        contenedor.revalidate();
        contenedor.repaint();
    }

    // Método que diseña la Tarjeta visual de un turno específico
    private JPanel crearTurnoCard(Turno turno) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(750, 100));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new EmptyBorder(10, 20, 10, 20),
                new LineBorder(new Color(230, 230, 230), 1, true)
        ));

        // === Lado Izquierdo: Cliente, Fecha, Hora, Servicio y Estado ===
        JPanel izquierda = new JPanel();
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
        izquierda.setBackground(Color.WHITE);

        JLabel lblCliente = new JLabel(turno.getCliente() + " (Tel: " + turno.getTelefono() + ")");
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Si el turno no tiene servicio asignado por algún error de la BD, dice "Ninguno"
        String nombreServicio = turno.getServicio() != null ? turno.getServicio().getNombre() : "Ninguno";
        JLabel lblDetalle = new JLabel(turno.getFecha() + " - " + turno.getHora() + " hs | Servicio: " + nombreServicio);
        lblDetalle.setForeground(Color.GRAY);
        
        JLabel lblEstado = new JLabel("Estado: " + turno.getEstado());
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        
        // Cambia el color del texto dependiendo del estado del turno
        if (turno.getEstado() != null && turno.getEstado().contains("Pagado")) {
            lblEstado.setForeground(new Color(34, 139, 34)); // Verde Oscuro
        } else if (turno.getEstado() != null && turno.getEstado().contains("Cancelado")) {
            lblEstado.setForeground(Color.RED); // Rojo
        } else {
            lblEstado.setForeground(Color.ORANGE); // Naranja para Pendiente de Pago
        }

        izquierda.add(lblCliente);
        izquierda.add(lblDetalle);
        izquierda.add(lblEstado);

        // === Lado Derecho: Botones de acción (Cobrar, Editar, Eliminar) ===
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        derecha.setBackground(Color.WHITE);

        JButton btnCobrar = new JButton("Cobrar");
        // Deshabilita el botón si el turno ya figura como pagado
        if (turno.getEstado() != null && turno.getEstado().contains("Pagado")) {
            btnCobrar.setEnabled(false);
        }
        btnCobrar.addActionListener(e -> {
            // Abre la ventanita modal de pago (Modal bloquea la agenda hasta que se cierre)
            PagoDialog pago = new PagoDialog(this, true, turno.getId());
            pago.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    cargarTurnos(); // Actualiza si el estado cambió a Pagado
                }
            });
            pago.setVisible(true);
        });

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> {
            // Abre el formulario en Modo Edición pasándole el ID del turno
            FormularioTurnoFrame form = new FormularioTurnoFrame(turno.getId());
            form.setVisible(true);
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    cargarTurnos();
                }
            });
        });

        JButton btnEliminar = new JButton("X");
        btnEliminar.setForeground(Color.RED);
        btnEliminar.setToolTipText("Eliminar turno");
        btnEliminar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea eliminar este turno?", 
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                control.eliminarTurno(turno.getId()); // Borra en Base de Datos
                cargarTurnos(); // Refresca UI
            }
        });

        derecha.add(btnCobrar);
        derecha.add(btnEditar);
        derecha.add(btnEliminar);

        // Ensambla ambas partes en la tarjeta
        card.add(izquierda, BorderLayout.WEST);
        card.add(derecha, BorderLayout.EAST);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        return card;
    }
}
