package cl.duoc.dao;

import cl.duoc.config.ConexionBD;
import cl.duoc.model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {


    public void create(Repartidor repartidor) throws Exception {

        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al crear el repartidor: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Repartidor> readAll() throws Exception {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidores";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Repartidor repartidor = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener los repartidores: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return repartidores;
    }

    public void update(Repartidor repartidor) throws Exception {

        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al actualizar el repartidor: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) throws Exception {

        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al eliminar el repartidor: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
