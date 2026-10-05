package cl.duoc.view;

import cl.duoc.dao.RepartidorDAO;
import cl.duoc.model.Repartidor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PanelRepartidores extends JFrame {

    private JTextField txtNombre;
    private JTable tabla;
    private DefaultTableModel modelo;
    private RepartidorDAO repartidorDAO;

    public PanelRepartidores() {

        repartidorDAO = new RepartidorDAO();

        setTitle("Repartidores");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 80, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(100, 30, 200, 25);
        add(txtNombre);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(320, 30, 100, 25);
        add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(430, 30, 120, 25);
        add(btnActualizar);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");

        tabla = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 80, 520, 300);
        add(scrollPane);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(30, 400, 120, 30);
        add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(165, 400, 120, 30);
        add(btnLimpiar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(430, 400, 120, 30);
        add(btnCerrar);

        tabla.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {
                    String nombre = modelo.getValueAt(fila, 1).toString();
                    txtNombre.setText(nombre);
                }
            }
        });

        btnAgregar.addActionListener(e -> agregar());

        btnActualizar.addActionListener(e -> actualizar());

        btnEliminar.addActionListener(e -> eliminar());

        btnLimpiar.addActionListener(e -> limpiar());

        btnCerrar.addActionListener(e -> dispose());

        cargarTabla();
    }

    private void cargarTabla() {

        modelo.setRowCount(0);

        try {

            List<Repartidor> repartidores = repartidorDAO.readAll();

            for (Repartidor repartidor : repartidores) {

                modelo.addRow(new Object[]{
                        repartidor.getId(),
                        repartidor.getNombre()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void agregar() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre."
            );

            return;
        }

        try {

            Repartidor repartidor = new Repartidor(nombre);

            repartidorDAO.create(repartidor);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor agregado correctamente."
            );

            limpiar();
            cargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizar() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor."
            );

            return;
        }

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre."
            );

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        try {

            Repartidor repartidor = new Repartidor(id, nombre);

            repartidorDAO.update(repartidor);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente."
            );

            limpiar();
            cargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminar() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor."
            );

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el repartidor seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            repartidorDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente."
            );

            limpiar();
            cargarTabla();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiar() {

        txtNombre.setText("");
        tabla.clearSelection();
    }
}

