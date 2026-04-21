import java.util.List;

public class MensajeService {

    private MensajeDAO dao = new MensajeDAO();

    public List<MensajeXML> obtenerMensajes() {
        return dao.listar();
    }
}   
