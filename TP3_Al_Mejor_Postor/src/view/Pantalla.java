package view;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import modelo.Oferta;
import presentador.PresentadorOfertas;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class Pantalla {

	private JFrame frame;
	private TablaOfertas tablaOfertas;
	private TablaOfertas tablaMejoresOfertas;
	private JPanel panelPaginas;

	private PresentadorOfertas presentadorOfertas;
	private Boolean diaTranscurrido;
	private JButton btnMejoresOfertas;
	private JButton btnSiguienteDia;
	private JButton btnFinalizarDia;
	private Timer timer;
    private long duracionDeDia = 15 * 1000;
//	private JCalendar calendar;
	private LocalDate fechaActual; //= LocalDate.of(2024, 11, 5);
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
		diaTranscurrido = false;
		presentadorOfertas = new PresentadorOfertas();
		//System.out.println(presentadorOfertas.actualizarFechaActual(fechaActual));

		fechaActual = presentadorOfertas.devolverFechaActual();
		
		frame = new JFrame();
		tablaOfertas = new TablaOfertas();
		tablaMejoresOfertas = new TablaOfertas();
				
		frame.setBounds(100, 100, 1010, 558);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		iniciarTemporizadorDia();

		panelPaginas = new JPanel();
		panelPaginas.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPaginas.setBounds(123, 30, 861, 435);
		panelPaginas.setLayout(null);
		frame.getContentPane().add(panelPaginas);
		mostrarPanelEnContenedor(tablaOfertas,panelPaginas);
		
		// Instanciar 
//		calendar = new JCalendar(); 
		
		
		// Ubicar y agregar al panel
		
//		calendar.setBounds(0, 0, 800, 800);
//		otroFrame.getContentPane().add(calendar);
			//System.out.println(calendar.getDate());*/
		
		JLabel lblFechaActual = new JLabel(" ");
		lblFechaActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaActual.setBounds(407, 11, 166, 14);
		actualizarLabelFechaActual(lblFechaActual);
		frame.getContentPane().add(lblFechaActual);

		JButton btnAgregarOferta = new JButton("Agregar Oferta");
		btnAgregarOferta.setFocusable(false);
		btnAgregarOferta.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAgregarOferta.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAgregarOferta.setHorizontalAlignment(SwingConstants.LEFT);
		btnAgregarOferta.setBounds(10, 69, 103, 30);
		btnAgregarOferta.addActionListener(e -> {
			crearOferta();
		});
		frame.getContentPane().add(btnAgregarOferta);
		
		JButton btnLimpiarTabla = new JButton("Limpiar Tabla");
		btnLimpiarTabla.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLimpiarTabla.setFocusable(false);
		btnLimpiarTabla.setBounds(10, 110, 103, 30);
		btnLimpiarTabla.addActionListener(e -> {
			presentadorOfertas.borrarListaDeOfertas();
			limpiarTablaOfertas();
		});
		frame.getContentPane().add(btnLimpiarTabla);
		
		JButton btnGuardarTabla = new JButton("Guardar");
		btnGuardarTabla.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGuardarTabla.setFocusable(false);
		btnGuardarTabla.setBounds(10, 151, 103, 30);
		btnGuardarTabla.addActionListener(e -> {

			String nombreDeArchivo = (cargarNombreDeArchivo()+"_"+fechaActual.toString());
			if(nombreDeArchivo != null) {
				if(presentadorOfertas.guardarOfertas(nombreDeArchivo)) {
					JOptionPane.showMessageDialog(null, "El archivo se guardo exitosamente");
				}		
			}
//				JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");			
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
					diaTranscurrido = true;
                    btnMejoresOfertas.setVisible(true);                 
//                    btnSiguienteDia.setVisible(false);
                    btnFinalizarDia.setVisible(false);
                    
                    if (timer != null) {
                        timer.cancel();
                    }
                    JOptionPane.showMessageDialog(frame,
                            "El día ha finalizado. Ya no se pueden ingresar más ofertas.",
                            "Fin del día",
                            JOptionPane.INFORMATION_MESSAGE);
					ArrayList<Integer> listaDeDNIClientes = presentadorOfertas.devolverDNISComoInteger();

					for (int dni : listaDeDNIClientes) {
						String dniCliente = String.valueOf(dni);
						ArrayList<String> datosDeOferta = new ArrayList<String>();
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
		frame.getContentPane().add(btnCargarOfertas);
		
		btnMejoresOfertas = new JButton("Mostrar mejores ofertas");
		btnMejoresOfertas.setVisible(false);
		btnMejoresOfertas.setVerticalAlignment(SwingConstants.TOP);
		btnMejoresOfertas.setFocusable(false);
		btnMejoresOfertas.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnMejoresOfertas.setText("<html>" + btnMejoresOfertas.getText().replace("\n", "<br>") + "</html>");
		btnMejoresOfertas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnMejoresOfertas.addActionListener(e-> {
			ArrayList<Oferta> listaOfertaOrdenadas = new ArrayList<>(presentadorOfertas.devolverListaDeOfertasOrdenadaPorBeneficio());
			if (!listaOfertaOrdenadas.isEmpty()) {
				ArrayList<Oferta> mejoresOfertasPorBeneficio = new ArrayList<>(presentadorOfertas.devolverOfertasQueNoSeSolapan(listaOfertaOrdenadas));
				if (!mejoresOfertasPorBeneficio.isEmpty()) {
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
		});
		btnMejoresOfertas.setBounds(10, 233, 103, 35);
		frame.getContentPane().add(btnMejoresOfertas);
		
		JButton btnVolver = new JButton("Volver Atras");
		btnVolver.setFocusable(false);
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnVolver.addActionListener(e-> {
			mostrarPanelEnContenedor(tablaOfertas,panelPaginas);
			
		
		});
		btnVolver.setBounds(10, 28, 103, 30);
		frame.getContentPane().add(btnVolver);
		
		btnFinalizarDia = new JButton("Finalizar Dia");
		btnFinalizarDia.setFocusable(false);
		btnFinalizarDia.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnFinalizarDia.addActionListener(e-> {
            if (!diaTranscurrido) {
                int confirmacion = JOptionPane.showConfirmDialog(frame,
                    "¿Está seguro que desea finalizar el día?\nNo podrá agregar más ofertas.",
                    "Confirmar finalización",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    diaTranscurrido = true;
                    btnMejoresOfertas.setVisible(true);
                    btnSiguienteDia.setVisible(true);
                    btnFinalizarDia.setVisible(false);
                    if (timer != null) {
                        timer.cancel();
                    }
                    JOptionPane.showMessageDialog(frame,
                        "El día ha finalizado. Ya no se pueden ingresar más ofertas.",
                        "Fin del día",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
		});
		btnFinalizarDia.setBounds(10, 279, 103, 30);
		frame.getContentPane().add(btnFinalizarDia);		
		
		btnSiguienteDia = new JButton("Siguiente Dia");
		btnSiguienteDia.setVisible(false);
		btnSiguienteDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diaTranscurrido=false;
				btnMejoresOfertas.setVisible(false);
				btnSiguienteDia.setVisible(false);
				btnFinalizarDia.setVisible(true);
				presentadorOfertas.borrarListaDeOfertas();
				limpiarTablaOfertas();
				incrementarUnDia();
				actualizarLabelFechaActual(lblFechaActual);
				iniciarTemporizadorDia();
			}

			private void incrementarUnDia() {
				LocalDate nuevaFecha = fechaActual.plusDays(1);
				fechaActual = nuevaFecha;
				presentadorOfertas.actualizarFechaActual(nuevaFecha);
			}
		});
		btnSiguienteDia.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSiguienteDia.setFocusable(false);
		btnSiguienteDia.setBounds(10, 320, 103, 30);
		frame.getContentPane().add(btnSiguienteDia);		
		
		JButton btnCalendario = new JButton("Calendario");
		btnCalendario.addActionListener(e->{
			
				JPanel panelCalendario = new JPanel();
				panelCalendario.setBounds(123, 30, 861, 435);
//				panelCalendario.add(calendar);
				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setBounds(80, 69, 70, 20);
				panelCalendario.add(dateChooser);
				
//				JDayChooser dayChooser = calendar.getDayChooser();
//				dayChooser.setAlwaysFireDayProperty(true); // here is the key
//				dayChooser.addPropertyChangeListener("day", calendar);
//				calendar.getDayChooser();
				JButton btnMostrarFecha = new JButton("Calendario");				
				panelCalendario.add(btnMostrarFecha);
				btnMostrarFecha.setBounds(430, 353, 100, 100);
				btnMostrarFecha.addActionListener(e2->{
					Date fecha = dateChooser.getDate();
					SimpleDateFormat formato = new SimpleDateFormat("d/MM/yyyy");
//					@SuppressWarnings("unused")
//					String stFecha= formato.format(fecha);
					
					JOptionPane.showMessageDialog(null,
	                        ""+ formato.format(fecha));
					
				});
				mostrarPanelEnContenedor(panelCalendario,panelPaginas);
				
				
//				btnCalendario.addActionListener
			
				
		});
		btnCalendario.setBounds(10, 360, 89, 23);
		frame.getContentPane().add(btnCalendario);
		
		
		
		

		 
	

	}

	
	private void actualizarLabelFechaActual(JLabel lblFechaActual) {
		String _fecha = convertirLocalDateAString(fechaActual);
		lblFechaActual.setText(_fecha);
	}
	
	private String convertirLocalDateAString(LocalDate fecha) {
		String año = String.valueOf(fecha.getYear());
		String mes=String.valueOf(fecha.getMonthValue());
		String dia=String.valueOf(fecha.getDayOfMonth());
		return año + " - " + mes + " - " + dia;
	}
	
	private void iniciarTemporizadorDia() {
		 timer = new Timer();
		 
		 TimerTask tarea1 = new TimerTask() {
	            public void run() {
	            	EventQueue.invokeLater(() -> {
	                    diaTranscurrido = true;
	                    btnMejoresOfertas.setVisible(true);
	                    btnSiguienteDia.setVisible(true);
	                    btnFinalizarDia.setVisible(false);
	                    JOptionPane.showMessageDialog(frame, 
	                        "El día ha finalizado. Ya no se pueden ingresar más ofertas.",
	                        "Fin del día",
	                        JOptionPane.INFORMATION_MESSAGE);
	                });
	            }
	        };
	    timer.schedule(tarea1, duracionDeDia);
		
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
	
	private void crearOferta() {
		if (diaTranscurrido) {
            JOptionPane.showMessageDialog(frame,
                "No se pueden agregar más ofertas. El día ha finalizado.",
                "Día finalizado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
		JPanel panel = new JPanel();
		panel.setBounds(new Rectangle(123, 30, 867, 438));
		panel.setLayout(null);

		JLabel nombre = new JLabel("Nombre");
		nombre.setBounds(20, 20, 100, 25);
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
			String nombreOferta = textNombre.getText().trim();
			String dniOferta = textDni.getText().trim().toLowerCase();
			String precioOferta = textPrecio.getText().trim().toLowerCase();
			String horarioI = textHorarioInicio.getText().trim().toLowerCase();
			String horarioF = textHorarioFin.getText().trim().toLowerCase();
			
			if(!nombreOferta.isEmpty() && !dniOferta.isEmpty() && !precioOferta.isEmpty() && 
					!horarioI.isEmpty() && !horarioF.isEmpty() ) {
				
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
						eliminarFilaDeTablaConDniSiEsRepetido(dniCliente);						
						}
					}catch (IllegalArgumentException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
							
				}else {
					JOptionPane.showMessageDialog(null, "Error: Las entradas numericas no deben tener letras involucradas");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Campos inválidos, por favor complete todos los campos.");
			}
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
	
	
}

