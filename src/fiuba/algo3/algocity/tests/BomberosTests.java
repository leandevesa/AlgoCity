package fiuba.algo3.algocity.tests;
import static org.junit.Assert.*;

import org.junit.Test;

import fiuba.algo3.algocity.modelo.CentralElectrica;
import fiuba.algo3.algocity.modelo.CentralEolica;
import fiuba.algo3.algocity.modelo.Dimension;
import fiuba.algo3.algocity.modelo.EstacionBomberos;
import fiuba.algo3.algocity.modelo.Mapa;
import fiuba.algo3.algocity.modelo.Posicion;

public class BomberosTests {
	@Test
	public void estacionBomberosCuraPosicionDaniada() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica centralEolica = new CentralEolica(new Posicion(2,2), mapa);
		
		mapa.construir(centralEolica);
		
		assertEquals(centralEolica.getSalud(), 100);
		centralEolica.daniar();
		assertEquals(centralEolica.getSalud(), 65);
		
		//Creo estacion de bomberos
		EstacionBomberos estacion = new EstacionBomberos(new Posicion(2,3), mapa);
		mapa.construir(estacion);
		
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 80);
	}
	
	@Test
	public void estacionBomberosNoCuraPosicionDaniadaPorEstarFueraDelRango() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica centralEolica = new CentralEolica(new Posicion(2,2), mapa);
		
		mapa.construir(centralEolica);
		
		assertEquals(centralEolica.getSalud(), 100);
		centralEolica.daniar();
		assertEquals(centralEolica.getSalud(), 65);
		
		//Creo estacion de bomberos
		EstacionBomberos estacion = new EstacionBomberos(new Posicion(2,7), mapa);
		mapa.construir(estacion);
		
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 65);
	}
	
	@Test
	public void estacionDeBomberosNoCuraMasDeSaludMaxima100() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica centralEolica = new CentralEolica(new Posicion(2,2), mapa);
		
		mapa.construir(centralEolica);
		
		assertEquals(centralEolica.getSalud(), 100);
		centralEolica.daniar();
		assertEquals(centralEolica.getSalud(), 65);
		
		//Creo estacion de bomberos
		EstacionBomberos estacion = new EstacionBomberos(new Posicion(2,3), mapa);
		mapa.construir(estacion);
		
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 80);
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 95);
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 100);
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 100);
	}
	
	@Test
	public void estacionBomberosSeReparaASiMisma() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		
		//Creo estacion de bomberos
		EstacionBomberos estacion = new EstacionBomberos(new Posicion(2,7), mapa);
		mapa.construir(estacion);
		
		estacion.daniar();
		assertEquals(estacion.getSalud(), 50);
		
		mapa.repararConstrucciones();
		assertEquals(estacion.getSalud(), 100);
	}
	
	@Test
	public void dosEstacionesDeBomberosCuranAlDobleDeVelocidadPorCadaTurno() throws Exception {
		Mapa mapa = new Mapa(new Dimension(10,10));
		CentralElectrica centralEolica = new CentralEolica(new Posicion(2,2), mapa);
		
		mapa.construir(centralEolica);
		
		assertEquals(centralEolica.getSalud(), 100);
		centralEolica.daniar();
		assertEquals(centralEolica.getSalud(), 65);
		
		//Creo estacion de bomberos
		EstacionBomberos estacion = new EstacionBomberos(new Posicion(2,3), mapa);
		mapa.construir(estacion);
		EstacionBomberos estacion2 = new EstacionBomberos(new Posicion(2,1), mapa);
		mapa.construir(estacion2);
		
		mapa.repararConstrucciones();
		assertEquals(centralEolica.getSalud(), 95);
	}
}
