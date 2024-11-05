package casosDeTest;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;

import modelo.LogicaOfertas;

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
	
	public LogicaOfertas inicializar() {
		LogicaOfertas logica= new LogicaOfertas();
		return logica;
	}
}
