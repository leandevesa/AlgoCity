package fiuba.algo3.algocity.vista;

import javax.swing.JButton;

import fiuba.algo3.algocity.modelo.Posicion;

public class JButtonConCoordenadas extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Posicion posicion;

	public JButtonConCoordenadas(String unTexto, Posicion unaPosicion) {
		super(unTexto);
		posicion = unaPosicion;
	}

	public Posicion getPosicion(){
		return posicion;
	}
}
