package fiuba.algo3.algocity.controlador;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fiuba.algo3.algocity.modelo.Catastrofe;
import fiuba.algo3.algocity.modelo.Conector;
import fiuba.algo3.algocity.modelo.DespertarDiagonal;
import fiuba.algo3.algocity.modelo.EstrategiaGodzilla;
import fiuba.algo3.algocity.modelo.Godzilla;
import fiuba.algo3.algocity.modelo.Mapa;
import fiuba.algo3.algocity.modelo.Posicion;
import fiuba.algo3.algocity.modelo.Terremoto;
import fiuba.algo3.algocity.modelo.DespertarDerechoHorizontal;
import fiuba.algo3.algocity.modelo.DespertarDerechoVertical;

public class TurnosControlador {
	private static Catastrofe catastrofe;
	private Mapa mapa;
	private static List posicionesDaniadas;
	private static Random rand = new Random();;
	private static int n; //posibilidad

	public TurnosControlador(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public void avanzar() {
		generarCatastrofe();
		mapa.repararConstrucciones();
	}
	
	private void generarCatastrofe() {
		posicionesDaniadas = null; // De esta manera, se borra la catastrofe que pudo provocarse anteriormente
		n = rand.nextInt(100);		
		if (n <= 10) { // Se origina un terremoto
			Posicion posicionDanio = mapa.getPosicionRandom();
			catastrofe = new Terremoto(mapa, posicionDanio);
			catastrofe.originar();
			posicionesDaniadas = catastrofe.getPosicionesDaniadas();
		}
		if (n <= 20 && n > 10) { //Se origina Godzilla :(
			Posicion posicionDanio = mapa.getPosicionRandomGodzilla();
			catastrofe = new Godzilla(mapa, posicionDanio, generarEstrategiaGodzilla());
			catastrofe.originar();
			posicionesDaniadas = catastrofe.getPosicionesDaniadas();
		}
		else {
			// Tu mapa esta a salvo :D
		}
	}
	
	private EstrategiaGodzilla generarEstrategiaGodzilla() {
		int n = rand.nextInt(3);
		if (n == 0) {
			return new DespertarDerechoHorizontal();
		}
		if (n == 1) {
			return new DespertarDerechoVertical();
		}
		if (n == 2) {
			return new DespertarDiagonal();
		}
		return null;
	}
	
	public List getPosicionesDaniadas() {
		return posicionesDaniadas;
	}
	
	public String getImagen() {
		return catastrofe.getImagen();
	}

	public String getSonido() {
		return catastrofe.getSonido();
	}

	public void eliminarRutasYTendidosSinSalud(LinkedList<LinkedList<Conector>> postesYRutas) {
		
		List<List<Conector>> redesDaniadas = new LinkedList();
				
		for(List<Conector> red : postesYRutas) {
			int i = 0;
			boolean encontrado=false;
			if (red.size() != 0) {
				while(!encontrado && i<red.size()) { //con encontrar una en cero ya es suficiente para borrar todo
					Conector unConector = red.get(i);
					if (unConector.getSalud() == 0) {
						redesDaniadas.add(red);
						encontrado = true;
					}
					i++;
				}
			}
		}
		
		for(List<Conector> red : redesDaniadas) {
			for(Conector unConector : red) {
				try {
					mapa.eliminarConstruccion(unConector);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
