package Modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
	
	public ArrayList<String> devolverOfertaEnLista(String dni){
		int dniCliente = Integer.parseInt(dni);
		ArrayList<String> listaDeUnaOferta = new ArrayList<String>();
		Oferta oferta = this.listaDeOfertas.get(dniCliente);
		String precioString = String.valueOf(oferta.getPrecio());
		String horaInicio =String.valueOf(oferta.getHoraDeInicio());
		String horaFin = String.valueOf(oferta.getHoraDeFinalizacion());
		listaDeUnaOferta.add(oferta.getNombre());
		listaDeUnaOferta.add(dni);
		listaDeUnaOferta.add(precioString);
		listaDeUnaOferta.add(horaInicio);
		listaDeUnaOferta.add(horaFin);
		return listaDeUnaOferta;
	}
	
	public ArrayList<String> devolverTodosLosDniDeLosClientes(){
		ArrayList<String> listaDeDni = new ArrayList<String>();
		for (Entry<Integer, Oferta> ofertaCliente : listaDeOfertas.entrySet()) {
			String dniCliente = String.valueOf(ofertaCliente.getKey());
			listaDeDni.add(dniCliente);
		}
		return listaDeDni;
	}
	
	//Ojo... en el caso que vaya a volver a ofertar la misma persona habria que preguntar si aumento
	//el precio de la oferta, entonces si es el caso cambiamos el valor del precio
	public HashMap<Integer, Oferta> getListaDeOfertas() {
		return listaDeOfertas;
	}

	public void setListaDeOfertas(HashMap<Integer, Oferta> listaDeOfertas) {
		this.listaDeOfertas = listaDeOfertas;
	}
	public void borrarListaDeOfertas() {
		listaDeOfertas.clear();
	}
	
}
