package fiuba.algo3.algocity.modelo;

public class RegionAcuatica extends Region {
	
	public RegionAcuatica(Posicion unaPosicion, Dimension unaDimension, Mapa mapa) {
		posicion= unaPosicion;
		dimension= unaDimension;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/agua.png";
		this.mapa = mapa;
	}
	
	@Override
	public void eliminar() throws Exception {
		// El agua no puede ser eliminada del mapa
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		mapa = unMapa;
		unMapa.posicionEstaLibre(posicion, dimension);
		unMapa.agregarRegionAcuatica(this);
	}
	
	public void agregarPozoDeAgua(Mapa unMapa, PozoDeAgua pozoDeAgua) {
		((PozoDeAgua) pozoDeAgua).inicializar();
		unMapa.agregarConstruccion(pozoDeAgua);
	}

	
}
