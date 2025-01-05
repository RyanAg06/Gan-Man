package Codigo;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelRecords extends JPanel{
    private final AudioClip sonidoClick=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoClick.wav"));
    private DefaultTableModel modeloRecords;
    private JTable tablaRecords;
    
    public static ArrayList<PanelRecords>lista=new ArrayList<>();
    public String nombre;
    public String pantallas;
    public int puntos;
    
    public PanelRecords(){
        setLayout(null);
        setBackground(Color.BLACK);
        etiquetas();
        separadores();
        botones();
        tablas();
        ordenarDatos();
    }
    public void ordenarDatos(){
        PanelRecords aux;
        for(int a=0;a<lista.size();a++){
            for(int b=0;b<lista.size()-a-1;b++){
                if(lista.get(b).puntos<lista.get(b+1).puntos){
                    aux=lista.get(b+1);
                    lista.set(b+1,lista.get(b));
                    lista.set(b,aux);
                }
            }
            String auxPuntos;
            String datos[]={lista.get(a).nombre,auxPuntos=String.valueOf(lista.get(a).puntos),lista.get(a).pantallas};
            modeloRecords.addRow(datos);
        }
    }
    private void etiquetas(){
        JLabel titulo=new JLabel("Records",SwingConstants.CENTER);
        add(titulo);
        titulo.setBounds(0,100,800,80);
        titulo.setFont(new Font("arial",3,50));
        titulo.setForeground(Color.YELLOW);
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
    private void tablas(){
        final String titulos[]={"Nombre","Puntos","Pantallas"};
        
        modeloRecords=new DefaultTableModel(titulos,0);
        
        tablaRecords=new JTable(modeloRecords);
        add(tablaRecords);
        tablaRecords.setBounds(40,230,700,623);
        tablaRecords.setBackground(Color.BLACK);
        tablaRecords.setForeground(Color.WHITE);
        tablaRecords.setFont(new Font("arial",1,30));
        tablaRecords.setRowHeight(60);
        tablaRecords.setEnabled(false);
        
        JScrollPane scRecords=new JScrollPane(tablaRecords);
        add(scRecords);
        scRecords.setBounds(40,230,700,623);
    }
}