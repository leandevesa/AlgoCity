package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;
import java.util.List;

public abstract class Construccion implements Construible {

	protected int costo;
	protected Posicion posicion;
	protected List<CentralElectrica> centralesOrigen;
	protected List<PozoDeAgua> pozosOrigen;
	protected int salud;	
	protected Mapa mapa;
	protected String pathImagen;
	protected Boolean activa = false;
	protected boolean tieneVistaSubterranea = false;
	protected int saludMax = 100;
	protected int porcentajeReparacion;
	protected String pathImagenMapa;
	
	public Construccion() {
		centralesOrigen = new LinkedList<CentralElectrica>();
		pozosOrigen = new LinkedList<PozoDeAgua>();
	}
	
	/* Cada construccion sabe cuanto danio le produce Godzilla */
	abstract public void daniar();
	
	abstract public int getAguaDisponible();
	
	abstract public int getElectricidadDisponible();
	
	public String getImagenSobreMapa() {
		return pathImagenMapa;
	}
	
	@Override
	public void construirEn(Mapa unMapa) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getImagen() {
		return pathImagen;
	}
	
	public void daniar(int porcentajeDanio) {
		this.salud = salud - porcentajeDanio;
		if (salud < 0) {
			salud = 0;
		}
	}
	
	public int getCosto() {
		return costo;
	}
	
	public void setFuenteDeRecursos(FuenteDeRecursos fuenteDeRecursos) {
		fuenteDeRecursos.alimentar(this);
		activar();
	}
	
	
	public Posicion getPosicion() {
		return posicion;
	}


	public void agregarPozoOrigen(PozoDeAgua pozoDeAgua) {
		if (!pozosOrigen.contains(pozoDeAgua)) {
			pozosOrigen.add(pozoDeAgua);
		}
	}
	
	public void agregarCentralOrigen(CentralElectrica centralElectrica) {
		if (!centralesOrigen.contains(centralElectrica)) {
			centralesOrigen.add(centralElectrica);
		}
	}
	
	public boolean esAdyacente(Posicion unaPosicion)  {
		return posicion.esAdyacente(unaPosicion);
	}

	public int getSalud() {
		return salud;
	}
	
	public CentralElectrica getUnaCentralConElectricidadDisponible() {
		for (CentralElectrica tmpCentral : centralesOrigen) {
			if (tmpCentral.getRecursosDisponibles() > 0) {
				return tmpCentral;
			}
		}
		return null;
	}
	
	public PozoDeAgua getUnPozoConAguaDisponible() {
		for (PozoDeAgua tmpPozo : pozosOrigen) {
			if (tmpPozo.getRecursosDisponibles() > 0) {
				return tmpPozo;
			}
		}
		return null;
	}
	
	public void setPosicion(Posicion unaPosicion) {
		posicion = unaPosicion;
	}

	public void activar() {
		this.activa = true;
	}

	public String getNombre() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean getTieneVistaSubterranea() {
		return tieneVistaSubterranea;
	}

	public void quitarPozoDeAgua(PozoDeAgua pozo) {
		if (pozosOrigen.contains(pozo)) {
			pozosOrigen.remove(pozo);
		}
	}

	public void quitarCentralElectrica(CentralElectrica centralElectrica) {
		if (centralesOrigen.contains(centralElectrica)) {
			centralesOrigen.remove(centralElectrica);
		}
	}

	public int getSaludMax() {
		return saludMax;
	}

	public void reparar() {
		salud += porcentajeReparacion;
		
		if (salud > 100) {
			salud = 100;
		}
		
	}
	
	//reparar y recibir dano deberia ser para todos los construibles? que pasa con las calles, lineas de
	//tension, etc?
}
 