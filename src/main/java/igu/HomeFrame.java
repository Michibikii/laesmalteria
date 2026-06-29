package igu;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    public HomeFrame() {
        // Configuración básica de la ventana
        setTitle("LA ESMALTERÍA - Sistema de Gestión");
        setSize(900, 600);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla al abrir
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Finaliza la ejecución del programa al cerrar

        // Panel principal que divide la ventana en dos (Izquierda y Derecha)
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // ==========================================
        // PANEL IZQUIERDO: Marca y Diseño Visual
        // ==========================================
        Color colorMarca = new Color(252, 228, 236); // Color rosa pastel
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(colorMarca);
        panelIzquierdo.setPreferredSize(new Dimension(400, 600));
        panelIzquierdo.setLayout(new GridBagLayout()); // GridBagLayout permite centrar los elementos

        // Panel interno para apilar los textos verticalmente
        JPanel panelTextos = new JPanel();
        panelTextos.setLayout(new BoxLayout(panelTextos, BoxLayout.Y_AXIS));
        panelTextos.setBackground(colorMarca);

        JLabel lblTitulo = new JLabel("La Esmaltería");
        lblTitulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 42));
        lblTitulo.setForeground(new Color(48, 48, 48));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("SISTEMA DE GESTIÓN");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregamos los textos al panel izquierdo con espacios intermedios
        // (VerticalStrut)
        panelTextos.add(lblTitulo);
        panelTextos.add(Box.createVerticalStrut(10));
        panelTextos.add(lblSubtitulo);

        panelIzquierdo.add(panelTextos);

        // ==========================================
        // PANEL DERECHO: Botones de Acción (Menú)
        // ==========================================
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setLayout(new GridBagLayout());

        // Panel para apilar los botones verticalmente
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(Color.WHITE);

        JLabel lblBienvenido = new JLabel("¡Bienvenido!");
        lblBienvenido.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        lblBienvenido.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInstruccion = new JLabel("¿Qué deseas hacer hoy?");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInstruccion.setForeground(Color.GRAY);
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creamos los botones usando el método auxiliar de abajo para que todos sean
        // idénticos
        JButton btnCatalogo = crearBotonMenu("Ver Catálogo de Servicios");
        JButton btnAgenda = crearBotonMenu("Gestionar Agenda (Turnos)");
        JButton btnSalir = crearBotonMenu("Salir del Sistema");

        // El botón salir tiene un color distinto para destacarlo del resto
        btnSalir.setBackground(new Color(240, 240, 240));
        btnSalir.setForeground(new Color(80, 80, 80));

        // Agregamos todo al panel de botones
        panelBotones.add(lblBienvenido);
        panelBotones.add(Box.createVerticalStrut(5));
        panelBotones.add(lblInstruccion);
        panelBotones.add(Box.createVerticalStrut(40));

        panelBotones.add(btnCatalogo);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnAgenda);
        panelBotones.add(Box.createVerticalStrut(40));
        panelBotones.add(btnSalir);

        panelDerecho.add(panelBotones);

        // ==========================================
        // ENSAMBLAJE FINAL
        // ==========================================
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        add(panelPrincipal);

        // ==========================================
        // EVENTOS DE LOS BOTONES (Acciones al hacer clic)
        // ==========================================
        btnCatalogo.addActionListener(e -> new CatalogoFrame().setVisible(true));
        btnAgenda.addActionListener(e -> new AgendaFrame().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0)); // Cierra toda la ejecución
    }

    // Método auxiliar para crear botones estilizados con el mismo formato
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(40, 40, 40)); // Gris oscuro moderno
        btn.setFocusPainted(false); // Quita el feo recuadro de selección
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia el cursor a "manito"
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 45)); // Fija un tamaño estándar para todos
        return btn;
    }
}
