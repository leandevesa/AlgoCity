package fiuba.algo3.algocity.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fiuba.algo3.algocity.controlador.JuegoControlador;

public class MenuVista {
	
	public MenuVista(JMenuBar barra, final JuegoControlador controlador, final JuegoVista juegoVista) {
		//Archivo
		JMenu menuArchivo = new JMenu("Archivo");
        menuArchivo.setMnemonic('A');
        
        JMenuItem elementoNuevo = new JMenuItem("Nueva partida");
        elementoNuevo.setMnemonic('p');
        elementoNuevo.addActionListener(
            	new ActionListener() {
            			public void actionPerformed( ActionEvent evento ) {
            				new JuegoControlador();
            				new JuegoVista();
            				juegoVista.refresh();
            				juegoVista.setVisible(true);
            				juegoVista.setMensaje("Bienvenido a AlgoCity");
            			}
                    }
            	);
        menuArchivo.add(elementoNuevo);
        
        JMenuItem elementoAcerca = new JMenuItem("Acerca de...");
        elementoAcerca.setMnemonic('c');
        elementoAcerca.addActionListener(
        		new ActionListener() {
                   public void actionPerformed( ActionEvent evento ) {
                	  ImageIcon imagen = new ImageIcon("src/fiuba/algo3/algocity/modelo/imagenes/acerca.png");
                	  JOptionPane.showMessageDialog(
                			   null,
                			   "AlgoCity Algoritmos III | FIUBA\n\nIntegrantes:\nDesuque Leandro\nDevesa Leandro\n", 
                			   "Acerca de AlgoCity",
                			   JOptionPane.OK_OPTION,
                			   imagen);		   
                   }
                }
        );
        menuArchivo.add(elementoAcerca);
        
        
        JMenuItem elementoSalir = new JMenuItem("Salir");
        elementoSalir.setMnemonic('S');
        elementoSalir.addActionListener(
        	new ActionListener() {
        			public void actionPerformed( ActionEvent evento ) {
        				System.exit( 0 );
        			}
                }
        	);
        menuArchivo.add(elementoSalir);
        
        //Ver
        JMenu menuVer = new JMenu("Ver");
        menuVer.setMnemonic('V');
        final JMenuItem construcciones = new JMenuItem("Mapa de construcciones");
        final JMenuItem tuberias = new JMenuItem("Mapa subterraneo");
        
        construcciones.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		controlador.setVistaSubterranea(false);
     			juegoVista.refresh();
     	    }
     	});
        
        tuberias.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		controlador.setVistaSubterranea(true);
     			juegoVista.refresh();
     	    }
     	});
        
        construcciones.setMnemonic('p');
        menuVer.add(construcciones);
        tuberias.setMnemonic('t');
        menuVer.add(tuberias);
        
        //Sonido
      	JMenu menuSonido = new JMenu("Sonido");
        menuSonido.setMnemonic('n');
        
        final JMenuItem elementoActivar = new JMenuItem("Activar");
        final JMenuItem elementoDesactivar = new JMenuItem("Desactivar");
        
        elementoActivar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		controlador.setEstadoSonido(true);
        		elementoActivar.setEnabled(false);
        		elementoDesactivar.setEnabled(true);
     	    }
     	});
        
        elementoDesactivar.addActionListener(new ActionListener() {
        	@Override
     		public void actionPerformed(ActionEvent e) {
        		controlador.setEstadoSonido(false);
        		elementoActivar.setEnabled(true);
        		elementoDesactivar.setEnabled(false);
     	    }
     	});
        
        elementoActivar.setEnabled(false); //La musica por defecto viene activada
        
        menuSonido.add(elementoActivar);
        menuSonido.add(elementoDesactivar);
        
        barra.add(menuArchivo);
        barra.add(menuVer);
        barra.add(menuSonido);
	}
	
}
