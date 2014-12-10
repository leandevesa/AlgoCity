package fiuba.algo3.algocity.modelo;


public class EstacionBomberos extends Construccion {
	
	private int rangoCobertura;
	
	public EstacionBomberos(Posicion unaPosicion, Mapa mapa) {
		posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		rangoCobertura = 4;
		costo = 3500;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/estacion.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/estacion_mapa.png";
		porcentajeReparacion = 75;
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
	public void eliminar() throws Exception {
		if ( !(mapa.getListaConstrucciones().contains(this)) ) {
			throw new Exception();
		}
		mapa.getListaConstrucciones().remove(this);
		mapa.getEstacionesBomberos().remove(this);
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		unMapa.posicionEstaLibre(posicion);
		unMapa.agregarEstacionBomberos(this);
		unMapa.agregarConstruccion(this);
	}

	@Override
	public void daniar() {
		this.salud = salud - 50;
		if (salud < 0) {
			salud = 0;
		}
	}

	public int getRangoDeCobertura() {
		return rangoCobertura;
	}
	
	public void repararConstruccion(Construccion construccionDanida) {
		construccionDanida.reparar();
	}
}
