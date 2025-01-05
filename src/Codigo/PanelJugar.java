package Codigo;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;

public class PanelJugar extends JPanel{
    //Instancias
    private final AudioClip sonidoClick=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoClick.wav"));//Sonidos
    private final AudioClip sonidoIniciar=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoIniciar.wav"));
    private final AudioClip sonidoGanar=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoGanar.wav"));
    private final AudioClip sonidoMorir=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoMorir.wav"));
    private final AudioClip sonidoComerChef=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoComerChef.wav"));
    private final AudioClip sonidoFondoComerChef=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoFondoComerChef.wav"));
    private final AudioClip sonidoTU=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoTU.wav"));
    private final AudioClip sonidoKI=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoKI.wav"));
    private final AudioClip sonidoSirena=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/sonidoSirena.wav"));
    private final ImageIcon imgChefRojo=new ImageIcon(getClass().getResource("/Imagenes/chefRojo.png"));//Imagenes
    private final ImageIcon imgChefNaranja=new ImageIcon(getClass().getResource("/Imagenes/chefNaranja.png"));
    private final ImageIcon imgChefRosa=new ImageIcon(getClass().getResource("/Imagenes/chefRosa.png"));
    private final ImageIcon imgChefAzul=new ImageIcon(getClass().getResource("/Imagenes/chefAzul.png"));
    private final ImageIcon imgChefBlanco=new ImageIcon(getClass().getResource("/Imagenes/chefBlanco.png"));
    private final ImageIcon personajeIz=new ImageIcon(getClass().getResource("/Imagenes/GanManIz.gif"));
    private final ImageIcon personajeAr=new ImageIcon(getClass().getResource("/Imagenes/GanManAr.gif"));
    private final ImageIcon personajeDe=new ImageIcon(getClass().getResource("/Imagenes/GanManDe.gif"));
    private final ImageIcon personajeAb=new ImageIcon(getClass().getResource("/Imagenes/GanManAb.gif"));
    private final ImageIcon imgMuro=new ImageIcon(getClass().getResource("/Imagenes/muro.png"));
    private final ImageIcon imgComida=new ImageIcon(getClass().getResource("/Imagenes/comida.png"));
    private final ImageIcon imgCoctel=new ImageIcon(getClass().getResource("/Imagenes/coctel.png"));
    private final ImageIcon imgPulpo=new ImageIcon(getClass().getResource("/Imagenes/pulpo.png"));
    private final Laberintos lab=new Laberintos();//Creo objeto de una clase
    //Otras Variables
    private int puntos=0,puntosTotales=0,canComida,pantallas=0;
    private final String opciones[]={"Reanudar","Salir"};
    //Mapa
    private int nivel=1,filas,columnas,tam,resX,resY;
    private int mapa[][];
    //Jugadores
    private KeyListener controlesJugadores;
    private Timer delayMoverJugadores;
    private int ganX,ganY,spawnGanX,spawnGanY,vidas=3,tono=0;
    private char direccionGan;
    private char direccionJugador2;
    public static boolean jugador2;
    private boolean comerFan;
    //Fantasmas
    private Timer delayMoverFantasmas;
    private int velocidadFan;
    private int fanRojoX,fanRojoY,spawnFanRojoX,spawnFanRojoY;
    private int fanRosaX,fanRosaY,spawnFanRosaX,spawnFanRosaY;
    private int fanAzulX,fanAzulY,spawnFanAzulX,spawnFanAzulY;
    private int fanNaranjaX,fanNaranjaY,spawnFanNaranjaX,spawnFanNaranjaY;
    private char direccionFan;
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        //Mapa
        for(int a=0;a<filas;a++){
            for(int b=0;b<columnas;b++){
                if(mapa[a][b]==0){//Limpiar Cuadro
                    g.setColor(Color.BLACK);
                    g.fillRect(resX+b*tam,resY+a*tam,tam,tam);
                }
                if(mapa[a][b]==1){//Dibujar Paredes
                    g.drawImage(imgMuro.getImage(),resX+b*tam,resY+a*tam,tam,tam,this);
                }
                if(mapa[a][b]==2){//Dibujar Comida
                    g.drawImage(imgComida.getImage(),resX+b*tam,resY+a*tam,tam,tam,this);
                }
                if(mapa[a][b]==3){//Dibujar Coctel
                    g.drawImage(imgCoctel.getImage(),resX+b*tam,resY+a*tam,tam,tam,this);
                }
                if(mapa[a][b]==4){//Dibujar Pulpo
                    g.drawImage(imgPulpo.getImage(),resX+b*tam,resY+a*tam,tam,tam,this);
                }
            }
        }
        //Personaje
        switch(direccionGan){
            case 'l':{
                g.drawImage(personajeIz.getImage(),resX+ganX*tam,resY+ganY*tam,tam,tam,this);
                break;
            }
            case 'u':{
                g.drawImage(personajeAr.getImage(),resX+ganX*tam,resY+ganY*tam,tam,tam,this);
                break;
            }
            case 'r':{
                g.drawImage(personajeDe.getImage(),resX+ganX*tam,resY+ganY*tam,tam,tam,this);
                break;
            }
            case 'd':{
                g.drawImage(personajeAb.getImage(),resX+ganX*tam,resY+ganY*tam,tam,tam,this);
                break;
            }
            default:{
                g.drawImage(personajeDe.getImage(),resX+ganX*tam,resY+ganY*tam,tam,tam,this);
                break;
            }
        }
        //Fantasmas        
        if(comerFan==true){
            g.drawImage(imgChefBlanco.getImage(),resX+fanRojoX*tam,resY+fanRojoY*tam,tam,tam,this);
            g.drawImage(imgChefBlanco.getImage(),resX+fanRosaX*tam,resY+fanRosaY*tam,tam,tam,this);
            g.drawImage(imgChefBlanco.getImage(),resX+fanAzulX*tam,resY+fanAzulY*tam,tam,tam,this);
            g.drawImage(imgChefBlanco.getImage(),resX+fanNaranjaX*tam,resY+fanNaranjaY*tam,tam,tam,this);
        }else{
            g.drawImage(imgChefRojo.getImage(),resX+fanRojoX*tam,resY+fanRojoY*tam,tam,tam,this);
            g.drawImage(imgChefRosa.getImage(),resX+fanRosaX*tam,resY+fanRosaY*tam,tam,tam,this);
            g.drawImage(imgChefAzul.getImage(),resX+fanAzulX*tam,resY+fanAzulY*tam,tam,tam,this);
            g.drawImage(imgChefNaranja.getImage(),resX+fanNaranjaX*tam,resY+fanNaranjaY*tam,tam,tam,this);
        }
        //Dibujar puntaje
        g.setFont(new Font("arial",3,20));
        g.setColor(Color.YELLOW);
        g.drawString("Puntos: "+puntosTotales,50,25);
        g.drawString("Pantallas: "+pantallas,260,25);
        g.drawString("Vidas: "+vidas,510,25);
    }
    public PanelJugar(){//Propiedades del panel y acciones
        setLayout(null);
        setFocusable(true);
        setBackground(Color.BLACK);
        iniciarNivel();
        controlesJugadores();
        moverJugadores();
        moverFantasmas();
        sonidoIniciar.play();
        pausarJuego(5300);
    }
    private void iniciarNivel(){//Asignamos variables
        puntos=0;
        direccionGan=' ';
        comerFan=false;
        mapa=lab.laberintos(nivel);
        tam=lab.tam;
        resX=lab.resX;
        resY=lab.resY;
        filas=lab.filas;
        columnas=lab.columnas;
        canComida=lab.canComida;
        ganX=lab.ganX;
        ganY=lab.ganY;
        spawnGanX=lab.spawnGanX;
        spawnGanY=lab.spawnGanY;
        fanRojoX=lab.fanRojoX;
        fanRojoY=lab.fanRojoY;
        velocidadFan=lab.velocidadFan;
        spawnFanRojoX=lab.spawnFanRojoX;
        spawnFanRojoY=lab.spawnFanRojoY;
        fanRosaX=lab.fanRosaX;
        fanRosaY=lab.fanRosaY;
        spawnFanRosaX=lab.spawnFanRosaX;
        spawnFanRosaY=lab.spawnFanRosaY;
        fanAzulX=lab.fanAzulX;
        fanAzulY=lab.fanAzulY;
        spawnFanAzulX=lab.spawnFanAzulX;
        spawnFanAzulY=lab.spawnFanAzulY;
        fanNaranjaX=lab.fanNaranjaX;
        fanNaranjaY=lab.fanNaranjaY;
        spawnFanNaranjaX=lab.spawnFanNaranjaX;
        spawnFanNaranjaY=lab.spawnFanNaranjaY;
    }
    private void perderVidas(){
        Runnable rMorir=()->{
            sonidoMorir.play();
        };
        Thread hiloMorir=new Thread(rMorir);
        hiloMorir.start();
        
        vidas--;
        if(vidas==0){
            try{
                comerFan=false;
                PanelJugar.this.removeKeyListener(controlesJugadores);
                delayMoverJugadores.cancel();
                delayMoverFantasmas.cancel();
                sonidoSirena.stop();
                Thread.sleep(3000);
                PanelRecords info=new PanelRecords();
                do{
                    info.nombre=JOptionPane.showInputDialog(null,"Ingresa tu Nombre");
                    info.puntos=puntosTotales;
                    info.pantallas=String.valueOf(pantallas);
                    PanelRecords.lista.add(info);
                    info.ordenarDatos();
                }while(info.nombre==null||info.nombre.equals(""));
                setVisible(false);
                VentanaPrincipal.pInicio.setVisible(true);
            }catch(InterruptedException ex){}
        }else{
            try{
                pausarJuego(3000);
                Thread.sleep(3000);
                direccionGan=' ';
                ganX=spawnGanX;
                ganY=spawnGanY;
            }catch(InterruptedException e){}
        }
    }
    private void controlesJugadores(){//COntroles de los Jugadores
        controlesJugadores=new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent ke){
                //Jugador 1
                if(ke.getKeyCode()==37){//Izquierda
                    direccionGan='l';
                }
                if(ke.getKeyCode()==38){//Arriba
                    direccionGan='u';
                }
                if(ke.getKeyCode()==39){//Derecha
                    direccionGan='r';
                }
                if(ke.getKeyCode()==40){//Abajo
                    direccionGan='d';
                }
                //Jugador 2
                if(ke.getKeyChar()=='a'){//Izquierda
                    direccionJugador2='l';
                }
                if(ke.getKeyChar()=='w'){//Arriba
                    direccionJugador2='u';
                }
                if(ke.getKeyChar()=='d'){//Derecha
                    direccionJugador2='r';
                }
                if(ke.getKeyChar()=='s'){//Abajo
                    direccionJugador2='d';
                }
                //Tecla Pausa
                if(ke.getKeyChar()=='p'){
                    sonidoClick.play();
                    delayMoverJugadores.cancel();
                    delayMoverFantasmas.cancel();
                    sonidoSirena.stop();
                    int opcion=JOptionPane.showOptionDialog(null,"Menu de Pausa","Gan-Man",1,1,null,opciones,opciones);
                    switch(opcion){
                        case 0:{
                            moverJugadores();
                            moverFantasmas();
                            sonidoSirena.loop();
                            break;
                        }
                        case 1:{
                            setVisible(false);
                            VentanaPrincipal.pInicio.setVisible(true);
                            break;
                        }
                    }
                }
            }
        };
        this.addKeyListener(controlesJugadores);
    }
    private void moverJugadores(){
        TimerTask tareaMoverJugadores=new TimerTask(){//Tarea que se ejecuta siempre
            @Override
            public void run(){
                switch(direccionGan){
                    case 'l':{
                        if(mapa[ganY][(ganX)-1]!=1){
                            ganX--;
                        }
                        break;
                    }
                    case 'u':{
                        if(mapa[(ganY)-1][ganX]!=1){
                            ganY--;
                        }
                        break;
                    }
                    case 'r':{
                        if(mapa[ganY][(ganX)+1]!=1){
                            ganX++;
                        }
                        break;
                    }
                    case 'd':{
                        if(mapa[(ganY)+1][ganX]!=1){
                            ganY++;
                        }
                        break;
                    }
                }
                if(jugador2){
                    switch(direccionJugador2){
                        case 'l':{
                            if(mapa[fanRojoY][(fanRojoX)-1]!=1){
                                fanRojoX--;
                            }
                            break;
                        }
                        case 'u':{
                            if(mapa[(fanRojoY)-1][fanRojoX]!=1){
                                fanRojoY--;
                            }
                            break;
                        }
                        case 'r':{
                            if(mapa[fanRojoY][(fanRojoX)+1]!=1){
                                fanRojoX++;
                            }
                            break;
                        }
                        case 'd':{
                            if(mapa[(fanRojoY)+1][fanRojoX]!=1){
                                fanRojoY++;
                            }
                            break;
                        }
                    }
                }
                verificarComida();
                verificarMapa();
            }
        };
        delayMoverJugadores=new Timer();
        delayMoverJugadores.scheduleAtFixedRate(tareaMoverJugadores,0,200);
    }
    private void moverFantasmas(){
        TimerTask tareaMoverFantasmas=new TimerTask(){//Tarea para mover los fantasas
            @Override
            public void run(){
                switch(direccionFan){
                    case 'l':{
                        if(mapa[fanNaranjaY][(fanNaranjaX)+1]!=1){
                            fanNaranjaX++;
                        }
                        if(jugador2==false){
                            if(mapa[fanRojoY][(fanRojoX)+1]!=1){
                                fanRojoX++;
                            }
                        }
                        if(mapa[fanRosaY][(fanRosaX)-1]!=1){
                            fanRosaX--;
                        }
                        if(mapa[fanAzulY][(fanAzulX)-1]!=1){
                            fanAzulX--;
                        }
                        break;
                    }
                    case 'u':{
                        if(mapa[fanAzulY][(fanAzulX)+1]!=1){
                            fanAzulX++;
                        }
                        if(jugador2==false){
                            if(mapa[(fanRojoY)-1][fanRojoX]!=1){
                                fanRojoY--;
                            }
                        }
                        if(mapa[(fanRosaY)-1][fanRosaX]!=1){
                            fanRosaY--;
                        }
                        if(mapa[(fanAzulY)-1][fanAzulX]!=1){
                            fanAzulY--;
                        }
                        break;
                    }
                    case 'r':{
                        repaint();
                        if(mapa[fanRosaY][(fanRosaX)+1]!=1){
                            fanRosaX++;
                        }
                        if(mapa[(fanRosaY)+1][fanRosaX]!=1){
                            fanRosaY++;
                        }
                        if(mapa[(fanNaranjaY)+1][fanNaranjaX]!=1){
                            fanNaranjaY++;
                        }
                        if(mapa[(fanAzulY)+1][fanAzulX]!=1){
                            fanAzulY++;
                        }
                        break;
                    }
                    case 'd':{
                        if(jugador2==false){
                            if(mapa[(fanRojoY)+1][fanRojoX]!=1){
                                fanRojoY++;
                            }
                        }
                        if(mapa[fanNaranjaY][(fanNaranjaX)-1]!=1){
                            fanNaranjaX--;
                        }
                        if(mapa[(fanNaranjaY)-1][fanNaranjaX]!=1){
                            fanNaranjaY--;
                        }
                        if(jugador2==false){
                            if(mapa[fanRojoY][(fanRojoX)-1]!=1){
                                fanRojoX--;
                            }
                        }
                        break;
                    }
                }
                verificarPosision();
                direccionFan=lab.controlesFantasmas();
            }
        };
        delayMoverFantasmas=new Timer();
        delayMoverFantasmas.scheduleAtFixedRate(tareaMoverFantasmas,0,velocidadFan);
    }
    private void verificarComida(){
        switch(mapa[ganY][ganX]){
            case 2:{//Comida
                if(tono==0){
                    Runnable rTU=()->{
                        sonidoTU.play();
                    };
                    Thread hiloTU=new Thread(rTU);
                    hiloTU.start();
                    tono++;
                }else{
                    Runnable rKI=()->{
                        sonidoKI.play();
                    };
                    Thread hiloKI=new Thread(rKI);
                    hiloKI.start();
                    tono--;
                }
                mapa[ganY][ganX]=0;
                puntos++;
                puntosTotales+=10;
                break;
            }
            case 3:{//Coctel
                mapa[ganY][ganX]=0;
                puntosTotales+=300;
                break;
            }
            case 4:{//Pulpo                
                Runnable rComerFan=()->{
                    try{
                        sonidoSirena.stop();
                        sonidoFondoComerChef.loop();
                        mapa[ganY][ganX]=0;
                        puntosTotales+=50;
                        comerFan=true;
                        Thread.sleep(10000);
                        sonidoSirena.loop();
                        sonidoFondoComerChef.stop();
                        comerFan=false;
                    }catch(InterruptedException e){}
                };
                Thread hiloComerFan=new Thread(rComerFan);
                hiloComerFan.start();
                break;
            }
        }
        if(puntosTotales==10000){
            vidas++;
            puntosTotales+=10;
        }else if(puntosTotales==40000){
            vidas++;
            puntosTotales+=10;
        }
    }
    private void verificarPosision(){
        Runnable rComerFan=()->{
            try{
                PanelJugar.this.removeKeyListener(controlesJugadores);
                sonidoComerChef.play();
                delayMoverJugadores.cancel();
                delayMoverFantasmas.cancel();
                Thread.sleep(1000);
                PanelJugar.this.addKeyListener(controlesJugadores);
                moverJugadores();
                moverFantasmas();
                puntosTotales+=50;
            }catch(InterruptedException e){}
        };
        Thread hiloComerFan=new Thread(rComerFan);
        
        Runnable rPastilla=()->{
            if(fanRojoX==ganX&&fanRojoY==ganY&&comerFan==false){
                perderVidas();
            }else if(fanRosaX==ganX&&fanRosaY==ganY&&comerFan==false){
                perderVidas();
            }else if(fanAzulX==ganX&&fanAzulY==ganY&&comerFan==false){
                perderVidas();
            }else if(fanNaranjaX==ganX&&fanNaranjaY==ganY&&comerFan==false){
                perderVidas();
            }
            if(fanRojoX==ganX&&fanRojoY==ganY&&comerFan==true){
                hiloComerFan.start();
                direccionJugador2=' ';
                fanRojoX=spawnFanRojoX;
                fanRojoY=spawnFanRojoY;
            }else if(fanRosaX==ganX&&fanRosaY==ganY&&comerFan==true){
                hiloComerFan.start();
                fanRosaX=spawnFanRosaX;
                fanRosaY=spawnFanRosaY;
            }else if(fanAzulX==ganX&&fanAzulY==ganY&&comerFan==true){
                hiloComerFan.start();
                fanAzulX=spawnFanAzulX;
                fanAzulY=spawnFanAzulY;
            }else if(fanNaranjaX==ganX&&fanNaranjaY==ganY&&comerFan==true){
                hiloComerFan.start();
                fanNaranjaX=spawnFanNaranjaX;
                fanNaranjaY=spawnFanNaranjaY;
            }
        };
        Thread hiloPastillas=new Thread(rPastilla);
        hiloPastillas.start();
    }
    private void verificarMapa(){
        Runnable rVerificar=()->{
            if(puntos>=canComida){
                try{
                    sonidoGanar.play();
                    pausarJuego(2000);
                    Thread.sleep(2000);
                    pantallas++;
                    nivel++;
                    if(nivel==4){
                        nivel=1;
                    }
                    iniciarNivel();
                    sonidoIniciar.play();
                    pausarJuego(5300);
                }catch(InterruptedException e){}
            }
        };
        Thread hiloVerificar=new Thread(rVerificar);
        hiloVerificar.start();
    }
    private void pausarJuego(int tiempo){
        Runnable rPausar=()->{
            try{
                PanelJugar.this.removeKeyListener(controlesJugadores);
                delayMoverJugadores.cancel();
                delayMoverFantasmas.cancel();
                sonidoSirena.stop();
                Thread.sleep(tiempo);
                PanelJugar.this.addKeyListener(controlesJugadores);
                moverJugadores();
                moverFantasmas();
                sonidoSirena.loop();
            }catch(InterruptedException e){}
        };
        Thread hiloPausar=new Thread(rPausar);
        hiloPausar.start();
    }
}
