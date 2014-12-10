package fiuba.algo3.algocity.modelo;

public class Partida {
	Mapa mapa;
	private int poblacion = 0;
	
	public void setMapa(Mapa unMapa) {
		mapa = unMapa;		
	}

	public void pasarTurno() throws Exception {
		int cantidadInmigrantes = 205;  //deberia ser random con cota o depender de algun factor/evento
		mapa.recibirInmigrantes(cantidadInmigrantes);
	}

}
