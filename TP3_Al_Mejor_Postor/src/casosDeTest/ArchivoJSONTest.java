package casosDeTest;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import modelo.ArchivoJSON;
import modelo.Oferta;
public class ArchivoJSONTest {
	ArchivoJSON archivo;
	Oferta oferta;
	HashMap<Integer, Oferta> listaDeOfertas;
	
	@Before
	public void archivoPrueba() {
		listaDeOfertas=new HashMap<Integer, Oferta>();
		archivo = new ArchivoJSON();
		oferta = unaOfertaPrecargada();	
		listaDeOfertas.put(oferta.getDni(), oferta);
		archivo.setListaDeOfertas(listaDeOfertas);
		
	}
	
	@Test (expected= IllegalArgumentException.class)
	public void leerRutaInexistenteTest() {
		archivo.generarJSON("archivoPrueba");
        archivo.leerJSON("noExiste.json");
    }
	@Test
	public void leerRutaExistenteTest() {
		archivo.generarJSON("archivoPrueba");
        assertNotNull(archivo.leerJSON("archivoPrueba"));       
    }
	
	private Oferta unaOfertaPrecargada() {
		Oferta oferta = new Oferta("Pedro", 25543423, 12000, 12, 15);
		
		return oferta;
	}
}