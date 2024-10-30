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
	private TablaMejoresOfertas tablaMejoresOfertas;
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
		tablaMejoresOfertas = new TablaMejoresOfertas();
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
			presentadorOfertas.borrarListaDeOfertas();
			limpiarTablaOfertas();
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
	    			limpiarTablaOfertas();
	    			convertirDniDeStringAInteger();
	    			
	    			for (int dni : this.listaDeDniClientes) {
	    				String dniCliente = String.valueOf(dni);
	    				ArrayList<String> ofertaDatos = new ArrayList<String>();
	    				//System.out.println(presentadorOfertas.devolverOfertaEnLista(dniCliente));
	    				ofertaDatos = presentadorOfertas.devolverOfertaEnLista(dniCliente);
						tablaOfertas.agregarOfertaEnTabla(ofertaDatos.get(0), ofertaDatos.get(1), "$"+ofertaDatos.get(2), ofertaDatos.get(3) + " a " + ofertaDatos.get(4));
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
	    
	    JButton btnMejoresOfertas = new JButton("Mostrar mejores ofertas");
	    btnMejoresOfertas.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            ArrayList<Oferta> listaOfertaOrdenada = new ArrayList<>(presentadorOfertas.devolverListaDeOfertasOrdenada());
	            if (!listaOfertaOrdenada.isEmpty()) {
	            	
	            	//aca llamo al panel
	            	mostrarPanelPagina(tablaMejoresOfertas,panelPaginas);
	            	tablaMejoresOfertas.limpiarTabla();
	            	for (Oferta oferta : listaOfertaOrdenada) {
	            		String dniCliente = String.valueOf(oferta.getDni());
	            		tablaMejoresOfertas.agregarOfertaEnTabla(
					        oferta.getNombre(), 
					        dniCliente, 
					        "$" + oferta.getPrecio(), 
					        oferta.getHoraDeInicio() + " a " + oferta.getHoraDeFinalizacion()
					    );
					}
	            	
	            	
	               
	                
	            } else {
	                JOptionPane.showMessageDialog(null, "Error: no existen ofertas");
	            }
	        }

	    });
	    btnMejoresOfertas.setBounds(10, 204, 103, 41);
	    panelInicio.add(btnMejoresOfertas);
	    
	    JButton btnPrincipal = new JButton("Principal");
	    btnPrincipal.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		mostrarPanelPagina(tablaOfertas,panelPaginas);
	    	}
	    });
	    btnPrincipal.setBounds(10, 11, 89, 23);
	    panelInicio.add(btnPrincipal);
	    
	   
	    
	}
	
	private void mostrarPanelPagina(JPanel nuevoPanel, JPanel contenedor) {
		nuevoPanel.setBounds(10, 11, 847, 416);
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
		panel.setBounds(new Rectangle(123, 30, 867, 438));
		panel.setLayout(null);
		JLabel nombre = new JLabel("Nombre");
	    nombre.setBounds(20, 20, 100, 25); // Posición y tamaño manual
	    panel.add(nombre);
	    JTextField textNombre = new JTextField(10);
	    textNombre.setBounds(130, 20, 150, 25);
	    panel.add(textNombre);

	    JLabel dni = new JLabel("Dni");
	    dni.setBounds(20, 60, 100, 25);
	    panel.add(dni);
	    JTextField textDni = new JTextField(10);
	    textDni.setBounds(130, 60, 150, 25);
	    panel.add(textDni);

	    JLabel precio = new JLabel("Precio");
	    precio.setBounds(20, 100, 100, 25);
	    panel.add(precio);
	    JTextField textPrecio = new JTextField(10);
	    textPrecio.setBounds(130, 100, 150, 25);
	    panel.add(textPrecio);

	    JLabel horarioInicio = new JLabel("Hora de Inicio");
	    horarioInicio.setBounds(20, 140, 100, 25);
	    panel.add(horarioInicio);
	    JTextField textHorarioInicio = new JTextField(10);
	    textHorarioInicio.setBounds(130, 140, 150, 25);
	    panel.add(textHorarioInicio);

	    JLabel horarioFin = new JLabel("Hora de Finalización");
	    horarioFin.setBounds(20, 180, 150, 25);
	    panel.add(horarioFin);
	    JTextField textHorarioFin = new JTextField(10);
	    textHorarioFin.setBounds(180, 180, 150, 25);
	    panel.add(textHorarioFin);
		mostrarPanelPagina(panel,panelPaginas);	
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String nombreOferta = textNombre.getText().trim();
	    		String dniOferta = textDni.getText().trim();
	    		String precioOferta = textPrecio.getText().trim();
	    		String horarioI = textHorarioInicio.getText().trim();
	    		String horarioF = textHorarioFin.getText().trim();
	    		if(!nombreOferta.isEmpty() && !dniOferta.isEmpty() && !precioOferta.isEmpty() && 
						!horarioI.isEmpty() && !horarioF.isEmpty() ) {
						int dniCliente = Integer.parseInt(dniOferta);
						int horarioInicial = Integer.parseInt(horarioI);
						int horarioFinal = Integer.parseInt(horarioF);
						double precioOfertado = Double.parseDouble(precioOferta);
						if (presentadorOfertas.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal)) {
							mostrarPanelPagina(tablaOfertas,panelPaginas);
							String horarioOferta =  horarioI + " a" + horarioF;
							tablaOfertas.agregarOfertaEnTabla(nombreOferta, dniOferta, precioOferta, horarioOferta);
						} else {
							JOptionPane.showMessageDialog(null, "Error: No se pudo crear la oferta");
						}
					
				} else {
					JOptionPane.showMessageDialog(null, "Campos inválidos, por favor complete todos los campos.");
				}
	    	}
	    });
		btnEnviar.setBounds(180, 350, 150, 25);
		panel.add(btnEnviar);
		
		
		
	}

	public void limpiarTablaOfertas() {
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

