package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import fiuba.algo3.algocity.excepciones.NoSePuedeConstruirSobreAgua;
import fiuba.algo3.algocity.excepciones.NoSePuedeConstruirSobreOtraConstruccion;
import fiuba.algo3.algocity.excepciones.RegionAConstruirSeEncuentraOcupada;
import fiuba.algo3.algocity.excepciones.RegionFueraDeLosLimites;
import fiuba.algo3.algocity.excepciones.SeDebeSeleccionarUnaFuenteDeRecursos;


public class Mapa {
	private List<RegionAcuatica> regionesDeAgua;
	private List<RegionIndustrial> regionesIndustriales;
	private List<RegionResidencial> regionesResidenciales;
	private List<Construccion> construcciones;
	private List<FuenteDeRecursos> fuentesDeRecursos;
	private List<Tuberia> tuberias;
	private List<EstacionBomberos> estaciones;
	private Dimension dimensionMapa;
		
	public Mapa(Dimension dimension) {
		regionesDeAgua = new LinkedList<RegionAcuatica>();
		fuentesDeRecursos = new LinkedList<FuenteDeRecursos>();
		regionesIndustriales = new LinkedList<RegionIndustrial>();
		regionesResidenciales = new LinkedList<RegionResidencial>();
		construcciones = new LinkedList<Construccion>();
		tuberias = new LinkedList<Tuberia>();
		estaciones = new LinkedList<EstacionBomberos>();
		dimensionMapa = dimension;
	}
	
	public void agregarConstruccion(Construccion unaConstruccion) {
		construcciones.add(unaConstruccion);
	}
	
	public void agregarFuenteDeRecursos(FuenteDeRecursos unaConstruccion) {
		fuentesDeRecursos.add(unaConstruccion);
	}
	
	public void agregarRegionAcuatica(RegionAcuatica unaRegion) {
		regionesDeAgua.add(unaRegion);
	}
	
	public void agregarRegionIndustrial(RegionIndustrial unaRegion) {
		regionesIndustriales.add(unaRegion);
	}
	
	public void agregarRegionResidencial(RegionResidencial unaRegion) {
		regionesResidenciales.add(unaRegion);
	}
	
	public void construir(Construccion unaConstruccion) throws Exception {
		unaConstruccion.construirEn(this);
	}
	
	public List<RegionAcuatica> getRegionesDeAgua() {
		return regionesDeAgua;
	}
	
	public List<RegionIndustrial> getRegionesIndustriales() {
		return regionesIndustriales;
	}
	
	public List<RegionResidencial> getRegionesResidenciales() {
		return regionesResidenciales;
	}
	
	public List<Construccion> getListaConstrucciones() {
		return construcciones;
	}
	
	public void recibirInmigrantes(int cantidadInmigrantes) throws Exception {
		for (Region region : regionesResidenciales) {
			if (cantidadInmigrantes > 0) {
				cantidadInmigrantes = region.recibirInmigrantes(cantidadInmigrantes);
			}
		}
	}
	
	public int getPoblacion() {
		int poblacion = 0;
		for (Region region : regionesResidenciales) {
			poblacion += region.getPoblacion();
		}
		return poblacion;
	}
	
	public FuenteDeRecursos getFuenteDeRecursos(Posicion unaPosicion) throws Exception {
		for (FuenteDeRecursos fuente : fuentesDeRecursos) {
			if (fuente.getPosicion().esIgualA(unaPosicion)) {
				return fuente;
			}
		}
		
		throw new SeDebeSeleccionarUnaFuenteDeRecursos();
	}
	
	public Construccion getConstruccion(Posicion unaPosicion) {
		//Recorro todas las listas en busca de la construccion
		Construccion unaConstruccion = null;
			
		for (Construccion region : regionesIndustriales) {
			if (((Region) region).ocupaPosicion(unaPosicion)) {
				unaConstruccion = region;
			}
		}
		for (Construccion region : regionesResidenciales) {
			if (((Region) region).ocupaPosicion(unaPosicion)) {
				unaConstruccion = region;
			}
		}
		for (Construccion construccion : construcciones) {
			if (construccion.getPosicion().esIgualA(unaPosicion)) {
				unaConstruccion = construccion;
			}
		}
		for (FuenteDeRecursos construccion : fuentesDeRecursos) {
			if (construccion.getPosicion().esIgualA(unaPosicion)) {
				unaConstruccion = (Construccion) construccion;
			}
		}
		
		// las tuberias deberian de poder ocupar todo el mapa sin molestar
		//if (tuberias.size() != 0) {
		//	for (Construccion tuberia : tuberias) {
		//		if (tuberia.getPosicion().esIgualA(unaPosicion)) {
		//			unaConstruccion = tuberia;
		//		}
		//	}
		//}
		
		return unaConstruccion;
	}

	/* Si hay un porcentaje de danio, la catastrofe fue un terremoto */
	public void daniar(Posicion posicion, int porcentajeDanio) {
		Construccion unaConstruccion = getConstruccion(posicion);
		if (unaConstruccion != null) {
			unaConstruccion.daniar(porcentajeDanio);
		}
	}
	
	/* Si no hay porcentaje de danio, cada edificio sabe que danio le hace Godzilla */
	public void daniar(Posicion posicion) {
		Construccion unaConstruccion = getConstruccion(posicion);
		if (unaConstruccion != null) {
			unaConstruccion.daniar();
		}
	}

	public void posicionEstaLibre(Posicion posicion) throws Exception {

		if (posicion.x() >= dimensionMapa.x()) {
			throw new Exception("La region elegida excede los limites del mapa");
		}
		if (posicion.y() >= dimensionMapa.y()) {
			throw new Exception("La region elegida excede los limites del mapa");
		}
		
		if (construcciones.size() != 0) {
			if (parcelaOcupada(posicion)) {
				throw new NoSePuedeConstruirSobreOtraConstruccion();
			}
		}
		if (regionesDeAgua.size() != 0) {
			if (regionOcupada(regionesDeAgua, posicion)) {
				throw new NoSePuedeConstruirSobreAgua();
			}
		}
		if (regionesIndustriales.size() != 0) {
			if (regionOcupada(regionesIndustriales, posicion)) {
				throw new RegionAConstruirSeEncuentraOcupada();
			}
		}
		if (regionesResidenciales.size() != 0) {
			if (regionOcupada(regionesResidenciales, posicion)) {
				throw new RegionAConstruirSeEncuentraOcupada();
			}
		}
		
	}
	
	public void posicionEstaLibre(Posicion posicion, Dimension dimension) throws Exception {
		Posicion tmpPosicion;
		
		if (posicion.x() + dimension.x() > dimensionMapa.x()) {
			throw new RegionFueraDeLosLimites();
		}
		if (posicion.y() + dimension.y() > dimensionMapa.y()) {
			throw new RegionFueraDeLosLimites();
		}
		
		for (int x = posicion.x(); x < (posicion.x() + dimension.x()); x++) {
    		for (int y = posicion.y(); y < (posicion.y() + dimension.y()); y++) {
    			tmpPosicion = new Posicion(x,y);
    			
    			if (construcciones.size() != 0) {
    				if (parcelaOcupada(tmpPosicion)) {
    					throw new NoSePuedeConstruirSobreOtraConstruccion();
    				}
    			}
    			if (regionesDeAgua.size() != 0) {
    				if (regionOcupada(regionesDeAgua, tmpPosicion)) {
    					throw new RegionAConstruirSeEncuentraOcupada();
    				}
    			}
    			if (regionesIndustriales.size() != 0) {
    				if (regionOcupada(regionesIndustriales, tmpPosicion)) {
    					throw new RegionAConstruirSeEncuentraOcupada();
    				}
    			}
    			if (regionesResidenciales.size() != 0) {
    				if (regionOcupada(regionesResidenciales, tmpPosicion)) {
    					throw new RegionAConstruirSeEncuentraOcupada();
    				}
    			}
    		}    		
		}  
		
	}

	private boolean regionOcupada(List regiones, Posicion posicion) {
		int i = 0;
		boolean encontrado = false;
		
		while ( i < regiones.size() && (!encontrado) ) {
			Region unaRegion = (Region) regiones.get(i);
			
			if ( unaRegion.ocupaPosicion(posicion) ) {
				encontrado = true;
			}
			i++;
		}
		return encontrado;
	}
	
	private boolean parcelaOcupada(Posicion posicion) {
		int i = 0;
		boolean encontrado = false;
		
		while (i < construcciones.size() && (!encontrado) ) {
			Construccion unaConstruccion = construcciones.get(i);
			if ( unaConstruccion.getPosicion().esIgualA(posicion) ) {
				encontrado = true;
			}
			i++;
		}
		return encontrado;
	}

	public Region getRegion(Posicion posicion) {	
		/*
		for (Region region : regionesDeAgua) {
			if (region.ocupaPosicion(posicion)) {
				return region;
			}
		}
		*/
		
		for (Region region : regionesIndustriales) {
			if (region.ocupaPosicion(posicion)) {
				return region;
			}
		}
		
		for (Region region : regionesResidenciales) {
			if (region.ocupaPosicion(posicion)) {
				return region;
			}
		}
		return null;
	}
	

	public List<Region> getRegionesAdyacentes(Posicion posicion) {
		List<Region> regiones = new LinkedList<Region>();
		
		for (Region region : regionesResidenciales) {
			if (region.esAdyacente(posicion)) {
				regiones.add(region);
			}
		}
		
		for (Region region : regionesIndustriales) {
			if (region.esAdyacente(posicion)) {
				regiones.add(region);
			}
		}
		
		return regiones;
	}
	
	public List<Construccion> getConstruccionesAdyacentes(Posicion posicion) {
		List<Construccion> tmpConstrucciones = new LinkedList<Construccion>();
		
		for (Construccion construccion : construcciones) {
			if (construccion.getPosicion().esAdyacente(posicion)) {
				tmpConstrucciones.add(construccion);
			}
		}
		
		return tmpConstrucciones;
	}
	
	public RegionAcuatica getRegionAcuatica(Posicion posicion) {		
		for (RegionAcuatica region : regionesDeAgua) {
			if (region.ocupaPosicion(posicion)) {
				return region;
			}
		}
		
		return null;
	}

	public Dimension getDimension() {
		return dimensionMapa;
	}

	public void agregarTuberia(Tuberia tuberia) {
		tuberias.add(tuberia);
	}
	
	public List<Tuberia> getTuberias() {
		return tuberias;
	}
	
	public void eliminarConstruccion(Construccion construccionAEliminar) throws Exception {
		construccionAEliminar.eliminar();
	}

	public void establecerConeccion(Construccion fuente, Posicion posicionConstruccion) throws Exception {
		Red nuevaRed = new RedDeRecursos(this);
		nuevaRed.conectar(fuente, getConstruccion(posicionConstruccion));
	}
	
	public boolean parcelaValida(Posicion posicionParcela) {
		if ( (posicionParcela.x()) < 0 || (posicionParcela.y()) < 0 ) {
			return false;
		}
		if ( (posicionParcela.x()) > dimensionMapa.x() || (posicionParcela.y()) > dimensionMapa.y() ) {
			return false;
		}
		return true;
	}

	public int distanciaEntreParcelas(Posicion unaPosicion, Posicion otraPosicion){
		int distX=0;
		int distY=0;
		
		if (unaPosicion.x() < otraPosicion.x()) {
			distX = unaPosicion.x()- otraPosicion.x();
		}
		else {
			distX = otraPosicion.x() - unaPosicion.x();
		}
		
		if (unaPosicion.y() < otraPosicion.y()) {
			distY = unaPosicion.y() - otraPosicion.y();
		}
		else {
			distY = otraPosicion.y() - unaPosicion.y();
		}

		int difX = (distX)*(distX);
		int difY = (distY)*(distY);
		
		int suma = difX + difY;
		int distancia = (int) Math.sqrt(suma);
		
		return distancia;
	}

	public Posicion getPosicionRandom() {
		Random rand = new Random();
		int x = rand.nextInt(dimensionMapa.x()+1);
		int y = rand.nextInt(dimensionMapa.y()+1);
		
		return new Posicion(x,y);
	}
	
	public Posicion getPosicionRandomGodzilla() {
		Random rand = new Random();
		int n = rand.nextInt(3); // Cuatro posibilidades, o aparece sobre los ejes x o sobre los ejes y
		
		if (n == 0) { // Y=0 X=0..10
			int y = 0;
			int x = rand.nextInt(dimensionMapa.x()+1);
			return new Posicion(x,y);
		}
		if (n == 1) { //Y=10 X=0..10
			int y = 0;
			int x = rand.nextInt(dimensionMapa.x()+1);
			return new Posicion(x,y);
		}
		if (n == 2) { //X=0 Y=0..10
			int y = rand.nextInt(dimensionMapa.y()+1);
			int x = 0;
			return new Posicion(x,y);
		}
		if (n == 3) { //X=10 Y=0..10
			int y = rand.nextInt(dimensionMapa.y()+1);
			int x = 10;
			return new Posicion(x,y);
		}
		
		return null;
	}
	
	private void llamarBomberos(Construccion construccionDanida) {
		for(EstacionBomberos estacion : estaciones) {
			if(distanciaEntreParcelas(estacion.getPosicion(), construccionDanida.getPosicion()) <= estacion.getRangoDeCobertura()) {
				estacion.repararConstruccion(construccionDanida);
			}
		}
	}
	
	public void repararConstrucciones() {
		Construccion construccionDaniada;
		//Repara construcciones
		for(Construccion construccion : construcciones) {
			if (construccion.getSalud() < construccion.getSaludMax()) {
				construccionDaniada = construccion;
				llamarBomberos(construccionDaniada);
			}
		}
		//Repara estaciones de bomberos
		for(Construccion construccion : estaciones) {
			if (construccion.getSalud() < construccion.getSaludMax()) {
				construccionDaniada = construccion;
				llamarBomberos(construccionDaniada);
			}
		}
		//Repara fuentes de recursos
		for(FuenteDeRecursos construccion : fuentesDeRecursos) {
			if (((Construccion) construccion).getSalud() < construccion.getSaludMax()) {
				construccionDaniada = (Construccion) construccion;
				llamarBomberos(construccionDaniada);
			}
		}
		
	}

	public void agregarEstacionBomberos(EstacionBomberos estacionBomberos) {
		estaciones.add(estacionBomberos);
	}

	public List<EstacionBomberos> getEstacionesBomberos() {
		return estaciones;
	}

	public Tuberia getTuberia(Posicion posicionParcela) {
		for (Tuberia tuberia : tuberias ) {
			if (tuberia.getPosicion().esIgualA(posicionParcela)) {
				return tuberia;
			}
		}
		return null;
	}}