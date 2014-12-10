package fiuba.algo3.algocity.modelo;


public class CentralNuclear extends CentralElectrica {
	
	public CentralNuclear(Posicion posicion, Mapa mapa) {
		costo = 10000;
		coberturaEnHectareasALaRedonda = 25;
		electricidadDisponible = 1000;
		porcentajeDeReparacionPorTurno = 3;
		this.posicion = posicion; 
		this.salud = 100;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/centralNuclear.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/centralNuclear_mapa.png";
		this.mapa = mapa;
		porcentajeReparacion = 3;
	}
	
	public String getNombre() {
		return "Central Nuclear";
	}
	
}