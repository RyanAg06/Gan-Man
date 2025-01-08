package Codigo;

// Imports
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelJugar extends JPanel
{
	// Imagenes e Instancias
	private final ImageIcon imgMuro = new ImageIcon("./src/main/java/Imagenes/muro.png");
	private final ImageIcon imgComida = new ImageIcon("./src/main/java/Imagenes/comida.png");
	private final ImageIcon imgPulpo = new ImageIcon("./src/main/java/Imagenes/pulpo.png");
	private final ImageIcon imgCoctel = new ImageIcon("./src/main/java/Imagenes/coctel.png");
	private  final Laberintos partida = new Laberintos();
	private KeyAdapter controles;
	private GanMan jugador1;
	private final ArrayList<Fantasmas> fantasmasList = new ArrayList<>();

	// Variables
	private int mapa[][];
	private int nivel = 1, tamano, puntos = 0, comida = 0, centrarX = 0, centrarY = 0;
	private boolean verCamino = false;

	// Constructor
	public PanelJugar()
	{
		// Propiedades
		setBackground(Color.decode("#000000"));
		setLayout(null);
		setFocusable(true);
		
		// Componentes
		controlesPanel();
	}

	// Main
	private void jugando()
	{
		// Bucle Principal del Juego
		new Timer().scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{	
				// Verifico si Jugador1 Comio
				switch(mapa[jugador1.getY()][jugador1.getX()])
				{
					case 2 ->				// Si es Comida Normal
					{
						puntos += 100;
						comida--;
						break;
					}
					case 3 ->				// Si es un Pulpo
					{
						puntos += 500;
						comida--;
						break;
					}
					case 4 ->				// SI es Un Coctel
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.modoHuir();
						}
						puntos += 250;
						comida--;
						break;
					}
				}
				mapa[jugador1.getY()][jugador1.getX()] = 0;	// Vacio Posicion Actual
				
				// Verifico si Comio Todo
				if(comida <= 0)
				{
					avanzarNivel();
				}
				
				// Fantasmas
				for(Fantasmas fantasma : fantasmasList)
				{
					// Asigno Meta
					fantasma.setMeta(jugador1.getX(), jugador1.getY());
					
					// Asigno Comida
					fantasma.setComida(comida);
					
					// Verifico si Toco Gan
					if(fantasma.getX() == jugador1.getX() && fantasma.getY() == jugador1.getY())
					{
						if(fantasma.getModoCaza())		// Matar Jugador 1
						{
							pausarPartida(2000);
							jugador1.morir();
							System.out.println("GanMan Muerto");
							if(jugador1.getVidas() <= 0)
							{
								terminarPartida();
							}
						}
						else						// Matar Fantasma
						{
							pausarPartida(1000);
							fantasma.morir();
							puntos += 500;
							System.out.println("Fantasma Muerto");
						}
					}
				}
				
				// Repinto Pantalla
				repaint();
			}
		}, 1, 1);
	}
	
	// Acciones
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		// Pinto Mapa
		for(int a = 0; a < mapa[0].length; a++)		// Recorrer Columnas
		{
			for(int b = 0; b < mapa.length; b++)	// Recorrer Filas
			{
				switch(mapa[b][a])
				{
					case 0 ->
					{
						g.setColor(Color.BLACK);
						g.fillRect(a * tamano + centrarX, b * tamano + centrarY, tamano, tamano);
					}
					case 1 -> g.drawImage(imgMuro.getImage(), a * tamano + centrarX, b * tamano + centrarY, tamano, tamano, null);
					case 2 -> g.drawImage(imgComida.getImage(), a * tamano + centrarX, b * tamano + centrarY, tamano, tamano, null);
					case 3 -> g.drawImage(imgPulpo.getImage(), a * tamano + centrarX, b * tamano + centrarY, tamano, tamano, null);
					case 4 -> g.drawImage(imgCoctel.getImage(), a * tamano + centrarX, b * tamano + centrarY, tamano, tamano, null);
				}
			}
		}

		// Fantasmas
		for(Fantasmas fantasma : fantasmasList)
		{
			// Pinto Camino
			if(verCamino)
			{
				ArrayList<Node> ruta  = fantasma.getRuta();
				switch(fantasma.getTipo())
				{
					case 2 ->		// Fantasma Naranja
					{
						for(Node camino : ruta)
						{
							g.setColor(Color.decode("#f77400"));
							g.fillRect(camino.y * tamano + centrarX, camino.x * tamano + centrarY, tamano, tamano);
						}
					}
					case 4 ->		// fantasma Morado
					{
						for(Node camino : ruta)
						{
							g.setColor(Color.decode("#ca00f7"));
							g.fillRect(camino.y * tamano + centrarX, camino.x * tamano + centrarY, tamano, tamano);
						}
					}
				}
			}
			
			// Pinto Fantasmas
			g.drawImage(fantasma.getImg(), fantasma.getX() * tamano + centrarX, fantasma.getY() * tamano + centrarY, tamano, tamano, null);
		}
		
		// Pinto Jugador1
		switch(jugador1.getDireccion())
		{
			case 'u' -> g.drawImage(jugador1.getImgUp(), jugador1.getX() * tamano + centrarX, jugador1.getY() * tamano + centrarY, tamano, tamano, null);
			case 'd' -> g.drawImage(jugador1.getImgDown(), jugador1.getX() * tamano + centrarX, jugador1.getY() * tamano + centrarY, tamano, tamano, null);
			case 'l' -> g.drawImage(jugador1.getImgLeft(), jugador1.getX() * tamano + centrarX, jugador1.getY() * tamano + centrarY, tamano, tamano, null);
			case 'r' -> g.drawImage(jugador1.getImgRight(), jugador1.getX() * tamano + centrarX, jugador1.getY() * tamano + centrarY, tamano, tamano, null);
			default -> g.drawImage(jugador1.getImg(), jugador1.getX() * tamano + centrarX, jugador1.getY() * tamano + centrarY, tamano, tamano, null);
		}
		
		// Pinto UI
		{
			// Pinto Puntuacion
			g.setColor(Color.YELLOW);
			g.setFont(new Font("arial", 0, 20));
			g.drawString("Puntos: " + puntos, 10, 20);

			// Pinto Vidas
			g.setColor(Color.YELLOW);
			g.setFont(new Font("arial", 0, 20));
			g.drawString("Vidas: " + jugador1.getVidas(), 10, 40);
		}
	}
	public void nuevaPartida()
	{
		// Cargo Variables
		partida.setNivel(nivel);
		mapa = partida.getMapa();
		tamano = partida.getTamano();
		comida = partida.getComida();
		centrarX = (800 - (tamano * mapa[0].length)) / 2 - 10;
		centrarY = (800 - (tamano * mapa.length)) / 2 - 22;

		// Creo Jugador1
		jugador1 = new GanMan();
		jugador1.setMapa(mapa);
		jugador1.setX(partida.getGanX());
		jugador1.setY(partida.getGanY());
		
		// Pido Cantidad Fantasmas
		int cantidadFantasmas = 0;
		do
		{
			String str_cantidadFantasmas = JOptionPane.showInputDialog(null, "Ingresa los Fantasmas 0-4");
			if(str_cantidadFantasmas == null || str_cantidadFantasmas.equals(""))
			{
				str_cantidadFantasmas = "0";
			}
			cantidadFantasmas = Integer.parseInt(str_cantidadFantasmas);
		}
		while(cantidadFantasmas < 0 || cantidadFantasmas > 4);

		// Creo Fantasmas
		for (int a = 1; a <= cantidadFantasmas; a++)
		{
			Fantasmas fantasma = new Fantasmas(a);
			fantasma.setMapa(mapa);
			fantasma.setMeta(jugador1.getX(), jugador1.getY());
			fantasma.setX(partida.getFanX(a));
			fantasma.setY(partida.getFanY(a));
			fantasma.setComida(comida);
			fantasma.setComidaAux(comida);
			fantasmasList.add(fantasma);
		}
		
		// Partida en Curso
		agregarControles();
		jugador1.mover();
		jugando();
	}
	public void avanzarNivel()
	{
		// Pauso Juego
		pausarPartida(2000);
		
		// Sumo Nivel
		nivel++;
		if(nivel > 3)
		{
			nivel = 1;
		}
		
		// Cargo Variables
		partida.setNivel(nivel);
		mapa = partida.getMapa();
		tamano = partida.getTamano();
		comida = partida.getComida();
		centrarX = (800 - (tamano * mapa[0].length)) / 2 - 10;
		centrarY = (800 - (tamano * mapa.length)) / 2 - 22;

		// Cargo Jugador1
		jugador1.setMapa(mapa);
		jugador1.setX(partida.getGanX());
		jugador1.setY(partida.getGanY());
		jugador1.setDIreccion('r');

		// Cargo Fantasmas
		int a = 1;
		for(Fantasmas fantasma : fantasmasList)
		{
			fantasma.setMapa(mapa);
			fantasma.setMeta(jugador1.getX(), jugador1.getY());
			fantasma.setX(partida.getFanX(a));
			fantasma.setY(partida.getFanY(a));
			fantasma.setComida(comida);
			fantasma.setComidaAux(comida);
			fantasma.modoCaza();
			a++;
		}
	}
	public void agregarControles()
	{
		addKeyListener(controles);
	}
	public void eliminarControles()
	{
		removeKeyListener(controles);
	}
	public void pausarPartida(int tiempo)
	{
		// Inmovilizo GanMan y Fantasmas
		jugador1.inmovilizar();
		for(Fantasmas fantasma : fantasmasList)
		{
			fantasma.inmovilizar();
		}
		eliminarControles();
		
		// Espero Tiempo
		try
		{
			Thread.sleep(tiempo);
		}
		catch(InterruptedException ex){}
		
		// Reanudo Movimiento GanMan y Fantasmas
		jugador1.mover();
		for(Fantasmas fantasma : fantasmasList)
		{
			fantasma.mover();
		}
		agregarControles();
	}
	public void terminarPartida()
	{
		// Reinicio Posicion Fantasmas
		for(Fantasmas fantasma : fantasmasList)
		{
			fantasma.inmovilizar();
			fantasma.cargarSpawn();
		}
		
		// Reinicio Posicion GanMan
		jugador1.inmovilizar();
		jugador1.cargarSpawn();
		
		// Muestro Mensaje Final
		JOptionPane.showMessageDialog(null, "Perdiste :c");
		
		// PanelJugar
		setVisible(false);
		Ventana.pInicio.setVisible(true);
	}
	
	// Controles
	private void controlesPanel()
	{
		// Creo Controles al Panel
		controles = new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent ke)
			{
				switch(ke.getKeyCode())
				{
					case 27 -> // Tecla ESCAPE
					{
						// Pauso Juego
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.inmovilizar();
							fantasma.pausar();
						}
						eliminarControles();
						jugador1.inmovilizar();
						
						// Muestro Menu Pausa
						String[] opciones = {"Salir", "Continuar"};
						int opcion = JOptionPane.showOptionDialog(null, "Que Quieres Hacer?", "Menu Pausa", 0, 3, null, opciones, 0);
						
						// Verifico Opcion
						if(opcion == 0)
						{
							// Salgo Juego
							setVisible(false);
							Ventana.pInicio.setVisible(true);
						}
						else
						{
							// Reanudo Juego
							agregarControles();
							for(Fantasmas fantasma : fantasmasList)
							{
								fantasma.mover();
								fantasma.pausar();
							}
							jugador1.mover();
						}
					}
					
					// Movimiento Jugador 1
					case 37 -> jugador1.setDIreccion('l');
					case 38 -> jugador1.setDIreccion('u');
					case 39 -> jugador1.setDIreccion('r');
					case 40 -> jugador1.setDIreccion('d');
					
					// Movimiento Jugador 2
					case 87 ->
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.setDireccion('u');
						}
						break;
					}
					case 68 ->
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.setDireccion('r');
						}
						break;
					}
					case 83 ->
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.setDireccion('d');
						}
						break;
					}
					case 65 ->
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.setDireccion('l');
						}
						break;
					}
					
					// Modo Dev
					case 46 -> avanzarNivel();	// Tecla >
					case 59 ->	// Tecla :
					{
						int auxVelocidad = jugador1.getVelocidad() + 25;
						jugador1.setVelocidad(auxVelocidad);
						System.out.println("Velocidad GanMan Reducida");
						break;
					}
					case 222 ->	// Tecla "
					{
						int auxVelocidad = jugador1.getVelocidad() - 25;
						jugador1.setVelocidad(auxVelocidad);
						System.out.println("Velocidad GanMan Aumentada");
						break;
					}
					case 80 ->	// Tecla P
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							fantasma.modoHuir();
						}
						System.out.println("Fantasmas Modo Cambiado");
						break;
					}
					case 91 ->	// Tecla {
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							int auxVelocidad = fantasma.getVelocidad() + 100;
							fantasma.setVelocidad(auxVelocidad);
						}
						System.out.println("Velocidad Fantasma Reducida");
						break;
					}
					case 93 ->	// Tecla }
					{
						for(Fantasmas fantasma : fantasmasList)
						{
							// Asigno Velocidad
							int auxVelocidad = fantasma.getVelocidad() - 100;
							fantasma.setVelocidad(auxVelocidad);
						}
						System.out.println("Velocidad Fantasma Aumentada");
						break;
					}
					case 92 -> verCamino = !verCamino;
					default -> System.out.println("Tecla Presioada: " + ke.getKeyCode());
				}
			}
		};
	}
}
