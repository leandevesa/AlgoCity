package fiuba.algo3.algocity.modelo;


public class EdificioIndustrialAlta extends EdificioIndustrial {

	public EdificioIndustrialAlta(Posicion unaPosicion, Mapa mapa) {
		costo = 500;
		cantidadPersonas = 0;
		maxCantidadPersonas = 150; //trabajan 150 personas por edificio
		this.posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		porcentajeReparacion = 3;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/fabrica3.png";
	}
	
}
