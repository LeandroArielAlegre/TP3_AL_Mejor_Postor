package Modelo;

import java.util.HashMap;

public class Logica {

	private AdministradorOfertas admOfertas;
	private ArchivoJSON  archivoJSON;
	public Logica() {
		admOfertas = new AdministradorOfertas();
		archivoJSON = new ArchivoJSON();
	}

	public void agregarOferta(String nombre, int dni, double precio, int horaDeInicio , int horaDeFinalizacion) {
		admOfertas.agregarOferta(nombre,dni,precio, horaDeInicio, horaDeFinalizacion);
	}
	
	public void eliminarOferta(int dni) {
		admOfertas.eliminarOferta(dni);
	}

	public boolean guardarOferta(HashMap<Integer, Oferta> ofertas, String nombreArchivo) {
			// TODO Auto-generated method stub
			try {
				archivoJSON.setListaDeOfertas(ofertas);
				archivoJSON.generarJSON(nombreArchivo);

			} catch (IllegalArgumentException e) {
				return false;
			} catch (Exception e) {

				return false;
			}
		
		return true;
		
	}

	public HashMap<Integer, Oferta> devolverOfertas() {

		return admOfertas.getListaDeOfertas();
	}

	public HashMap<Integer, Oferta> devolverOfertasArchivo() {

		return this.archivoJSON.getListaDeOfertas();
	}
	
	
}
