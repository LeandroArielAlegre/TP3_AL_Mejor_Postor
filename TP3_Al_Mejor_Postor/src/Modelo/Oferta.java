package Modelo;

import java.io.Serializable;


public class Oferta implements Serializable{
	private String nombre;
	private int dni;
	private double precio;
	private int horaDeInicio;
	private int horaDeFinalizacion;

	public Oferta(String nombre,int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
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

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
