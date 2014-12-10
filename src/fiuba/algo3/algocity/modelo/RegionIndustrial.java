package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;
import java.util.List;


public abstract class RegionIndustrial extends Region {
	public List<EdificioIndustrial> edificiosDesarrollados;
	public int cantTrabajoDisponible = 0;
	private List<CentralElectrica> tmpCentralesConsumidas = new LinkedList<CentralElectrica>();
	private List<Integer> tmpCantidadesDeElectricidadConsumidas = new LinkedList<Integer>();
	private List<PozoDeAgua> tmpPozosConsumidos = new LinkedList<PozoDeAgua>();
	private List<Integer> tmpCantidadesDeAguaConsumidas = new LinkedList<Integer>();
	protected String pathImagenEdificio;
	
	public RegionIndustrial() {
		edificiosDesarrollados = new LinkedList<EdificioIndustrial>();
	}

	public List<EdificioIndustrial> getListaEdificiosDesarrollados() {
		return edificiosDesarrollados;
	}
	
	public String getImagenEdificio() {
		return pathImagenEdificio;
	}
	
	@Override
	public void eliminar() throws Exception {
		if ( (mapa.getRegionesIndustriales().contains(this)) ) {
			for (Red red : redesConectadas) {
				red.eliminar();
			}
			mapa.getRegionesIndustriales().remove(this);
		}
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		mapa = unMapa;
		unMapa.posicionEstaLibre(posicion, dimension);
		unMapa.agregarRegionIndustrial(this);
	}
	
	public void destruirRegion() {
		edificiosDesarrollados = new LinkedList<EdificioIndustrial>();
		dejarDeConsumirAgua();
		dejarDeConsumirElectricidad();
		cantTrabajoDisponible = maxPersonasPorHectarea * cantHectareas;
	}
	
	public int getCantTrabajoDisponible() {
		int persQuePuedenTrabajar = cantTrabajoDisponible;
		
		//veo a cuantas personas soporta la central electrica conectada
		if (getElectricidadDisponible() < persQuePuedenTrabajar) {
			
			if (getElectricidadDisponible() == 0) {
				return 0;
			}
			
			persQuePuedenTrabajar = getElectricidadDisponible();
		}
		//veo a cuantas personas soporta el pozo conectado
		if (getAguaDisponible() < persQuePuedenTrabajar) {
			
			if (getAguaDisponible() == 0) {
				return 0;
			}
			
			persQuePuedenTrabajar = getAguaDisponible();
		}
		
		return persQuePuedenTrabajar;
	}

	public void recibirTrabajadores(int cantPersQueQuierenTrabajar) throws Exception {
		EdificioIndustrial unEdificioSinCompletar = null;
		int personasAEnviarAEdificio = 1; //para que entre al while
		
		
		//si ya hay algun edificio desarrollado
		if (edificiosDesarrollados.size() > 0) {
			unEdificioSinCompletar = edificiosDesarrollados.get(edificiosDesarrollados.size() - 1);
			//Veo si hay lugar disponible
			if (maxPersonasPorHectarea > unEdificioSinCompletar.getCantidadPersonas()) {
				int cantPersQueEntranEnEdificio = maxPersonasPorHectarea - unEdificioSinCompletar.getCantidadPersonas();
				if (cantPersQueQuierenTrabajar > cantPersQueEntranEnEdificio) {
					personasAEnviarAEdificio = cantPersQueEntranEnEdificio;
				} else {
					personasAEnviarAEdificio = cantPersQueQuierenTrabajar;
				}
			} else {
				unEdificioSinCompletar = null; //esta lleno!
			}
		}
		
		while ((personasAEnviarAEdificio > 0) && (cantPersQueQuierenTrabajar > 0)) {
			
			if (unEdificioSinCompletar == null) {
				if (cantPersQueQuierenTrabajar > maxPersonasPorHectarea) {
					personasAEnviarAEdificio = maxPersonasPorHectarea;
				} else {
					personasAEnviarAEdificio = cantPersQueQuierenTrabajar;
				}
			}
			//consumo electricidad
			
			for (CentralElectrica tmpCentral : centralesOrigen) {
				if (tmpCentral.getRecursosDisponibles() > 0) {
					
				}
			}
			consumirElectricidad(personasAEnviarAEdificio);
			consumirAgua(personasAEnviarAEdificio);
			cantTrabajoDisponible -= personasAEnviarAEdificio;
			cantPersQueQuierenTrabajar -= personasAEnviarAEdificio;
			
			if (unEdificioSinCompletar == null) {
				EdificioIndustrial edificio = getEdificioAConstruir(calcularPosicion(edificiosDesarrollados.size()));
				edificio.agregarPersonas(personasAEnviarAEdificio);
				edificiosDesarrollados.add(edificio);
			} else {
				unEdificioSinCompletar.agregarPersonas(personasAEnviarAEdificio);
				unEdificioSinCompletar = null;
			}
						
		}
	}
	
	public void consumirTemporalmente(int cantPersQueQuierenTrabajar) {
		int personasAntesDeConsumir = cantPersQueQuierenTrabajar;
		int personasQueQuedanSinConsumir = cantPersQueQuierenTrabajar;
		
		for (CentralElectrica tmpCentral : centralesOrigen) {
			
			personasQueQuedanSinConsumir = tmpCentral.consumir(personasQueQuedanSinConsumir);
			
			tmpCentralesConsumidas.add(tmpCentral);
			tmpCantidadesDeElectricidadConsumidas.add(personasAntesDeConsumir - personasQueQuedanSinConsumir);
			
			personasAntesDeConsumir = personasQueQuedanSinConsumir;
			
			if (personasQueQuedanSinConsumir == 0) {
				break;
			}
		}
		
		personasAntesDeConsumir = cantPersQueQuierenTrabajar;
		personasQueQuedanSinConsumir = cantPersQueQuierenTrabajar;
		
		for (PozoDeAgua tmpPozo : pozosOrigen) {
			
			personasQueQuedanSinConsumir = tmpPozo.consumir(personasQueQuedanSinConsumir);
			
			tmpPozosConsumidos.add(tmpPozo);
			tmpCantidadesDeAguaConsumidas.add(personasAntesDeConsumir - personasQueQuedanSinConsumir);
			
			personasAntesDeConsumir = personasQueQuedanSinConsumir;
			
			if (personasQueQuedanSinConsumir == 0) {
				break;
			}
		}
	}
	public void dejarDeConsumirTemporalmente() {
		for (int i = 0; i < tmpCentralesConsumidas.size(); i++) {
			tmpCentralesConsumidas.get(i).dejarDeConsumir(tmpCantidadesDeElectricidadConsumidas.get(i));
		}
		for (int i = 0; i < tmpPozosConsumidos.size(); i++) {
			tmpPozosConsumidos.get(i).dejarDeConsumir(tmpCantidadesDeAguaConsumidas.get(i));
		}		

		tmpCentralesConsumidas = new LinkedList<CentralElectrica>();
		tmpCantidadesDeElectricidadConsumidas = new LinkedList<Integer>();
		tmpPozosConsumidos = new LinkedList<PozoDeAgua>();
		tmpCantidadesDeAguaConsumidas = new LinkedList<Integer>();
	}
	
	@Override
	public void conectarCon(Region otraRegion) {
		if ( !(otraRegion.getRegionesIndustrialesConectadas().contains(this)) ) {
			otraRegion.conectarRegionIndustrial(this);
		}
	}
	
	protected abstract EdificioIndustrial getEdificioAConstruir(Posicion unaPosicion);

}
