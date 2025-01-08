package Codigo;

// Imports
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelInicio extends JPanel
{	
	// Constructor
	public PanelInicio()
	{
		// Propiedades
		setLayout(null);
		setFocusable(true);
		setBackground(Color.decode("#000000"));
		requestFocus();
		
		// Componentes
		componentes();
	}

	// Componentes
	private void componentes()
	{
		// Boton Jugar 1 Jugador
		JButton btnJugar1 = new JButton("1 Jugador");
		btnJugar1.setBounds(50,100, 150,50);
		add(btnJugar1);
		btnJugar1.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent me)
			{
				requestFocusInWindow();
				setVisible(false);
				
				PanelJugar pJugar = new PanelJugar();
				Ventana.vPrincipal.add(pJugar);
				pJugar.nuevaPartida();
			}
		});
		
		// Boton Jugar 2 jugadores
		JButton btnJugar2 = new JButton("2 Jugadores");
		btnJugar2.setBounds(50,200, 150,50);
		add(btnJugar2);
		btnJugar2.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent me)
			{
				requestFocusInWindow();
				setVisible(false);
				
				PanelJugar pJugar = new PanelJugar();
				Ventana.vPrincipal.add(pJugar);
				pJugar.nuevaPartida();
			}
		});
	}
}
