package Presentador;

import java.util.ArrayList;
import java.util.HashMap;

import Modelo.Logica;
import Modelo.Oferta;

public class PresentadorOfertas {
	private Logica logica;
	public PresentadorOfertas() {
		logica = new Logica();
	}

	public boolean agregarOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
	    try {
	        logica.agregarOferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);
	        return true;
	    } catch (IllegalArgumentException e) {
//	        System.out.println("Error al agregar oferta: " + e.getMessage()); // Log para detalles del error
	        return false;
	    }
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
			return logica.devolverOfertas();
			
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
	
	public boolean borrarListaDeOfertas() {
		try {
			logica.borrarListaDeOfertas();
			return true;
			
		}catch (IllegalArgumentException e){
			return false;
			
		}
	}
	
	public ArrayList<String> devolverOfertaEnLista(String dni){
		try {
			return logica.devolverOfertaEnLista(dni);
			
		}catch (IllegalArgumentException e){
			return null;
			
		}
		
	}
	
	public ArrayList<String> devolverTodosLosDniDeLosClientes(){
		try {
			return logica.devolverTodosLosDniDeLosClientes();
			
		}catch (IllegalArgumentException e){
			return null;
			
		}
		
	}
	
}
