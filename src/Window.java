import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame implements ActionListener, MouseListener, KeyListener {
    Rec[][] matrix;
    Timer timer=new Timer(5,this);
    List<Integer> coordinatesOfMouse=new ArrayList<>();
    int height=0,width=0,scale=0,number_of_ants=1;
    CelularAutomata automata;

    public Window(int height,int width,int scale){
        this.height = height;
        this.width = width;
        this.scale = scale;

        initializeWindow();
    }
    private void initializeWindow(){
        Mesh mesh=new Mesh(scale,width);
        automata=new CelularAutomata(width,height,scale,number_of_ants);

        this.setSize(width+scale-3,height+2*scale+5);
        this.setVisible(true);
        this.setFocusable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(mesh);
        this.add(automata);


        this.addMouseListener(this);
        this.addKeyListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode()==32) automata.keyStartSpace();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x=e.getX()-5,y=e.getY()-5;
        automata.mouseChange(x,y);
        automata.repaint();


        //System.out.println(e.getX());
        //System.out.println(e.getY());
    }


    @Override
    public void mousePressed(MouseEvent e) {
       // matrix= automata.getMatrix();                 // TUTAJ BĘDZIE DO PRZECIĄGANIA
      //  timer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // timer.stop();
    }

    @Override
    public void mouseEntered(MouseEvent e) {


    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
