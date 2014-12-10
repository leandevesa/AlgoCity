package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;
import java.util.List;

import fiuba.algo3.algocity.excepciones.NoSePuedeConstruirSobreOtraConstruccion;
import fiuba.algo3.algocity.excepciones.SeDebeConstruirSobreAgua;


public class PozoDeAgua extends Construccion implements FuenteDeRecursos, Construible {
	private int aguaRestante;
	private boolean estaActiva = true;
	private List<Construccion> construccionesQueAlimenta;
	private List<Red> redesConectadas;
	
	public PozoDeAgua (Posicion unaPosicion, Mapa mapa) {
		costo = 1500;
		pathImagen = "src/fiuba/algo3/algocity/modelo/imagenes/pozoDeAgua.png";
		pathImagenMapa = "src/fiuba/algo3/algocity/modelo/imagenes/pozoDeAgua_mapa.png";
		posicion = unaPosicion;
		this.salud = 100;
		this.mapa = mapa;
		this.aguaRestante = 0;
		construccionesQueAlimenta = new LinkedList<Construccion>();
		redesConectadas = new LinkedList<Red>();
	}
	
	@Override
	public int getElectricidadDisponible() {
		return 0;
	}

	@Override
	public int getAguaDisponible() {
		return aguaRestante;
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		RegionAcuatica unaRegion = unMapa.getRegionAcuatica(this.posicion);
		if (unaRegion == null ) {
			throw new SeDebeConstruirSobreAgua();
		}
		if (unMapa.getConstruccion(this.posicion) != null) {
			throw new NoSePuedeConstruirSobreOtraConstruccion();
		}
		(unaRegion).agregarPozoDeAgua(unMapa, this);
		unMapa.agregarFuenteDeRecursos(this);
		inicializar();
	}
	
	@Override
	public void alimentar(Construccion unaConstruccion) {
		unaConstruccion.agregarPozoOrigen(this);
		construccionesQueAlimenta.add(unaConstruccion);
		unaConstruccion.activar();
	}

	@Override
	public void daniar() {
		this.salud = salud - 35;
		if (salud < 0) {
			salud = 0;
		}
	}
	
	@Override
	public void eliminar() throws Exception {
		if ((mapa.getListaConstrucciones().contains(this)) ) {
			for(Construccion construccion : construccionesQueAlimenta) {
				construccion.quitarPozoDeAgua(this);
			}
			for (Red red : redesConectadas) {
				red.eliminar();
			}
			mapa.getListaConstrucciones().remove(this);
		}
	}
	
	public void inicializar() {
		aguaRestante = 250;
	}
	
	public int consumir(int cantidadPersonasAConsumir) {
		int personasQueQuedanSinConsumir = 0;
		
		if (cantidadPersonasAConsumir > aguaRestante) {
			personasQueQuedanSinConsumir = cantidadPersonasAConsumir - aguaRestante;
			cantidadPersonasAConsumir = aguaRestante;
		}
		
		aguaRestante -= cantidadPersonasAConsumir;
		
		return personasQueQuedanSinConsumir;
	}	
	public void dejarDeConsumir(int cantidadPersonasAConsumir) {
		aguaRestante += cantidadPersonasAConsumir;
	}
	
	public void desactivar() {
		estaActiva = false;
	}

	public int getRecursosDisponibles() {
		if (estaActiva) {
			return aguaRestante;
		} else {
			return 0;
		}
	}
	
	public String getNombre() {
		return "Pozo de agua";
	}
	
	public Conector getNewConector(Posicion unaPosicion) {
		return new Tuberia(unaPosicion, mapa);
	}

	@Override
	public void agregarRedConectada(Red tmpRedDeRecursos) {
		if (!redesConectadas.contains(tmpRedDeRecursos)) {
			redesConectadas.add(tmpRedDeRecursos);
		}
	}
	
	public void eliminarConstruccionQueAlimenta(Construccion unaConstruccion) {
		if (construccionesQueAlimenta.contains(unaConstruccion)) {
			unaConstruccion.quitarPozoDeAgua(this);
			construccionesQueAlimenta.remove(unaConstruccion);
		}

	}
	
}
