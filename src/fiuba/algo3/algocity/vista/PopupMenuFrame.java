package fiuba.algo3.algocity.vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import fiuba.algo3.algocity.controlador.JuegoControlador;
import fiuba.algo3.algocity.modelo.Construccion;
import fiuba.algo3.algocity.modelo.Posicion;

public class PopupMenuFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static private JPopupMenu popup;
	JuegoControlador controlador = new JuegoControlador();
	
	
	
	public void mostrarMenu(MouseEvent e, final Posicion posicionParcela) {
		setBounds(100, 100, 200, 200); // Ubicacion
		popup = new JPopupMenu();
		

		//Elementos del menu
		JMenu info = new JMenu("Informacion");
		Construccion unaConstruccion = controlador.getConstruccion(posicionParcela);
		
		if (unaConstruccion == null) {
			info.add(new JMenuItem("Costo: -"));
			info.add(new JMenuItem("Posicion: (" + (posicionParcela.x()+1) + "," + (posicionParcela.y()+1) + ")"));
			info.add(new JMenuItem("Salud: -"));
			info.add(new JMenuItem("Electricidad disponible: -"));
			info.add(new JMenuItem("Agua disponible: -"));
		}
		else {
			int posX = unaConstruccion.getPosicion().x();
			int posY = unaConstruccion.getPosicion().y();
			
			info.add(new JMenuItem("Costo: " + unaConstruccion.getCosto()));
			info.add(new JMenuItem("Posicion: (" + (posX+1) + "," + (posY+1) + ")"));
			info.add(new JMenuItem("Salud: " + unaConstruccion.getSalud()));
			info.add(new JMenuItem("Electricidad disponible: " + unaConstruccion.getElectricidadDisponible()));
			info.add(new JMenuItem("Agua disponible: " + unaConstruccion.getAguaDisponible()));
		}
		popup.add(info);	
		
		JMenuItem eliminarConstruccion = new JMenuItem("Eliminar construccion");
		eliminarConstruccion.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			controlador.eliminarConstruccion(posicionParcela);
    	    }
    	});
		popup.add(eliminarConstruccion);
		
		
		/*
		JMenu conectarConstruccion = new JMenu("Conectar con...");
		//Obtengo las construcciones que brindan algun recurso
		List<Construccion> construcciones = controlador.getConstrucciones();
		
		for (final Construccion construccion : construcciones) {
			int posXMapa = construccion.getPosicion().x() + 1;
			int posYMapa = construccion.getPosicion().y() + 1;
			JMenuItem construccionMenu = new JMenuItem(construccion.getNombre() + " (" + posXMapa + "," + posYMapa + ")");
			construccionMenu.addActionListener(new ActionListener() {
	    		@Override
	    		public void actionPerformed(ActionEvent e) {
	    			try {
						controlador.conectar(construccion, posicionParcela);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    	    }
	    	});
			conectarConstruccion.add(construccionMenu);
		}
		popup.add(conectarConstruccion);
		*/
		
		

		// prueba conectores

		JMenuItem agregarConexion = new JMenuItem("Agregar conexion | Costo $50");
		agregarConexion.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			controlador.agregarConexion(posicionParcela);
    	    }
    	});
		popup.add(agregarConexion);
		

		// prueba conectores

		JMenuItem pasarTurno = new JMenuItem("Pasar turno!");
		pasarTurno.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			try {
					controlador.contarTurnos();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	    }
    	});
		popup.add(pasarTurno);
		
		// Mostrar en ubicacion del mouse
		popup.show(e.getComponent(),
        	e.getX(), e.getY());
	}
}
