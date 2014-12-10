package fiuba.algo3.algocity.vista;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import fiuba.algo3.algocity.modelo.CentralEolica;
import fiuba.algo3.algocity.modelo.CentralMineral;
import fiuba.algo3.algocity.modelo.CentralNuclear;
import fiuba.algo3.algocity.modelo.Construccion;
import fiuba.algo3.algocity.modelo.EstacionBomberos;
import fiuba.algo3.algocity.modelo.Posicion;
import fiuba.algo3.algocity.modelo.PozoDeAgua;
import fiuba.algo3.algocity.modelo.Region;
import fiuba.algo3.algocity.modelo.RegionAcuatica;
import fiuba.algo3.algocity.modelo.RegionIndustrial;
import fiuba.algo3.algocity.modelo.RegionIndustrialAlta;
import fiuba.algo3.algocity.modelo.RegionIndustrialBaja;
import fiuba.algo3.algocity.modelo.RegionIndustrialMedia;
import fiuba.algo3.algocity.modelo.RegionResidencial;
import fiuba.algo3.algocity.modelo.RegionResidencialAlta;
import fiuba.algo3.algocity.modelo.RegionResidencialBaja;
import fiuba.algo3.algocity.modelo.RegionResidencialMedia;
import fiuba.algo3.algocity.modelo.Tuberia;
import fiuba.algo3.algocity.controlador.JuegoControlador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
 

public class JuegoVista extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JuegoControlador controlador = new JuegoControlador();
    JButton[][] grid; //nombra la grilla de botones
    JLabel statusbar = new JLabel("Bienvenido a AlgoCity");
    JLabel progressbar = new JLabel("Plata: " + controlador.getPlata() + " | " + "Poblacion: " + controlador.getPoblacion() + " | " + "Año actual: " + controlador.getAnio());
    MapaVista mapa;
    
    private int devuelvoInputValido(String unString) {
    	int x = 0;
		while (x < 1) {
			try {
				String dato = JOptionPane.showInputDialog(unString  ,"1"); 
				if (dato == null) {
					break;
				}
				x = Integer.parseInt(dato);
			}
			catch (Exception e1) {
				x= 0;
			}
		}
		return x;
    }
    
    public void vibrarPantalla() {
    	ShakingFrame s = new ShakingFrame(this);
        s.startShake();
    }
    
    public JuegoVista() {
    	mapa = new MapaVista();
    	add(mapa.getMapa(), BorderLayout.CENTER);
    	

        // al apretar esc, cancelar la accion actual
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                controlador.cancelarAccion();
            }
        });
        
    	JMenuBar barra = new JMenuBar();   
    	new MenuVista(barra, controlador, this);
    	setJMenuBar(barra); 
    	      
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        add(toolbar, BorderLayout.NORTH);
  
        JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
        vertical.setFloatable(false);
        vertical.setMargin(new Insets(10, 5, 5, 5));
        
        JButton botonDeToolbar;
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/casa1.png", "Region residencial (baja densidad) | Costo: $70 por hectarea | Capacidad de personas: 50");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
        		int x = devuelvoInputValido("Elija el largo de la region:");
        		int y = devuelvoInputValido("Elija el alto de la region:");
     			controlador.setConstruccion(new RegionResidencialBaja(new Posicion(0,0), new fiuba.algo3.algocity.modelo.Dimension(x,y), controlador.getMapa()));
        	}
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/casa2.png", "Region residencial (media densidad) | Costo: $220 por hectarea | Capacidad de personas: 100");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
        		int x = devuelvoInputValido("Elija el largo de la region:");
        		int y = devuelvoInputValido("Elija el alto de la region:");
     			controlador.setConstruccion(new RegionResidencialMedia(new Posicion(0,0), new fiuba.algo3.algocity.modelo.Dimension(x,y), controlador.getMapa()));
        	}
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/casa3.png", "Region residencial (alta densidad) | Costo: $400 por hectarea | Capacidad de personas: 150");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
        		int x = devuelvoInputValido("Elija el largo de la region:");
        		int y = devuelvoInputValido("Elija el alto de la region:");
     			controlador.setConstruccion(new RegionResidencialAlta(new Posicion(0,0), new fiuba.algo3.algocity.modelo.Dimension(x,y), controlador.getMapa()));
        	}
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/fabrica1.png", "Region industrial (baja densidad) | Costo: $100 por hectarea | Capacidad de personas: 50");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
        		int x = devuelvoInputValido("Elija el largo de la region:");
        		int y = devuelvoInputValido("Elija el alto de la region:");
     			controlador.setConstruccion(new RegionIndustrialBaja(new Posicion(0,0), new fiuba.algo3.algocity.modelo.Dimension(x,y), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/fabrica2.png", "Region industrial (media densidad) | Costo: $300 por hectarea | Capacidad de personas: 100");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
        		int x = devuelvoInputValido("Elija el largo de la region:");
        		int y = devuelvoInputValido("Elija el alto de la region:");
     			controlador.setConstruccion(new RegionIndustrialMedia(new Posicion(0,0), new fiuba.algo3.algocity.modelo.Dimension(x,y), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/fabrica3.png", "Region industrial (alta densidad) | Costo: $500 por hectarea | Capacidad de personas: 150");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
        		int x = devuelvoInputValido("Elija el largo de la region:");
        		int y = devuelvoInputValido("Elija el alto de la region:");
     			controlador.setConstruccion(new RegionIndustrialAlta(new Posicion(0,0), new fiuba.algo3.algocity.modelo.Dimension(x,y), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/centralEolica.png", "Central Eolica | Costo $3000 | Electricidad para 100 personas");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
     			controlador.setConstruccion(new CentralEolica(new Posicion(0,0), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/centralMineral.png", "Central Mineral | Costo $5000 | Electricidad para 400 personas");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
     			controlador.setConstruccion(new CentralMineral(new Posicion(0,0), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/centralNuclear.png", "Central Nuclear | Costo $10000 | Electricidad para 1000 personas");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
     			controlador.setConstruccion(new CentralNuclear(new Posicion(0,0), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/pozoDeAgua.png", "Pozo de agua | Costo $1500 | Agua para 250 personas");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refresh();
     			controlador.setConstruccion(new PozoDeAgua(new Posicion(0,0), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/estacion.png", "Estacion de bomberos | Costo $3500 | Arregla construcciones ubicadas a 4 hectareas a la redonda");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		controlador.setVistaSubterranea(false);
        		refresh();
     			controlador.setConstruccion(new EstacionBomberos(new Posicion(0,0), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        /*
        
        //Tuberia
        botonDeToolbar = creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/tuberia.png", "Tuberia");
        botonDeToolbar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		refreshTierra();
     			controlador.setConstruccion(new Tuberia(new Posicion(0,0), controlador.getMapa()));
     	    }
     	});
        vertical.add(botonDeToolbar);
        
        //Sin implementar
        vertical.add(creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/ruta.png", "Ruta"));
        vertical.add(creoIconoPanelDeControl("src/fiuba/algo3/algocity/modelo/imagenes/tendidoElectrico.png", "Tendido Electrico"));

*/
        add(vertical, BorderLayout.WEST);
          
        statusbar.setPreferredSize(new Dimension(-1, 22));
        statusbar.setBorder(LineBorder.createGrayLineBorder());
        add(statusbar, BorderLayout.SOUTH);
        
        progressbar.setPreferredSize(new Dimension(-1, 22));
        progressbar.setBorder(LineBorder.createGrayLineBorder());
        add(progressbar, BorderLayout.NORTH);
          
        setSize(700, 600);
        setResizable(false);
        setTitle("AlgoCity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
 
    private JButton creoIconoPanelDeControl(String nombreImagen, String descripcionIcono) {
	    ImageIcon unaImagen = new ImageIcon(nombreImagen);
	    JButton unBoton = new JButton(unaImagen);
	   
	    unBoton.setBorder(new EmptyBorder(3, 0, 3, 0));
	    unBoton.setToolTipText(descripcionIcono);
	    
	    return unBoton;
    }

    public void refreshProgressbar() {
    	progressbar.setText(("Plata: " + controlador.getPlata() + " | " + "Poblacion: " + controlador.getPoblacion() + " | " + "Año actual: " + controlador.getAnio()));
    }
    
    public void refresh() {
    	refreshProgressbar();
    	if (controlador.getVistaSubterranea()) {
    		refreshVistaSubterranea();
    	} else {
    		refreshVista();
    	}
    	
    }
    
    private void refreshVista() {
    	JButton[][] botones = mapa.getBotones();
    	ImageIcon unaImagen;
    	int edificiosDesarrollados = 0;
    	//lleno el mapa con pasto
    	for (int x = 0; x < mapa.getDimension().x(); x++) {
        	for (int y = 0; y < mapa.getDimension().y(); y++) {        		
        		unaImagen = new ImageIcon("src/fiuba/algo3/algocity/modelo/imagenes/pasto.png");
        		botones[x][y].setIcon(unaImagen);
        	}
    	}
    	//pongo las regiones en el mapaGrafico
    	java.util.List<RegionAcuatica> regionesDeAgua = controlador.getMapa().getRegionesDeAgua();
    	for (RegionAcuatica tmpRegion : regionesDeAgua) {
    		for (int x = tmpRegion.getPosicion().x(); x < (tmpRegion.getPosicion().x() + tmpRegion.getDimension().x()); x++) {
        		for (int y = tmpRegion.getPosicion().y(); y < (tmpRegion.getPosicion().y() + tmpRegion.getDimension().y()); y++) {
        			unaImagen = new ImageIcon(tmpRegion.getImagen());
        			botones[x][y].setIcon(unaImagen);
        		}    		
    		}    	
    	}
    	
    	java.util.List<RegionResidencial> regionesResidenciales = controlador.getMapa().getRegionesResidenciales();
    	for (RegionResidencial tmpRegion : regionesResidenciales) {
    		edificiosDesarrollados = tmpRegion.getListaEdificiosDesarrollados().size();
        	for (int y = tmpRegion.getPosicion().y(); y < (tmpRegion.getPosicion().y() + tmpRegion.getDimension().y()); y++) {
        		for (int x = tmpRegion.getPosicion().x(); x < (tmpRegion.getPosicion().x() + tmpRegion.getDimension().x()); x++) {
        			if (edificiosDesarrollados > 0) {
        				unaImagen = new ImageIcon(tmpRegion.getImagenEdificio());
        				edificiosDesarrollados -= 1;
        			} else {
        				unaImagen = new ImageIcon(tmpRegion.getImagen());
        			}
        			botones[x][y].setIcon(unaImagen);
        		}
    		}
    	}
    	
    	java.util.List<RegionIndustrial> regionesIndustriales = controlador.getMapa().getRegionesIndustriales();
    	for (RegionIndustrial tmpRegion : regionesIndustriales) {
    		edificiosDesarrollados = tmpRegion.getListaEdificiosDesarrollados().size();
    		for (int y = tmpRegion.getPosicion().y(); y < (tmpRegion.getPosicion().y() + tmpRegion.getDimension().y()); y++) {
    			for (int x = tmpRegion.getPosicion().x(); x < (tmpRegion.getPosicion().x() + tmpRegion.getDimension().x()); x++) {
        			if (edificiosDesarrollados > 0) {
        				unaImagen = new ImageIcon(tmpRegion.getImagenEdificio());
        				edificiosDesarrollados -= 1;
        			} else {
        				unaImagen = new ImageIcon(tmpRegion.getImagen());
        			}
        			botones[x][y].setIcon(unaImagen);
        		}    		
    		}    	
    	}
    	
    	//pongo los edificios
    	java.util.List<Construccion> edificios = controlador.getMapa().getListaConstrucciones();
    	for (Construccion tmpConstruccion : edificios) {
    		unaImagen = new ImageIcon(tmpConstruccion.getImagenSobreMapa());
    		botones[tmpConstruccion.getPosicion().x()][tmpConstruccion.getPosicion().y()].setIcon(unaImagen);
    	}
    	
    	//pongo las estaciones
    	java.util.List<EstacionBomberos> estaciones = controlador.getMapa().getEstacionesBomberos();
    	for (EstacionBomberos tmpEstacion : estaciones) {
    		unaImagen = new ImageIcon(tmpEstacion.getImagenSobreMapa());
    		botones[tmpEstacion.getPosicion().x()][tmpEstacion.getPosicion().y()].setIcon(unaImagen);
    	}	
    }
    
    private void refreshVistaSubterranea() {
    	JButton[][] botones = mapa.getBotones();
    	ImageIcon unaImagen;
    	List<Posicion> posicionesDeAgua = new LinkedList<Posicion>();
    	//Lleno el mapa con tierra de las profundidades de Melee Island
    	for (int x = 0; x < mapa.getDimension().x(); x++) {
        	for (int y = 0; y < mapa.getDimension().y(); y++) {        		
        		unaImagen = new ImageIcon("src/fiuba/algo3/algocity/modelo/imagenes/tierra.png");
        		botones[x][y].setIcon(unaImagen);
        	}
    	}
    	
    	//pongo el agua
    	java.util.List<RegionAcuatica> regionesDeAgua = controlador.getMapa().getRegionesDeAgua();
    	for (Region tmpRegion : regionesDeAgua) {
    		for (int x = tmpRegion.getPosicion().x(); x < (tmpRegion.getPosicion().x() + tmpRegion.getDimension().x()); x++) {
        		for (int y = tmpRegion.getPosicion().y(); y < (tmpRegion.getPosicion().y() + tmpRegion.getDimension().y()); y++) {
        			unaImagen = new ImageIcon("src/fiuba/algo3/algocity/modelo/imagenes/tierraMojada.png");
        			botones[x][y].setIcon(unaImagen);
        			posicionesDeAgua.add(new Posicion(x,y));
        		}    		
    		}    	
    	}
    	
    	//pongo las regiones residenciales
    	java.util.List<RegionResidencial> regionesResidenciales = controlador.getMapa().getRegionesResidenciales();
    	for (Region tmpRegion : regionesResidenciales) {
    		for (int x = tmpRegion.getPosicion().x(); x < (tmpRegion.getPosicion().x() + tmpRegion.getDimension().x()); x++) {
        		for (int y = tmpRegion.getPosicion().y(); y < (tmpRegion.getPosicion().y() + tmpRegion.getDimension().y()); y++) {
        			unaImagen = new ImageIcon("src/fiuba/algo3/algocity/modelo/imagenes/tierraDeResidencial.png");
        			botones[x][y].setIcon(unaImagen);
        			posicionesDeAgua.add(new Posicion(x,y));
        		}    		
    		}    	
    	}
    	
    	//pongo las regiones industriales
    	java.util.List<RegionIndustrial> regionesIndustriales = controlador.getMapa().getRegionesIndustriales();
    	for (Region tmpRegion : regionesIndustriales) {
    		for (int x = tmpRegion.getPosicion().x(); x < (tmpRegion.getPosicion().x() + tmpRegion.getDimension().x()); x++) {
        		for (int y = tmpRegion.getPosicion().y(); y < (tmpRegion.getPosicion().y() + tmpRegion.getDimension().y()); y++) {
        			unaImagen = new ImageIcon("src/fiuba/algo3/algocity/modelo/imagenes/tierraDeIndustrial.png");
        			botones[x][y].setIcon(unaImagen);
        			posicionesDeAgua.add(new Posicion(x,y));
        		}    		
    		}    	
    	}
    	
    	//pongo los tubos
    	java.util.List<Tuberia> tuberias = controlador.getMapa().getTuberias();
    	for (Tuberia tuberia : tuberias) {
    		unaImagen = new ImageIcon(tuberia.getImagen());
    		botones[tuberia.getPosicion().x()][tuberia.getPosicion().y()].setIcon(unaImagen);
    		
    		//pongo los tubos sobre el agua
    		for (Posicion posicion : posicionesDeAgua) {
    			if (tuberia.getPosicion().esIgualA(posicion)) {
    				unaImagen = new ImageIcon(tuberia.getImagenSobreAgua());
        			botones[tuberia.getPosicion().x()][tuberia.getPosicion().y()].setIcon(unaImagen);
    			}
    		}
    		
    	}
    	
    }
    
    public void setMensaje(String text) {
    	statusbar.setText(text);
    }

	public void mostrarCatastrofe(List<Posicion> posicionesDaniadas, String imagen) {
		JButton[][] botones = mapa.getBotones();
    	ImageIcon unaImagen;
    	
		for(Posicion posicion : posicionesDaniadas) {
			unaImagen = new ImageIcon(imagen);
			botones[posicion.x()][posicion.y()].setIcon(unaImagen);
		}
	}
    
}