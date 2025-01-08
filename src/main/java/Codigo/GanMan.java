package Codigo;

// imports
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public final class GanMan
{
	// Imagenes e Instancias
	private final ImageIcon imgGanManArriba = new ImageIcon("./src/main/java/Imagenes/ganManAr.gif");
	private final ImageIcon imgGanManDerecha = new ImageIcon("./src/main/java/Imagenes/ganManDe.gif");
	private final ImageIcon imgGanManAbajo = new ImageIcon("./src/main/java/Imagenes/ganManAb.gif");
	private final ImageIcon imgGanManIzquierda = new ImageIcon("./src/main/java/Imagenes/ganManIz.gif");
	private final ImageIcon imgGanMan = new ImageIcon("./src/main/java/Imagenes/ganMan.png");
	private TimerTask tareaMover;

	// Variables
	private int spawnX, spawnY, ganX, ganY, velocidad = 200, vidas = 3;
	private int mapa[][];
	private char direccion = 'r', auxDireccion = 'r';
	
	// Acciones
	public void inmovilizar()
	{
		tareaMover.cancel();
		auxDireccion = direccion;
		direccion = ' ';
	}
	public void mover()
	{
		direccion = auxDireccion;
		tareaMover = new TimerTask()
		{
			@Override
			public void run()
			{
				switch(direccion)
				{
					case 'u' ->
					{
						if(mapa[ganY - 1][ganX] != 1)
						{
							ganY--;
						}
						break;
					}
					case 'r' ->
					{
						if(mapa[ganY][ganX + 1] != 1)
						{
							ganX++;
						}
						break;
					}
					case 'd' ->
					{
						if(mapa[ganY + 1][ganX] != 1)
						{
							ganY++;
						}
						break;
					}
					case 'l' ->
					{
						if(mapa[ganY][ganX - 1] != 1)
						{
							ganX--;
						}
						break;
					}
				}
			}
		};
		new Timer().scheduleAtFixedRate(tareaMover, 1, velocidad);
	}
	public void morir()
	{
		vidas--;
		cargarSpawn();
	}
	public void cargarSpawn()
	{
		ganX = spawnX;
		ganY = spawnY;
	}

	// Getters
	public Image getImgUp()
	{
		return imgGanManArriba.getImage();
	}
	public Image getImgDown()
	{
		return imgGanManAbajo.getImage();
	}
	public Image getImgLeft()
	{
		return imgGanManIzquierda.getImage();
	}
	public Image getImgRight()
	{
		return imgGanManDerecha.getImage();
	}
	public Image getImg()
	{
		return imgGanMan.getImage();
	}
	public int getX()
	{
		return ganX;
	}
	public int getY()
	{
		return ganY;
	}
	public int getVelocidad()
	{
		return velocidad;
	}
	public int getVidas()
	{
		return vidas;
	}
	public char getDireccion()
	{
		return direccion;
	}

	// Setters
	public void setMapa(int mapa[][])
	{
		this.mapa = mapa;
	}
	public void setX(int x)
	{
		this.ganX = x;
		this.spawnX = x;
	}
	public void setY(int y)
	{
		this.ganY = y;
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
	public void setVidas(int vidas)
	{
		this.vidas = vidas;
	}
	public void setDIreccion(char direccion)
	{
		this.direccion = direccion;
	}
}
