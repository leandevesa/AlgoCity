package fiuba.algo3.algocity.modelo;

import java.util.List;


public class Godzilla extends Catastrofe {
	private EstrategiaGodzilla estrategia; 
	private String imagen;
	private String sonido;
	
	public Godzilla(Mapa mapa, Posicion unaPosicion, EstrategiaGodzilla tipoDeEstrategia) {
		this.mapa = mapa;
		this.posicionInicio = unaPosicion;
		this.estrategia = tipoDeEstrategia;
		this.imagen = "src/fiuba/algo3/algocity/modelo/imagenes/godzilla.png";
		this.sonido = "src/fiuba/algo3/algocity/modelo/sonido/godzilla.wav";
	}
	
	@Override
	public void originar() {
		estrategia.caminar(posicionInicio, mapa);
	}
	
	@Override
	public String getImagen() {
		return imagen;
	}
	
	@Override
	public List getPosicionesDaniadas() {
		return estrategia.getPosicionesDaniadas();
	}

	@Override
	public String getSonido() {
		return sonido;
	}
	public void actualizarPosicion(Posicion posicion) {
		this.posicionInicio = posicion;
	}

}
