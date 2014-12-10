package fiuba.algo3.algocity.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import fiuba.algo3.algocity.modelo.Catastrofe;
import fiuba.algo3.algocity.modelo.CentralElectrica;
import fiuba.algo3.algocity.modelo.CentralEolica;
import fiuba.algo3.algocity.modelo.CentralNuclear;
import fiuba.algo3.algocity.modelo.Dimension;
import fiuba.algo3.algocity.modelo.EstacionBomberos;
import fiuba.algo3.algocity.modelo.Godzilla;
import fiuba.algo3.algocity.modelo.Mapa;
import fiuba.algo3.algocity.modelo.Posicion;
import fiuba.algo3.algocity.modelo.PozoDeAgua;
import fiuba.algo3.algocity.modelo.RegionAcuatica;
import fiuba.algo3.algocity.modelo.Terremoto;
import fiuba.algo3.algocity.modelo.DespertarDerechoHorizontal;
import fiuba.algo3.algocity.modelo.DespertarDerechoVertical;

public class CatastrofesTests {
	
	@Test
	public void creoUnTerremotoEnUnPuntoDelMapaDestruyeEdificio() throws Exception {		
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica centralEolica = new CentralEolica(new Posicion(5,5), mapa);
		mapa.construir(centralEolica);
		
		Catastrofe terremoto = new Terremoto(mapa, new Posicion(5,5));
		terremoto.originar();
		
		assertEquals(mapa.getConstruccion(new Posicion(5,5)).getSalud(), 0);
	}
	
	@Test
	public void despiertaGodzillaCaminandoEnFormaRectaHorizontalDaniaCentrales() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica central1 = new CentralEolica(new Posicion(5,0), mapa);
		CentralElectrica central2 = new CentralNuclear(new Posicion(1,0), mapa);
		CentralElectrica central3 = new CentralNuclear(new Posicion(9,0), mapa);
		
		mapa.construir(central1);
		mapa.construir(central2);
		mapa.construir(central3);
		
		assertEquals(mapa.getConstruccion(new Posicion(5,0)).getSalud(), 100);
		assertEquals(mapa.getConstruccion(new Posicion(1,0)).getSalud(), 100);
		assertEquals(mapa.getConstruccion(new Posicion(9,0)).getSalud(), 100);
		
		Catastrofe godzilla = new Godzilla(mapa, new Posicion(0,0), new DespertarDerechoHorizontal());
		
		godzilla.originar();
		
		/* Las centrales son daniadas un 35% */
		assertEquals(mapa.getConstruccion(new Posicion(5,0)).getSalud(), 65);
		assertEquals(mapa.getConstruccion(new Posicion(1,0)).getSalud(), 65);
		assertEquals(mapa.getConstruccion(new Posicion(9,0)).getSalud(), 65);
	}
	
	@Test
	public void despiertaGodzillaCaminandoEnFormaRectaHorizontalDesdeOtroExtremoDelMapaDaniaCentral() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica central1 = new CentralEolica(new Posicion(5,0), mapa);
		
		mapa.construir(central1);
		
		assertEquals(mapa.getConstruccion(new Posicion(5,0)).getSalud(), 100);
		
		Catastrofe godzilla = new Godzilla(mapa, new Posicion(10,0), new DespertarDerechoHorizontal());
		
		godzilla.originar();
		
		/* Las centrales son daniadas un 35% */
		assertEquals(mapa.getConstruccion(new Posicion(5,0)).getSalud(), 65);
	}
	
	@Test
	public void despiertaGodzillaCaminandoEnFormaRectaVerticalDaniaCentral() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica central1 = new CentralEolica(new Posicion(5,5), mapa);
		
		mapa.construir(central1);
		
		assertEquals(mapa.getConstruccion(new Posicion(5,5)).getSalud(), 100);
		
		Catastrofe godzilla = new Godzilla(mapa, new Posicion(5,0), new DespertarDerechoVertical());
		
		godzilla.originar();
		
		/* Las centrales son daniadas un 35% */
		assertEquals(mapa.getConstruccion(new Posicion(5,5)).getSalud(), 65);
	}
	
	@Test
	public void despiertaGodzillaCaminandoEnFormaRectaVerticalDesdeOtroExtremoDelMapaDaniaCentral() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica central1 = new CentralEolica(new Posicion(5,5), mapa);
		
		mapa.construir(central1);
		
		assertEquals(mapa.getConstruccion(new Posicion(5,5)).getSalud(), 100);
		
		Catastrofe godzilla = new Godzilla(mapa, new Posicion(5,10), new DespertarDerechoVertical());
		
		godzilla.originar();
		
		/* Las centrales son daniadas un 35% */
		assertEquals(mapa.getConstruccion(new Posicion(5,5)).getSalud(), 65);
	}
	
	@Test
	public void godzillaDaniaPozoDeAgua() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		mapa.construir(new RegionAcuatica(new Posicion(3,0), new Dimension(2,2), mapa));
		PozoDeAgua pozoDeAgua = new PozoDeAgua(new Posicion(3,0), mapa);
		
		mapa.construir(pozoDeAgua);
		
		assertEquals(mapa.getConstruccion(new Posicion(3,0)).getSalud(), 100);
		
		Catastrofe godzilla = new Godzilla(mapa, new Posicion(0,0), new DespertarDerechoHorizontal());
		
		godzilla.originar();
		
		assertEquals(mapa.getConstruccion(new Posicion(3,0)).getSalud(), 65);
	}
	
	@Test
	public void godzillaDaniaEstacionDeBomberos() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		mapa.construir(new EstacionBomberos(new Posicion(3,0), mapa));
				
		assertEquals(mapa.getConstruccion(new Posicion(3,0)).getSalud(), 100);
		
		Catastrofe godzilla = new Godzilla(mapa, new Posicion(0,0), new DespertarDerechoHorizontal());
		
		godzilla.originar();
		
		assertEquals(mapa.getConstruccion(new Posicion(3,0)).getSalud(), 50);
	}
	
	@Test
	public void accederAPosicionesValidasEInvalidasDelMapa() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		Posicion posicion1 = new Posicion(0,0);
		assertEquals(mapa.parcelaValida(posicion1), true);
		
		Posicion posicion2 = new Posicion(5,5);
		assertEquals(mapa.parcelaValida(posicion2), true);
		
		Posicion posicion3 = new Posicion(10,10);
		assertEquals(mapa.parcelaValida(posicion3), true);
		
		Posicion posicion4 = new Posicion(-1,0);
		assertEquals(mapa.parcelaValida(posicion4), false);
		
		Posicion posicion5 = new Posicion(13,11);
		assertEquals(mapa.parcelaValida(posicion5), false);
		
		Posicion posicion6 = new Posicion(-1,20);
		assertEquals(mapa.parcelaValida(posicion6), false);
	}
	
	@Test
	public void originarTerremotoEnCentroDelMapaDaniaConstruccionesASuAlrededor() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		CentralElectrica central1 = new CentralEolica(new Posicion(5,5), mapa);
		CentralElectrica central2 = new CentralEolica(new Posicion(5,4), mapa);
		CentralElectrica central3 = new CentralEolica(new Posicion(4,3), mapa);
		CentralElectrica central4 = new CentralEolica(new Posicion(3,1), mapa);
		CentralElectrica central5 = new CentralEolica(new Posicion(0,0), mapa);
		
		mapa.construir(central1);
		mapa.construir(central2);
		mapa.construir(central3);
		mapa.construir(central4);
		mapa.construir(central5);
		
		Catastrofe terremoto = new Terremoto(mapa, new Posicion(5,5));
		terremoto.originar();
		
		assertEquals(mapa.getConstruccion(new Posicion(5,5)).getSalud(), 0);
		assertEquals(mapa.getConstruccion(new Posicion(5,4)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(4,3)).getSalud(), 70);
		assertEquals(mapa.getConstruccion(new Posicion(3,1)).getSalud(), 100);
		assertEquals(mapa.getConstruccion(new Posicion(0,0)).getSalud(), 100);
	}
	
	@Test
	public void danioAlRededorDelTerremoto() throws Exception {
Mapa mapa = new Mapa(new Dimension(10,10));
		
		CentralElectrica central1 = new CentralEolica(new Posicion(4,4), mapa);
		CentralElectrica central2 = new CentralEolica(new Posicion(4,5), mapa);
		CentralElectrica central3 = new CentralEolica(new Posicion(4,6), mapa);
		CentralElectrica central4 = new CentralEolica(new Posicion(5,4), mapa);
		CentralElectrica central5 = new CentralEolica(new Posicion(5,6), mapa);
		CentralElectrica central6 = new CentralEolica(new Posicion(6,4), mapa);
		CentralElectrica central7 = new CentralEolica(new Posicion(6,5), mapa);
		CentralElectrica central8 = new CentralEolica(new Posicion(6,6), mapa);
		
		mapa.construir(central1);
		mapa.construir(central2);
		mapa.construir(central3);
		mapa.construir(central4);
		mapa.construir(central5);
		mapa.construir(central6);
		mapa.construir(central7);
		mapa.construir(central8);
		
		Catastrofe terremoto = new Terremoto(mapa, new Posicion(5,5));
		terremoto.originar();
		
		assertEquals(mapa.getConstruccion(new Posicion(4,4)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(4,5)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(4,6)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(5,4)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(5,6)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(6,4)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(6,5)).getSalud(), 35);
		assertEquals(mapa.getConstruccion(new Posicion(6,6)).getSalud(), 35);
	}
}
