package fiuba.algo3.algocity.tests;
import static org.junit.Assert.*;
import org.junit.Test;
import fiuba.algo3.algocity.excepciones.RegionAConstruirSeEncuentraOcupada;
import fiuba.algo3.algocity.modelo.CentralElectrica;
import fiuba.algo3.algocity.modelo.CentralEolica;
import fiuba.algo3.algocity.modelo.Conector;
import fiuba.algo3.algocity.modelo.Dimension;
import fiuba.algo3.algocity.modelo.Mapa;
import fiuba.algo3.algocity.modelo.Posicion;
import fiuba.algo3.algocity.modelo.PozoDeAgua;
import fiuba.algo3.algocity.modelo.Red;
import fiuba.algo3.algocity.modelo.RedDeRecursos;
import fiuba.algo3.algocity.modelo.RegionAcuatica;
import fiuba.algo3.algocity.modelo.RegionResidencialBaja;
import fiuba.algo3.algocity.modelo.TendidoElectrico;
import fiuba.algo3.algocity.modelo.Tuberia;


public class CentralElectricaTests {
	@Test
	public void conectoUnaCentralElectricaConUnaRegionSinConexionDeAguaALaRedElectrica() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		Posicion posCentralEolica = new Posicion(1,1);
		CentralElectrica centralEolica = new CentralEolica(posCentralEolica, mapa);
		RegionResidencialBaja unaRegion = new RegionResidencialBaja(new Posicion(3,1), new Dimension(2,2), mapa);
		
		Red redElectrica = new RedDeRecursos(mapa);

		Conector poste1 = new TendidoElectrico(new Posicion(1,2), mapa);
		Conector poste2 = new TendidoElectrico(new Posicion(2,2), mapa);
		
		redElectrica.conectar(poste1);
		redElectrica.conectar(poste2);
		redElectrica.conectar(unaRegion);
		
		assertEquals(centralEolica.getRecursosDisponibles(), 0);
		assertEquals(unaRegion.getElectricidadDisponible(), 0);
	}
	
	@Test
	public void conectoUnaCentralElectricaAUnPozoDeAguaConUnaRedDeTuberiasValida() throws Exception {
		//Creo un nuevo mapa con una region acuatica
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		try {
			mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(2,2), mapa));
		} catch (RegionAConstruirSeEncuentraOcupada e) {
			
		}
		
		//Agrego el pozo de agua al mapa
		PozoDeAgua pozoDeAgua = new PozoDeAgua(new Posicion(1,1), mapa);
		mapa.construir(pozoDeAgua);
		
		assertTrue(pozoDeAgua.getRecursosDisponibles() == 250);
		
		Red redDeTuberias = new RedDeRecursos(mapa);
		redDeTuberias.setFuente(pozoDeAgua);
		//Agrego los tuberias a la red y conecto la red de tuberias con la central electrica
		Conector tuberia1 = new Tuberia(new Posicion(2,1), mapa);
		Conector tuberia2 = new Tuberia(new Posicion(3,1), mapa);
		
		redDeTuberias.conectar(tuberia1);
		redDeTuberias.conectar(tuberia2);
		
		CentralElectrica centralEolica = new CentralEolica(new Posicion(4,1), mapa);
		
		redDeTuberias.conectar(centralEolica);
		
		assertEquals(centralEolica.getRecursosDisponibles(), 100);
	}
	
}
