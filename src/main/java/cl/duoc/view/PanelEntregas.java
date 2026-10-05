package cl.duoc.view;

import cl.duoc.dao.EntregaDAO;
import cl.duoc.dao.PedidoDAO;
import cl.duoc.dao.RepartidorDAO;
import cl.duoc.model.Entrega;
import cl.duoc.model.Pedido;
import cl.duoc.model.Repartidor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PanelEntregas extends JFrame {

    private JTextField txtIdPedido;
    private JTextField txtIdRepartidor;

    private JComboBox<Integer> comboDia;
    private JComboBox<Integer> comboMes;
    private JComboBox<Integer> comboAnio;

    private JComboBox<Integer> comboHora;
    private JComboBox<Integer> comboMinuto;

    private JTable tablaEntregas;
    private JTable tablaPedidos;
    private JTable tablaRepartidores;

    private DefaultTableModel modeloEntregas;
    private DefaultTableModel modeloPedidos;
    private DefaultTableModel modeloRepartidores;

    private EntregaDAO entregaDAO;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;

    public PanelEntregas() {

        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        setTitle("Entregas");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblRepartidores = new JLabel("Repartidores disponibles");
        lblRepartidores.setBounds(30, 20, 200, 25);
        add(lblRepartidores);

        modeloRepartidores = new DefaultTableModel();
        modeloRepartidores.addColumn("Código");
        modeloRepartidores.addColumn("Nombre");

        tablaRepartidores = new JTable(modeloRepartidores);
        tablaRepartidores.setAutoCreateRowSorter(true);

        JScrollPane scrollRepartidores = new JScrollPane(tablaRepartidores);
        scrollRepartidores.setBounds(30, 50, 400, 150);
        add(scrollRepartidores);

        JLabel lblPedidos = new JLabel("Pedidos disponibles");
        lblPedidos.setBounds(470, 20, 200, 25);
        add(lblPedidos);

        modeloPedidos = new DefaultTableModel();
        modeloPedidos.addColumn("Código");
        modeloPedidos.addColumn("Dirección");
        modeloPedidos.addColumn("Tipo");
        modeloPedidos.addColumn("Estado");

        tablaPedidos = new JTable(modeloPedidos);
        tablaPedidos.setAutoCreateRowSorter(true);

        JScrollPane scrollPedidos = new JScrollPane(tablaPedidos);
        scrollPedidos.setBounds(470, 50, 580, 150);
        add(scrollPedidos);

        JLabel lblIdPedido = new JLabel("Código Pedido:");
        lblIdPedido.setBounds(30, 225, 100, 25);
        add(lblIdPedido);

        txtIdPedido = new JTextField();
        txtIdPedido.setBounds(130, 225, 100, 25);
        add(txtIdPedido);

        JLabel lblIdRepartidor = new JLabel("Código Repartidor:");
        lblIdRepartidor.setBounds(250, 225, 120, 25);
        add(lblIdRepartidor);

        txtIdRepartidor = new JTextField();
        txtIdRepartidor.setBounds(370, 225, 100, 25);
        add(txtIdRepartidor);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(490, 225, 50, 25);
        add(lblFecha);

        comboDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            comboDia.addItem(i);
        }
        comboDia.setBounds(540, 225, 60, 25);
        add(comboDia);

        comboMes = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            comboMes.addItem(i);
        }
        comboMes.setBounds(605, 225, 60, 25);
        add(comboMes);

        comboAnio = new JComboBox<>();
        for (int i = 2025; i <= 2035; i++) {
            comboAnio.addItem(i);
        }
        comboAnio.setBounds(670, 225, 75, 25);
        add(comboAnio);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(760, 225, 45, 25);
        add(lblHora);

        comboHora = new JComboBox<>();
        for (int i = 0; i <= 23; i++) {
            comboHora.addItem(i);
        }
        comboHora.setBounds(805, 225, 60, 25);
        add(comboHora);

        comboMinuto = new JComboBox<>();
        for (int i = 0; i <= 59; i++) {
            comboMinuto.addItem(i);
        }
        comboMinuto.setBounds(870, 225, 60, 25);
        add(comboMinuto);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(30, 265, 100, 30);
        add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(140, 265, 120, 30);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(270, 265, 120, 30);
        add(btnEliminar);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(400, 265, 120, 30);
        add(btnLimpiar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(930, 265, 120, 30);
        add(btnCerrar);

        JLabel lblEntregas = new JLabel("Entregas registradas");
        lblEntregas.setBounds(30, 320, 200, 25);
        add(lblEntregas);

        modeloEntregas = new DefaultTableModel();

        modeloEntregas.addColumn("Código");
        modeloEntregas.addColumn("Código Pedido");
        modeloEntregas.addColumn("Dirección");
        modeloEntregas.addColumn("Tipo");
        modeloEntregas.addColumn("Estado");
        modeloEntregas.addColumn("Código Repartidor");
        modeloEntregas.addColumn("Fecha");
        modeloEntregas.addColumn("Hora");

        tablaEntregas = new JTable(modeloEntregas);
        tablaEntregas.setAutoCreateRowSorter(true);

        JScrollPane scrollEntregas = new JScrollPane(tablaEntregas);
        scrollEntregas.setBounds(30, 350, 1020, 250);
        add(scrollEntregas);

        tablaRepartidores.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int filaVista = tablaRepartidores.getSelectedRow();

                if (filaVista != -1) {

                    int filaModelo =
                            tablaRepartidores.convertRowIndexToModel(filaVista);

                    txtIdRepartidor.setText(
                            modeloRepartidores
                                    .getValueAt(filaModelo, 0)
                                    .toString()
                    );
                }
            }
        });

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int filaVista = tablaPedidos.getSelectedRow();

                if (filaVista != -1) {

                    int filaModelo =
                            tablaPedidos.convertRowIndexToModel(filaVista);

                    txtIdPedido.setText(
                            modeloPedidos
                                    .getValueAt(filaModelo, 0)
                                    .toString()
                    );
                }
            }
        });

        tablaEntregas.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int filaVista = tablaEntregas.getSelectedRow();

                if (filaVista != -1) {

                    int filaModelo =
                            tablaEntregas.convertRowIndexToModel(filaVista);

                    txtIdPedido.setText(
                            modeloEntregas
                                    .getValueAt(filaModelo, 1)
                                    .toString()
                    );

                    txtIdRepartidor.setText(
                            modeloEntregas
                                    .getValueAt(filaModelo, 5)
                                    .toString()
                    );

                    LocalDate fecha = LocalDate.parse(
                            modeloEntregas
                                    .getValueAt(filaModelo, 6)
                                    .toString()
                    );

                    LocalTime hora = LocalTime.parse(
                            modeloEntregas
                                    .getValueAt(filaModelo, 7)
                                    .toString()
                    );

                    comboDia.setSelectedItem(fecha.getDayOfMonth());
                    comboMes.setSelectedItem(fecha.getMonthValue());
                    comboAnio.setSelectedItem(fecha.getYear());

                    comboHora.setSelectedItem(hora.getHour());
                    comboMinuto.setSelectedItem(hora.getMinute());
                }
            }
        });

        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnCerrar.addActionListener(e -> dispose());

        cargarRepartidores();
        cargarPedidos();
        cargarEntregas();
    }

    private void cargarRepartidores() {

        modeloRepartidores.setRowCount(0);

        try {

            List<Repartidor> repartidores = repartidorDAO.readAll();

            for (Repartidor repartidor : repartidores) {

                modeloRepartidores.addRow(new Object[]{
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

    private void cargarPedidos() {

        modeloPedidos.setRowCount(0);

        try {

            List<Pedido> pedidos = pedidoDAO.readAll();

            for (Pedido pedido : pedidos) {

                modeloPedidos.addRow(new Object[]{
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

    private void cargarEntregas() {

        modeloEntregas.setRowCount(0);

        try {

            List<Entrega> entregas = entregaDAO.readAll();
            List<Pedido> pedidos = pedidoDAO.readAll();
            List<Repartidor> repartidores = repartidorDAO.readAll();

            for (Entrega entrega : entregas) {

                Pedido pedidoEncontrado = null;

                for (Pedido pedido : pedidos) {

                    if (pedido.getId() == entrega.getIdPedido()) {
                        pedidoEncontrado = pedido;
                        break;
                    }
                }

                Repartidor repartidorEncontrado = null;

                for (Repartidor repartidor : repartidores) {

                    if (repartidor.getId() == entrega.getIdRepartidor()) {
                        repartidorEncontrado = repartidor;
                        break;
                    }
                }

                if (pedidoEncontrado != null && repartidorEncontrado != null) {

                    modeloEntregas.addRow(new Object[]{
                            entrega.getId(),
                            pedidoEncontrado.getId(),
                            pedidoEncontrado.getDireccion(),
                            pedidoEncontrado.getTipo(),
                            pedidoEncontrado.getEstado(),
                            repartidorEncontrado.getId(),
                            entrega.getFecha(),
                            entrega.getHora()
                    });
                }
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

    private LocalDate obtenerFecha() {

        int dia = (int) comboDia.getSelectedItem();
        int mes = (int) comboMes.getSelectedItem();
        int anio = (int) comboAnio.getSelectedItem();

        return LocalDate.of(anio, mes, dia);
    }

    private LocalTime obtenerHora() {

        int hora = (int) comboHora.getSelectedItem();
        int minuto = (int) comboMinuto.getSelectedItem();

        return LocalTime.of(hora, minuto);
    }

    private void agregar() {

        try {

            int idPedido = Integer.parseInt(
                    txtIdPedido.getText().trim()
            );

            int idRepartidor = Integer.parseInt(
                    txtIdRepartidor.getText().trim()
            );

            LocalDate fecha = obtenerFecha();
            LocalTime hora = obtenerHora();

            Entrega entrega = new Entrega(
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            entregaDAO.create(entrega);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega agregada correctamente."
            );

            limpiar();
            cargarEntregas();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Los códigos deben ser números.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Verifique los datos ingresados.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void actualizar() {

        int filaVista = tablaEntregas.getSelectedRow();

        if (filaVista == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        int filaModelo =
                tablaEntregas.convertRowIndexToModel(filaVista);

        try {

            int id = (int) modeloEntregas.getValueAt(filaModelo, 0);

            int idPedido = Integer.parseInt(
                    txtIdPedido.getText().trim()
            );

            int idRepartidor = Integer.parseInt(
                    txtIdRepartidor.getText().trim()
            );

            LocalDate fecha = obtenerFecha();
            LocalTime hora = obtenerHora();

            Entrega entrega = new Entrega(
                    id,
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            entregaDAO.update(entrega);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada correctamente."
            );

            limpiar();
            cargarEntregas();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Los códigos deben ser números.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Verifique los datos ingresados.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminar() {

        int filaVista = tablaEntregas.getSelectedRow();

        if (filaVista == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        int filaModelo =
                tablaEntregas.convertRowIndexToModel(filaVista);

        int id = (int) modeloEntregas.getValueAt(filaModelo, 0);

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar la entrega seleccionada?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            entregaDAO.delete(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente."
            );

            limpiar();
            cargarEntregas();

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

        txtIdPedido.setText("");
        txtIdRepartidor.setText("");

        comboDia.setSelectedIndex(0);
        comboMes.setSelectedIndex(0);
        comboAnio.setSelectedIndex(0);

        comboHora.setSelectedIndex(0);
        comboMinuto.setSelectedIndex(0);

        tablaEntregas.clearSelection();
        tablaPedidos.clearSelection();
        tablaRepartidores.clearSelection();
    }
}


