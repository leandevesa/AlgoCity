package fiuba.algo3.algocity.modelo;


public abstract class EdificioResidencial extends Edificio {
	
	@Override
	public void daniar() {
		this.salud = 0;
	}
	
}
