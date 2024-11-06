package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class LogicaOfertas {
    private HashMap<Integer, Oferta> mapDeOfertas;
    private ArchivoJSON  archivoJSON;
//    private ArchivoJSON  archivoJSONFecha;
    
    public LogicaOfertas() {
        this.mapDeOfertas = new HashMap<>();
        archivoJSON = new ArchivoJSON();
//        archivoJSONFecha = new ArchivoJSON();
    }
    
    public void guardarOferta(String nombreArchivo) {
		HashMap<Integer, Oferta> ofertasLocales= getListaDeOfertas();
		
	        archivoJSON.setListaDeOfertas(ofertasLocales);
	        actualizarFechaActual(devolverFechaActual());
//	        archivoJSON.setFecha(actualizarFechaActual(devolverFechaActual()));
	        archivoJSON.generarJSON(nombreArchivo);

	}
    public void cargarOfertasDeArchivo(String archivo) {
		this.archivoJSON = archivoJSON.leerJSON(archivo);
		setListaDeOfertas(archivoJSON.getListaDeOfertas());
	}
    
    public void setListaDeOfertas(HashMap<Integer, Oferta> ofertasCargadas) {
        if (ofertasCargadas == null) {
            throw new IllegalArgumentException("La lista de ofertas no puede ser null");
        }
        
        for (Map.Entry<Integer, Oferta> entry : ofertasCargadas.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException("Oferta nula encontrada para DNI: " + entry.getKey());
            }
            if (!entry.getKey().equals(entry.getValue().getDni())) {
                throw new IllegalArgumentException("DNI inconsistente encontrado: " + entry.getKey());
            }
        }
        
        this.mapDeOfertas = new HashMap<>(ofertasCargadas);
    }
    public HashMap<Integer, Oferta> devolverOfertasArchivo() {

		return this.archivoJSON.getListaDeOfertas();
	}
    public boolean puedeAgregarOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
        
    	if(validarDatosOferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion)){
    		return true;
    	}
        return false;
    }

    private boolean validarDatosOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
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
        return true;
    }
    
    public void agregarOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {
		if (mapDeOfertas.containsKey(dni)) {
            manejarOfertaExistente(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);
        } else {
            agregarNuevaOferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion);
        }
	}
    
    private void manejarOfertaExistente(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) 
    {
        Oferta ofertaExistente = mapDeOfertas.get(dni);     
        if (ofertaExistente.getPrecio() > precio) 
        {
            throw new IllegalArgumentException("La nueva oferta debe superar el precio actual de " + 
                ofertaExistente.getPrecio());
        }        
        if (esOfertaIdentica(ofertaExistente, precio, horaDeInicio, horaDeFinalizacion)) 
        {
            throw new IllegalArgumentException("Esta oferta es idéntica a la existente");
        }      
        mapDeOfertas.put(dni, new Oferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion));
    }
    private boolean esOfertaIdentica(Oferta oferta, double precio, int horaDeInicio, int horaDeFinalizacion) {
        return oferta.getPrecio() == precio && 
               oferta.getHoraDeInicio() == horaDeInicio && 
               oferta.getHoraDeFinalizacion() == horaDeFinalizacion;
    }

    private void agregarNuevaOferta(String nombre, int dni, double precio, int horaDeInicio, int horaDeFinalizacion) {        
        mapDeOfertas.put(dni, new Oferta(nombre, dni, precio, horaDeInicio, horaDeFinalizacion));
    }

    public void eliminarOferta(int dni) {
        if (!mapDeOfertas.containsKey(dni)) {
            throw new IllegalArgumentException("No existe ninguna oferta registrada con el DNI: " + dni);
        }
        mapDeOfertas.remove(dni);
    }
      
    public ArrayList<Oferta> devolverListaDeOfertasOrdenadaPorBeneficio(){
   
    	ArrayList<Oferta> listaDeOfertasOrdenada = new ArrayList<Oferta>(this.mapDeOfertas.values());
     	Collections.sort(listaDeOfertasOrdenada, (o1,o2) -> Double.compare(o1.calcularGananciasPorHora(), o2.calcularGananciasPorHora()));
     	Collections.reverse(listaDeOfertasOrdenada);
 
     	return listaDeOfertasOrdenada;
    	
    }
    
    public ArrayList<Oferta> devolverOfertasQueNoSeSolapan(ArrayList<Oferta> listaDeOfertasOrdenada){
    	ArrayList<Oferta> listaDeOfertasQueNoSeSolapan = new ArrayList<Oferta>();
    	
    	for (Oferta ofertaActual : listaDeOfertasOrdenada) {
            boolean seSolapan = false;
            
            for (Oferta ofertaSeleccionada : listaDeOfertasQueNoSeSolapan) {
                if (ofertaActual.seSolapaCon(ofertaSeleccionada)) {
                    seSolapan = true;
                    break; 
                }
            }

            if (!seSolapan) {
                listaDeOfertasQueNoSeSolapan.add(ofertaActual);
            }
        }

        return listaDeOfertasQueNoSeSolapan;
    }
    

    public HashMap<Integer, Oferta> getListaDeOfertas() {
        return new HashMap<>(mapDeOfertas); 
    }

    public Oferta obtenerOfertaAsociadaConDNI(int dni) {
        if (!mapDeOfertas.containsKey(dni)) {
            throw new IllegalArgumentException("No existe ninguna oferta con el DNI: " + dni);
        }
        return mapDeOfertas.get(dni);
    }

    

    public ArrayList<String> devolverOfertaComoUnaLista(String dni){
		int dniCliente = Integer.parseInt(dni);
		ArrayList<String> ofertaComoUnaLista = new ArrayList<String>();
		Oferta oferta = this.mapDeOfertas.get(dniCliente);
		String precioString = String.valueOf(oferta.getPrecio());
		String horaInicio =String.valueOf(oferta.getHoraDeInicio());
		String horaFin = String.valueOf(oferta.getHoraDeFinalizacion());
		ofertaComoUnaLista.add(oferta.getNombre());
		ofertaComoUnaLista.add(dni);
		ofertaComoUnaLista.add(precioString);
		ofertaComoUnaLista.add(horaInicio);
		ofertaComoUnaLista.add(horaFin);
		return ofertaComoUnaLista;
	}

	public ArrayList<String> devolverTodosLosDniDeLosClientes(){
		ArrayList<String> listaDeDni = new ArrayList<String>();
		for (Entry<Integer, Oferta> ofertaCliente : mapDeOfertas.entrySet()) {
			String dniCliente = String.valueOf(ofertaCliente.getKey());
			listaDeDni.add(dniCliente);
		}
		return listaDeDni;
	}

	public void borrarListaDeOfertas() {
		mapDeOfertas.clear();
	}

	public ArrayList<Integer> devolverDNISComoListaDeIntegers() {
		ArrayList<String> listaDeDniStrings = new ArrayList<String>();
		ArrayList<Integer> listaDeDniNumericos = new ArrayList<Integer>();

		listaDeDniStrings = devolverTodosLosDniDeLosClientes();
		for (String dniString : listaDeDniStrings) {
			int dniCliente = Integer.parseInt(dniString);
			listaDeDniNumericos.add(dniCliente);
		}
		return listaDeDniNumericos;
	}

	public void actualizarFechaActual(LocalDate nuevaFechaActual) {
		String año = String.valueOf(nuevaFechaActual.getYear());
		String mes=String.valueOf(nuevaFechaActual.getMonthValue());
		String dia=String.valueOf(nuevaFechaActual.getDayOfMonth());
		ArrayList<String> _nuevaFechaActual = new ArrayList<String>();
		_nuevaFechaActual.add(año);
		_nuevaFechaActual.add(mes);
		_nuevaFechaActual.add(dia);
		archivoJSON.setFecha(_nuevaFechaActual);
		archivoJSON.generarJSON("fechaActual");  
	}
	public ArrayList<String> devolverFechaComoLista(LocalDate nuevaFechaActual) {
		String año = String.valueOf(nuevaFechaActual.getYear());
		String mes=String.valueOf(nuevaFechaActual.getMonthValue());
		String dia=String.valueOf(nuevaFechaActual.getDayOfMonth());
		ArrayList<String> _nuevaFechaActual = new ArrayList<String>();
		_nuevaFechaActual.add(año);
		_nuevaFechaActual.add(mes);
		_nuevaFechaActual.add(dia);
//		archivoJSON.setFecha(_nuevaFechaActual);
//		archivoJSON.generarJSON("fechaActual"); 
		return _nuevaFechaActual;
		 
	}
	public LocalDate devolverFechaActual() {
	    archivoJSON = archivoJSON.leerJSON("fechaActual");
	    try {
	    	ArrayList<String> nuevaFecha = new ArrayList<String>();
	        nuevaFecha = archivoJSON.getFecha();
	        LocalDate fechaActual = LocalDate.of(Integer.parseInt(nuevaFecha.get(0)), Integer.parseInt(nuevaFecha.get(1)), Integer.parseInt(nuevaFecha.get(2)));
	        return fechaActual;
	    } catch (Exception e) {
	        throw new IllegalArgumentException("ERROR: No se pudo leer la fecha actual");
	    }
	}

	public boolean puedeGuardarOferta(String nombreDeArchivo) {
		if (nombreDeArchivo.isEmpty()||nombreDeArchivo==null) {
			return false;
		}
		return true;
	}

}