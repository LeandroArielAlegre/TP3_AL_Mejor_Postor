package view;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Rectangle;
import javax.swing.ScrollPaneConstants;


public class TablaOfertas extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;

	public TablaOfertas() {
		setLayout(null);
		setBounds(new Rectangle(0, 0, 847, 416));
		model = new DefaultTableModel(new Object[]{"Nombre", "DNI", "Precio", "Horario"}, 0);                  
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 827, 396);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}

	public void agregarOfertaEnTabla(String nombreOferta, String dniCliente, String precioOferta, String horario) {
		model.addRow(new Object[]{nombreOferta, dniCliente, precioOferta, horario});
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
			if (model.getValueAt(i, 1).toString().equals(dni)) {
				contador++;
			}
		}
		return contador;
	}

	public void limpiarTabla() {
		model.setRowCount(0);
	}
}
