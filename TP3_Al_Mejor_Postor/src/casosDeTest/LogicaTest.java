package casosDeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
	@Test (expected=IllegalArgumentException.class)
	public void cargarOfertasConNombreDeArchivoVacioTest() {
		LogicaOfertas logica = inicializar();
		logica.puedeCargarOfertasDeArchivo("");
	}
	@Test (expected=IllegalArgumentException.class)
	public void cargarOfertasConNombreDeArchivoNullTest() {
		LogicaOfertas logica = inicializar();
		logica.puedeCargarOfertasDeArchivo(null);
	}
	@Test
	public void ofertaExistenteConDNITest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44, 5000, 2, 8);
		Oferta ofertaRecibida = new Oferta("leandro", 44, 5000, 2, 8);
		assertEquals(ofertaRecibida,logica.obtenerOfertaAsociadaConDNI(44));
		
	}
	@Test
	public void guardarOfertaTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("Prueba", 233342, 12, 1, 3);

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
		logica.cargarOfertasDeArchivo("test");
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
	public void OfertasQueSeSolapanTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44610, 6000, 4, 8);
		logica.agregarOferta("matias", 44620, 5000, 4, 8);
		ArrayList<Oferta> recibido = logica.devolverListaDeOfertasOrdenadaPorGananciasPorHora();
		ArrayList<Oferta> esperado = new ArrayList<Oferta>();
		Oferta ofertaEsperada = new Oferta("leandro", 44610, 6000, 4, 8);
		esperado.add(ofertaEsperada);
		assertEquals(esperado.get(0),logica.devolverOfertasQueNoSeSolapan(recibido).get(0));
	}
	
	@Test
	public void OfertasQueNoSeSolapanTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44610, 6000, 4, 8);
		logica.agregarOferta("matias", 44620, 5000, 9, 12);
		ArrayList<Oferta> recibido = logica.devolverListaDeOfertasOrdenadaPorGananciasPorHora();
		ArrayList<Oferta> esperado = new ArrayList<Oferta>();
		Oferta ofertaEsperada1 = new Oferta("leandro", 44610, 6000, 4, 8);
		Oferta ofertaEsperada2 = new Oferta("matias", 44620, 5000, 9, 12);
		esperado.add(ofertaEsperada2);
		esperado.add(ofertaEsperada1);
		assertEquals(esperado,logica.devolverOfertasQueNoSeSolapan(recibido));
	}
	@Test
	public void puedeAgregarOfertaTest() {
		LogicaOfertas logica= new LogicaOfertas();
		assertTrue(logica.puedeAgregarOferta("facha", 43, 46423.0, 4, 8));
	}
	
	@Test
	public void devolverOfertaComoUnaListaTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44610, 6000, 4, 8);
		ArrayList<String> esperado = new ArrayList<>(List.of("leandro","44610","6000.0","4","8"));
		assertEquals(esperado, logica.devolverOfertaComoUnaLista("44610"));
		
	}
	@Test
	public void devolverOfertaComoUnaListaDiferenteTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44610, 6000, 4, 8);
		ArrayList<String> esperado = new ArrayList<>(List.of("leandro","44610","7000.0","4","8"));
		assertNotEquals(esperado, logica.devolverOfertaComoUnaLista("44610"));
		
	}
	
	@Test
	public void devolverTodosLosDniDeLosClientesComoStringTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44610, 6000, 4, 8);
		assertEquals("44610", logica.devolverTodosLosDniDeLosClientesComoString().get(0));
		
	}
	
	@Test
	public void devolverDNISComoListaDeIntegersTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("leandro", 44610, 6000, 4, 8);
		Integer numero = 44610;
		assertEquals(numero, logica.devolverDNISComoListaDeIntegers().get(0));
	}
	@Test
	public void puedeguardarOfertaTest() {
		LogicaOfertas logica = inicializar();
		assertTrue(logica.puedeGuardarOferta("test"));
	}
	@Test
	public void guardarOfertaConNombreDeArchivoVacioTest() {
		LogicaOfertas logica = inicializar();
		assertFalse(logica.puedeGuardarOferta(""));
	}
	@Test
	public void guardarOfertaConNombreNullTest() {
		LogicaOfertas logica = inicializar();
		assertFalse(logica.puedeGuardarOferta(null));
	}
	@Test
	public void fechaComoListaTest() {
		LogicaOfertas logica = inicializar();
		LocalDate fecha = logica.devolverFechaActual();	
		ArrayList<String> recibido=logica.devolverFechaComoLista(fecha);		
		ArrayList<String> esperado= new ArrayList<String>();
		
		esperado.add(recibido.get(0));
		esperado.add(recibido.get(1));
		esperado.add(recibido.get(2));
		assertEquals(esperado,recibido);
	}
	@Test
	public void eliminarOfertasIngresadasTest() {
		LogicaOfertas logica = inicializar();
		logica.agregarOferta("Bastian", 4532176, 170000, 1, 4);
		logica.agregarOferta("Bastian", 4532146, 170000, 1, 4);
		logica.borrarListaDeOfertas();
		ArrayList<String> esperado=new ArrayList<String>();
		assertEquals(esperado,logica.devolverTodosLosDniDeLosClientesComoString());		
	}
	@Test 
	public void cargarOfertasDeArchivoTest() {
		LogicaOfertas logica = inicializar();
		logica.puedeCargarOfertasDeArchivo("Prueba");
	}
	public LogicaOfertas inicializar() {
		LogicaOfertas logica= new LogicaOfertas();

		return logica;
	}
}
