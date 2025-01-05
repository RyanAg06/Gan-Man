package Codigo;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelCreditos extends JPanel{
    AudioClip sonidoClick=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoClick.wav"));
    
    public PanelCreditos(){
        setLayout(null);
        setBackground(Color.BLACK);
        etiquetas();
        separadores();
        botones();
    }
    private void etiquetas(){
        JLabel titulo=new JLabel("Creditos del Juego",SwingConstants.CENTER);
        add(titulo);
        titulo.setBounds(0,100,800,80);
        titulo.setFont(new Font("arial",3,50));
        titulo.setForeground(Color.YELLOW);
        
        JLabel txtSprites=new JLabel("Diseños del Juego:                          Serjan Karoll Arellano Valenzuela");
        add(txtSprites);
        txtSprites.setBounds(40,250,700,50);
        txtSprites.setFont(new Font("arial",3,21));
        txtSprites.setForeground(Color.WHITE);
        
        JLabel txtProgramacion=new JLabel("Programacion del Juego:                Brian Jesus Aguero Herrera");
        add(txtProgramacion);
        txtProgramacion.setBounds(40,320,700,50);
        txtProgramacion.setFont(new Font("arial",3,21));
        txtProgramacion.setForeground(Color.WHITE);
        
        JLabel txtMapas=new JLabel("Mapas del Juego:                             Isaack Derek Mendez Virgen");
        add(txtMapas);
        txtMapas.setBounds(40,390,700,50);
        txtMapas.setFont(new Font("arial",3,21));
        txtMapas.setForeground(Color.WHITE);
        
        JLabel txtSonidos1=new JLabel("Sonidos del Juego:                          Anhuar Contreras Dominguez");
        add(txtSonidos1);
        txtSonidos1.setBounds(40,460,700,50);
        txtSonidos1.setFont(new Font("arial",3,21));
        txtSonidos1.setForeground(Color.WHITE);
        
        JLabel txtSonidos2=new JLabel("Sonidos del Juego:                          Luis Hernandez Vasquez");
        add(txtSonidos2);
        txtSonidos2.setBounds(40,490,700,50);
        txtSonidos2.setFont(new Font("arial",3,21));
        txtSonidos2.setForeground(Color.WHITE);
        
        JLabel txtSonidos3=new JLabel("Sonidos del Juego:                          Isaack Derek Mendez Virgen ");
        add(txtSonidos3);
        txtSonidos3.setBounds(40,520,700,50);
        txtSonidos3.setFont(new Font("arial",3,21));
        txtSonidos3.setForeground(Color.WHITE);
    }
    private void separadores(){
        JSeparator sep1=new JSeparator(0);
        add(sep1);
        sep1.setBounds(40,200,700,10);
        sep1.setBackground(Color.WHITE);
    }
    private void botones(){
        JButton btnRegresar=new JButton("<");
        add(btnRegresar);
        btnRegresar.setBounds(20,20,45,45);
        btnRegresar.setFont(new Font("arial",3,17));
        btnRegresar.setBackground(Color.WHITE);
        btnRegresar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent me){
                sonidoClick.play();
                setVisible(false);
                VentanaPrincipal.pInicio.setVisible(true);
            }
        });
        EfectoHover.botonHover(btnRegresar);
    }
}
