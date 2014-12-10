package fiuba.algo3.algocity.vista;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import fiuba.algo3.algocity.controlador.JuegoControlador;
import fiuba.algo3.algocity.modelo.Dimension;
import fiuba.algo3.algocity.modelo.Posicion;

public class MapaVista extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JuegoControlador controlador = new JuegoControlador();
    JButton[][] grid; //nombra la grilla de botones
    static PopupMenuFrame popupMenu = new PopupMenuFrame();

    private Dimension dimension = new Dimension(10,10);

    public MapaVista() { //constructor
            setLayout(new GridLayout(dimension.y(),dimension.x())); //crea el diseño
            setSize(500,500);
            grid = new JButton[dimension.y()][dimension.x()]; //determina el tamaño de la grilla
            for(int y = 0; y < dimension.y(); y++){
                    for(int x = 0; x < dimension.x(); x++){                   	
                        grid[x][y] = new JButtonConCoordenadas("", new Posicion(x,y)); //crea un botón nuevo
                        grid[x][y].addActionListener(new ActionListener() {
                    		@Override
                    		public void actionPerformed(ActionEvent e) {
                    			JButtonConCoordenadas boton = (JButtonConCoordenadas) e.getSource();
                    			controlador.construir(boton.getPosicion());
                    	    }
                    	});
                        
                        grid[x][y].addMouseListener(new java.awt.event.MouseAdapter() {
                        	
                    		public void mouseClicked(java.awt.event.MouseEvent e) {
	                        	if( e.getButton() == java.awt.event.MouseEvent.BUTTON3 ) {
	                        		JButtonConCoordenadas boton = (JButtonConCoordenadas) e.getSource();
	                        		popupMenu.mostrarMenu(e, boton.getPosicion());
	                        	}
                    		}
                    		
                    		/*
                    		public void mouseEntered(java.awt.event.MouseEvent e) {
                    			((JComponent) e.getSource()).setBorder(new LineBorder(Color.BLACK, 2));
                    		}
                    		
                    		public void mouseExited(java.awt.event.MouseEvent e) {
                    			((JComponent) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
                    		}
                    		*/
                    		
                    	});
                        
                       
                        add(grid[x][y]); //añade el botón a a grilla
                    }
            }
    }

    public Dimension getDimension() {
    	return dimension;
    }
    
    public JButton[][] getBotones() {
    	return grid;
    }
    
    public JPanel getMapa() {
    	return this;
    }

}
