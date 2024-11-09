package casosDeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

import modelo.LogicaOfertas;
import modelo.Oferta;

public class LogicaTest {	

	@Test(expected=IllegalArgumentException.class)
	public void setearListaDeOfertasNulltest() {
		LogicaOfertas logica = inicializar();
		logica.setListaDeOfertas(null);
	}
	@Test(expected=IllegalArgumentException.class)
	public void ofertaInexistenteConDNITest() {
		LogicaOfertas logica = inicializar();
		logica.obtenerOfertaAsociadaConDNI(1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void nombreVacioTest() {
		LogicaOfertas logica = inicializar();
		logica.puedeAgregarOferta(null, 1, 2, 4, 5);

	}
	@Test(expected=IllegalArgumentException.class)
	public void dniNegativotest() {
		LogicaOfertas logica = inicializar();
		logica.puedeAgregarOferta("Rivas", -1, 2, 4, 5);

	}
	@Test(expected=IllegalArgumentException.class)
	public void precioNegativoTest() {
		LogicaOfertas logica = inicializar();
		logica.puedeAgregarOferta("Rivas", 1, -2, 4, 5);

	}
	@Test(expected=IllegalArgumentException.class)
	public void intervaloInvalidoTest() {
		LogicaOfertas logica = inicializar();
		logica.puedeAgregarOferta("Rivas", 1, 12, 4, 3);

	}
	@Test(expected=IllegalArgumentException.class)
	public void clienteSinOfertaTest() {
		LogicaOfertas logica = inicializar();
		HashMap<Integer, Oferta> ofertasCargadas=new HashMap<Integer, Oferta>();

		ofertasCargadas.put(234544, null);
		logica.setListaDeOfertas(ofertasCargadas);

	}

	@Test(expected=IllegalArgumentException.class)
	public void dosClientesIgualesTest() {
		LogicaOfertas logica = inicializar();
		HashMap<Integer, Oferta> ofertasCargadas=new HashMap<Integer, Oferta>();

		ofertasCargadas.put(234544, null);
		logica.setListaDeOfertas(ofertasCargadas);

	}
	@Test(expected=IllegalArgumentException.class)
	public void precioIdenticoTest() {
		LogicaOfertas logica = inicializar();
		Oferta oferta=new Oferta("Prueba", 233342, 12, 1, 3);

		logica.agregarOferta(oferta.getNombre(), oferta.getDni(), oferta.getPrecio(), oferta.getHoraDeInicio(), oferta.getHoraDeFinalizacion());		
		logica.agregarOferta(oferta.getNombre(), oferta.getDni(), oferta.getPrecio(), oferta.getHoraDeInicio(), oferta.getHoraDeFinalizacion());		

	}
	@Test(expected=IllegalArgumentException.class)
	public void precioMenorTest() {
		LogicaOfertas logica = inicializar();
		Oferta oferta=new Oferta("Prueba", 233342, 12, 1, 3);

		logica.agregarOferta(oferta.getNombre(), oferta.getDni(), oferta.getPrecio(), oferta.getHoraDeInicio(), oferta.getHoraDeFinalizacion());		
		logica.agregarOferta(oferta.getNombre(), oferta.getDni(), oferta.getPrecio()-1, oferta.getHoraDeInicio(), oferta.getHoraDeFinalizacion());		

	}
	@Test(expected=IllegalArgumentException.class)
	public void ofertaIdenticaTest() {
		LogicaOfertas logica = inicializar();
		Oferta oferta=new Oferta("Prueba", 233342, 12, 1, 3);

		logica.agregarOferta(oferta.getNombre(), oferta.getDni(), oferta.getPrecio(), oferta.getHoraDeInicio(), oferta.getHoraDeFinalizacion());		
		logica.agregarOferta(oferta.getNombre(), oferta.getDni(), oferta.getPrecio(), oferta.getHoraDeInicio(), oferta.getHoraDeFinalizacion());		

	}
	@Test(expected=IllegalArgumentException.class)
	public void eliminarOfertaInexixtenteTest() {
		LogicaOfertas logica = inicializar();
		logica.eliminarOferta(545437);
	}
	@Test
	public void guardarOfertaTest() {
		LogicaOfertas logica = inicializar();
		logica.guardarOferta("test");
	}
	@Test
	public void agregarOfertaTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("Prueba", 233342, 12, 1, 3);
	}
	@Test
	public void eliminarOfertaTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("Prueba", 233342, 12, 1, 3);
		logica.eliminarOferta(233342);
	}
	@Test
	public void devolverListaDeOfertasOrdenadaPorGananciasPorHoraTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("Prueba", 233342, 12, 1, 3);
		logica.agregarOferta("Prueba2", 254342, 12386, 4, 7);
		logica.agregarOferta("Prueba3", 255342, 15386, 11, 14);
		logica.agregarOferta("Prueba4", 256342, 127386, 16, 17);
		ArrayList<Oferta> ofertasPorBeneficio= new ArrayList<>();
		ofertasPorBeneficio.add(new Oferta("Prueba4", 256342, 127386, 16, 17));
		ofertasPorBeneficio.add(new Oferta("Prueba3", 255342, 15386, 11, 14));
		ofertasPorBeneficio.add(new Oferta("Prueba2", 254342, 12386, 4, 7));
		ofertasPorBeneficio.add(new Oferta("Prueba", 233342, 12, 1, 3));
		int i =0;
		ArrayList<Oferta> ofertasPorBeneficioLogica = new ArrayList<>();
		ofertasPorBeneficioLogica= logica.devolverListaDeOfertasOrdenadaPorGananciasPorHora();
		for (Oferta oferta: ofertasPorBeneficio) {
			assertEquals(oferta.toString(),ofertasPorBeneficioLogica.get(i).toString());
			i++;
		}
	}
	@Test
	public void cargarArchivoTest() {
		LogicaOfertas logica = inicializar();
		logica.cargarOfertasDeArchivo("dia1");
	}
	@Test
	public void devolverOfertasDeArchivoTest() {
		LogicaOfertas logica = inicializar();
		logica.cargarOfertasDeArchivo("dia1");
		HashMap<Integer, Oferta> recibido = logica.devolverOfertasArchivo();
		HashMap<Integer, Oferta> esperado=new HashMap<Integer, Oferta>();
		esperado.put(43, new Oferta("facha", 43, 46423.0, 4, 8));
		esperado.put(444, new Oferta("leandro", 444, 235235, 2, 4));
		Iterator<Oferta> iteradorRecibido = recibido.values().iterator();
		Iterator<Oferta> iteradorEsperado = esperado.values().iterator();
		while(iteradorRecibido.hasNext()&& iteradorEsperado.hasNext()) {
			Oferta esperada = iteradorEsperado.next();
			Oferta recibida = iteradorRecibido.next();
		assertEquals(esperada.toString(),recibida.toString());
		}
	}
	@Test
	public void puedeAgregarOfertaTest() {
		LogicaOfertas logica= new LogicaOfertas();
		assertTrue(logica.puedeAgregarOferta("facha", 43, 46423.0, 4, 8));
	}

	public LogicaOfertas inicializar() {
		LogicaOfertas logica= new LogicaOfertas();

		return logica;
	}
}
