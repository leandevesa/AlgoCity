package fiuba.algo3.algocity.modelo;
import java.util.LinkedList;

import fiuba.algo3.algocity.excepciones.NoSePudoEstablecerUnionEnLaRed;


public abstract class Red {
	protected FuenteDeRecursos fuente;
	protected Region regionFuente;
	protected LinkedList<Conector> red;
	protected Mapa mapa;
	protected LinkedList<Construccion> conexiones;


	
	public Red() {
		
	}
	
	private void validarNuevoConector(Conector unConector) throws Exception {
		if (red.size() != 0) {
			if (!unConector.getPosicion().esAdyacente(red.getLast().getPosicion())) {
				throw new NoSePudoEstablecerUnionEnLaRed("Los conectores se deben de crear uno al lado del otro");
			}
		} else {
			if (fuente != null) {
				if (!unConector.getPosicion().esAdyacente(fuente.getPosicion())) {
					throw new NoSePudoEstablecerUnionEnLaRed("Se debe de crear al lado de donde empieza la conexión");
				}
			} else {
				if (!regionFuente.esAdyacente(unConector.getPosicion())) {
					throw new NoSePudoEstablecerUnionEnLaRed("Se debe de crear al lado de donde empieza la conexión");
				}
			}
		}
	}
	
	public void agregarConector(Conector unConector) throws Exception {
		validarNuevoConector(unConector);
		red.add(unConector);
		mapa.construir(unConector);
	}

	public int getLongitudDeRed() {
		return red.size();
	}
	
	public boolean tieneConexiones() {
		return (conexiones.size() > 0);
	}
	
	public void eliminar() {
		for (Conector unConector : red) {
			try {
				mapa.eliminarConstruccion(unConector);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	

	public LinkedList<Conector> getConectores() {
		return red;
	}
	
	public void setFuente(FuenteDeRecursos tmpFuenteDeRecursos) {
		fuente = tmpFuenteDeRecursos;
	}
	
	public void setFuente(Region unaRegion) {
		regionFuente = unaRegion;
	}
	
	public FuenteDeRecursos getFuenteDeRecursos() {
		return fuente;
	}
	
	public Region getRegionFuente() {
		return regionFuente;
	}
	
	public LinkedList<Construccion> getConexiones() {
		return conexiones;
	}
		
	public void conectar(Construccion unaFuente, Construccion unaConstruccion) throws NoSePudoEstablecerUnionEnLaRed {

	}
	public void conectar(Construccion unaConstruccion) throws NoSePudoEstablecerUnionEnLaRed {

	}
	
}