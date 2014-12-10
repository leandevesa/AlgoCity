package fiuba.algo3.algocity.modelo;

import fiuba.algo3.algocity.excepciones.PlataInsuficiente;

public class Jugador {
	private String nombre = "";
	private double plata = 20000;
	
	public void crearJugador(String nombre) {
		validoNuevoJugador(nombre);
		//si paso anterior -> creo
	}
	
	public Jugador buscarJugador(String nombre) {
		//busco jugador y si encuentro devuelvo objeto cargado
		
		//si no encontro -> null
		return null;
	}
	
	private void validoNuevoJugador(String nombre) {
		if (nombre.length() >= 4) {
			if (buscarJugador(nombre) != null) {
				//excepcion
			}
		} else {
			//excepcion
		}
		
		return;
	}
	
	public double getPlata() {
		return plata;
	}
	
	public void gastarPlata(double gasto) throws Exception{
		if (gasto > plata) {
			throw new PlataInsuficiente();
		}
		plata -= gasto;
	}

	public void recibirPlata(int i) {
		plata += i;
		
	}
}
