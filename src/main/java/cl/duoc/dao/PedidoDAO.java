package cl.duoc.dao;

import cl.duoc.config.ConexionBD;
import cl.duoc.model.EstadoPedido;
import cl.duoc.model.Pedido;
import cl.duoc.model.TipoPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {


    public void create(Pedido pedido) throws Exception {

        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al crear el pedido: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pedido> readAll() throws Exception {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, estado FROM pedidos";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        TipoPedido.valueOf(rs.getString("tipo")),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener los pedidos: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return pedidos;
    }

    public void update(Pedido pedido) throws Exception {

        String sql = """
            UPDATE pedidos
            SET direccion = ?, tipo = ?, estado = ?
            WHERE id = ?
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al actualizar el pedido: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) throws Exception {

        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al eliminar el pedido: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
