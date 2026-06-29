package igu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import logica.Controladora;
import logica.Servicio;
import logica.Turno;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormularioTurnoFrame extends JFrame {

    private Controladora control;
    private Turno turnoAEditar; // null para alta
    private boolean esModificacion = false;

    private JTextField txtCliente;
    private JTextField txtTelefono;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbServicios;
    private List<Servicio> listaServicios;
    
    private JButton btnGuardar;
    private JButton btnCancelar;

    // Alta
    public FormularioTurnoFrame() {
        control = new Controladora();
        this.esModificacion = false;
        initComponents();
        cargarServiciosEnCombo();
    }

    // Edicion
    public FormularioTurnoFrame(Long id_turno) {
        control = new Controladora();
        this.esModificacion = true;
        this.turnoAEditar = control.buscarTurno(id_turno);
        initComponents();
        cargarServiciosEnCombo();
        cargarDatosPrevios();
    }

    private void initComponents() {
        setTitle(esModificacion ? "Editar Turno" : "Agregar Nuevo Turno");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(245, 245, 245));
        add(panelPrincipal);

        JLabel lblTitulo = new JLabel(esModificacion ? "Editar Turno" : "Agregar Turno");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(110, 20, 200, 30);
        panelPrincipal.add(lblTitulo);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(50, 70, 80, 25);
        panelPrincipal.add(lblCliente);
        txtCliente = new JTextField();
        txtCliente.setBounds(150, 70, 180, 25);
        panelPrincipal.add(txtCliente);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(50, 110, 80, 25);
        panelPrincipal.add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(150, 110, 180, 25);
        panelPrincipal.add(txtTelefono);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(50, 150, 80, 25);
        panelPrincipal.add(lblFecha);
        
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setAllowKeyboardEditing(false);
        dateSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
        datePicker = new DatePicker(dateSettings);
        dateSettings.setVetoPolicy(new DateVetoPolicy() {
            @Override
            public boolean isDateAllowed(LocalDate date) {
                return !date.isBefore(LocalDate.now());
            }
        });
        datePicker.setBounds(150, 150, 180, 25);
        panelPrincipal.add(datePicker);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(50, 190, 100, 25);
        panelPrincipal.add(lblHora);
        
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setAllowKeyboardEditing(false);
        timeSettings.setFormatForDisplayTime("HH:mm");
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.ThirtyMinutes, null, null);
        timePicker = new TimePicker(timeSettings);
        timePicker.setBounds(150, 190, 180, 25);
        panelPrincipal.add(timePicker);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(50, 230, 80, 25);
        panelPrincipal.add(lblEstado);
        cmbEstado = new JComboBox<>(new String[]{"Pendiente de Pago", "Pagado", "Cancelado"});
        cmbEstado.setBounds(150, 230, 180, 25);
        panelPrincipal.add(cmbEstado);
        
        JLabel lblServicio = new JLabel("Servicio:");
        lblServicio.setBounds(50, 270, 80, 25);
        panelPrincipal.add(lblServicio);
        cmbServicios = new JComboBox<>();
        cmbServicios.setBounds(150, 270, 180, 25);
        panelPrincipal.add(cmbServicios);

        btnGuardar = new JButton(esModificacion ? "Guardar Cambios" : "Guardar");
        btnGuardar.setBounds(60, 320, 140, 35);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGuardarActionPerformed(e);
            }
        });
        panelPrincipal.add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(210, 320, 100, 35);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelPrincipal.add(btnCancelar);
    }
    
    private void cargarServiciosEnCombo() {
        listaServicios = control.traerServicios();
        if (listaServicios != null) {
            for (Servicio s : listaServicios) {
                cmbServicios.addItem(s.getNombre() + " ($" + s.getPrecio() + ")");
            }
        }
    }

    private void cargarDatosPrevios() {
        if (turnoAEditar != null) {
            txtCliente.setText(turnoAEditar.getCliente());
            txtTelefono.setText(turnoAEditar.getTelefono());
            
            try {
                if (turnoAEditar.getFecha() != null && !turnoAEditar.getFecha().isEmpty()) {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    datePicker.setDate(LocalDate.parse(turnoAEditar.getFecha(), dateFormatter));
                }
                if (turnoAEditar.getHora() != null && !turnoAEditar.getHora().isEmpty()) {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    timePicker.setTime(LocalTime.parse(turnoAEditar.getHora(), timeFormatter));
                }
            } catch (Exception ex) {
                // Ignore parse errors
            }
            
            cmbEstado.setSelectedItem(turnoAEditar.getEstado());

            if (turnoAEditar.getServicio() != null && listaServicios != null) {
                Long idServicioTurno = turnoAEditar.getServicio().getId();
                for (int i = 0; i < listaServicios.size(); i++) {
                    if (listaServicios.get(i).getId().equals(idServicioTurno)) {
                        cmbServicios.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    private void btnGuardarActionPerformed(ActionEvent evt) {
        String cliente = txtCliente.getText();
        String telefono = txtTelefono.getText();
        String estado = (String) cmbEstado.getSelectedItem();
        int indexServicio = cmbServicios.getSelectedIndex();
        
        LocalDate localDate = datePicker.getDate();
        LocalTime localTime = timePicker.getTime();

        if (cliente.isEmpty() || telefono.isEmpty() || localDate == null || localTime == null || estado.isEmpty() || indexServicio == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione un servicio.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String fecha = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String hora = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        Servicio servicioSeleccionado = listaServicios.get(indexServicio);

        if (esModificacion) {
            if (turnoAEditar != null) {
                turnoAEditar.setCliente(cliente);
                turnoAEditar.setTelefono(telefono);
                turnoAEditar.setFecha(fecha);
                turnoAEditar.setHora(hora);
                turnoAEditar.setEstado(estado);
                turnoAEditar.setServicio(servicioSeleccionado);
                control.editarTurno(turnoAEditar);
                JOptionPane.showMessageDialog(this, "Turno modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            Turno turno = new Turno();
            turno.setCliente(cliente);
            turno.setTelefono(telefono);
            turno.setFecha(fecha);
            turno.setHora(hora);
            turno.setEstado(estado);
            turno.setServicio(servicioSeleccionado);
            control.guardarTurno(turno);
            JOptionPane.showMessageDialog(this, "Turno guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
        
        this.dispose();
    }
}
