package fiuba.algo3.algocity.modelo;

public abstract class Edificio extends Construccion {
	protected int cantidadPersonas;
	protected int maxCantidadPersonas;
	
	@Override
	public int getElectricidadDisponible() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAguaDisponible() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void construirEn(Mapa unMapa) {
		//unMapa.agregarConstruccion(this);		
	}
	
	@Override
	public void eliminar() throws Exception {
		if ( !(mapa.getListaConstrucciones().contains(this)) ) {
			throw new Exception();
		}
		mapa.getListaConstrucciones().remove(this);
	}
	
	public void agregarPersonas(int personas) {
		cantidadPersonas += personas;
	}
	public void removerPersonas(int personas) {
		cantidadPersonas -= personas;
	}
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}
	public int getMaxCantidadPersonas() {
		return maxCantidadPersonas;
	}
}
