import java.sql.*;
import java.util.*;

public class MensajeDAO {

    public List<MensajeXML> listar() {

        List<MensajeXML> lista = new ArrayList<>();

        try (Connection cn = ConexionDB.getConexion()) {

            String sql = "SELECT ID, NOMBRE, MENSAJE.getClobVal() AS XML_DATA FROM MENSAJES_XML";

            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MensajeXML m = new MensajeXML();
                m.setId(rs.getInt("ID"));
                m.setNombre(rs.getString("NOMBRE"));
                m.setXml(rs.getString("XML_DATA"));

                lista.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
