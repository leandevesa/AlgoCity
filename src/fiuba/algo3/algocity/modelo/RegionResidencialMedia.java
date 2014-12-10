package fiuba.algo3.algocity.modelo;

public class RegionResidencialMedia extends RegionResidencial {
	
	public RegionResidencialMedia(Posicion unaPosicion, Dimension unaDimension, Mapa mapa) {
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/regionResidencialMedia.png";
		posicion = unaPosicion;
		dimension = unaDimension;
		maxPersonasPorHectarea = getEdificioAConstruir(new Posicion(0,0)).getMaxCantidadPersonas();
		cantHectareas = (dimension.x) * (dimension.y);
		costo = (getEdificioAConstruir(new Posicion(0,0)).getCosto() * cantHectareas);
		pathImagenEdificio = "src/fiuba/algo3/algocity/modelo/imagenes/casa_regionmedia.png";
		this.mapa = mapa;
	}

	@Override
	protected EdificioResidencial getEdificioAConstruir(Posicion unaPosicion) {
		return new EdificioResidencialMedia(unaPosicion, mapa);
	}
		
}

