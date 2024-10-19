package Modelo;

import java.util.ArrayList;

public class Logica {
	
	private ArchivoJSON archivoJSON = new ArchivoJSON();
	private conjuntoDeOfertasDelDia admOfertas;
	public Logica() {
		admOfertas = new conjuntoDeOfertasDelDia();
	}

	public void agregarOferta(String nombre, int dni, double precio, int horaDeInicio , int horaDeFinalizacion) {
		admOfertas.agregarOferta(nombre,dni,precio, horaDeInicio, horaDeFinalizacion);
	}
	
	public void eliminarOferta(int dni) {
		admOfertas.eliminarOferta(dni);
	}
	
	public void borrarListaDeOfertas() {
		admOfertas.borrarListaDeOfertas();
	}

	public void guardarEnArchivo(String archivo) {
		archivoJSON.setListaDeOfertas(admOfertas.getListaDeOfertas());
		archivoJSON.generarJSON(archivo);
	}
	
	public void cargarDeArchivo(String archivo) {
		this.archivoJSON = archivoJSON.leerJSON(archivo);
		admOfertas.setListaDeOfertas(archivoJSON.getListaDeOfertas());
	}
	public ArrayList<String> devolverOfertaEnLista(String dni){
		return admOfertas.devolverOfertaEnLista(dni);
		
	}
	public ArrayList<String> devolverTodosLosDniDeLosClientes(){
		return admOfertas.devolverTodosLosDniDeLosClientes();
		
	}
	
}
