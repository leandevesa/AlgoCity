package fiuba.algo3.algocity.modelo;


public class EdificioIndustrialBaja extends EdificioIndustrial {
	public EdificioIndustrialBaja(Posicion unaPosicion, Mapa mapa) {
		posicion = unaPosicion;
		costo = 100;
		cantidadPersonas = 0;
		maxCantidadPersonas = 50; //trabajan 50 personas por edificio
		this.posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		porcentajeReparacion = 3;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/fabrica1.png";
	}	


}
