package Codigo;

import javax.swing.*;

public class VentanaPrincipal{
    ImageIcon icono=new ImageIcon(getClass().getResource("/Imagenes/imgCamaron.png"));//Creo la imagen del icono
    public static PanelInicio pInicio=new PanelInicio();//Creo panel donde esta toda la interfazz inicial
    public static JFrame Ventana=new JFrame("Gan-Man");//Creo un JFrame que es estatico
    
    public VentanaPrincipal(){//Defino la configuracion de mi Ventana
        Ventana.setVisible(true);
        Ventana.setSize(800,1000);
        Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana.setResizable(false);
        Ventana.setLocationRelativeTo(null);
        Ventana.setIconImage(icono.getImage());
        paneles();
    }
    private void paneles(){//Agrego el panel a la ventana
        Ventana.add(pInicio);
    }
}
