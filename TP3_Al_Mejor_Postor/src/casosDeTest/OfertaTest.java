package casosDeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import modelo.Oferta;

public class OfertaTest {

	@Test(expected=IllegalArgumentException.class)
	public void horaInicioIgualQueHoraFinTest() {
		@SuppressWarnings("unused")
		Oferta oferta = new Oferta("o",1,1,1,1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void horaInicioNegativaTest() {
		@SuppressWarnings("unused")
		Oferta oferta = new Oferta("o",1,1,-1,1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void horaFinMayorA24HorasTest() {
		@SuppressWarnings("unused")
		Oferta oferta = new Oferta("o",1,1,1,25);
	}
	@Test(expected=IllegalArgumentException.class)
	public void horaFinNegativatest() {
		@SuppressWarnings("unused")
		Oferta oferta = new Oferta("o",1,1,1,-2);
	}
	@Test(expected=IllegalArgumentException.class)
	public void precioNegativoTest() {
		@SuppressWarnings("unused")
		Oferta oferta = new Oferta("o",1,-100,1,2);
	}
	@Test(expected=IllegalArgumentException.class)
	public void NombreNullTest() {
		@SuppressWarnings("unused")
		Oferta oferta = new Oferta(null,1,100,1,2);
	}
	@Test(expected=IllegalArgumentException.class)
	public void nuevoPrecioNegativoTest() {
		Oferta oferta = new Oferta(null,1,100,1,2);
		oferta.setPrecio((double) -1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void nuevoPrecioMenorQueActualTest() {
		Oferta oferta = new Oferta(null,1,100,1,2);
		oferta.setPrecio(56.0);
	}
	@Test
	public void ofertaIdenticaTest() {
		Oferta oferta = new Oferta("Pablo",1135463,100,1,2);
		Oferta ofertaOtra = new Oferta("Pablo",1135463,100,1,2);
		assertEquals(0,oferta.compareTo(ofertaOtra));
	}
	@Test
	public void ofertaSeSolapaConOtraTest() {
		Oferta oferta = new Oferta("Pablo",1135463,100,1,2);
		Oferta ofertaOtra = new Oferta("Pablo",1135463,100,1,2);
		assertTrue(oferta.seSolapaCon(ofertaOtra));
	}
	@Test
	public void ofertaNoSeSolapaConOtraTest() {
		Oferta oferta = new Oferta("Pablo",1135463,100,1,2);
		Oferta ofertaOtra = new Oferta("Pablo",1135463,100,2,3);
		assertFalse(oferta.seSolapaCon(ofertaOtra));
	}
	@Test
	public void ofertaGananciasTest() {
		Oferta oferta = new Oferta("Pablo",1135463,100,4,6);
		int ganancia= 100/(6-4);
		assertEquals(ganancia,oferta.calcularGananciasPorHora(),0);
	}
	@Test
	public void horaInicioMinimaTest() {
		Oferta oferta = new Oferta("Pablo",1135463,100,1,7);
		assertNotNull(oferta);
	}
	@Test
	public void horaFinMaximaTest() {
		Oferta oferta = new Oferta("Pablo",1135463,100,1,24);
		assertNotNull(oferta);
	}
}


