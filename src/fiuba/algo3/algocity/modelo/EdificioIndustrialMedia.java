package fiuba.algo3.algocity.modelo;


public class EdificioIndustrialMedia extends EdificioIndustrial {
	
	public EdificioIndustrialMedia(Posicion unaPosicion, Mapa mapa) {
		costo = 300;
		cantidadPersonas = 0;
		maxCantidadPersonas = 100; //trabajan 100 personas por edificio
		this.posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		porcentajeReparacion = 3;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/fabrica2.png";
	}
	
}
