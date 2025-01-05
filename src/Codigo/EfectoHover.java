package Codigo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class EfectoHover {//Aqui esta el efecto del hover
    public static void botonHover(JButton btn){
        btn.setFont(new Font("arial",3,19));
        btn.setBackground(Color.WHITE);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent me){//Cuando entra
                btn.setCursor(new Cursor(12));
                btn.setBackground(Color.YELLOW);
                btn.setHorizontalAlignment(SwingConstants.CENTER);
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE,4));
            }
            @Override
            public void mouseExited(MouseEvent me){//Cuando sale
                btn.setBackground(Color.WHITE);
                btn.setHorizontalAlignment(SwingConstants.LEFT);
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK,0));
            }
        });
    }
}
