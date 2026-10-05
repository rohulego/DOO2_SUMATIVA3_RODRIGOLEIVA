package cl.duoc.dao;

import cl.duoc.config.ConexionBD;
import cl.duoc.model.Entrega;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {


    public void create(Entrega entrega) throws Exception {

        String sql = """
            INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al crear la entrega: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Entrega> readAll() throws Exception {

        List<Entrega> entregas = new ArrayList<>();

        String sql = """
            SELECT id, id_pedido, id_repartidor, fecha, hora
            FROM entregas
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Entrega entrega = new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime()
                );

                entregas.add(entrega);
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener las entregas: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entregas;
    }

    public void update(Entrega entrega) throws Exception {

        String sql = """
            UPDATE entregas
            SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ?
            WHERE id = ?
            """;

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.setInt(5, entrega.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al actualizar la entrega: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) throws Exception {

        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al eliminar la entrega: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
