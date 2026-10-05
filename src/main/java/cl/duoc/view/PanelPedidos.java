package cl.duoc.view;

import cl.duoc.dao.PedidoDAO;
import cl.duoc.model.EstadoPedido;
import cl.duoc.model.Pedido;
import cl.duoc.model.TipoPedido;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PanelPedidos extends JFrame {

    private JTextField txtDireccion;
    private JComboBox<TipoPedido> comboTipo;
    private JComboBox<EstadoPedido> comboEstado;
    private JTable tabla;
    private DefaultTableModel modelo;
    private PedidoDAO pedidoDAO;

    public PanelPedidos() {

        pedidoDAO = new PedidoDAO();

        setTitle("Pedidos");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(30, 30, 80, 25);
        add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(100, 30, 180, 25);
        add(txtDireccion);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(300, 30, 50, 25);
        add(lblTipo);

        comboTipo = new JComboBox<>(TipoPedido.values());
        comboTipo.setBounds(340, 30, 130, 25);
        add(comboTipo);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(490, 30, 60, 25);
        add(lblEstado);

        comboEstado = new JComboBox<>(EstadoPedido.values());
        comboEstado.setBounds(550, 30, 130, 25);
        add(comboEstado);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(200, 70, 100, 25);
        add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(310, 70, 120, 25);
        add(btnActualizar);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Dirección");
        modelo.addColumn("Tipo");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 115, 680, 300);
        add(scrollPane);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(30, 430, 120, 30);
        add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(165, 430, 120, 30);
        add(btnLimpiar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(590, 430, 120, 30);
        add(btnCerrar);

        tabla.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {

                    txtDireccion.setText(
                            modelo.getValueAt(fila, 1).toString()
                    );

                    comboTipo.setSelectedItem(
                            TipoPedido.valueOf(
                                    modelo.getValueAt(fila, 2).toString()
                            )
                    );

                    comboEstado.setSelectedItem(
                            EstadoPedido.valueOf(
                                    modelo.getValueAt(fila, 3).toString()
                            )
                    );
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

            List<Pedido> pedidos = pedidoDAO.readAll();

            for (Pedido pedido : pedidos) {

                modelo.addRow(new Object[]{
                        pedido.getId(),
                        pedido.getDireccion(),
                        pedido.getTipo(),
                        pedido.getEstado()
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

        String direccion = txtDireccion.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección."
            );

            return;
        }

        TipoPedido tipo = (TipoPedido) comboTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) comboEstado.getSelectedItem();

        try {

            Pedido pedido = new Pedido(
                    direccion,
                    tipo,
                    estado
            );

            pedidoDAO.create(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido agregado correctamente."
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
                    "Seleccione un pedido."
            );

            return;
        }

        String direccion = txtDireccion.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar una dirección."
            );

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        TipoPedido tipo = (TipoPedido) comboTipo.getSelectedItem();
        EstadoPedido estado = (EstadoPedido) comboEstado.getSelectedItem();

        try {

            Pedido pedido = new Pedido(
                    id,
                    direccion,
                    tipo,
                    estado
            );

            pedidoDAO.update(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente."
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
                    "Seleccione un pedido."
            );

            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el pedido seleccionado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            pedidoDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente."
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

        txtDireccion.setText("");
        comboTipo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        tabla.clearSelection();
    }
}

