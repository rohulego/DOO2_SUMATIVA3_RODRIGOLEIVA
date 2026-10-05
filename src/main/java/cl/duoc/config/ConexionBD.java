package cl.duoc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_app_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";

    private static final String PASSWORD = "my-secret-pw";

    static {
        inicializarTabla();
    }

    // Llamamos la conexion y nos conectamos
    public static Connection obtenerConexion() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void inicializarTabla() {
        // Creamos las tablas necesarias para SpeedFast
        String sqlRepartidores = """
            CREATE TABLE IF NOT EXISTS repartidores (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL
            )
            """;

        String sqlPedidos = """
            CREATE TABLE IF NOT EXISTS pedidos (
                id INT AUTO_INCREMENT PRIMARY KEY,
                direccion VARCHAR(100) NOT NULL,
                tipo ENUM('COMIDA', 'ENCOMIENDA', 'EXPRESS') NOT NULL,
                estado ENUM('PENDIENTE', 'EN_REPARTO', 'ENTREGADO') NOT NULL
            )
            """;

        String sqlEntregas = """
            CREATE TABLE IF NOT EXISTS entregas (
                id INT AUTO_INCREMENT PRIMARY KEY,
                id_pedido INT NOT NULL,
                id_repartidor INT NOT NULL,
                fecha DATE NOT NULL,
                hora TIME NOT NULL,
                FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE,
                FOREIGN KEY (id_repartidor) REFERENCES repartidores(id) ON DELETE CASCADE
            )
            """;

        try (Connection conexion = obtenerConexion();
             Statement statement = conexion.createStatement()) {

            statement.execute(sqlRepartidores);
            statement.execute(sqlPedidos);
            statement.execute(sqlEntregas);

        } catch (SQLException e) {
            System.err.println("Error en inicializarTabla de la base de datos: " + e.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}