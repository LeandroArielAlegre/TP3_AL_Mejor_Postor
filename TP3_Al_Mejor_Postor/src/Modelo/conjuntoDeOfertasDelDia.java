package Modelo;
import java.util.HashMap;
public class conjuntoDeOfertasDelDia {
	
	private HashMap<Integer,Oferta> listaDeOfertas = new HashMap<Integer,Oferta>();
	
	public conjuntoDeOfertasDelDia() {
		
	}
	
	public void agregarOferta(String nombre,int dni, double precio, int horaDeInicio , int horaDeFinalizacion) {
		listaDeOfertas.put(dni,new Oferta(nombre,dni,precio,horaDeInicio,horaDeFinalizacion));
	}
	
	public void eliminarOferta(int dni) {
		 if(listaDeOfertas.containsKey(dni)) {
			 listaDeOfertas.remove(dni);
		 }
	}
	//Ojo... en el caso que vaya a volver a ofertar la misma persona habria que preguntar si aumento
	//el precio de la oferta, entonces si es el caso cambiamos el valor del precio
}
