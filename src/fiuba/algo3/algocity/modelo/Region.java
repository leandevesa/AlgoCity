package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;
import java.util.List;

public abstract class Region extends Construccion {
	protected Dimension dimension;
	protected int cantHectareas = 0;
	protected int maxPersonasPorHectarea = 0;
	protected List<RegionIndustrial> regionesIndustrialesConectadas;
	protected List<RegionResidencial> regionesResidencialesConectadas;
	protected List<Red> redesConectadas;
	
	public List<CantidadConsumida> centralesElectricasConsumidas;
	public List<CantidadConsumida> pozosConsumidos;
	
	protected Mapa mapa;
	
	public Region() {
		regionesIndustrialesConectadas = new LinkedList<RegionIndustrial>();
		regionesResidencialesConectadas = new LinkedList<RegionResidencial>();		
		centralesElectricasConsumidas = new LinkedList<CantidadConsumida>();
		redesConectadas = new LinkedList<Red>();
		pozosConsumidos = new LinkedList<CantidadConsumida>();
	}
	
	@Override
	public void daniar() {
		// Las regiones no son afectadas por las catastrofes
	}
	
	public void agregarConstruccion(Mapa unMapa, Construccion unaConstruccion) {
		//Cada region sabe como construir cosas dentro suyo	
	}
	
	public void agregarConexionElectrica(CentralElectrica unaCentral) {
		centralesOrigen.add(unaCentral);
	}
	public void agregarConexionDeAgua(PozoDeAgua unPozo) {
		pozosOrigen.add(unPozo);
	}
	public int getAguaDisponible() {
		int totalAgua = 0;
		if (pozosOrigen.size() != 0) {
			for (PozoDeAgua tmpPozo : pozosOrigen) {
				totalAgua += tmpPozo.getRecursosDisponibles();
			}
			
			return totalAgua;
		} else {
			return 0;
		}
	}
	public void consumirAgua(int cantidadPersonasAConsumir) {
		int cantConsumida = 0;
		int personasAntesDeConsumir = cantidadPersonasAConsumir;
		int personasQueQuedanSinConsumir = cantidadPersonasAConsumir;
		
		for (PozoDeAgua tmpPozo : pozosOrigen) {
			personasAntesDeConsumir = cantidadPersonasAConsumir;
			personasQueQuedanSinConsumir = tmpPozo.consumir(personasQueQuedanSinConsumir);
			cantConsumida = personasAntesDeConsumir - personasQueQuedanSinConsumir;
			if (cantConsumida > 0) {
				pozosConsumidos.add(new CantidadConsumida(tmpPozo, cantConsumida));
			}
			if (personasQueQuedanSinConsumir == 0) {
				break;
			}
		}
		
	}
	public void consumirElectricidad(int cantidadPersonasAConsumir) {
		int cantConsumida = 0;
		int personasAntesDeConsumir = cantidadPersonasAConsumir;
		int personasQueQuedanSinConsumir = cantidadPersonasAConsumir;
		
		for (CentralElectrica tmpCentral : centralesOrigen) {
			personasAntesDeConsumir = cantidadPersonasAConsumir;
			personasQueQuedanSinConsumir = tmpCentral.consumir(personasQueQuedanSinConsumir);
			cantConsumida = personasAntesDeConsumir - personasQueQuedanSinConsumir;
			if (cantConsumida > 0) {
				pozosConsumidos.add(new CantidadConsumida(tmpCentral, cantConsumida));
			}
			if (personasQueQuedanSinConsumir == 0) {
				break;
			}
		}
	}
	public void dejarDeConsumirAgua() {
		for (CantidadConsumida unaRegion : pozosConsumidos) {
			unaRegion.getFuente().dejarDeConsumir(unaRegion.getCantidad());
		}
		pozosConsumidos = new LinkedList<CantidadConsumida>();
	}
	public void dejarDeConsumirElectricidad() {
		for (CantidadConsumida unaRegion : centralesElectricasConsumidas) {
			unaRegion.getFuente().dejarDeConsumir(unaRegion.getCantidad());
		}
		centralesElectricasConsumidas = new LinkedList<CantidadConsumida>();
	}
	public int getElectricidadDisponible() {
		int totalElectricidad = 0;
		if (centralesOrigen.size() != 0) {
			for (CentralElectrica tmpCentral : centralesOrigen) {
				totalElectricidad += tmpCentral.getRecursosDisponibles();
			}
			
			return totalElectricidad;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean esAdyacente(Posicion unaPosicion) {
		Posicion tmpPos;
		int x = posicion.x;
		int y = posicion.y;
		
		//recorro el borde superior
		for (int i = 0; i < dimension.x; i++) {
			tmpPos = new Posicion(x + i, y);
			if (tmpPos.esAdyacente(unaPosicion)) {
				return true;
			}
		}		
		//recorro el borde derecho
		for (int i = 0; i < dimension.y; i++) {
			tmpPos = new Posicion(x + dimension.x - 1, y + i);
			if (tmpPos.esAdyacente(unaPosicion)) {
				return true;
			}
		}
		//recorro el borde inferior
		for (int i = 0; i < dimension.x; i++) {
			tmpPos = new Posicion(x + i, y + dimension.y - 1);
			if (tmpPos.esAdyacente(unaPosicion)) {
				return true;
			}
		}		
		//recorro el borde izquierdo
		for (int i = 0; i < dimension.y; i++) {
			tmpPos = new Posicion(x, y + i);
			if (tmpPos.esAdyacente(unaPosicion)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean ocupaPosicion(Posicion unaPosicion) {		
		for (int x = posicion.x; x < (posicion.x + dimension.x); x++) {
			for (int y = posicion.y; y < (posicion.y + dimension.y); y++) {
				if (new Posicion(x,y).esIgualA(unaPosicion)) {
					return true;
				}
			}
		}		
		return false;
	}
	
	public void conectarCon(Region otraRegion) {
		
	}
	
	public void conectarRegionResidencial(RegionResidencial unaRegion) {
		regionesResidencialesConectadas.add(unaRegion);
	}
	public void conectarRegionIndustrial(RegionIndustrial unaRegion) {
		regionesIndustrialesConectadas.add(unaRegion);
	}
		
	public List<RegionIndustrial> getRegionesIndustrialesConectadas() {
		return regionesIndustrialesConectadas;
	}
	public List<RegionResidencial> getRegionesResidencialesConectadas() {
		return regionesResidencialesConectadas;
	}
	
	
	//Estos dos (cuando esté hecha la diferenciacion de regiones en mapa) van solo a region residencial
	public int recibirInmigrantes(int cantidadInmigrantes) throws Exception {
		return 0;
	}
	public int getPoblacion() {
		return 0;
	}
	
	public Posicion calcularPosicion(int numeroDeEdificio) {  //nr de edif arranca de 0
		int y = (numeroDeEdificio / (dimension.x + 1));
		int x = numeroDeEdificio - (y * (dimension.x + 1));
		y += posicion.y;
		x += posicion.x;
		return new Posicion(x,y);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Conector getNewConector(Posicion posicion) {
		return new Ruta(posicion, mapa);
	}

	public void agregarRedConectada(RedDeRutas tmpRedDeRutas) {
		if (!redesConectadas.contains(tmpRedDeRutas)) {
			redesConectadas.add(tmpRedDeRutas);
		}
	}

	public void eliminarRegionQueAlimenta(Construccion construccion) {
		if (regionesIndustrialesConectadas.contains(construccion)) {
			regionesIndustrialesConectadas.remove(construccion);
		}
		if (regionesResidencialesConectadas.contains(construccion)) {
			regionesResidencialesConectadas.remove(construccion);
		}
	}

	
}