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

import Modelo.Oferta;
import Presentador.PresentadorOfertas;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Pantalla {

	private JFrame frame;
	private TablaOfertas tablaOfertas;
	private JPanel panelPaginas;

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
		listaDeDniClientes= new ArrayList<Integer>();
		frame = new JFrame();
		tablaOfertas = new TablaOfertas();
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
		
		 panelPaginas = new JPanel();
		 panelPaginas.setBounds(123, 30, 867, 438);
		 panelPaginas.setBorder(new LineBorder(new Color(0, 0, 0)));
		 panelPaginas.setLayout(null);
		 panelInicio.add(panelPaginas);

		 
		 //Muestro el panel bienvenida
		 mostrarPanelPagina(tablaOfertas,panelPaginas);
		 

		 /*
		DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre","DNI" , "Precio", "Horario"}, 0);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 867, 438);
		panelPaginas.add(scrollPane);
		  */
		JButton btnAgregarOferta = new JButton("Agregar Oferta");
		btnAgregarOferta.setBounds(10, 40, 103, 30);
		btnAgregarOferta.addActionListener(e -> {
			crearOferta();

		});
		panelInicio.add(btnAgregarOferta);

		JButton btnLimpiarTabla = new JButton("Limpiar Tabla");
		btnLimpiarTabla.setBounds(10, 81, 93, 30);
		btnLimpiarTabla.addActionListener(e -> {
			limpiarTabla();
			//Deberia tambien limpiar el modelo
		});
		panelInicio.add(btnLimpiarTabla);
		JButton btnGuardarTabla = new JButton("Guardar");
	    btnGuardarTabla.setBounds(10, 122, 94, 30);
	    btnGuardarTabla.addActionListener(e -> {
	    	String nombreDeArchivo =guardarOfertas();
	    	if(nombreDeArchivo != null) {
	    		if(presentadorOfertas.guardarOfertas(nombreDeArchivo)) {
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
	    btnCargarTabla.setBounds(10, 163, 103, 30);
	    btnCargarTabla.addActionListener(e -> {
	    	String nombreDeArchivo =cargarOfertas();
	    	if(nombreDeArchivo != null) {
	    		if(presentadorOfertas.cargarOfertasDeArchivo(nombreDeArchivo)) {
	    			limpiarTabla();
	    			convertirDniDeStringAInteger();
	    			//lO mando a la tabla
	    			tablaOfertas.cargarTablaDeArchivo(listaDeDniClientes);
	    			/*
	    			for (int dni : this.listaDeDniClientes) {
	    				String dniCliente = String.valueOf(dni);
	    				ArrayList<String> ofertaDatos = new ArrayList<String>();
	    				//System.out.println(presentadorOfertas.devolverOfertaEnLista(dniCliente));
	    				ofertaDatos = presentadorOfertas.devolverOfertaEnLista(dniCliente);
	    				model.addRow(new Object[]{ofertaDatos.get(0), ofertaDatos.get(1), "$"+ofertaDatos.get(2), ofertaDatos.get(3) + " a " + ofertaDatos.get(4)});
						
					}*/
		    		JOptionPane.showMessageDialog(null, "El archivo se cargo exitosamente");
		    	}else{
		    		JOptionPane.showMessageDialog(null, "ERROR: no se pudo cargar el archivo");
		    	}
	    		
	    	}else {
	    		JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");
	    	}
	    	
	    	
	    	
	    });
	    panelInicio.add(btnCargarTabla);
	    
	    JButton btnMejoresOfertas = new JButton("Mostrar mejores ofertas");
	    btnMejoresOfertas.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            ArrayList<Oferta> listaOfertaOrdenada = new ArrayList<>(presentadorOfertas.devolverListaDeOfertasOrdenada());
	            if (!listaOfertaOrdenada.isEmpty()) {
	                JPanel panelMejoresOfertas = crearTablaDeMejoresOfertas(listaOfertaOrdenada);
	                crearPantallaDeMejoresOfertas(panelMejoresOfertas);
	                
	            } else {
	                JOptionPane.showMessageDialog(null, "Error: no existen ofertas");
	            }
	        }

			private JPanel crearTablaDeMejoresOfertas(ArrayList<Oferta> listaOfertaOrdenada) {
				JPanel panelMejoresOfertas = new JPanel();
				panelMejoresOfertas.setBounds(100, 100, 1066, 568);

				DefaultTableModel model2 = new DefaultTableModel(new Object[]{"Nombre", "DNI", "Precio", "Horario"}, 0);
				JTable tableMejores = new JTable(model2);
				JScrollPane scrollPaneMejores = new JScrollPane(tableMejores);
				scrollPaneMejores.setBounds(35, 41, 853, 388);
				panelMejoresOfertas.setLayout(null); // Establece layout nulo si necesitas posiciones específicas
				panelMejoresOfertas.add(scrollPaneMejores);

				for (Oferta oferta : listaOfertaOrdenada) {
				    model2.addRow(new Object[]{
				        oferta.getNombre(), 
				        oferta.getDni(), 
				        "$" + oferta.getPrecio(), 
				        oferta.getHoraDeInicio() + " a " + oferta.getHoraDeFinalizacion()
				    });
				}
				return panelMejoresOfertas;
			}

			private void crearPantallaDeMejoresOfertas(JPanel panelMejoresOfertas) {
				JFrame pantallaDeMejoresOfertas = new JFrame("Mejores Ofertas");
				pantallaDeMejoresOfertas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
				pantallaDeMejoresOfertas.getContentPane().add(panelMejoresOfertas);
				pantallaDeMejoresOfertas.setSize(1100, 600); 
				pantallaDeMejoresOfertas.setLocationRelativeTo(null); 
				pantallaDeMejoresOfertas.setVisible(true);
			}
	    });
	    btnMejoresOfertas.setBounds(10, 204, 103, 41);
	    panelInicio.add(btnMejoresOfertas);
	    
	   
	    
	}
	
	private void mostrarPanelPagina(JPanel nuevoPanel, JPanel contenedor) {
		nuevoPanel.setBounds(10, 11, 857, 451);
		contenedor.removeAll();
		contenedor.add(nuevoPanel);
		contenedor.revalidate();
		contenedor.repaint();
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

	private void crearOferta() {
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
				try {
					int dniCliente = Integer.parseInt(dniOferta);
					int horarioInicial = Integer.parseInt(horarioI);
					int horarioFinal = Integer.parseInt(horarioF);
					double precioOfertado = Double.parseDouble(precioOferta);

					if (presentadorOfertas.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal)) {
						mostrarPanelPagina(tablaOfertas,panelPaginas);
						tablaOfertas.agregarOfertaEnTabla(nombreOferta, dniOferta, precioOferta, horarioI, horarioF);
						//model.addRow(new Object[]{nombreOferta, dniCliente, "$" + precioOferta, horarioI + " a " + horarioF});
					} else {
						JOptionPane.showMessageDialog(null, "Error: No se pudo crear la oferta");
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos para DNI, Precio y Horarios.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Campos inválidos, por favor complete todos los campos.");
			}
		}
	}

	public void limpiarTabla() {
		//FALTA QUE LIMPIE EL MODELO
		mostrarPanelPagina(tablaOfertas,panelPaginas);
		tablaOfertas.limpiarTabla();
		
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
}

