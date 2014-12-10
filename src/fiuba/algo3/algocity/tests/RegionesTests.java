package fiuba.algo3.algocity.tests;
import static org.junit.Assert.*;
import org.junit.Test;
import fiuba.algo3.algocity.excepciones.NoSePudoEstablecerUnionEnLaRed;
import fiuba.algo3.algocity.modelo.CentralElectrica;
import fiuba.algo3.algocity.modelo.CentralNuclear;
import fiuba.algo3.algocity.modelo.Conector;
import fiuba.algo3.algocity.modelo.Dimension;
import fiuba.algo3.algocity.modelo.EstacionBomberos;
import fiuba.algo3.algocity.modelo.Mapa;
import fiuba.algo3.algocity.modelo.Posicion;
import fiuba.algo3.algocity.modelo.PozoDeAgua;
import fiuba.algo3.algocity.modelo.Red;
import fiuba.algo3.algocity.modelo.RedDeRutas;
import fiuba.algo3.algocity.modelo.RedDeRecursos;
import fiuba.algo3.algocity.modelo.Region;
import fiuba.algo3.algocity.modelo.RegionAcuatica;
import fiuba.algo3.algocity.modelo.RegionIndustrialBaja;
import fiuba.algo3.algocity.modelo.RegionResidencialBaja;
import fiuba.algo3.algocity.modelo.Ruta;
import fiuba.algo3.algocity.modelo.TendidoElectrico;
import fiuba.algo3.algocity.modelo.Tuberia;

public class RegionesTests {

	@Test
	public void construyoUnaRegionIndustrial() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		assertTrue(mapa.getRegionesIndustriales().size() == 0);
		mapa.construir(new RegionIndustrialBaja(new Posicion(1,1), new Dimension(2,2), mapa));
		assertTrue(mapa.getRegionesIndustriales().size() == 1);
	}
	
	
	@Test(expected = Exception.class)
	public void construyoRegionIndustrialSobreRegionOcupadaTiraExcepcionRegionAConstruirSeEncuentraOcupada() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		mapa.construir(new RegionIndustrialBaja(new Posicion(1,1), new Dimension(5,5), mapa));
		mapa.construir(new RegionIndustrialBaja(new Posicion(1,1), new Dimension(2,2), mapa));
	}
	
	@Test(expected = Exception.class)
	public void noSePuedeAgregarRegionSobreAguaTiraExcepcionRegionAConstruirSeEncuentraOcupada() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(2,2), mapa));
		mapa.construir(new RegionIndustrialBaja(new Posicion(1,1), new Dimension(5,5), mapa));
	}

	@Test
	public void agregarPozoDeAguaSobreAgua() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(2,2), mapa));
		
		PozoDeAgua pozo = new PozoDeAgua(new Posicion(0,0), mapa);
		
		mapa.construir(pozo);
		
		assertTrue(pozo.getRecursosDisponibles() == 250);
	}


	@Test(expected = Exception.class)
	public void agregarPozoDeAguaSobreTierraArrojaSuperficieInvalida() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));		
		PozoDeAgua pozo = new PozoDeAgua(new Posicion(0,0), mapa);		
		mapa.construir(pozo);
	}

	@Test(expected = Exception.class)
	public void agregarPozoDeAguaSobreRegionResidencialArrojaSuperficieInvalida() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		mapa.construir(new RegionResidencialBaja(new Posicion(0,0), new Dimension(5,5), mapa));
		
		mapa.construir(new PozoDeAgua(new Posicion(1,1), mapa));
		
	}
	
	@Test
	public void agregarEstacionDeBomberosEnTierra() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
	
		mapa.construir(new EstacionBomberos(new Posicion(1,1), mapa));
		assertTrue(mapa.getListaConstrucciones().size() == 1);
	}
	
	@Test(expected = Exception.class)
	public void agregarEstacionDeBomberosEnAguaArrojaExcepcionSuperficieInvalida() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
	
		mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(2,2), mapa));
		
		mapa.construir(new EstacionBomberos(new Posicion(1,1), mapa));
	}


	@Test
	public void PozoDeAguaAlimentaUnaRegion() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(1,1), mapa));
		
		PozoDeAgua pozo = new PozoDeAgua(new Posicion(0,0), mapa);
		RegionResidencialBaja region = new RegionResidencialBaja(new Posicion(0,3), new Dimension(5,5), mapa);
		
		mapa.construir(pozo);
		
		Red redDeTubos = new RedDeRecursos(mapa);
		
		redDeTubos.setFuente(pozo);
		
		Conector tuberia1 = new Tuberia(new Posicion(0,1), mapa);
		Conector tuberia2 = new Tuberia(new Posicion(0,2), mapa);
		redDeTubos.conectar(tuberia1);
		redDeTubos.conectar(tuberia2);
		
		redDeTubos.conectar(region);
				
		assertEquals(region.getAguaDisponible(), 250);	
	}
	
	@Test
	public void CentralElectricaAlimentaUnaRegion() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		CentralNuclear central = new CentralNuclear(new Posicion(3,0), mapa);
		mapa.construir(central);
		
		// Conecto la central a un pozo de agua para que pueda funcionar
		
		Region regionAcuatica = new RegionAcuatica(new Posicion(0,0), new Dimension(1,1), mapa);
		mapa.construir(regionAcuatica);
		
		PozoDeAgua pozo = new PozoDeAgua(new Posicion(0,0), mapa);
		mapa.construir(pozo);
		
		Red redDeTubos = new RedDeRecursos(mapa);
		redDeTubos.setFuente(pozo);
		
		Conector tuberia1 = new Tuberia(new Posicion(1,0), mapa);
		Conector tuberia2 = new Tuberia(new Posicion(2,0), mapa);
		redDeTubos.conectar(tuberia1);
		redDeTubos.conectar(tuberia2);
		
		redDeTubos.conectar(central);
		
		// Extiendo un cableado desde la central a la region
		
		Red redElectrica = new RedDeRecursos(mapa);
		redElectrica.setFuente(central);
		
		Conector poste1 = new TendidoElectrico(new Posicion(4,1), mapa);
		Conector poste2 = new TendidoElectrico(new Posicion(4,2), mapa);
		
		RegionResidencialBaja region = new RegionResidencialBaja(new Posicion(4,3), new Dimension(5,5), mapa);
		
		redElectrica.conectar(poste1);
		redElectrica.conectar(poste2);
		
		redElectrica.conectar(region);
		
		assertEquals(region.getElectricidadDisponible(), 1000);		
	}
	
	@Test
	public void creoMapaConPozoCentralYAlimentoRegionDelTodo() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(1,1), mapa));

		PozoDeAgua pozo = new PozoDeAgua(new Posicion(0,0), mapa);
		mapa.construir(pozo);

		assertEquals(pozo.getRecursosDisponibles(), 250);
		
		RegionResidencialBaja region = new RegionResidencialBaja(new Posicion(3,0), new Dimension(5,5), mapa);
				
		Red redDeTubos = new RedDeRecursos(mapa);
		redDeTubos.setFuente(pozo);
		
		Conector tubo1 = new Tuberia(new Posicion(1,0), mapa);
		Conector tubo2 = new Tuberia(new Posicion(2,0), mapa);

		redDeTubos.conectar(tubo1);
		redDeTubos.conectar(tubo2);	
		
		redDeTubos.conectar(region);

		Conector tubo3 = new Tuberia(new Posicion(0,1), mapa);
		Conector tubo4 = new Tuberia(new Posicion(0,2), mapa);
		
		CentralElectrica central = new CentralNuclear(new Posicion(0,3), mapa);
						
		Red otraRedDeTubos = new RedDeRecursos(mapa);
		otraRedDeTubos.setFuente(pozo);
		
		otraRedDeTubos.conectar(tubo3);
		otraRedDeTubos.conectar(tubo4);	
		
		otraRedDeTubos.conectar(central);
		
		assertEquals(central.getRecursosDisponibles(), 1000);
		
		Red redElectrica = new RedDeRecursos(mapa);
		redElectrica.setFuente(central);
		
		Conector poste1 = new TendidoElectrico(new Posicion(1,3), mapa);
		Conector poste2 = new TendidoElectrico(new Posicion(2,3), mapa);
		
		try {
			redElectrica.conectar(poste1);
			redElectrica.conectar(poste2);
			redElectrica.conectar(region);
		} catch (NoSePudoEstablecerUnionEnLaRed e) {

		}
		
		assertTrue(region.getAguaDisponible() == 250);
		assertEquals(region.getElectricidadDisponible(), 1000);
	}
	
	@Test
	public void unirDosRegionesMedianteRutas() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		RegionResidencialBaja unaRegion = new RegionResidencialBaja(new Posicion(0,0), new Dimension(2,2), mapa);
		RegionResidencialBaja otraRegion = new RegionResidencialBaja(new Posicion(5,0), new Dimension(2,2), mapa);
		
		Red red = new RedDeRutas(mapa);
		red.setFuente(unaRegion);
		Conector asfalto1 = new Ruta(new Posicion(0,1), mapa);
		Conector asfalto2 = new Ruta(new Posicion(0,2), mapa);
		
		red.conectar(asfalto1);
		red.conectar(asfalto2);
		
		red.conectar(otraRegion);		
	}
	
}
