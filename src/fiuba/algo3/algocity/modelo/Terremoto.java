package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;
import java.util.List;


public class Terremoto extends Catastrofe {
	
	static final int danioMax = 100;
	static final int danioMin = 35;
	private String imagen;
	private String sonido;
	private int rangoMaximo = 4;
	
	public Terremoto(Mapa unMapa, Posicion posicion) {
		this.mapa = unMapa;
		this.posicionInicio = posicion;
		this.posicionesDaniadas = new LinkedList();
		this.imagen = "src/fiuba/algo3/algocity/modelo/imagenes/crash.png";
		this.sonido = "src/fiuba/algo3/algocity/modelo/sonido/crash.wav";
	}
	
	@Override
	public String getImagen() {
		return imagen;
	}
	
	@Override
	public String getSonido() {
		return sonido;
	}
	
	@Override
	public void originar() {		
		for(int i = 0; i < mapa.getDimension().x(); i++) {
			for(int j = 0; j < mapa.getDimension().y(); j++) {
				Posicion posicionImpactada = new Posicion(i,j);
				int fuerzaDeImpacto = calcularFuerza(posicionImpactada);
				
				if (fuerzaDeImpacto > 0) {
					posicionesDaniadas.add(posicionImpactada);
					mapa.daniar(posicionImpactada, fuerzaDeImpacto);
				}
				
			}
		}
	}
	
	private int calcularFuerza(Posicion posicionImpactada) {
		int distanciaAlOrigen = mapa.distanciaEntreParcelas(posicionInicio, posicionImpactada);
		int danio = danioMax;
		
		for(int i = 0; i < distanciaAlOrigen; i++) {		
			danio = danio - danioMin;
		}
		
		if (distanciaAlOrigen > rangoMaximo) {
			danio = 0;
		}
		
		if (danio < 0) {
			return 0;
		}
		return danio; 
	}
	
	public List getPosicionesDaniadas() {
		return posicionesDaniadas;
	}

}
