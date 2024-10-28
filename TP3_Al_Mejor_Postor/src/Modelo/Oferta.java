package Modelo;

import java.io.Serializable;

public class Oferta implements Serializable, Comparable<Oferta> {
    private String nombre;
    private int dni;
    private double precio;
    private int horaDeInicio;
    private int horaDeFinalizacion;

    public Oferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
        if (horaDeInicio >= horaDeFinalizacion || horaDeInicio < 0 || horaDeFinalizacion > 24) {
            throw new IllegalArgumentException("El intervalo de tiempo desde " + horaDeInicio 
                    + " hasta " + horaDeFinalizacion + " es inválido.");
        }

        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo.");
        }

        this.nombre = nombre;
        this.dni = dni;
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
        if (nuevoPrecio <= 0) {
            throw new IllegalArgumentException("El nuevo precio debe ser positivo.");
        }
        if(nuevoPrecio<this.precio) {
        	throw new IllegalArgumentException("El nuevo precio debe ser mayor que el nuevo");
        }
        this.precio = nuevoPrecio;
    }

    public int getDni() {
        return dni;
    }

    // Verificar si dos ofertas se solapan
    public boolean seSolapaCon(Oferta otra) {
        return (this.horaDeFinalizacion < otra.horaDeInicio || otra.horaDeFinalizacion < this.horaDeInicio);
    }

    // Calcular las ganancias por hora
    public double getGananciasPorHora() {
        return precio / (horaDeFinalizacion - horaDeInicio);
    }

    // Comparación para ordenar ofertas por precio
    @Override
    public int compareTo(Oferta otra) {
        return Double.compare(otra.getPrecio(), this.getPrecio());  // De mayor a menor
    }

    // ID serial para asegurar compatibilidad en la serialización
    private static final long serialVersionUID = 1L;
}
