package Presentador;

import java.util.ArrayList;

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
	public boolean guardarEnArchivo(String archivo) {
		try {
			logica.guardarEnArchivo(archivo);
			return true;
			
		}catch (IllegalArgumentException e){
			return false;
			
		}
	}
	
	public boolean cargarDeArchivo(String archivo) {
		try {
			logica.cargarDeArchivo(archivo);
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
