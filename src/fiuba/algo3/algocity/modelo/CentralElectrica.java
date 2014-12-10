package fiuba.algo3.algocity.modelo;

import java.util.LinkedList;
import java.util.List;


public abstract class CentralElectrica extends Construccion implements FuenteDeRecursos, Construible {
	protected int electricidadDisponible;
	protected int coberturaEnHectareasALaRedonda;
	protected int porcentajeDeReparacionPorTurno;
	protected List<Construccion> construccionesQueAlimenta;
	private List<Red> redesConectadas;
	
	public CentralElectrica() {
		construccionesQueAlimenta = new LinkedList<Construccion>();
		redesConectadas = new LinkedList<Red>();
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		unMapa.posicionEstaLibre(this.getPosicion());
		unMapa.agregarConstruccion(this);		
		unMapa.agregarFuenteDeRecursos(this);
	}
	
	@Override
	public void daniar() {
		this.salud = salud - 35;
		if (salud < 0) {
			salud = 0;
			activa = false;
		}
	}
	
	public int getRecursosDisponibles() {
		if ((pozosOrigen.size() != 0) && (activa)) {
			return this.electricidadDisponible;
		}
		return 0;
	}
	
	@Override
	public void eliminar() throws Exception {
		if ( (mapa.getListaConstrucciones().contains(this)) ) {
			for(Construccion construccion : construccionesQueAlimenta) {
				construccion.quitarCentralElectrica(this);
			}
			for (Red red : redesConectadas) {
				red.eliminar();
			}
			mapa.getListaConstrucciones().remove(this);
		}
	}
	
	@Override
	public int getElectricidadDisponible() {
		if (salud > 0) {
			return getRecursosDisponibles();
		}
		return 0;
	}

	@Override
	public int getAguaDisponible() {
		int agua = 0;
		for(PozoDeAgua pozo : pozosOrigen) {
			agua += pozo.getAguaDisponible();
		}
		return agua;
	}
	
	public int consumir(int cantidadPersonasAConsumir) {
		int personasQueQuedanSinConsumir = 0;
		
		if (cantidadPersonasAConsumir > electricidadDisponible) {
			personasQueQuedanSinConsumir = cantidadPersonasAConsumir - electricidadDisponible;
			cantidadPersonasAConsumir = electricidadDisponible;
		}
		
		electricidadDisponible -= cantidadPersonasAConsumir;
		
		return personasQueQuedanSinConsumir;
	}
	public void dejarDeConsumir(int cantidadPersonasAConsumir) {
		electricidadDisponible += cantidadPersonasAConsumir;
	}
	
	public int getCoberturaEnHectareasALaRedonda() {
		return this.coberturaEnHectareasALaRedonda;
	}
	
	public int getporcentajeDeReparacionPorTurno() {
		return this.porcentajeDeReparacionPorTurno;
	}
	
	public Posicion getPosicion() {
		return posicion;
	}
	
	public void desactivar() {
		activa = false;
	}
	
	@Override
	public void alimentar(Construccion unaConstruccion) {
		unaConstruccion.agregarCentralOrigen(this);
		construccionesQueAlimenta.add(unaConstruccion);
	}
	
	public Conector getNewConector(Posicion unaPosicion) {
		return new TendidoElectrico(unaPosicion, mapa);
	}
	
	@Override
	public void agregarRedConectada(Red tmpRedDeRecursos) {
		if (!redesConectadas.contains(tmpRedDeRecursos)) {
			redesConectadas.add(tmpRedDeRecursos);
		}
	}
	
	public void eliminarConstruccionQueAlimenta(Construccion unaConstruccion) {
		if (construccionesQueAlimenta.contains(unaConstruccion)) {
			unaConstruccion.quitarCentralElectrica(this);
			construccionesQueAlimenta.remove(unaConstruccion);
		}
	}	

}
