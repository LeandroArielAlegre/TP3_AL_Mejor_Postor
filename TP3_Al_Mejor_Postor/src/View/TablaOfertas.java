package View;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Rectangle;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class TablaOfertas extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    public TablaOfertas() {
        setLayout(null);
        setBounds(new Rectangle(0, 0, 847, 416));

        // Configurar el modelo de la tabla
        model = new DefaultTableModel(new Object[]{"Nombre", "DNI", "Precio", "Horario"}, 0); 
           
        

        // Configurar la tabla
        table = new JTable(model);
//        table.getTableHeader().setReorderingAllowed(false); // Evitar que se reordenen las columnas
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Configurar el ScrollPane
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 827, 396); // Dejar un pequeño margen
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        // Asegurarse de que la tabla llene el viewport
//        table.setFillsViewportHeight(true);
        
        add(scrollPane);
    }

    public void agregarOfertaEnTabla(String nombreOferta, String dniCliente, String precioOferta, String horario) {
        model.addRow(new Object[]{nombreOferta, dniCliente, precioOferta, horario});
        // Hacer scroll a la última fila añadida
        table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
    }
    public void eliminarFilaPorDni(String dni) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).toString().equals(dni)) { 
                model.removeRow(i);
                break; 
            }
        }
    }
    public int contarDuplicadosPorDni(String dni) {
        int contador = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).toString().equals(dni)) { // 
                contador++;
            }
        }
        return contador;
    }
    

    public void limpiarTabla() {
        model.setRowCount(0);
    }
    
}
