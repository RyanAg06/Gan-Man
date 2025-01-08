package Codigo;

// Imports 
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Ventana
{
	// Instancias
	private final ImageIcon icon = new ImageIcon("./src/main/java/Imagenes/ganMan.png");
	public static JFrame vPrincipal = new JFrame("Gan-Man");
	public static PanelInicio pInicio = new PanelInicio();

	// Constructor
	public Ventana()
	{
		// Propiedades Ventana
		vPrincipal.setVisible(true);
		vPrincipal.setSize(800, 800);
		vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		vPrincipal.setLocationRelativeTo(null);
		vPrincipal.setIconImage(icon.getImage());

		// Agrego Panel
		vPrincipal.add(pInicio);
	}
}
