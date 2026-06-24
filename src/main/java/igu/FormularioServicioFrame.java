package igu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logica.Controladora;
import logica.Servicio;

public class FormularioServicioFrame extends JFrame {

    private Controladora control;
    private Servicio servicioAEditar; // Será null si es un alta
    private boolean esModificacion = false;

    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtDuracion;
    private JTextField txtPrecio;
    private JButton btnGuardar;
    private JButton btnCancelar;

    // Constructor para ALTA
    public FormularioServicioFrame() {
        control = new Controladora();
        this.esModificacion = false;
        initComponents();
    }

    // Constructor para EDICIÓN
    public FormularioServicioFrame(Long id_servicio) {
        control = new Controladora();
        this.esModificacion = true;
        this.servicioAEditar = control.buscarServicio(id_servicio);
        initComponents();
        cargarDatosPrevios();
    }

    private void initComponents() {
        String tituloVentana = esModificacion ? "Editar Servicio" : "Agregar Nuevo Servicio";
        setTitle(tituloVentana);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(245, 245, 245));
        add(panelPrincipal);

        JLabel lblTitulo = new JLabel(esModificacion ? "Editar Servicio" : "Agregar Servicio");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(100, 20, 250, 30);
        panelPrincipal.add(lblTitulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(50, 80, 80, 25);
        panelPrincipal.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(150, 80, 180, 25);
        panelPrincipal.add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(50, 120, 80, 25);
        panelPrincipal.add(lblCategoria);
        txtCategoria = new JTextField();
        txtCategoria.setBounds(150, 120, 180, 25);
        panelPrincipal.add(txtCategoria);

        JLabel lblDuracion = new JLabel("Duración (min):");
        lblDuracion.setBounds(50, 160, 100, 25);
        panelPrincipal.add(lblDuracion);
        txtDuracion = new JTextField();
        txtDuracion.setBounds(150, 160, 180, 25);
        panelPrincipal.add(txtDuracion);

        JLabel lblPrecio = new JLabel("Precio ($):");
        lblPrecio.setBounds(50, 200, 80, 25);
        panelPrincipal.add(lblPrecio);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(150, 200, 180, 25);
        panelPrincipal.add(txtPrecio);

        btnGuardar = new JButton(esModificacion ? "Guardar Cambios" : "Guardar");
        btnGuardar.setBounds(60, 260, 140, 35);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGuardarActionPerformed(e);
            }
        });
        panelPrincipal.add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 260, 100, 35);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelPrincipal.add(btnCancelar);
    }

    private void cargarDatosPrevios() {
        if (servicioAEditar != null) {
            txtNombre.setText(servicioAEditar.getNombre());
            txtCategoria.setText(servicioAEditar.getCategoria());
            txtDuracion.setText(String.valueOf(servicioAEditar.getDuracion()));
            txtPrecio.setText(String.valueOf(servicioAEditar.getPrecio()));
        }
    }

    private void btnGuardarActionPerformed(ActionEvent evt) {
        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        String duracionStr = txtDuracion.getText();
        String precioStr = txtPrecio.getText();

        if (nombre.isEmpty() || categoria.isEmpty() || duracionStr.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int duracion = Integer.parseInt(duracionStr);
            double precio = Double.parseDouble(precioStr);

            if (esModificacion) {
                if (servicioAEditar != null) {
                    servicioAEditar.setNombre(nombre);
                    servicioAEditar.setCategoria(categoria);
                    servicioAEditar.setDuracion(duracion);
                    servicioAEditar.setPrecio(precio);
                    control.editarServicio(servicioAEditar);
                    JOptionPane.showMessageDialog(this, "Servicio modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                Servicio nuevoServicio = new Servicio();
                nuevoServicio.setNombre(nombre);
                nuevoServicio.setCategoria(categoria);
                nuevoServicio.setDuracion(duracion);
                nuevoServicio.setPrecio(precio);
                control.guardarServicio(nuevoServicio);
                JOptionPane.showMessageDialog(this, "Servicio guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración y el precio deben ser números válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
