package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class Tuberia extends Conector {
		
	public Tuberia(Posicion posicion, Mapa mapa) {
		tieneVistaSubterranea = true;
		this.posicion = posicion;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/tuberiaMapa.png";
		costo = 50;
		this.mapa = mapa;
		this.salud = 100;
	}
	
	@Override
	public int getElectricidadDisponible() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAguaDisponible() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void construirEn(Mapa mapa) throws Exception {
		validarTuberia(mapa.getTuberias());
		mapa.agregarTuberia(this);
	}

	@Override
	public void daniar() {
		// La tuberia no puede ser daniada
	}
	
	private void validarTuberia(java.util.List<Tuberia> tuberias) throws Exception {
		for (Tuberia tuberia : tuberias) {
			if (tuberia.getPosicion().esIgualA(posicion)) {
				throw new Exception("ya existe una tuberia aca");
			}
		}
	}
	
	public int getCosto() {
		return costo;
	}

	public String getImagenSobreAgua() {
		return "src/fiuba/algo3/algocity/modelo/imagenes/tuberiaMapaSobreAgua.png";
	}

	@Override
	public void eliminar() throws Exception {
		if (red != null) {
			LinkedList<Conector> tubos = red.getConectores();
			for (Conector tubo : tubos) {
				if ((mapa.getTuberias().contains(tubo)) ) {
					mapa.getTuberias().remove(tubo);
					
					LinkedList<Construccion> construccionesAlimentadas = red.getConexiones();
					for (Construccion construccion : construccionesAlimentadas) {
						red.getFuenteDeRecursos().eliminarConstruccionQueAlimenta(construccion);
					}
					
				}
			}
		} else {
			if ((mapa.getTuberias().contains(this)) ) {
				mapa.getTuberias().remove(this);
			}
		}
	}
}
