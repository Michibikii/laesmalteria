package igu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.Controladora;
import logica.Turno;

public class PagoDialog extends JDialog {

    private Controladora control;
    private Turno turnoACobrar;

    private JLabel lblTotal;
    private JComboBox<String> cmbMetodoPago;
    private JButton btnConfirmar;
    private JButton btnCancelar;

    public PagoDialog(Frame parent, boolean modal, Long idTurno) {
        super(parent, modal);
        control = new Controladora();
        turnoACobrar = control.buscarTurno(idTurno);
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Pago");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        add(panel);

        JLabel lblTitulo = new JLabel("Registrar Pago");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setBounds(100, 15, 200, 30);
        panel.add(lblTitulo);

        // Obtenemos el precio del servicio vinculado al turno
        double precio = (turnoACobrar.getServicio() != null) ? turnoACobrar.getServicio().getPrecio() : 0.0;
        
        lblTotal = new JLabel("Total a cobrar: $" + precio);
        lblTotal.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTotal.setBounds(50, 60, 250, 25);
        panel.add(lblTotal);

        JLabel lblMetodo = new JLabel("Método:");
        lblMetodo.setBounds(50, 100, 80, 25);
        panel.add(lblMetodo);

        cmbMetodoPago = new JComboBox<>(new String[]{"Efectivo", "Transferencia", "MercadoPago", "Tarjeta"});
        cmbMetodoPago.setBounds(120, 100, 150, 25);
        panel.add(cmbMetodoPago);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(50, 160, 110, 35);
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarPago();
            }
        });
        panel.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(170, 160, 100, 35);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnCancelar);
    }

    private void confirmarPago() {
        String metodo = (String) cmbMetodoPago.getSelectedItem();
        
        // El diagrama de clases indica que el estado debe ser exactamente "Pagado"
        turnoACobrar.setEstado("Pagado");
        control.editarTurno(turnoACobrar);

        JOptionPane.showMessageDialog(this, "Pago registrado correctamente en " + metodo + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        this.dispose(); // Cierra el diálogo
    }
}
