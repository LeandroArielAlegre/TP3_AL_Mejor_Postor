package View;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
//import javax.swing.table.DefaultTableModel;

//import org.junit.runners.Parameterized.Parameter;

import Modelo.Oferta;
import Presentador.PresentadorOfertas;
//import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.SwingConstants;
//import java.awt.Component;
import java.awt.Font;
import java.awt.Component;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;

public class Pantalla {

	private JFrame frame;
	private TablaOfertas tablaOfertas;
	private TablaOfertas tablaMejoresOfertas;
	private JPanel panelPaginas;

	private PresentadorOfertas presentadorOfertas;
	//	private ArrayList<Integer> listaDeDniClientes;
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
		//		listaDeDniClientes= new ArrayList<Integer>();
		frame = new JFrame();
		tablaOfertas = new TablaOfertas();
		//		tablaOfertas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaMejoresOfertas = new TablaOfertas();
		//frame.setBackground(new Color(74, 102, 232));
		frame.setBounds(100, 100, 1010, 558);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		//		JPanel panelInicio = new JPanel();
		//		panelInicio.setBorder(new LineBorder(new Color(0, 0, 0)));
		//		//panelInicio.setBackground(new Color(74, 102, 232));
		//		panelInicio.setBounds(10, 4, 1000, 510);
		//		panelInicio.setLayout(null);
		//		frame.getContentPane().add(panelInicio);

		panelPaginas = new JPanel();
		panelPaginas.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPaginas.setBounds(123, 30, 861, 435);
		panelPaginas.setLayout(null);
		//		panelInicio.add(panelPaginas);
		frame.getContentPane().add(panelPaginas);

		//Muestro el panel bienvenida
		mostrarPanelEnContenedor(tablaOfertas,panelPaginas);


		/*
		DefaultTableModel model = new DefaultTableModel(new Object[]{"Nombre","DNI" , "Precio", "Horario"}, 0);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 867, 438);
		panelPaginas.add(scrollPane);
		 */
		JButton btnAgregarOferta = new JButton("Agregar Oferta");
		btnAgregarOferta.setFocusable(false);
		btnAgregarOferta.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAgregarOferta.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAgregarOferta.setHorizontalAlignment(SwingConstants.LEFT);
		btnAgregarOferta.setBounds(10, 69, 103, 30);
		btnAgregarOferta.addActionListener(e -> {
			crearOferta();
		});
		//		panelInicio.add(btnAgregarOferta);
		frame.getContentPane().add(btnAgregarOferta);
		JButton btnLimpiarTabla = new JButton("Limpiar Tabla");
		btnLimpiarTabla.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLimpiarTabla.setFocusable(false);
		btnLimpiarTabla.setBounds(10, 110, 103, 30);
		btnLimpiarTabla.addActionListener(e -> {
			presentadorOfertas.borrarListaDeOfertas();
			limpiarTablaOfertas();
		});
		//		panelInicio.add(btnLimpiarTabla);
		frame.getContentPane().add(btnLimpiarTabla);
		JButton btnGuardarTabla = new JButton("Guardar");
		btnGuardarTabla.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGuardarTabla.setFocusable(false);
		btnGuardarTabla.setBounds(10, 151, 103, 30);
		btnGuardarTabla.addActionListener(e -> {
			String nombreDeArchivo = cargarNombreDeArchivo();
			if(nombreDeArchivo != null) {
				if(presentadorOfertas.guardarOfertas(nombreDeArchivo)) {
					JOptionPane.showMessageDialog(null, "El archivo se guardo exitosamente");
				}
				//	    		else{
				//		    		JOptionPane.showMessageDialog(null, "ERROR: no se pudo guardar el archivo");
				//		    	}

			}else {
				JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");
			}

		});
		frame.getContentPane().add(btnGuardarTabla);

		JButton btnCargarOfertas = new JButton("Cargar");
		btnCargarOfertas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCargarOfertas.setFocusable(false);
		btnCargarOfertas.setBounds(10, 192, 103, 30);
		btnCargarOfertas.addActionListener(e -> {
			String nombreDeArchivo = cargarNombreDeArchivo();
			if(nombreDeArchivo != null) {
				if(presentadorOfertas.cargarOfertasDeArchivo(nombreDeArchivo)) {
					limpiarTablaOfertas();

					ArrayList<Integer> listaDeDNIClientes = presentadorOfertas.devolverDNISComoInteger();

					for (int dni : listaDeDNIClientes) {
						String dniCliente = String.valueOf(dni);
						ArrayList<String> datosDeOferta = new ArrayList<String>();
						//System.out.println(presentadorOfertas.devolverOfertaEnLista(dniCliente));
						datosDeOferta = presentadorOfertas.devolverOfertaComoUnaLista(dniCliente);
						tablaOfertas.agregarOfertaEnTabla(datosDeOferta.get(0), datosDeOferta.get(1), "$"+datosDeOferta.get(2), datosDeOferta.get(3) + " a " + datosDeOferta.get(4));
					}
					JOptionPane.showMessageDialog(null, "El archivo se cargo exitosamente");
				}else{
					JOptionPane.showMessageDialog(null, "ERROR: no se pudo cargar el archivo");
				}

			}else {
				JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");
			}



		});
		//		panelInicio.add(btnCargarOfertas);
		frame.getContentPane().add(btnCargarOfertas);
		JButton btnMejoresOfertas = new JButton("Mostrar mejores ofertas");
		btnMejoresOfertas.setVerticalAlignment(SwingConstants.TOP);
		btnMejoresOfertas.setFocusable(false);
		btnMejoresOfertas.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnMejoresOfertas.setText("<html>" + btnMejoresOfertas.getText().replace("\n", "<br>") + "</html>");
		btnMejoresOfertas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnMejoresOfertas.addActionListener(e-> {
			//	        public void actionPerformed(ActionEvent e) {
			ArrayList<Oferta> listaOfertaOrdenadas = new ArrayList<>(presentadorOfertas.devolverListaDeOfertasOrdenadaPorBeneficio());
			if (!listaOfertaOrdenadas.isEmpty()) {
				ArrayList<Oferta> mejoresOfertasPorBeneficio = new ArrayList<>(presentadorOfertas.devolverOfertasQueNoSeSolapan(listaOfertaOrdenadas));
				if (!mejoresOfertasPorBeneficio.isEmpty()) {
					//aca llamo al panel
					mostrarPanelEnContenedor(tablaMejoresOfertas,panelPaginas);
					tablaMejoresOfertas.limpiarTabla();
					for (Oferta oferta : mejoresOfertasPorBeneficio) {
						String dniCliente = String.valueOf(oferta.getDni());
						tablaMejoresOfertas.agregarOfertaEnTabla(
								oferta.getNombre(), 
								dniCliente, 
								"$" + oferta.getPrecio(), 
								oferta.getHoraDeInicio() + " a " + oferta.getHoraDeFinalizacion()
								);
					}	            		            	               
				}   
			} else {
				JOptionPane.showMessageDialog(null, "Error: no existen ofertas");
			}
			//	        }

		});
		btnMejoresOfertas.setBounds(10, 233, 103, 35);
		//		panelInicio.add(btnMejoresOfertas);
		frame.getContentPane().add(btnMejoresOfertas);
		JButton btnVolver = new JButton("Volver Atras");
		btnVolver.setFocusable(false);
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnVolver.addActionListener(e-> {
			//	    	public void actionPerformed(ActionEvent e) {
			mostrarPanelEnContenedor(tablaOfertas,panelPaginas);
			//	    	}
		});
		btnVolver.setBounds(10, 30, 103, 30);
		//		panelInicio.add(btnVolver);
		frame.getContentPane().add(btnVolver);


	}

	/**
	   @param nuevoPanel Panel a crear
	   @param contenedor Panel que alojara al nuevo panel
	 */
	private void mostrarPanelEnContenedor(JPanel nuevoPanel, JPanel contenedor) {
		nuevoPanel.setBounds(10, 11, 841, 415);
		contenedor.removeAll();
		contenedor.add(nuevoPanel);
		contenedor.revalidate();
		contenedor.repaint();
	}



	//	private void actualizarDNILocales(ArrayList<Integer> listaDnI) {
	//		listaDeDniClientes.clear();	
	//		for (Integer dni : listaDnI) {
	//			int dniCliente = dni;
	//			listaDeDniClientes.add(dniCliente);
	//		}		
	//	}
	private void crearOferta() {
		JPanel panel = new JPanel();
		panel.setBounds(new Rectangle(123, 30, 867, 438));
		panel.setLayout(null);

		JLabel nombre = new JLabel("Nombre");
		nombre.setBounds(20, 20, 100, 25); // Posición y tamaño manual
		panel.add(nombre);	    	   
		JLabel dni = new JLabel("Dni");
		dni.setBounds(20, 60, 100, 25);
		panel.add(dni);	    
		JLabel precio = new JLabel("Precio");
		precio.setBounds(20, 100, 100, 25);
		panel.add(precio);	    
		JLabel horarioInicio = new JLabel("Hora de Inicio");
		horarioInicio.setBounds(20, 140, 100, 25);
		panel.add(horarioInicio);	    
		JLabel horarioFin = new JLabel("Hora de Finalización");
		horarioFin.setBounds(20, 180, 150, 25);
		panel.add(horarioFin);	    

		JTextField textDni = new JTextField(10);
		textDni.setBounds(130, 60, 150, 25);
		panel.add(textDni);
		JTextField textNombre = new JTextField(10);
		textNombre.setBounds(130, 20, 150, 25);
		panel.add(textNombre);
		JTextField textPrecio = new JTextField(10);
		textPrecio.setBounds(130, 100, 150, 25);
		panel.add(textPrecio);
		JTextField textHorarioInicio = new JTextField(10);
		textHorarioInicio.setBounds(130, 140, 150, 25);
		panel.add(textHorarioInicio);
		JTextField textHorarioFin = new JTextField(10);
		textHorarioFin.setBounds(180, 180, 150, 25);
		panel.add(textHorarioFin);

		mostrarPanelEnContenedor(panel,panelPaginas);	

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(e->{
			//	    	public void actionPerformed(ActionEvent e) {
			String nombreOferta = textNombre.getText().trim();
			String dniOferta = textDni.getText().trim().toLowerCase();
			String precioOferta = textPrecio.getText().trim().toLowerCase();
			String horarioI = textHorarioInicio.getText().trim().toLowerCase();
			String horarioF = textHorarioFin.getText().trim().toLowerCase();
			if(!nombreOferta.isEmpty() && !dniOferta.isEmpty() && !precioOferta.isEmpty() && 
					!horarioI.isEmpty() && !horarioF.isEmpty() ) {
				//ver que pasa si los datos no son numeros, ¿quien decide si las entradas son validas, la interfas o el modelo?
				if (!contieneLetras(dniOferta)&&!contieneLetrasElHorario(horarioI,horarioF)&& !contieneLetras(precioOferta))	{	
					int dniCliente = Integer.parseInt(dniOferta);
					int horarioInicial = Integer.parseInt(horarioI);
					int horarioFinal = Integer.parseInt(horarioF);
					double precioOfertado = Double.parseDouble(precioOferta);

					try { 				
						if (presentadorOfertas.puedeAgregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal)) {
						
						presentadorOfertas.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal);
						mostrarPanelEnContenedor(tablaOfertas,panelPaginas);
						String horarioOferta =  horarioI + " a " + horarioF;
						tablaOfertas.agregarOfertaEnTabla(nombreOferta, dniOferta, precioOferta, horarioOferta);
						}
					}catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
			
					
//					else {
//						JOptionPane.showMessageDialog(null, "Error: No se pudo crear la oferta");
//					}

				}else {
					JOptionPane.showMessageDialog(null, "Error: Las entradas numericas no deben tener letras involucradas");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Campos inválidos, por favor complete todos los campos.");
			}
			//	    	}
		});
		btnEnviar.setBounds(180, 350, 150, 25);
		panel.add(btnEnviar);



	}

	private boolean contieneLetrasElHorario(String horarioI, String horarioF) {
		boolean condicion1= contieneLetras(horarioI);
		boolean condicion2= contieneLetras(horarioF);
		if (condicion1&&condicion2){
			return true;
		}else{
			return false;
		}
	}
	public void eliminarFilaDeTablaConDniSiEsRepetido(int dni) {
		String dniCliente = String.valueOf(dni);
		if(tablaOfertas.contarDuplicadosPorDni(dniCliente) > 1){
			tablaOfertas.eliminarFilaPorDni(dniCliente);
		}
	
}
	private boolean contieneLetras(String precioOferta) {
		return precioOferta.contains("a")||precioOferta.contains("e")||precioOferta.contains("i")||precioOferta.contains("o")||precioOferta.contains("u");

	}

	public void limpiarTablaOfertas() {
		mostrarPanelEnContenedor(tablaOfertas,panelPaginas);
		tablaOfertas.limpiarTabla();

	}

	private String cargarNombreDeArchivo() {
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
	//	private String guardarOfertas() {
	//		JPanel panel = new JPanel();
	//		panel.setLayout(new GridLayout(2, 1));
	//		JLabel label = new JLabel("Coloque el nombre del archivo:");
	//		panel.add(label);
	//		JTextField textField = new JTextField(10);
	//		panel.add(textField);
	//		int resultado = JOptionPane.showConfirmDialog(null, panel, "Guardar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	//		if (resultado == JOptionPane.OK_OPTION) {
	//			String nombreArchivo = textField.getText().trim();
	//			if(nombreArchivo.isEmpty()) {
	//				return null;
	//			}
	//			return nombreArchivo;
	//			
	//		}else {
	//			return null;
	//		}
	//	}
}

