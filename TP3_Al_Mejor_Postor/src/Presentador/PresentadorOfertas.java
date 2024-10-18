package Presentador;

import java.util.HashMap;

import Modelo.Logica;
import Modelo.Oferta;

public class PresentadorOfertas {
	private Logica logica;
	public PresentadorOfertas() {
		logica = new Logica();
	}

	public boolean agregarOferta(String nombre, int dni,double precio, int horaDeInicio , int horaDeFinalizacion) {
		try {
			logica.agregarOferta(nombre,dni,precio,horaDeInicio,horaDeFinalizacion);
			return true;
			
		}catch (IllegalArgumentException e){
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
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	public boolean guardarOfertas(HashMap<Integer, Oferta> ofertas, String nombreArchivo) {
		// TODO Auto-generated method stub
		if(logica.guardarOferta(ofertas, nombreArchivo)) {
			return true;
		}
		return false;
	}

	public boolean cargarOfertas(String nombreArchivo) {
		// TODO Auto-generated method stub
		return false;
	}

	public HashMap<Integer, Oferta> devolverOfertasArchivo() {
		// TODO Auto-generated method stub
		return logica.devolverOfertasArchivo();
	}
	
	
	
}
