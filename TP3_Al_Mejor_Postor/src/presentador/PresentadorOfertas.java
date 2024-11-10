package presentador;

import java.time.LocalDate;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.HashMap;

import modelo.LogicaOfertas;
import modelo.Oferta;

public class PresentadorOfertas {
	private LogicaOfertas logica;
	
	public PresentadorOfertas() {
		logica = new LogicaOfertas();
	}

	public boolean puedeAgregarOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {	
		return logica.puedeAgregarOferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);	   
	}
	
	public void agregarOferta(String nombreOferta, int dniCliente, double precioOfertado, int horarioInicial,
			int horarioFinal) {
		logica.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal);

	}
	public boolean eliminarOferta(int dni) {
		try {
			logica.eliminarOferta(dni);
			return true;

		}catch (IllegalArgumentException e){
			return false;

		}
	}

	public HashMap<Integer, Oferta> devolverOfertas() {
		try {
			return logica.getMapDeOfertas();

		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public void guardarOfertas(String nombreArchivo) {
		logica.guardarOferta(nombreArchivo);
	}

	public boolean puedeGuardarOfertas(String nombreDeArchivo) {
		return logica.puedeGuardarOferta(nombreDeArchivo);
	}
	public boolean puedeCargarOfertasDeArchivo(String nombreArchivo) {
		
			return logica.puedeCargarOfertasDeArchivo(nombreArchivo);
	}

	public void cargarOfertasDeArchivo(String nombreDeArchivo) {
		try {
			logica.cargarOfertasDeArchivo(nombreDeArchivo);
		}catch(IllegalArgumentException e){
			
		}
	}

	public void borrarListaDeOfertas() {
		logica.borrarListaDeOfertas();	

	}

	public ArrayList<String> devolverOfertaComoUnaLista(String dni){
		try {
			return 	logica.devolverOfertaComoUnaLista(dni);

		}catch (IllegalArgumentException e){
			return null;

		}

	}

	public ArrayList<Oferta> devolverListaDeOfertasOrdenadaPorGananciasPorHora(){

		try {
			return 	logica.devolverListaDeOfertasOrdenadaPorGananciasPorHora();

		}catch (IllegalArgumentException e){
			return null;

		}
	}

	public ArrayList<String> devolverTodosLosDniDeLosClientesComoString(){
		try {
			return 	logica.devolverTodosLosDniDeLosClientesComoString();

		}catch (IllegalArgumentException e){
			return null;

		}

	}

	public ArrayList<Oferta> devolverOfertasQueNoSeSolapan(ArrayList<Oferta> listaOfertaOrdenadas) {
		try {
			return 	logica.devolverOfertasQueNoSeSolapan(listaOfertaOrdenadas);

		}catch (IllegalArgumentException e){
			return null;

		}
	}

	public ArrayList<Integer> devolverDNISComoInteger() {
		return 	logica.devolverDNISComoListaDeIntegers();
	}

	public boolean actualizarFechaActual(LocalDate nuevaFechaActual) {
		try {
			logica.actualizarFechaActual(nuevaFechaActual);
			return true;

		}catch (IllegalArgumentException e){
			return false;

		}
	}

	public LocalDate devolverFechaActual() {
		try {
			return 	logica.devolverFechaActual();

		}catch (IllegalArgumentException e){
			return null;

		}
	}

}
