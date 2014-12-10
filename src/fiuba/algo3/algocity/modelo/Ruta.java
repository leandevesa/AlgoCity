package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class Ruta extends Conector {
	
	public Ruta(Posicion posicion, Mapa unMapa) {
		mapa = unMapa;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/ruta.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/ruta_mapa.png";
		costo = 50;
		this.posicion = posicion;
		this.salud = 100;
	}

	@Override
	public void daniar() {
		this.salud = 0;
	}
	
	@Override
	public void daniar(int porcentajeDanio) {
		this.salud = 0;
	}
	
	@Override
	public void eliminar() throws Exception {
		if (red != null) {
			LinkedList<Conector> rutas = red.getConectores();
			for (Conector ruta : rutas) {
				if ((mapa.getListaConstrucciones().contains(ruta)) ) {
					mapa.getListaConstrucciones().remove(ruta);
					
					LinkedList<Construccion> construccionesAlimentadas = red.getConexiones();
					for (Construccion construccion : construccionesAlimentadas) {
						red.getRegionFuente().eliminarRegionQueAlimenta(construccion);
					}
				}
			}
		} else {
			if ((mapa.getListaConstrucciones().contains(this)) ) {
				mapa.getListaConstrucciones().remove(this);
			}
		}
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
		mapa.agregarConstruccion(this);
	}
}
