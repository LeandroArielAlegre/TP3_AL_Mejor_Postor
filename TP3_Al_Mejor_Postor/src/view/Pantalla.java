package view;

import java.awt.Color;
import java.awt.EventQueue;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import modelo.Oferta;
import presentador.PresentadorOfertas;

import java.util.Timer;
import java.util.TimerTask;

import java.text.SimpleDateFormat;
import java.time.LocalDate;


import com.toedter.calendar.JCalendar;

public class Pantalla {

	private JFrame frame;
	private TablaOfertas tablaOfertas;
	private TablaOfertas tablaMejoresOfertas;
	private JPanel panelPaginas;
	private PresentadorOfertas presentadorOfertas;
	private Boolean diaTranscurrido;
	private JButton btnFinalizarDia;
	private JButton btnGuardarOfertas;
	protected JButton btnMejoresOfertas;
	protected JButton btnSiguienteDia;
	private Timer timer;
	private Integer tiempoTranscurrido;
	private long duracionDeDia = 300 * 1000;
	private LocalDate fechaActual;
	private JLabel lblTiempoActual;
	private JLabel lblFechaActual;

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

	public Pantalla() {
		initialize();
	}

	private void initialize() {
		diaTranscurrido = false;
		presentadorOfertas = new PresentadorOfertas();
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
		mostrarPanelEnOtroPanel(tablaOfertas,panelPaginas);

		lblFechaActual = new JLabel(" ");
		lblFechaActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaActual.setBounds(407, 11, 166, 14);
		actualizarLabelFechaActual();
		frame.getContentPane().add(lblFechaActual);

		JButton btnAgregarOfertaEnTabla = new JButton("Agregar Oferta");
		btnAgregarOfertaEnTabla.setFocusable(false);
		btnAgregarOfertaEnTabla.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAgregarOfertaEnTabla.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAgregarOfertaEnTabla.setHorizontalAlignment(SwingConstants.LEFT);
		btnAgregarOfertaEnTabla.setBounds(10, 69, 103, 30);
		btnAgregarOfertaEnTabla.addActionListener(e -> {
			crearOferta();
		});
		frame.getContentPane().add(btnAgregarOfertaEnTabla);

		JButton btnLimpiarOfertasIngresadas = new JButton("Limpiar Ofertas");
		btnLimpiarOfertasIngresadas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnLimpiarOfertasIngresadas.setFocusable(false);
		btnLimpiarOfertasIngresadas.setBounds(10, 110, 103, 30);
		btnLimpiarOfertasIngresadas.addActionListener(e -> {			
			presentadorOfertas.borrarListaDeOfertas();
			limpiarTablaOfertas();
		});
		frame.getContentPane().add(btnLimpiarOfertasIngresadas);

		
		btnGuardarOfertas = new JButton("Guardar");
		btnGuardarOfertas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGuardarOfertas.setFocusable(false);
		btnGuardarOfertas.setBounds(10, 151, 103, 30);
		btnGuardarOfertas.setVisible(false);
		btnGuardarOfertas.addActionListener(e -> {
			String nombreDeArchivo = (fechaActual.toString());
			if(presentadorOfertas.puedeGuardarOfertas(nombreDeArchivo)) {
				presentadorOfertas.guardarOfertas(nombreDeArchivo);
				JOptionPane.showMessageDialog(null, "El archivo se guardo exitosamente");
			}else {		
				JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");			
			}
		});
		frame.getContentPane().add(btnGuardarOfertas);

		
		btnMejoresOfertas = new JButton("Mostrar mejores ofertas");
		btnMejoresOfertas.setVisible(false);
		btnMejoresOfertas.setVerticalAlignment(SwingConstants.TOP);
		btnMejoresOfertas.setFocusable(false);
		btnMejoresOfertas.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		btnMejoresOfertas.setText("<html>" + btnMejoresOfertas.getText().replace("\n", "<br>") + "</html>");
		btnMejoresOfertas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnMejoresOfertas.addActionListener(e-> {
			ArrayList<Oferta> listaDeOfertasOrdenadasPorGananciasPorHora =
					new ArrayList<>(presentadorOfertas.devolverListaDeOfertasOrdenadaPorGananciasPorHora());
			if (!listaDeOfertasOrdenadasPorGananciasPorHora.isEmpty()) {
				ArrayList<Oferta> mejoresOfertasPorBeneficio = 
						new ArrayList<>(presentadorOfertas.
								devolverOfertasQueNoSeSolapan(listaDeOfertasOrdenadasPorGananciasPorHora));
				if (!mejoresOfertasPorBeneficio.isEmpty()) {

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
					mostrarPanelEnOtroPanel(tablaMejoresOfertas,panelPaginas);
				}   
			} else {
				JOptionPane.showMessageDialog(null, "Error: no existen ofertas");
			}
		});
		btnMejoresOfertas.setBounds(10, 233, 103, 35);
		frame.getContentPane().add(btnMejoresOfertas);

		btnSiguienteDia = new JButton("Siguiente Dia");
		btnSiguienteDia.setVisible(false);
		btnSiguienteDia.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnSiguienteDia.setFocusable(false);
		btnSiguienteDia.setBounds(10, 320, 103, 30);
		btnSiguienteDia.addActionListener(e-> {
			avanzarDia();				
		});				
		frame.getContentPane().add(btnSiguienteDia);

		JButton btnCargarOfertas = new JButton("Cargar Ofertas");
		btnCargarOfertas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCargarOfertas.setFocusable(false);
		btnCargarOfertas.setBounds(10, 192, 103, 30);
		btnCargarOfertas.addActionListener(e -> {			
			String nombreDeArchivo = cargarNombreDeArchivo();
			if(nombreDeArchivo != null) {
				if(presentadorOfertas.puedeCargarOfertasDeArchivo(nombreDeArchivo)) {
					presentadorOfertas.cargarOfertasDeArchivo(nombreDeArchivo);	
					finalizarDia();
					agregarOfertasEnTabla();
					
				}else{
					JOptionPane.showMessageDialog(null, "ERROR: no se pudo cargar el archivo");
				}
			}else {
				JOptionPane.showMessageDialog(null, "ERROR: nombre de archivo vacio");
			}

		});
		frame.getContentPane().add(btnCargarOfertas);

		JButton btnVolver = new JButton("Volver Atras");
		btnVolver.setFocusable(false);
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnVolver.addActionListener(e-> {
			mostrarPanelEnOtroPanel(tablaOfertas,panelPaginas);					
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
					finalizarDia();
				}
			}
		});
		btnFinalizarDia.setBounds(10, 279, 103, 30);
		frame.getContentPane().add(btnFinalizarDia);				

		JPanel panelCalendario = new JPanel();
		panelCalendario.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelCalendario.setBounds(123, 30, 861, 435);
		panelCalendario.setLayout(null);
		
		JCalendar calendario = new JCalendar();
		calendario.setBounds(14, 8, panelCalendario.getWidth()-48, panelCalendario.getHeight()-102);
		panelCalendario.add(calendario);
		JButton btnMostrarOfertasDeFechaSeleccionada = new JButton("Mostrar ofertas de fecha seleccionada");		
		btnMostrarOfertasDeFechaSeleccionada.setText("<html>" + btnMostrarOfertasDeFechaSeleccionada.getText().replace("\n", "<br>") + "</html>");
		btnMostrarOfertasDeFechaSeleccionada.setBounds(panelCalendario.getWidth()-135, panelCalendario.getHeight() - 95, 100, 70);
		panelCalendario.add(btnMostrarOfertasDeFechaSeleccionada);
		btnMostrarOfertasDeFechaSeleccionada.addActionListener(e->{
			Date fecha = calendario.getDate();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/d");
			String stFecha= formato.format(fecha).replace("/", "-");
			if(presentadorOfertas.puedeCargarOfertasDeArchivo(stFecha)) {
				presentadorOfertas.cargarOfertasDeArchivo(stFecha);				
				finalizarDia();
				limpiarTablaOfertas();
				agregarOfertasEnTabla();			
			}else{
				JOptionPane.showMessageDialog(null, "ERROR: no se pudo cargar el archivo");
			}

		});
		JButton btnCargarConCalendario = new JButton("Cargar con calendario");
		btnCargarConCalendario.setFocusable(false);
		btnCargarConCalendario.setText("<html>" + btnCargarConCalendario.getText().replace("\n", "<br>") + "</html>");		
		btnCargarConCalendario.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCargarConCalendario.addActionListener(e->{
			mostrarPanelEnOtroPanel(panelCalendario,panelPaginas);							
		});
		btnCargarConCalendario.setBounds(10, 361, 103, 47);
		frame.getContentPane().add(btnCargarConCalendario);								 	

		lblTiempoActual = new JLabel("Tiempo restante");
		lblTiempoActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblTiempoActual.setBounds(789, 11, 166, 14);
		frame.getContentPane().add(lblTiempoActual);
	}


	private void avanzarDia() {
		diaTranscurrido=false;
		btnMejoresOfertas.setVisible(false);
		btnSiguienteDia.setVisible(false);
		btnFinalizarDia.setVisible(true);
		btnGuardarOfertas.setVisible(false);
		presentadorOfertas.borrarListaDeOfertas();
		limpiarTablaOfertas();
		incrementarUnDia();
		actualizarLabelFechaActual();
		iniciarTemporizadorDia();
	}

	private void incrementarUnDia() {
		LocalDate nuevaFecha = fechaActual.plusDays(1);
		fechaActual = nuevaFecha;
		presentadorOfertas.actualizarFechaActual(nuevaFecha);	
	}

	private void finalizarDia() {
		diaTranscurrido = true;
		btnMejoresOfertas.setVisible(true);                 
		btnFinalizarDia.setVisible(false);
		btnGuardarOfertas.setVisible(true); 
		btnSiguienteDia.setVisible(true);
		if (timer != null) {
			timer.cancel();
		}
		JOptionPane.showMessageDialog(frame,
				"El día ha finalizado. Ya no se pueden ingresar más ofertas.",
				"Fin del día",
				JOptionPane.INFORMATION_MESSAGE);		
	}

	private void agregarOfertasEnTabla() {
		ArrayList<Integer> listaDeDNIClientes = presentadorOfertas.devolverDNISComoInteger();
		for (int dni : listaDeDNIClientes) {
			String dniCliente = String.valueOf(dni);
			ArrayList<String> datosDeOferta = new ArrayList<String>();
			datosDeOferta = presentadorOfertas.devolverOfertaComoUnaLista(dniCliente);
			tablaOfertas.agregarOfertaEnTabla(datosDeOferta.get(0), datosDeOferta.get(1), "$"+datosDeOferta.get(2), datosDeOferta.get(3) + " a " + datosDeOferta.get(4));
		}
		JOptionPane.showMessageDialog(null, "El archivo se cargo exitosamente");
	}

	private String cargarNombreDeArchivo() { 
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JLabel label = new JLabel("Coloque el nombre del archivo: numeroDeAnio-numeroDeMes-numeroDeDia");
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

	private void iniciarTemporizadorDia() {
		timer = new Timer();
		tiempoTranscurrido = (int) (duracionDeDia/1000);;
		TimerTask tarea1 = new TimerTask() {			
			public void run() {							
				EventQueue.invokeLater(() -> {
					diaTranscurrido = true;
					btnMejoresOfertas.setVisible(true);
					btnSiguienteDia.setVisible(true);
					btnFinalizarDia.setVisible(false);
					btnGuardarOfertas.setVisible(true);
					JOptionPane.showMessageDialog(frame, 
							"El día ha finalizado. Ya no se pueden ingresar más ofertas.",
							"Fin del día",
							JOptionPane.INFORMATION_MESSAGE);
				});
			}
		};
		TimerTask tareaActualizarlbl = new TimerTask() {

			public void run() {						
				EventQueue.invokeLater(() -> {
					if(tiempoTranscurrido>0) { 
						tiempoTranscurrido--;	            	
						lblTiempoActual.setText("Tiempo restante " + tiempoTranscurrido.toString());
					}else {
						timer.cancel();
					}
				});
			}
		};
		timer.schedule(tarea1, duracionDeDia);
		timer.scheduleAtFixedRate(tareaActualizarlbl, 0, 1000); 
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

		mostrarPanelEnOtroPanel(panel,panelPaginas);	

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(e->{
			String nombreOferta = textNombre.getText().trim();
			String dniOferta = textDni.getText().trim().toLowerCase();
			String precioOferta = textPrecio.getText().trim().toLowerCase();
			String horarioI = textHorarioInicio.getText().trim().toLowerCase();
			String horarioF = textHorarioFin.getText().trim().toLowerCase();

			if(!nombreOferta.isEmpty() && !dniOferta.isEmpty() && !precioOferta.isEmpty() && 
					!horarioI.isEmpty() && !horarioF.isEmpty() ) {

				if (soloNumeros(dniOferta)&&soloNumerosHorario(horarioI,horarioF)&&
						soloNumeros(precioOferta)&&soloLetras(nombreOferta))	{	
					int dniCliente = Integer.parseInt(dniOferta);
					int horarioInicial = Integer.parseInt(horarioI);
					int horarioFinal = Integer.parseInt(horarioF);
					double precioOfertado = Double.parseDouble(precioOferta);

					try { 				
						if (presentadorOfertas.puedeAgregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal)) {					
							presentadorOfertas.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal);
							mostrarPanelEnOtroPanel(tablaOfertas,panelPaginas);
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

	private void mostrarPanelEnOtroPanel(JPanel nuevoPanel, JPanel contenedor) {
		nuevoPanel.setBounds(10, 11, 841, 415);
		contenedor.removeAll();
		contenedor.add(nuevoPanel);
		contenedor.revalidate();
		contenedor.repaint();
	}

	private boolean soloNumerosHorario(String horarioI, String horarioF) {
		boolean condicion1= soloNumeros(horarioI);
		boolean condicion2= soloNumeros(horarioF);
		if (condicion1&&condicion2){
			return true;
		}else{
			return false;
		}
	}

	private boolean soloNumeros(String string) {
		return string.matches("^\\d+$");
	}

	private boolean soloLetras(String string) {
		return string.matches("^[a-zA-Z]+$");
	}

	private void actualizarLabelFechaActual() {
		String _fecha = convertirLocalDateAString(fechaActual);
		lblFechaActual.setText(_fecha);
	}

	private String convertirLocalDateAString(LocalDate fecha) {
		String año = String.valueOf(fecha.getYear());
		String mes=String.valueOf(fecha.getMonthValue());
		String dia=String.valueOf(fecha.getDayOfMonth());
		return año + " - " + mes + " - " + dia;
	}

	public void eliminarFilaDeTablaConDniSiEsRepetido(int dni) {
		String dniCliente = String.valueOf(dni);
		if(tablaOfertas.contarDuplicadosPorDni(dniCliente) > 1){
			tablaOfertas.eliminarFilaPorDni(dniCliente);
		}

	}

	public void limpiarTablaOfertas() {
		tablaOfertas.limpiarTabla();
		mostrarPanelEnOtroPanel(tablaOfertas,panelPaginas);

	}

}

