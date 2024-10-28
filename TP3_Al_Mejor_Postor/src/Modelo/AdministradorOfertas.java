package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AdministradorOfertas {
    private HashMap<Integer, Oferta> listaDeOfertas;

    public AdministradorOfertas() {
        this.listaDeOfertas = new HashMap<>();
    }

    public void agregarOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
        validarDatosOferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);
        
        if (listaDeOfertas.containsKey(dni)) {
            manejarOfertaExistente(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);
        } else {
            agregarNuevaOferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);
        }
    }

    private void validarDatosOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (dni <= 0) {
            throw new IllegalArgumentException("El DNI debe ser un número positivo");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (horaDeInicio < 0 || horaDeFinalizacion > 24 || horaDeInicio >= horaDeFinalizacion) {
            throw new IllegalArgumentException("El intervalo de tiempo es inválido");
        }
    }

    private void manejarOfertaExistente(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
        Oferta ofertaExistente = listaDeOfertas.get(dni);
        
        if (ofertaExistente.getPrecio() > precio) {
            throw new IllegalArgumentException("La nueva oferta debe superar el precio actual de " + 
                ofertaExistente.getPrecio());
        }
        
        if (esOfertaIdentica(ofertaExistente, precio, horaDeInicio, horaDeFinalizacion)) {
            throw new IllegalArgumentException("Esta oferta es idéntica a la existente");
        }
        
        listaDeOfertas.put(dni, new Oferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion));
    }

    private boolean esOfertaIdentica(Oferta oferta, double precio, int horaDeInicio, int horaDeFinalizacion) {
        return oferta.getPrecio() == precio && 
               oferta.getHoraDeInicio() == horaDeInicio && 
               oferta.getHoraDeFinalizacion() == horaDeFinalizacion;
    }

    private void agregarNuevaOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
        // Verificar solapamiento con otras ofertas
//        for (Oferta ofertaExistente : listaDeOfertas.values()) {
//            if (ofertaExistente.seSolapaCon(new Oferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion))) {
//                throw new IllegalArgumentException("La nueva oferta se solapa con una oferta existente");
//            }
//        }
        
        listaDeOfertas.put(dni, new Oferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion));
    }

    public void eliminarOferta(int dni) {
        if (!listaDeOfertas.containsKey(dni)) {
            throw new IllegalArgumentException("No existe ninguna oferta registrada con el DNI: " + dni);
        }
        listaDeOfertas.remove(dni);
    }

    public HashMap<Integer, Oferta> getListaDeOfertas() {
        return new HashMap<>(listaDeOfertas); // Retorna una copia defensiva
    }

    public Oferta obtenerOfertaDadoDNI(int dni) {
        if (!listaDeOfertas.containsKey(dni)) {
            throw new IllegalArgumentException("No existe ninguna oferta con el DNI: " + dni);
        }
        return listaDeOfertas.get(dni);
    }

    public void setListaDeOfertas(HashMap<Integer, Oferta> ofertasCargadas) {
        if (ofertasCargadas == null) {
            throw new IllegalArgumentException("La lista de ofertas no puede ser null");
        }
        
        // Validar todas las ofertas antes de hacer el set
        for (Map.Entry<Integer, Oferta> entry : ofertasCargadas.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException("Oferta nula encontrada para DNI: " + entry.getKey());
            }
            if (!entry.getKey().equals(entry.getValue().getDni())) {
                throw new IllegalArgumentException("DNI inconsistente encontrado: " + entry.getKey());
            }
        }
        
        this.listaDeOfertas = new HashMap<>(ofertasCargadas);
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

	public void borrarListaDeOfertas() {
		listaDeOfertas.clear();
	}
}