package fiuba.algo3.algocity.modelo;


public interface FuenteDeRecursos {
	void alimentar(Construccion unConstruible);
	Posicion getPosicion();
	Conector getNewConector(Posicion unaPosicion);
	int getRecursosDisponibles();
	void dejarDeConsumir(int cantidadPersonasADejarDeConsumir);
	int getSaludMax();
	void agregarRedConectada(Red tmpRedDeRecursos);
	void eliminarConstruccionQueAlimenta(Construccion unaConstruccion);
}
