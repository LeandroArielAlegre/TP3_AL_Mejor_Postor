package View;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Presentador.PresentadorOfertas;

import javax.swing.JScrollPane;
import java.awt.Color;

public class Pantalla {

	private JFrame frame;
	private JTable table;
	private PresentadorOfertas presentadorOfertas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pantalla window = new Pantalla();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Pantalla() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		presentadorOfertas = new PresentadorOfertas();
		frame = new JFrame();
		frame.setBackground(new Color(115, 136, 123));
		frame.setBounds(100, 100, 1066, 568);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre","DNI" , "Precio", "Horario"}, 0);
        JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(35, 41, 853, 388);
		frame.getContentPane().add(scrollPane);
		
		 JButton btnAgregarFila = new JButton("Agregar Oferta");
		    btnAgregarFila.setBounds(24, 440, 120, 30);
		    btnAgregarFila.addActionListener(e -> {
		    	crearOferta(model);
		    });
		    frame.getContentPane().add(btnAgregarFila);
	}
	
	
	 
	private void crearOferta(DefaultTableModel model) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		JLabel nombre = new JLabel("Nombre");
		panel.add(nombre);
		JTextField textNombre = new JTextField(4);
		panel.add(textNombre);
		JLabel dni = new JLabel("Dni");
		panel.add(dni);
		JTextField textDni = new JTextField(4);
		panel.add(textDni);
		JLabel precio = new JLabel("Precio");
		panel.add(precio);
		JTextField textPrecio = new JTextField(4);
		panel.add(textPrecio);
		JLabel horarioInicio = new JLabel("Hora de Inicio");
		panel.add(horarioInicio);
		JTextField textHorarioInicio = new JTextField(4);
		panel.add(textHorarioInicio);
		JLabel horarioFin = new JLabel("Hora de finalizacion");
		panel.add(horarioFin);
		JTextField textHorarioFin = new JTextField(4);
		panel.add(textHorarioFin);
		
		int resultado = JOptionPane.showConfirmDialog(null, panel, "Agregar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (resultado == JOptionPane.OK_OPTION) {
			String nombreOferta = textNombre.getText().trim();
			String dniOferta = textDni.getText().trim();
			String precioOferta = textPrecio.getText().trim();
			String horarioI = textHorarioInicio.getText().trim();
			String horarioF = textHorarioFin.getText().trim();
			if(!nombreOferta.isEmpty() && !dniOferta.isEmpty() && !precioOferta.isEmpty() || 
					!horarioI.isEmpty() && !horarioF.isEmpty() ) {
				//Hacer las verificaciones pertinentes para cada campo, 
				//de manera que sea adecuado al IREP
				int dniCliente=Integer.parseInt(dniOferta);
				int horarioInicial =Integer.parseInt(horarioI);
				Double precioOfertado =Double.parseDouble(precioOferta);
				int horarioFinal =Integer.parseInt(horarioF);
				if(presentadorOfertas.agregarOferta(nombreOferta,dniCliente,precioOfertado,horarioInicial,horarioFinal)) {
					model.addRow(new Object[]{nombreOferta, dniOferta, "$"+precioOferta, horarioI + " a " + horarioF});
				}else {
					JOptionPane.showMessageDialog(null, "Error: No se pudo crear la oferta");
				}
			}else {
				JOptionPane.showMessageDialog(null, "Campos invalidos");
				
			}
			
		}
	}
	
	
}

