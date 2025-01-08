package Codigo;

// imports
import static Codigo.PathFinder.findPath;
import com.sun.source.tree.ContinueTree;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public final class Fantasmas
{
	// Imagenes e instancias
	private final ImageIcon imgChefRojo = new ImageIcon("./src/main/java/Imagenes/chefRojo.png");
	private final ImageIcon imgChefAzul = new ImageIcon("./src/main/java/Imagenes/chefAzul.png");
	private final ImageIcon imgChefNaranja = new ImageIcon("./src/main/java/Imagenes/chefNaranja.png");
	private final ImageIcon imgChefRosa = new ImageIcon("./src/main/java/Imagenes/chefRosa.png");
	private final ImageIcon imgChefBlanco = new ImageIcon("./src/main/java/Imagenes/chefBlanco.png");
	private TimerTask tareaMover;
	private ArrayList<Node> ruta = null;
	
	// Variables
	private int spawnX, spawnY, fanX, fanY, ganX, ganY, tipo, velocidad = 600, comidaMapa, auxComida;
	private int mapa[][];
	private final int auxTipo;
	private boolean modoCaza = true, modoPausa = false;
	private char direccion = ' ';

	// Constructor
	public Fantasmas(int tipo)
	{
		// Variables
		this.tipo = tipo;
		this.auxTipo = tipo;
		
		// Acciones
		mover();
	}
	
	// Acciones
	public void inmovilizar()
	{
		tareaMover.cancel();
	}
	public void mover()
	{
		// Escojo Movimiento Fantasma
		switch(tipo)
		{
			case 0 -> movimientoRandom();
			case 1 -> movimientoTraspasar();
			case 2 -> movimientoSeguir();
			case 3 -> movimientoRandom();
			case 4 -> movimientoSeguirRetraso();
			case 5 -> movimientoJugador2();
		}
	}
	public void morir()
	{
		cargarSpawn();
		if(!modoCaza)
		{
			modoCaza();
		}
	}
	public void cargarSpawn()
	{
		fanX = spawnX;
		fanY = spawnY;
	}
	public void modoCaza()
	{
		limpiarRuta();
		modoCaza = true;
		tipo = auxTipo;
		inmovilizar();
		mover();
	}
	public void modoHuir()
	{
		limpiarRuta();
		modoCaza = false;
		tipo = 0;
		inmovilizar();
		mover();
	}
	public void limpiarRuta()
	{
		// Vacio Ruta de Fantasmas Naranja y Morado
		if(tipo == 2 || tipo == 4)
		{
			ruta.clear();
		}
	}
	public void pausar()
	{
		modoPausa = !modoPausa;
	}
	
	// Comportamientos
	private void movimientoTraspasar()
	{
		tareaMover = new TimerTask()
		{
			@Override
			public void run()
			{
				// Mover en X
				if (ganX > fanX)
				{
					fanX++;
				}
				else if (ganX < fanX)
				{
					fanX--;
				}

				// Mover en Y
				if (ganY > fanY)
				{
					fanY++;
				}
				else if (ganY < fanY)
				{
					fanY--;
				}
			}
		};
		new Timer().scheduleAtFixedRate(tareaMover, 1, velocidad);
	}
	private void movimientoSeguir()
	{
		tareaMover = new TimerTask()
		{
			@Override
			public void run()
			{
				try
				{
					// Defino Ruta
					Node inicio = new Node(fanY, fanX);
					Node meta = new Node(ganY, ganX);
					ruta =  (ArrayList<Node>) findPath(mapa, inicio, meta);
					
					// Recorro Ruta
					ruta.removeFirst();
					for(Node camino : ruta)
					{
						fanY = camino.x;
						fanX = camino.y;
						Thread.sleep(velocidad);
						break;
					}
				}
				catch(InterruptedException e) {}
			}
		};
		new Timer().scheduleAtFixedRate(tareaMover, 1, 1);
	}
	private void movimientoSeguirRetraso()
	{
		tareaMover = new TimerTask()
		{
			@Override
			public void run()
			{
				try
				{
					// Defino Ruta
					Node inicio = new Node(fanY, fanX);
					Node meta = new Node(ganY, ganX);
					ruta = (ArrayList<Node>) findPath(mapa, inicio, meta);

					// Activar Movimiento Despues de Comer 30 Puntos
					if(comidaMapa <= auxComida)
					{
						ruta.removeFirst();
						for(Node camino : ruta)
						{
							fanY = camino.x;
							fanX = camino.y;
							Thread.sleep(velocidad);
							
							// Rompo Recorrido si Esta Pausado
							if(modoPausa)
							{
								break;
							}
						}
					}
				}
				catch(Exception e)
				{
					System.out.println("Error Controlado: " + e);
				}
			}
		};
		new Timer().scheduleAtFixedRate(tareaMover, 1, 1);
	}
	private void movimientoRandom()
	{
		tareaMover = new TimerTask()
		{
			@Override
			public void run()
			{
				try
				{
					int direccion = (int) (Math.random() * 5);

					switch(direccion)
					{
						case 0 ->   // Mover Izquierda
						{
							if(mapa[fanY][fanX - 1] != 1)
							{
								fanX--;
							}
							break;
						}
						case 1 ->   // Mover Arriba
						{
							if(mapa[fanY - 1][fanX] != 1)
							{
								fanY--;
							}
							break;
						}
						case 2 ->   // Mover Derecha
						{
							if(mapa[fanY][fanX + 1] != 1)
							{
								fanX++;
							}
							break;
						}
						case 3 ->   // Mover Abajo
						{
							if(mapa[fanY + 1][fanX] != 1)
							{
								fanY++;
							}
							break;
						}
					}
					Thread.sleep(velocidad / 3);
				}
				catch(InterruptedException e){}
			}
		};
		new Timer().scheduleAtFixedRate(tareaMover, 1, velocidad);
	}
	private void movimientoJugador2()
	{
		tareaMover = new TimerTask()
		{
			@Override
			public void run()
			{
				switch(direccion)
				{
					case 'u' ->
					{
						if(mapa[fanY - 1][fanX] != 1)
						{
							fanY--;
						}
						break;
					}
					case 'r' ->
					{
						if(mapa[fanY][fanX + 1] != 1)
						{
							fanX++;
						}
						break;
					}
					case 'd' ->
					{
						if(mapa[fanY + 1][fanX] != 1)
						{
							fanY++;
						}
						break;
					}
					case 'l' ->
					{
						if(mapa[fanY][fanX - 1] != 1)
						{
							fanX--;
						}
						break;
					}
				}
			}
		};
		new Timer().scheduleAtFixedRate(tareaMover, 1, velocidad);
	}

	// Getters
	public ArrayList getRuta()
	{
		return ruta;
	}
	public Image getImg()
	{
		switch(tipo)
		{
			case 1 -> {return imgChefRojo.getImage();}
			case 2 -> {return imgChefNaranja.getImage();}
			case 3 -> {return imgChefAzul.getImage();}
			case 4 -> {return imgChefRosa.getImage();}
			default -> {return imgChefBlanco.getImage();}
		}
	}
	public int getX()
	{
		return fanX;
	}
	public int getY()
	{
		return fanY;
	}
	public int getVelocidad()
	{
		return velocidad;
	}
	public int getTipo()
	{
		return tipo;
	}
	public boolean getModoCaza()
	{
		return modoCaza;
	}

	// Setters
	public void setMapa(int mapa[][])
	{
		this.mapa = mapa;
	}
	public void setMeta(int ganX, int ganY)
	{
		this.ganX = ganX;
		this.ganY = ganY;
	}
	public void setX(int x)
	{
		this.fanX = x;
		this.spawnX = x;
	}
	public void setY(int y)
	{
		this.fanY = y;
		this.spawnY = y;
	}
	public void setVelocidad(int velocidad)
	{
		if(velocidad <= 0)
		{
			velocidad = 1;
		}
		this.velocidad = velocidad;
		inmovilizar();
		mover();
	}
	public void setModoCaza(boolean modo)
	{
		this.modoCaza = modo;
	}
	public void setDireccion(char direccion)
	{
		this.direccion = direccion;
	}
	public void setComida(int comida)
	{
		this.comidaMapa = comida;
	}
	public void setComidaAux(int comida)
	{
		this.auxComida = comida - 30;
	}
}