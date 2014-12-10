package fiuba.algo3.algocity.modelo;


public class CentralEolica extends CentralElectrica {
	
	public CentralEolica(Posicion posicion, Mapa mapa) {
		costo = 3000;
		coberturaEnHectareasALaRedonda = 4;
		electricidadDisponible = 100;
		porcentajeDeReparacionPorTurno = 15;
		this.posicion = posicion;
		this.salud = 100;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/centralEolica.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/centralEolica_mapa.png";
		this.mapa = mapa;
		porcentajeReparacion = 15;
	}
	
	public String getNombre() {
		return "Central Eolica";
	}
}
