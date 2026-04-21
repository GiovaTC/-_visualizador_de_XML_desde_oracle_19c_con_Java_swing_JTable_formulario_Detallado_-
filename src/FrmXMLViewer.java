import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.List;

public class FrmXMLViewer extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtUsuario, txtEmail, txtTelefono, txtCiudad, txtPais, txtDescripcion;

    private MensajeService service = new MensajeService();
    private List<MensajeXML> lista;

    public FrmXMLViewer() {

        setTitle("Visualizador XML Oracle");
        setSize(800, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnCargar = new JButton("Cargar Datos");
        btnCargar.setBounds(20, 20, 150, 30);
        add(btnCargar);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0);
        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 350, 200);
        add(scroll);

        // Campos
        txtUsuario = crearCampo("Usuario", 400, 60);
        txtEmail = crearCampo("Email", 400, 100);
        txtTelefono = crearCampo("Telefono", 400, 140);
        txtCiudad = crearCampo("Ciudad", 400, 180);
        txtPais = crearCampo("Pais", 400, 220);
        txtDescripcion = crearCampo("Descripcion", 400, 260);

        // Eventos
        btnCargar.addActionListener(e -> cargarDatos());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarXML(tabla.getSelectedRow());
            }
        });
    }

    private JTextField crearCampo(String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 100, 25);
        add(lbl);

        JTextField txt = new JTextField();
        txt.setBounds(x + 100, y, 200, 25);
        add(txt);

        return txt;
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        lista = service.obtenerMensajes();

        for (MensajeXML m : lista) {
            modelo.addRow(new Object[]{m.getId(), m.getNombre()});
        }
    }

    private void mostrarXML(int index) {

        if (index < 0) return;

        try {
            String xml = lista.get(index).getXml();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

            txtUsuario.setText(getTag(doc, "usuario"));
            txtEmail.setText(getTag(doc, "email"));
            txtTelefono.setText(getTag(doc, "telefono"));
            txtCiudad.setText(getTag(doc, "ciudad"));
            txtPais.setText(getTag(doc, "pais"));
            txtDescripcion.setText(getTag(doc, "descripcion"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTag(Document doc, String tag) {
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmXMLViewer().setVisible(true));
    }
}
