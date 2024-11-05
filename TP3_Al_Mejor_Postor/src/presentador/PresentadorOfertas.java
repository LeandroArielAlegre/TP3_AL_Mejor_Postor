package presentador;

import java.util.ArrayList;
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
			return logica.getListaDeOfertas();
			
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public boolean guardarOfertas(String nombreArchivo) {
	    return logica.guardarOferta(nombreArchivo);
	}

	
	public boolean cargarOfertasDeArchivo(String nombreArchivo) {

			try {
				logica.cargarOfertasDeArchivo(nombreArchivo);
				return true;
				
			}catch (IllegalArgumentException e){
				return false;							
		}
	}

	public HashMap<Integer, Oferta> devolverOfertasArchivo() {

		return logica.devolverOfertasArchivo();
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
	
	public ArrayList<Oferta> devolverListaDeOfertasOrdenadaPorBeneficio(){
		   
    	try {
    		return 	logica.devolverListaDeOfertasOrdenadaPorBeneficio();
			
		}catch (IllegalArgumentException e){
			return null;
			
		}
    }
	
	public ArrayList<String> devolverTodosLosDniDeLosClientes(){
		try {
			return 	logica.devolverTodosLosDniDeLosClientes();
			
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

	public void agregarOferta(String nombreOferta, int dniCliente, double precioOfertado, int horarioInicial,
			int horarioFinal) {
		logica.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal);
		
	}
	
	
}
