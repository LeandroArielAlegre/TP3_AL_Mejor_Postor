package Modelo;

public class Logica {

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
	
	
}
