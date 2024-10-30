package View;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Presentador.PresentadorOfertas;

import java.awt.Rectangle;
import java.util.ArrayList;

public class TablaMejoresOfertas extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre","DNI" , "Precio", "Horario"}, 0);
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public TablaMejoresOfertas() {
		setBounds(new Rectangle(123, 30, 867, 438));
		setLayout(null);
		
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 830, 438);
		add(scrollPane);
		


	}
	public void agregarOfertaEnTabla(String nombreOferta, String dniCliente, String precioOferta, String horario ) {
		model.addRow(new Object[]{nombreOferta, dniCliente, "$" + precioOferta, horario});
	}
	public void limpiarTabla() {
		model.setRowCount(0);
	}
	

		
		
	
	

}
