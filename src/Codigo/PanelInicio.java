 package Codigo;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelInicio extends JPanel{
    AudioClip sonidoClick=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoClick.wav"));
    ImageIcon imgChefRojo=new ImageIcon(getClass().getResource("/Imagenes/chefRojo.png"));
    ImageIcon imgChefNaranja=new ImageIcon(getClass().getResource("/Imagenes/chefNaranja.png"));
    ImageIcon imgChefRosa=new ImageIcon(getClass().getResource("/Imagenes/chefRosa.png"));
    ImageIcon imgChefAzul=new ImageIcon(getClass().getResource("/Imagenes/chefAzul.png"));
    ImageIcon imgGanManDe=new ImageIcon(getClass().getResource("/Imagenes/imgCamaron.png"));//Imagenes de fantasmas
    
    public PanelInicio(){//Metodo constructor
        setLayout(null);
        setBackground(Color.BLACK);
        etiquetas();
        separadores();
        imagenes();
        botones();
    }
    private void etiquetas(){//Textos
        JLabel titulo=new JLabel("Gan-Man",SwingConstants.CENTER);
        add(titulo);
        titulo.setBounds(0,80,800,50);
        titulo.setFont(new Font("arial",3,50));
        titulo.setForeground(Color.YELLOW);
    }
    private void separadores(){//Separadores
        JSeparator sep1=new JSeparator(0);
        add(sep1);
        sep1.setBounds(40,300,700,10);
        sep1.setBackground(Color.WHITE);
    }
    private void imagenes(){//Imagenes de los fantasmas y el camaron
        JLabel imgNaranja=new JLabel();
        add(imgNaranja);
        imgNaranja.setBounds(50,170,120,120);
        imgNaranja.setIcon(new ImageIcon(imgChefNaranja.getImage().getScaledInstance(imgNaranja.getWidth(),imgNaranja.getHeight(),Image.SCALE_SMOOTH)));
        
        JLabel imgAzul=new JLabel();
        add(imgAzul);
        imgAzul.setBounds(190,170,120,120);
        imgAzul.setIcon(new ImageIcon(imgChefAzul.getImage().getScaledInstance(imgAzul.getWidth(),imgAzul.getHeight(),Image.SCALE_SMOOTH)));
        
        JLabel imgRosa=new JLabel();
        add(imgRosa);
        imgRosa.setBounds(330,170,120,120);
        imgRosa.setIcon(new ImageIcon(imgChefRosa.getImage().getScaledInstance(imgRosa.getWidth(),imgRosa.getHeight(),Image.SCALE_SMOOTH)));
        
        JLabel imgRojo=new JLabel();
        add(imgRojo);
        imgRojo.setBounds(470,170,120,120);
        imgRojo.setIcon(new ImageIcon(imgChefRojo.getImage().getScaledInstance(imgRojo.getWidth(),imgRojo.getHeight(),Image.SCALE_SMOOTH)));
        
        JLabel imgGanMan=new JLabel();
        add(imgGanMan);
        imgGanMan.setBounds(620,170,120,120);
        imgGanMan.setIcon(new ImageIcon(imgGanManDe.getImage().getScaledInstance(imgGanMan.getWidth(),imgGanMan.getHeight(),Image.SCALE_SMOOTH)));
    }
    private void botones(){
        JButton btn1Jugador=new JButton("1 Jugador");
        add(btn1Jugador);
        btn1Jugador.setBounds(100,460,200,50);
        
        
        btn1Jugador.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                sonidoClick.play();
                PanelJugar pJugar=new PanelJugar();
                PanelJugar.jugador2=false;
                VentanaPrincipal.Ventana.add(pJugar);
                VentanaPrincipal.pInicio.setVisible(false);
                pJugar.setVisible(true);
            }
        });
        EfectoHover.botonHover(btn1Jugador);
        
        JButton btn12Jugadores=new JButton("2 Jugadores");
        add(btn12Jugadores);
        btn12Jugadores.setBounds(100,520,200,50);
        btn12Jugadores.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                sonidoClick.play();
                PanelJugar pJugar=new PanelJugar();
                PanelJugar.jugador2=true;
                VentanaPrincipal.Ventana.add(pJugar);
                VentanaPrincipal.pInicio.setVisible(false);
                pJugar.setVisible(true);
            }
        });
        EfectoHover.botonHover(btn12Jugadores);
        
        JButton btnRecords=new JButton("Records");
        add(btnRecords);
        btnRecords.setBounds(100,580,200,50);
        btnRecords.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                sonidoClick.play();
                PanelRecords pRecords=new PanelRecords();
                VentanaPrincipal.Ventana.add(pRecords);
                VentanaPrincipal.pInicio.setVisible(false);
                pRecords.setVisible(true);
            }
        });
        EfectoHover.botonHover(btnRecords);
        
        JButton btnCreditos=new JButton("Creditos");
        add(btnCreditos);
        btnCreditos.setBounds(100,640,200,50);
        btnCreditos.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                sonidoClick.play();
                PanelCreditos pCreditos=new PanelCreditos();
                VentanaPrincipal.Ventana.add(pCreditos);
                VentanaPrincipal.pInicio.setVisible(false);
                pCreditos.setVisible(true);
            }
        });
        EfectoHover.botonHover(btnCreditos);
    }
}
