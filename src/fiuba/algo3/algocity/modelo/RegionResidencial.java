package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;
import java.util.List;


public abstract class RegionResidencial extends Region {
	public List<EdificioResidencial> edificiosDesarrollados;
	protected String pathImagenEdificio;


	public RegionResidencial() {
		edificiosDesarrollados = new LinkedList<EdificioResidencial>();
	}
	
	public List<EdificioResidencial> getListaEdificiosDesarrollados() {
		return edificiosDesarrollados;
	}
	
	public String getImagenEdificio() {
		return pathImagenEdificio;
	}
	
	@Override
	public void eliminar() throws Exception {
		if ( (mapa.getRegionesResidenciales().contains(this)) ) {
			for (Red red : redesConectadas) {
				red.eliminar();
			}
			mapa.getRegionesResidenciales().remove(this);
		}
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		mapa = unMapa;
		unMapa.posicionEstaLibre(posicion, dimension);
		unMapa.agregarRegionResidencial(this);
	}
	
	@Override
	public int getPoblacion() {
		int personas = 0;
		for (EdificioResidencial unEdificio : edificiosDesarrollados) {
			personas += unEdificio.getCantidadPersonas();
		}
		
		return personas;
	}

	@Override
	public int recibirInmigrantes(int cantPersQueQuierenMigrar) throws Exception {
		int personasAEnviarAEdificio = 1;  //1 para que entre al while
		RegionIndustrial tmpRegionIndustrial = null;
		boolean puedoSeguirDesarrollando = true;
		EdificioResidencial unEdificioSinCompletar = null;
		
		cantPersQueQuierenMigrar += destruirRegion();
		
		//si la region no tiene trabajo disponible
		if (regionesIndustrialesConectadas.size() == 0) {
			return cantPersQueQuierenMigrar;
		}
		
		//si ya hay algun edificio desarrollado
		if (edificiosDesarrollados.size() > 0) {
			unEdificioSinCompletar = edificiosDesarrollados.get(edificiosDesarrollados.size() - 1);
			//Veo si hay lugar disponible
			if (maxPersonasPorHectarea > unEdificioSinCompletar.getCantidadPersonas()) {
				int cantPersQueEntranEnEdificio = maxPersonasPorHectarea - unEdificioSinCompletar.getCantidadPersonas();
				if (cantPersQueQuierenMigrar > cantPersQueEntranEnEdificio) {
					personasAEnviarAEdificio = cantPersQueEntranEnEdificio;
				} else {
					personasAEnviarAEdificio = cantPersQueQuierenMigrar;
				}
			} else {
				unEdificioSinCompletar = null; //esta lleno!
				if (edificiosDesarrollados.size() >= cantHectareas) {
					puedoSeguirDesarrollando = false; //no tengo lugar para desarrollar edificios nuevos
				}
			}
		}
		
		while ((personasAEnviarAEdificio > 0) && (cantPersQueQuierenMigrar > 0) && (puedoSeguirDesarrollando)) {
			
			if (unEdificioSinCompletar == null) {
				if (cantPersQueQuierenMigrar > maxPersonasPorHectarea) {
					personasAEnviarAEdificio = maxPersonasPorHectarea;
				} else {
					personasAEnviarAEdificio = cantPersQueQuierenMigrar;
				}
			}

			//Chequeo que haya trabajo disponible y que sea suficiente(si no hay, salgo) 	
			for (RegionIndustrial unaRegion : regionesIndustrialesConectadas) {
				if (unaRegion.getCantTrabajoDisponible() > 0) {
					tmpRegionIndustrial = unaRegion;
					if (tmpRegionIndustrial.getCantTrabajoDisponible() < personasAEnviarAEdificio) {
						personasAEnviarAEdificio = tmpRegionIndustrial.getCantTrabajoDisponible();
					}
					break;
				} else {
					tmpRegionIndustrial = null;
				}
			}

			if (tmpRegionIndustrial == null) {	//si no hay ninguna region industrial con trabajo disponible -> salgo
				return cantPersQueQuierenMigrar;
			}
			
			//Lo hago consumir los recursos de la region industrial por si tienen alguna central/pozo en comun
			//Si comparten alguna fuente de recursos, mas abajo cuando veo si tengo recursos disponibles
			//ya van a estar descontados
			tmpRegionIndustrial.consumirTemporalmente(personasAEnviarAEdificio);
			
			//Chequeo que haya electricidad disponible (si no hay, salgo) y calculo para cuantas pers es suficiente
			if (getElectricidadDisponible() < personasAEnviarAEdificio) {
		
				tmpRegionIndustrial.dejarDeConsumirTemporalmente();
				personasAEnviarAEdificio = (getElectricidadDisponible() / 2);
				puedoSeguirDesarrollando = false;
			}
			//Chequeo que haya agua disponible (si no hay, salgo) y calculo para cuantas pers es suficiente	
			if (getAguaDisponible() < personasAEnviarAEdificio) {

				tmpRegionIndustrial.dejarDeConsumirTemporalmente();
				personasAEnviarAEdificio = (getAguaDisponible() / 2);
				puedoSeguirDesarrollando = false;
			}

			tmpRegionIndustrial.dejarDeConsumirTemporalmente();
			consumirElectricidad(personasAEnviarAEdificio);
			consumirAgua(personasAEnviarAEdificio);
			tmpRegionIndustrial.recibirTrabajadores(personasAEnviarAEdificio);
			cantPersQueQuierenMigrar -= personasAEnviarAEdificio;
			
			if (unEdificioSinCompletar == null) {
				EdificioResidencial edificio = getEdificioAConstruir(calcularPosicion(edificiosDesarrollados.size()));
				edificio.agregarPersonas(personasAEnviarAEdificio);
				edificiosDesarrollados.add(edificio);
				mapa.construir(edificio);
			} else {
				unEdificioSinCompletar.agregarPersonas(personasAEnviarAEdificio);
				unEdificioSinCompletar = null;
			}
			
			if (edificiosDesarrollados.size() >= cantHectareas) {
				puedoSeguirDesarrollando = false;
			}
			if (getUnaCentralConElectricidadDisponible() == null) {
				puedoSeguirDesarrollando = false;
			}				
			if (getUnPozoConAguaDisponible() == null) {
				puedoSeguirDesarrollando = false;
			}
			
		}
		
		return cantPersQueQuierenMigrar;
	}
	
	public int destruirRegion() {
		int poblacion = getPoblacion();
		edificiosDesarrollados = new LinkedList<EdificioResidencial>();
		dejarDeConsumirAgua();
		dejarDeConsumirElectricidad();
		for (RegionIndustrial tmpRegion : regionesIndustrialesConectadas) {
			tmpRegion.destruirRegion();
		}
		return poblacion;
	}
	
	
	@Override
	public void conectarCon(Region otraRegion) {
		if ( !(otraRegion.getRegionesResidencialesConectadas().contains(this)) ) {
			otraRegion.conectarRegionResidencial(this);
		}
	}
	
	protected abstract EdificioResidencial getEdificioAConstruir(Posicion unaPosicion);
}
