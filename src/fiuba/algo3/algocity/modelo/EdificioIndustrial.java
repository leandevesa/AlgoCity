package fiuba.algo3.algocity.modelo;


public abstract class EdificioIndustrial extends Edificio {
	
	@Override
	public void daniar() {
		this.salud = salud - 40;
		if (salud < 0) {
			salud = 0;
		}
	}

	public int getTrabajoRestante() {
		return maxCantidadPersonas - cantidadPersonas;
	}
	
}
