package View;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Presentador.PresentadorOfertas;

import javax.swing.JScrollPane;
import java.awt.Color;

public class Pantalla {

	private JFrame frame;
	//private JTable table;
	private PresentadorOfertas presentadorOfertas;
	private ArrayList<Integer> listaDeDniClientes;
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
		listaDeDniClientes =  new ArrayList<Integer>();
		frame = new JFrame();
		//frame.setBackground(new Color(74, 102, 232));
		frame.setBounds(100, 100, 1066, 568);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelInicio = new JPanel();
		panelInicio.setBorder(new LineBorder(new Color(0, 0, 0)));
		//panelInicio.setBackground(new Color(74, 102, 232));
		panelInicio.setBounds(10, 4, 1000, 510);
		panelInicio.setLayout(null);
		frame.getContentPane().add(panelInicio);
		
		
		DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre","DNI" , "Precio", "Horario"}, 0);
        JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(35, 41, 853, 388);
		panelInicio.add(scrollPane);
		
		 JButton btnAgregarFila = new JButton("Agregar Oferta");
		    btnAgregarFila.setBounds(24, 440, 120, 30);
		    btnAgregarFila.addActionListener(e -> {
		    	crearOferta(model);
		    });
		    panelInicio.add(btnAgregarFila);
		    
		    JButton btnLimpiarTabla = new JButton("Limpiar Tabla");
		    btnLimpiarTabla.setBounds(154, 440, 120, 30);
		    btnLimpiarTabla.addActionListener(e -> {
		    	limpiarTabla(model);
		    	//Deberia tambien limpiar el modelo
		    });
		    panelInicio.add(btnLimpiarTabla);
		    
		    
		    JButton btnGuardarTabla = new JButton("Guardar");
		    btnGuardarTabla.setBounds(284, 440, 120, 30);
		    btnGuardarTabla.addActionListener(e -> {
		    	String nombreDeArchivo =guardarOfertas();
		    	if(nombreDeArchivo != null) {
		    		if(presentadorOfertas.guardarEnArchivo(nombreDeArchivo)) {
			    		JOptionPane.showMessageDialog(null, "El archivo se guardo exitosamente");
			    	}else{
			    		JOptionPane.showMessageDialog(null, "ERROR: no se pudo guardar el archivo");
			    	}
		    		
		    	}else {
		    		JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");
		    	}
		    	
		    });
		    panelInicio.add(btnGuardarTabla);
		    
		    JButton btnCargarTabla = new JButton("Cargar");
		    btnCargarTabla.setBounds(414, 440, 120, 30);
		    btnCargarTabla.addActionListener(e -> {
		    	String nombreDeArchivo =cargarOfertas();
		    	if(nombreDeArchivo != null) {
		    		if(presentadorOfertas.cargarDeArchivo(nombreDeArchivo)) {
		    			limpiarTabla(model);
		    			convertirDniDeStringAInteger();
		    			for (int dni : this.listaDeDniClientes) {
		    				String dniCliente = String.valueOf(dni);
		    				ArrayList<String> ofertaDatos = new ArrayList<String>();
		    				System.out.println(presentadorOfertas.devolverOfertaEnLista(dniCliente));
		    				ofertaDatos = presentadorOfertas.devolverOfertaEnLista(dniCliente);
		    				model.addRow(new Object[]{ofertaDatos.get(0), ofertaDatos.get(1), "$"+ofertaDatos.get(2), ofertaDatos.get(3) + " a " + ofertaDatos.get(4)});
							
						}
			    		JOptionPane.showMessageDialog(null, "El archivo se cargo exitosamente");
			    	}else{
			    		JOptionPane.showMessageDialog(null, "ERROR: no se pudo cargar el archivo");
			    	}
		    		
		    	}else {
		    		JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");
		    	}
		    	
		    	
		    	
		    });
		    panelInicio.add(btnCargarTabla);
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
					this.listaDeDniClientes.add(dniCliente);
					model.addRow(new Object[]{nombreOferta, dniOferta, "$"+precioOferta, horarioI + " a " + horarioF});
				}else {
					JOptionPane.showMessageDialog(null, "Error: No se pudo crear la oferta");
				}
			}else {
				JOptionPane.showMessageDialog(null, "Campos invalidos");
				
			}
			
		}
	}
	public void limpiarTabla(DefaultTableModel model) {
		//presentadorOfertas.borrarListaDeOfertas();
	    model.setRowCount(0);
	    
	}
	
	private String guardarOfertas() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JLabel label = new JLabel("Coloque el nombre del archivo:");
		panel.add(label);
		JTextField textField = new JTextField(10);
		panel.add(textField);
		int resultado = JOptionPane.showConfirmDialog(null, panel, "Guardar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (resultado == JOptionPane.OK_OPTION) {
			String nombreArchivo = textField.getText().trim();
			if(nombreArchivo.isEmpty()) {
				return null;
			}
			return nombreArchivo;
			
		}else {
			return null;
		}
	}
	
	private String cargarOfertas() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JLabel label = new JLabel("Coloque el nombre del archivo:");
		panel.add(label);
		JTextField textField = new JTextField(10);
		panel.add(textField);
		int resultado = JOptionPane.showConfirmDialog(null, panel, "Cargar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (resultado == JOptionPane.OK_OPTION) {
			String nombreArchivo = textField.getText().trim();
			if(nombreArchivo.isEmpty()) {
				return null;
			}
			return nombreArchivo;
		} else {
			return null;
		}
	}
	
	private void convertirDniDeStringAInteger() {
		listaDeDniClientes.clear();
		ArrayList<String> listaDeDni = new ArrayList<String>();
		listaDeDni = presentadorOfertas.devolverTodosLosDniDeLosClientes();
		for (String dniString : listaDeDni) {
			int dniCliente = Integer.parseInt(dniString);
			listaDeDniClientes.add(dniCliente);
		}
		
		
	}
}

