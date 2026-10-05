package cl.duoc.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

    private JLabel lblTitulo;
    private JButton btnRepartidores;
    private JButton btnPedidos;
    private JButton btnEntregas;
    private JButton btnSalir;

    public MainFrame() {

        setTitle("SpeedFast");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        lblTitulo = new JLabel("SPEEDFAST");
        lblTitulo.setBounds(190, 30, 150, 30);
        add(lblTitulo);

        btnRepartidores = new JButton("Repartidores");
        btnRepartidores.setBounds(150, 90, 200, 40);
        add(btnRepartidores);

        btnPedidos = new JButton("Pedidos");
        btnPedidos.setBounds(150, 140, 200, 40);
        add(btnPedidos);

        btnEntregas = new JButton("Entregas");
        btnEntregas.setBounds(150, 190, 200, 40);
        add(btnEntregas);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(150, 250, 200, 40);
        add(btnSalir);

        btnRepartidores.addActionListener(e -> {
            new PanelRepartidores().setVisible(true);
        });

        btnPedidos.addActionListener(e -> {
            new PanelPedidos().setVisible(true);
        });

        btnEntregas.addActionListener(e -> {
            new PanelEntregas().setVisible(true);
        });

        btnSalir.addActionListener(e -> {
            System.exit(0);
        });
    }
}

