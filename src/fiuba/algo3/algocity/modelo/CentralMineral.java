package fiuba.algo3.algocity.modelo;


public class CentralMineral extends CentralElectrica {
	
	public CentralMineral(Posicion posicion, Mapa mapa) {
		costo = 5000;
		coberturaEnHectareasALaRedonda = 10;
		electricidadDisponible = 400;
		porcentajeDeReparacionPorTurno = 10;
		this.posicion = posicion;
		this.salud = 100;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/centralMineral.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/centralMineral_mapa.png";
		this.mapa = mapa;
		porcentajeReparacion = 10;
	}
	
	public String getNombre() {
		return "Central Mineral";
	}
	
}