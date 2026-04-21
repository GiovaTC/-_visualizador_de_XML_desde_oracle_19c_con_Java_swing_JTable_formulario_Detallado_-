import java.sql.*;
import java.util.*;

public class MensajeDAO {

    public List<MensajeXML> listar() {

        List<MensajeXML> lista = new ArrayList<>();

        String sql = """
            SELECT 
                ID, 
                NOMBRE, 
                XMLSERIALIZE(CONTENT MENSAJE AS CLOB) AS XML_DATA 
            FROM MENSAJES_XML
        """;

        try (Connection cn = ConexionDB.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                MensajeXML m = new MensajeXML();
                m.setId(rs.getInt("ID"));
                m.setNombre(rs.getString("NOMBRE"));

                // Manejo seguro del CLOB como String
                String xml = rs.getString("XML_DATA");
                m.setXml(xml != null ? xml : "");

                lista.add(m);
            }

        } catch (SQLException e) {
            System.err.println("Error SQL al listar mensajes XML:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general:");
            e.printStackTrace();
        }

        return lista;
    }
}