package View;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Presentador.PresentadorOfertas;

import java.awt.Rectangle;
import java.util.ArrayList;

public class TablaOfertas extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre","DNI" , "Precio", "Horario"}, 0);
	private JScrollPane scrollPane;
	private PresentadorOfertas presentadorOfertas;
	/**
	 * Create the panel.
	 */
	public TablaOfertas() {
		presentadorOfertas = new PresentadorOfertas();
		setBounds(new Rectangle(123, 30, 867, 438));
		setLayout(null);
		
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 867, 438);
		add(scrollPane);
		


	}
	public void agregarOfertaEnTabla(String nombreOferta, String dniCliente, String precioOferta, String horarioI, String horarioF ) {
		model.addRow(new Object[]{nombreOferta, dniCliente, "$" + precioOferta, horarioI + " a " + horarioF});
	}
	public void limpiarTabla() {
		model.setRowCount(0);
	}
	
	public void cargarTablaDeArchivo(ArrayList<Integer> listaDeDniClientes) {
		limpiarTabla();
		for (int dni : listaDeDniClientes) {
			String dniCliente = String.valueOf(dni);
			ArrayList<String> ofertaDatos = new ArrayList<String>();
			//System.out.println(presentadorOfertas.devolverOfertaEnLista(dniCliente));
			ofertaDatos = presentadorOfertas.devolverOfertaEnLista(dniCliente);
			model.addRow(new Object[]{ofertaDatos.get(0), ofertaDatos.get(1), "$"+ofertaDatos.get(2), ofertaDatos.get(3) + " a " + ofertaDatos.get(4)});
			
		}
		
		
	}
	

}
