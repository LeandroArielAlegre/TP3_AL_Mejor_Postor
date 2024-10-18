package Modelo;

import java.io.Serializable;


public class Oferta implements Serializable{
	private String nombre;
	private int dni;
	private double precio;
	private int horaDeInicio;
	private int horaDeFinalizacion;

	public Oferta(String nombre,int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
		if (horaDeInicio >= horaDeFinalizacion || horaDeInicio < 0 || horaDeFinalizacion > 24)//la hora de inicio debe ser menor que la de fin 
		{
			throw new IllegalArgumentException("El lapso de tiempo desde "+ horaDeInicio 
					+"hasta " + horaDeFinalizacion + "es invalido");
		}

		if (precio <= 0) {
			throw new IllegalArgumentException("El precio debe ser positivo");// no se aceptan ofertas con numeros negativos
		}
	
		this.nombre = nombre;
		this.dni =dni;
		this.precio = precio;
		this.horaDeInicio = horaDeInicio;
		this.horaDeFinalizacion = horaDeFinalizacion;
	}

	public int getHoraDeInicio() {
		return horaDeInicio;
	}


	public int getHoraDeFinalizacion() {
		return horaDeFinalizacion;
	}

	public String getNombre() {
		return nombre;
	}


	public double getPrecio() {
		return precio;
	}
	public void setPrecio(Double nuevoPrecio) {
		this.precio = nuevoPrecio;
	}

	public int getDni() {
		return dni;
	}

	public boolean seSolapaCon(Oferta otra) {
		return !(this.horaDeFinalizacion <= otra.horaDeInicio || otra.horaDeFinalizacion <= this.horaDeInicio);
	}
	public double getGananciasPorHora() {
		return precio / (horaDeFinalizacion - horaDeInicio);
	}

	public int compareTo(Oferta otra) {
		// Compara por precio por hora de mayor a menor
		return Double.compare(otra.getPrecio(), this.getPrecio());
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
