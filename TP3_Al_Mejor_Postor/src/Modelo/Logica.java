package Modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Logica {

	private AdministradorOfertas admOfertas;
	private ArchivoJSON  archivoJSON;
	public Logica() {
		admOfertas = new AdministradorOfertas();
		archivoJSON = new ArchivoJSON();
	}

	public boolean puedeAgregarOferta(String nombre, int dni, double precio, int horaDeInicio , int horaDeFinalizacion) {
		return admOfertas.puedeAgregarOferta(nombre,dni,precio, horaDeInicio, horaDeFinalizacion);
	}
	
	

	public boolean guardarOferta(String nombreArchivo) {
		HashMap<Integer, Oferta> ofertasLocales= devolverOfertas();
	    try {
	        archivoJSON.setListaDeOfertas(ofertasLocales);
	        archivoJSON.generarJSON(nombreArchivo);
	        return true;
	    } catch (Exception e) {
//	        System.out.println("Error al guardar el archivo: " + e.getMessage());
	        return false;
	    }
	}

	public void eliminarOferta(int dni) {
		admOfertas.eliminarOferta(dni);
	}
	
	public HashMap<Integer, Oferta> devolverOfertas() {

		return admOfertas.getListaDeOfertas();
	}

	public HashMap<Integer, Oferta> devolverOfertasArchivo() {

		return this.archivoJSON.getListaDeOfertas();
	}


	public void actualizarOfertas(HashMap<Integer, Oferta> ofertasCargadas) {
	    this.admOfertas.setListaDeOfertas(ofertasCargadas);
	}

	public void cargarOfertasDeArchivo(String archivo) {
		this.archivoJSON = archivoJSON.leerJSON(archivo);
		admOfertas.setListaDeOfertas(archivoJSON.getListaDeOfertas());
	}

	public ArrayList<String> devolverOfertaComoUnaLista(String dni){
		return admOfertas.devolverOfertaComoUnaLista(dni);
		
	}
	public ArrayList<String> devolverTodosLosDniDeLosClientes(){
		return admOfertas.devolverTodosLosDniDeLosClientes();
		
	}
	
	public ArrayList<Oferta> devolverListaDeOfertasOrdenadaPorBeneficio(){
		   
    	return admOfertas.devolverListaDeOfertasOrdenadaPorBeneficio();
    	
    }

	public void borrarListaDeOfertas() {
		admOfertas.borrarListaDeOfertas();
	}

	public ArrayList<Oferta> devolverOfertasQueNoSeSolapan(ArrayList<Oferta> listaOfertaOrdenadas) {

		return admOfertas.devolverOfertasQueNoSeSolapen(listaOfertaOrdenadas);
	}

	public ArrayList<Integer> devolverDNISComoInteger() {
		// TODO Auto-generated method stub
		return admOfertas.devolverDNISComoInteger();
	}

	public void agregarOferta(String nombreOferta, int dniCliente, double precioOfertado, int horarioInicial,
			int horarioFinal) {
		admOfertas.agregarOferta(nombreOferta, dniCliente, precioOfertado, horarioInicial, horarioFinal);
		
	}

	
	
}
