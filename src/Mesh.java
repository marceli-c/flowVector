import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Mesh extends JPanel{


    int scale,size;
    Mesh(int scale,int size){
        this.setSize(size,size);
        this.size=size;
        this.setPreferredSize(new Dimension(size,size));
        this.scale=scale;
        this.setVisible(true);
        	this.setOpaque(false);


    }
    public void createMesh(Graphics2D g) {
        g.setColor(Color.black);
        g.setBackground(new Color(0,true));
        for(int i=0;i<=size;i+=scale) {
            //g.drawLine(i, 0, i, this.getHeight());
            //g.drawLine(0, i, this.getHeight(), i);
        }


    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d=(Graphics2D)g;
        createMesh(g2d);
    }



}
