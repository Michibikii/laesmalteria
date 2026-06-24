package igu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ContactoDialog extends JDialog {

    public ContactoDialog(JFrame padre) {
        super(padre, true);
        setTitle("Información de Contacto");
        setSize(400, 420);
        setLocationRelativeTo(padre);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        // ==========================================
        // HEADER (Encabezado)
        // ==========================================
        JPanel header = new JPanel();
        header.setBackground(new Color(252, 228, 236)); // Mismo rosa pastel del Home
        header.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel lblTitulo = new JLabel("La Esmaltería");
        lblTitulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 28));
        lblTitulo.setForeground(new Color(48, 48, 48));
        header.add(lblTitulo);

        // ==========================================
        // BODY (Cuerpo con la info)
        // ==========================================
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblSubtitulo = new JLabel("¡Reserva tu turno con nosotros!");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInsta = new JLabel("Instagram: @la.esmalterianails_");
        lblInsta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInsta.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblWsp = new JLabel("WhatsApp: 3764840557");
        lblWsp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblWsp.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDir = new JLabel("Dirección: Posadas, Misiones");
        lblDir.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDir.setAlignmentX(Component.CENTER_ALIGNMENT);

        body.add(lblSubtitulo);
        body.add(Box.createVerticalStrut(25));
        body.add(lblInsta);
        body.add(Box.createVerticalStrut(15));
        body.add(lblWsp);
        body.add(Box.createVerticalStrut(15));
        body.add(lblDir);

        // ==========================================
        // FOOTER (Botón cerrar)
        // ==========================================
        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);
        footer.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(40, 40, 40));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(150, 35));
        btnCerrar.addActionListener(e -> dispose());

        footer.add(btnCerrar);

        // Ensamblaje
        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(body, BorderLayout.CENTER);
        panelPrincipal.add(footer, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}
