package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class RegionIndustrialAlta extends RegionIndustrial {
	
	public RegionIndustrialAlta(Posicion unaPosicion, Dimension unaDimension, Mapa mapa) {
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/regionIndustrialAlta.png";
		edificiosDesarrollados = new LinkedList<EdificioIndustrial>();
		posicion = unaPosicion;
		dimension = unaDimension;
		maxPersonasPorHectarea = getEdificioAConstruir(new Posicion(0,0)).getMaxCantidadPersonas();
		cantHectareas = (dimension.x) * (dimension.y);
		cantTrabajoDisponible = maxPersonasPorHectarea * cantHectareas;
		costo = (getEdificioAConstruir(new Posicion(0,0)).getCosto() * cantHectareas);
		pathImagenEdificio = "src/fiuba/algo3/algocity/modelo/imagenes/fabrica_regionalta.png";
		this.mapa = mapa;
	}

	@Override
	protected EdificioIndustrial getEdificioAConstruir(Posicion unaPosicion) {
		return new EdificioIndustrialAlta(unaPosicion, mapa);
	}

}
