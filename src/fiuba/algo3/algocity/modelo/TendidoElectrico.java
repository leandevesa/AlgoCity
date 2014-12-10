package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;

public class TendidoElectrico extends Conector {

	public TendidoElectrico(Posicion posicion, Mapa unMapa) {
		costo = 50;
		mapa = unMapa;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/tendidoElectrico.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/tendidoElectrico_mapa.png";
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
			LinkedList<Conector> tendidos = red.getConectores();
			for (Conector tendido : tendidos) {
				if ((mapa.getListaConstrucciones().contains(tendido)) ) {
					mapa.getListaConstrucciones().remove(tendido);
					
					LinkedList<Construccion> construccionesAlimentadas = red.getConexiones();
					for (Construccion construccion : construccionesAlimentadas) {
						red.getFuenteDeRecursos().eliminarConstruccionQueAlimenta(construccion);
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
		//validarTuberia(mapa.getTuberias());
		mapa.agregarConstruccion(this);
	}

}
