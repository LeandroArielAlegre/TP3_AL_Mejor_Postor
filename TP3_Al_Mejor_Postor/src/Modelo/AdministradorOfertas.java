package Modelo;
import java.util.HashMap;
public class AdministradorOfertas {
	//EL CONJUNTO DE OFERTAS
	
	private HashMap<Integer,Oferta> listaDeOfertas = new HashMap<Integer,Oferta>();
	

//	private HashMap<Integer,Oferta> listaDeMejoresOfertas = new HashMap<Integer,Oferta>();
//	private double gananciaTotal;
	
	public AdministradorOfertas() {
		
	}
	
	public void agregarOferta(String nombre,int dni, double precio, int horaDeInicio , int horaDeFinalizacion) {
		//Ojo... en el caso que vaya a volver a ofertar la misma persona habria que preguntar si aumento
		//el precio de la oferta, entonces si es el caso cambiamos el valor del precio
		if(listaDeOfertas.containsKey(dni)) {
			if (listaDeOfertas.get(dni).getPrecio()>precio) {
				throw new IllegalArgumentException("Tu oferta no es mayor que tu anterior registrada");
			}else {
			listaDeOfertas.put(dni,new Oferta(nombre,dni,precio,horaDeInicio,horaDeFinalizacion));
			}	
		}else {
			listaDeOfertas.put(dni,new Oferta(nombre,dni,precio,horaDeInicio,horaDeFinalizacion));
		}
	}
	
	public void eliminarOferta(int dni) {
		 if(listaDeOfertas.containsKey(dni)) {
			 listaDeOfertas.remove(dni);
		 }else {
			 throw new IllegalArgumentException("La oferta a eliminar no existe");
		 }
	}
	public HashMap<Integer, Oferta> getListaDeOfertas() {
		return listaDeOfertas;
	}
	//CRITERIO DE PREFERENCIA 
//	public ArrayList<Oferta> ofertasOrdenadasPorPrecio(HashMap<Integer,Oferta> listaDeOfertas){
//		ArrayList<Oferta> ret= new ArrayList();
//		System.out.println(ordenar(ret,0,ret.size()-1).toString());
//		return ordenar(ret,0,ret.size()-1);
//	}
//
//
//	private ArrayList<Oferta> ordenar(ArrayList<Oferta> lista, int inicio, int fin) {//quicksort sobre los pesos ¿estoy reemplazando los indices?si
//        if (inicio < fin) {
//            // Encuentra el punto de partición
//            int indiceParticion = particion(lista, inicio, fin);
//
//            // Ordenar recursivamente 
//            ordenar(lista, inicio, indiceParticion - 1);
//            ordenar(lista, indiceParticion + 1, fin);
//        }
//        return lista;
//    }
//
//    private int particion(ArrayList<Oferta> lista, int inicio, int fin) {
//        double pivote = lista.get(fin).getPrecio();
//        int i = (inicio - 1);
//
//        for (int j = inicio; j < fin; j++) {
//            if (lista.get(j).getPrecio() <= pivote) {
//                i++;
//                // Intercambiar elementos
//                Oferta temp = lista.get(i);
//                lista.set(i, lista.get(j));
//                lista.set(j, temp);
//            }
//        }
//
//        // Colocar el pivote en su posición correcta
//        Oferta temp = lista.get(i + 1);
//        lista.set(i + 1, lista.get(fin));
//        lista.set(fin, temp);
//
//        return i + 1;
//    }

	
}
