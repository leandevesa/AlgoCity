package fiuba.algo3.algocity.controlador;
import java.awt.Cursor;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

import fiuba.algo3.algocity.excepciones.NoSePudoEstablecerUnionEnLaRed;
import fiuba.algo3.algocity.excepciones.PlataInsuficiente;
import fiuba.algo3.algocity.modelo.*;
import fiuba.algo3.algocity.vista.JuegoVista;

public class JuegoControlador {
	private static Jugador jugador;
	private static JuegoVista pantallaJuego;
	private static Mapa mapa;
	private static Construccion construccion;
	private static boolean vistaSubterranea = false;
	private static Region tmpRegion;
	private static RedDeRutas tmpRedDeRutas;
	private static FuenteDeRecursos tmpFuenteDeRecursos;
	private static Red tmpRedDeRecursos;
	private static boolean estaCreandoUnaRed = false;
	private static int anioActual;
	private static boolean sonidoActivo = true;
	private static TurnosControlador turnos;
	private static Clip sonido;
	private static LinkedList<LinkedList<Conector>> postesYRutas;
	
	public JuegoControlador() {
		try {
			construirMapa();
		} catch (Exception e) {
			//TODO: Throw new CarolosException();
		}
		jugador = new Jugador(); //Posibilidad para iniciar otra partida sin cerrar el juego
		anioActual = 2000;
		postesYRutas = new LinkedList<LinkedList<Conector>>();
	}
	
	public void iniciarSonidoAmbiente() throws Exception {
		if (sonidoActivo) {
			sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File("src/fiuba/algo3/algocity/modelo/sonido/ambiente.wav")));
			sonido.start();
			sonido.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	private void apagarSonidoAmbiente() {
		sonido.stop();
	}

	public void iniciar() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                pantallaJuego = new JuegoVista();
                pantallaJuego.refresh();
                pantallaJuego.setVisible(true);
                try {
					iniciarSonidoAmbiente();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });	
        
	}
	
	public void contarTurnos() {
		 anioActual += 1;
		 turnos = new TurnosControlador(mapa);
		 try {
			 if (jugador.getPlata() < 500) {
				 jugador.gastarPlata(jugador.getPlata());
			 } else {
				 jugador.gastarPlata(500); //pierde plata cada turno
			 }
		} catch (Exception e1) {
			
		}
		 try {
			 mapa.recibirInmigrantes(30);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 turnos.eliminarRutasYTendidosSinSalud(postesYRutas);
		 pantallaJuego.refresh();
		 if (anioActual > 2005) {
			 turnos.avanzar();
			 if (turnos.getPosicionesDaniadas() != null) {
				 mostrarCatastrofe(turnos.getPosicionesDaniadas(), turnos.getImagen(), turnos.getSonido());
			 }
			 jugador.recibirPlata(10 * mapa.getPoblacion());
		 }
	}
	
	public void setConstruccion(Construccion unaConstruccion) {
		construccion = unaConstruccion;
		pantallaJuego.setMensaje("Elija la posicion donde desea construir.");
		pantallaJuego.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	public void construirMapa() throws Exception {
		Random rand = new Random();
		//Random entre 0 y 2
		int numeroDeMapa = rand.nextInt((2 - 0) + 1) + 0;
		
		mapa = new Mapa(new Dimension(10,10));
		
		
		switch (numeroDeMapa) {
		case 0:
			mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(5,2), mapa));
			mapa.construir(new RegionAcuatica(new Posicion(8,8), new Dimension(2,2), mapa));
			break;
		case 1:
			mapa.construir(new RegionAcuatica(new Posicion(0,0), new Dimension(4,4), mapa));
			mapa.construir(new RegionAcuatica(new Posicion(0,8), new Dimension(2,2), mapa));
			break;
		case 2:
			mapa.construir(new RegionAcuatica(new Posicion(4,4), new Dimension(3,5), mapa));
			break;
		}
		
		
	}
	
	public void construir(Posicion unaPosicion) {
		if (!estaCreandoUnaRed) {			
			if (construccion != null) {
				vistaSubterranea = construccion.getTieneVistaSubterranea();				
				construccion.setPosicion(unaPosicion);
				pantallaJuego.setMensaje(" ");
				if (jugador.getPlata() >= construccion.getCosto()) {
					try {
						mapa.construir(construccion);
						jugador.gastarPlata(construccion.getCosto());
					} catch (Exception e) {
						pantallaJuego.setMensaje(e.getMessage());
						pantallaJuego.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				} else {
					pantallaJuego.setMensaje(new PlataInsuficiente().getMessage());
				}				
			}
			pantallaJuego.refresh();
			construccion = null;
			pantallaJuego.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));			
		} else {
			if (tmpRegion == null) {
				conectarFuenteDeRecursos(unaPosicion);
			} else {
				conectarRegion(unaPosicion);
			}
			pantallaJuego.refresh();
		}		
	}

	private void conectarRegion(Posicion unaPosicion) {
		if (jugador.getPlata() >= tmpRegion.getNewConector(unaPosicion).getCosto()) {
			try {
				tmpRedDeRutas.agregarConector(tmpRegion.getNewConector(unaPosicion));
				jugador.gastarPlata(tmpRegion.getNewConector(unaPosicion).getCosto());
				
				List<Region> regiones = mapa.getRegionesAdyacentes(unaPosicion);
				for (Region region : regiones) {
					if (region != tmpRegion) {
						tmpRedDeRutas.conectar(region);
						setAllRutasFuenteDeRecursos();
						tmpRegion.agregarRedConectada(tmpRedDeRutas);
					}
				}
				//pantallaJuego.setMensaje("Se alimentaron " + regiones.size() + " region/es");				
			} catch (Exception e) {
				cancelarAccion();
				pantallaJuego.setMensaje(e.getMessage());
			}				 
		} else {
			pantallaJuego.setMensaje(new PlataInsuficiente().getMessage());
		}
	}
	
	private void conectarFuenteDeRecursos(Posicion unaPosicion) {
		if (jugador.getPlata() >= tmpFuenteDeRecursos.getNewConector(unaPosicion).getCosto()) {
			try {
				tmpRedDeRecursos.agregarConector(tmpFuenteDeRecursos.getNewConector(unaPosicion));
				jugador.gastarPlata(tmpFuenteDeRecursos.getNewConector(unaPosicion).getCosto());
				
				List<Region> regiones = mapa.getRegionesAdyacentes(unaPosicion);
				for (Region region : regiones) {
					tmpRedDeRecursos.conectar(region);
					setAllRecursosFuenteDeRecursos();
					tmpFuenteDeRecursos.agregarRedConectada(tmpRedDeRecursos);
				}
				//pantallaJuego.setMensaje("Se alimentaron " + regiones.size() + " region/es");					
				List<Construccion> construcciones = mapa.getConstruccionesAdyacentes(unaPosicion);
				for (Construccion construccion : construcciones) {
					if (construccion != tmpFuenteDeRecursos) {
						tmpRedDeRecursos.conectar(construccion);
						setAllRecursosFuenteDeRecursos();
						tmpFuenteDeRecursos.agregarRedConectada(tmpRedDeRecursos);					}
				}
				//pantallaJuego.setMensaje("Se alimentaron " + construcciones.size() + " construccion/es");					
			} catch (Exception e) {
				cancelarAccion();
				pantallaJuego.setMensaje(e.getMessage());
			}				
		} else {
			pantallaJuego.setMensaje(new PlataInsuficiente().getMessage());
		}
	}
	
	private void setAllRecursosFuenteDeRecursos() {
		List<Conector> conectores = tmpRedDeRecursos.getConectores();
		for (Conector conector : conectores) {
			conector.setRed(tmpRedDeRecursos);
		}
		postesYRutas.add(tmpRedDeRecursos.getConectores());
	}
	
	private void setAllRutasFuenteDeRecursos() {
		List<Conector> conectores = tmpRedDeRutas.getConectores();
		for (Conector conector : conectores) {
			conector.setRed(tmpRedDeRutas);
		}
		postesYRutas.add(tmpRedDeRutas.getConectores());
	}
	
	public Mapa getMapa() {
		return mapa;
	}
	
	public double getPlata() {
		return jugador.getPlata();
	}
	
	public double getPoblacion() {
		return mapa.getPoblacion();
	}

	public int getAnio() {
		return anioActual;
	}
	
	public void setVistaSubterranea(boolean vista) {
		vistaSubterranea = vista;
	}

	public void eliminarConstruccion(Posicion posicionParcela) {
		try {
			Construccion construccionABorrar;
			
			if (vistaSubterranea) {
				construccionABorrar = mapa.getTuberia(posicionParcela);
			} else {
				construccionABorrar = mapa.getConstruccion(posicionParcela);
			}
			
			construccionABorrar.eliminar();
		} catch (Exception e) {
			pantallaJuego.setMensaje(e.getMessage());
		}		
		pantallaJuego.refresh();		
	}

	public List<Construccion> getConstrucciones() {
		return mapa.getListaConstrucciones();
	}

	public void conectar(Construccion unaConstruccion, Posicion posicionDeOtraConstruccionAConectar) throws Exception {
		try {
			mapa.establecerConeccion(unaConstruccion, posicionDeOtraConstruccionAConectar);
		} catch (NoSePudoEstablecerUnionEnLaRed e) {
			pantallaJuego.setMensaje("No hay conectores para establecer la conexion.");
		}		
	}

	public Construccion getConstruccion(Posicion posicionParcela) {
		return mapa.getConstruccion(posicionParcela);
	}

	public void agregarConexion(Posicion posicionParcela) {
		try {
			tmpRegion = mapa.getRegion(posicionParcela);
			estaCreandoUnaRed = true;
			if (tmpRegion == null) {
				tmpRedDeRecursos = new RedDeRecursos(mapa);
				tmpFuenteDeRecursos = mapa.getFuenteDeRecursos(posicionParcela);
				tmpRedDeRecursos.setFuente(tmpFuenteDeRecursos);
				vistaSubterranea = tmpFuenteDeRecursos.getNewConector(new Posicion(0,0)).getTieneVistaSubterranea();
			} else {
				tmpRedDeRutas = new RedDeRutas(mapa);
				tmpRedDeRutas.setFuente(tmpRegion);
				vistaSubterranea = tmpRegion.getNewConector(new Posicion(0,0)).getTieneVistaSubterranea();
			}
			pantallaJuego.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			pantallaJuego.refresh();
		} catch (Exception e) {
			cancelarAccion();
			pantallaJuego.setMensaje(e.getMessage());
		}
	}
	
	public boolean getVistaSubterranea() {
		return vistaSubterranea;
	}


	public void cancelarAccion() {
		pantallaJuego.setMensaje("");
		construccion = null;
		if (estaCreandoUnaRed) {
			if (tmpRedDeRecursos != null) {
				if (!tmpRedDeRecursos.tieneConexiones()) {
					tmpRedDeRecursos.eliminar();
				}
			} else {
				if (!tmpRedDeRutas.tieneConexiones()) {
					tmpRedDeRutas.eliminar();
				}
			}
			estaCreandoUnaRed = false;
			tmpRedDeRecursos = null;
			tmpRedDeRutas = null;
			tmpRegion = null;
		}
		pantallaJuego.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		pantallaJuego.refresh();
	}
	
	public void mostrarCatastrofe(List posicionesDaniadas, String imagen, String sonido) {
		pantallaJuego.vibrarPantalla();
		pantallaJuego.mostrarCatastrofe(posicionesDaniadas, imagen);
		pantallaJuego.setMensaje("Una catastrofe ha impactado tu ciudad!");
		if (sonidoActivo) {
			reproducirSonidoCatastrofe(sonido);
		}
	}
	
	private void reproducirSonidoCatastrofe(String sonido) {
		if (sonidoActivo) {
			try {
				Clip catastrofe = AudioSystem.getClip();
				catastrofe.open(AudioSystem.getAudioInputStream(new File(sonido)));
				catastrofe.start();

			} catch (Exception e1) {
				//
			}
		}
	}

	public void setEstadoSonido(boolean valor) {
		sonidoActivo = valor;
		
		if (valor == true) {
			try {
				iniciarSonidoAmbiente();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (valor == false) {
			apagarSonidoAmbiente();
		}
		
	}

	public boolean getEstadoSonido() {
		return sonidoActivo;
	}
	
}
