package Presentador;

import Modelo.Logica;

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
	
	
	
}
