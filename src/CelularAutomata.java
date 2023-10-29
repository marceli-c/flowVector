import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class CelularAutomata extends JPanel implements ActionListener {
    int height=0,width=0,scale=0,number_of_ants=0,sizeOfObjects=0;
    Rec[][] matrix;
    ArrayList<Integer> xOfObject=new ArrayList<>(),yOfObject=new ArrayList<>();
    Ant[] ants;       //BEZ MRÓWEK? JEDYNIE W MATRIX>IS_ANT()
    LinkedList<Rec> queueRec=new LinkedList<>(),ends=new LinkedList<>();
    ArrayList<Rec> queueAfterRec=new ArrayList<>(),queueCalculated=new ArrayList<>();
    Timer timer;
    //LinkedList<Ant> queueAnts=new LinkedList<>();

    public CelularAutomata(int width,int height,int scale,int number_of_ants){
        this.height=height;
        this.width=width;
        this.scale=scale;
        this.number_of_ants=number_of_ants;

        initializeMatrix();
        initializeAnts();

        this.setOpaque(false);
        this.repaint();


        queueRec.add(matrix[6][6]);
        dijkstra();
        initializeTimer();

    }
    private void initializeTimer(){
        timer=new Timer(46,this);


    }
    private void initializeAnts(){
        ants=new Ant[number_of_ants];
        for(int i=0;i<number_of_ants;i++){
            ants[i]=new Ant();
            ants[i].setBounds(300+(i*4),300+(i*4),scale/2,scale/2);
        }

    }


    private void initializeMatrix(){
        int x=0,y=0;
        matrix=new Rec[height/scale][width/scale];
        for(int i=0;i<height/scale;i++){

            for(int j=0;j<width/scale;j++){
                matrix[i][j]=new Rec(j,i);

                matrix[i][j].setBounds(x,y,scale,scale);
                //System.out.println("out"+matrix[i][j]);
                x+=scale;
            }
            y+=scale;
            x=0;

        }
    }
    public void mouseChange(int x,int y) {

        for (Rec[] c : matrix) {
            for (Rec cc : c) {
                if (cc.contains(x, y - 26)) {
                    if (cc.isPassable() && !cc.isAnt()) {
                        cc.setPassable(false);

                    } else if (!cc.isPassable()) {
                        cc.setAnt(true);
                        cc.setPassable(true);
                    } else if (cc.isAnt()) {
                        cc.setEnd(true);
                        cc.setAnt(false);

                    } else if (cc.isEnd()){
                        queueRec.add(matrix[cc.getXx()][cc.getYy()]);
                        cc.setEnd(false);
                    }
                    if(!cc.isEnd()&&queueRec.contains(cc)){
                        queueRec.remove(cc);
                    }

                    return;
                }

            }

        }
    }
        public void keyStartSpace(){
            for(Rec[] cc:matrix){
                for(Rec c:cc){
                    if(c.isEnd()) {
                        queueRec.add(c);
                        ends.add(c);
                    }

                }
            }
            queueCalculated.clear();
            queueAfterRec.clear();
            resetMatrixValues();
            dijkstra();
            surfaceOfObjects();
            if(timer.isRunning()) timer.stop();
            else timer.start();

        }


    private void resetMatrixValues(){
        for(Rec[] c:matrix){
            for(Rec cc:c){
                cc.setValue(1);
            }
    }}



    private void dijkstra(){
        while(!queueRec.isEmpty()){
            Rec calculating=queueRec.poll();
            if(!queueAfterRec.contains(calculating)&&
                    //!calculating.isAnt()&&        // JEŻELI TO DAMY TO NIE MRÓWKI BOLKUJĄ SIĘ W CHOKE POINTACH
                    calculating.isPassable()){
                setNeighbours(calculating);
                queueAfterRec.add(calculating);
            }
        }
    }
    private void setNeighbours(Rec calculating){
        //System.out.println("x="+calculating.getX()+" y="+calculating.getY()+" "+calculating.getValue());
        int y=calculating.getYy(),x=calculating.getXx(),value=calculating.getValue();
        if( y!=0 &&
            matrix[x][y-1].isPassable() &&
                !queueCalculated.contains(matrix[x][y-1]) ){

                    Rec upper=matrix[x][y-1];
                    upper.setValue(value+upper.getValue());
                    queueCalculated.add(upper);
                    queueRec.add(upper);
                    //System.out.println("licze1");
        }

        if( y<height/scale-1 &&
            matrix[x][y+1].isPassable()&&
                !queueCalculated.contains(matrix[x][y+1])){

                    Rec lower=matrix[x][y+1];
                    lower.setValue(value+lower.getValue());
                    queueCalculated.add(lower);
                    queueRec.add(lower);
                    //System.out.println("licze2");
        }


        if( x!=0 &&
            matrix[x-1][y].isPassable()&&
                !queueCalculated.contains(matrix[x-1][y])){

                    Rec left=matrix[x-1][y];
                    left.setValue(value+left.getValue());
                    queueCalculated.add(left);
                    queueRec.add(left);
                    //System.out.println("licze3");
        }


        if( x<width/scale-1 &&
            matrix[x+1][y].isPassable()&&
                !queueCalculated.contains(matrix[x+1][y])){

                    Rec right=matrix[x+1][y];
                    right.setValue(value+right.getValue());
                    queueCalculated.add(right);
                    queueRec.add(right);
                    //System.out.println("licze4");
        }



    }
    private void makeMoves(){
        for(int i=0;i<number_of_ants;i++){
            checkClosestVector(ants[i]);
        }


    }

    private void checkClosestVector(Ant calculating){
        int value=9999,i=0;
        int yy=(int)calculating.getY()+(scale/2),xx=(int)calculating.getX();       //Z MRÓWKI
        Rec calcMatrix;
        double xcalc=0,ycalc=0, a=0, b=0;
        ArrayList<Integer> pool=new ArrayList<>();
        for(Rec c:ends){

            calcMatrix=c;
            int x=(int)calcMatrix.getX(),y=(int)calcMatrix.getY();
            a=x-xx;
            b=y-yy;
            double cc=Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
            xcalc=3*(a/cc);
            ycalc=3*(b/cc);
            int xxx=(int)xcalc,yyy=(int)ycalc;
            pool.add(xxx+yyy);
        }
        int w=Collections.max(pool);
        int j=pool.indexOf(w);

        calcMatrix=ends.get(j);
        int x=(int)calcMatrix.getX()+(scale/2),y=(int)calcMatrix.getY()+scale;
        a=x-xx;
        b=y-yy;
        double cc=Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
        xcalc=3*(a/cc);
        ycalc=3*(b/cc);
        int xxx=(int)xcalc,yyy=(int)ycalc;












        //System.out.println("                x= "+x+"            y="+y);
        //System.out.println("                xx= "+xxx+"            yy="+yy);
        //System.out.println("                xxx= "+xxx+"            yyy="+yyy+"         j"+j);
        calculating.setVector(xxx,yyy);
        calculating.moveAnt();










    }
    private void surfaceOfObjects(){
        for(Rec[] cc:matrix){
            for(Rec c:cc){
                if(!c.isPassable()){
                    xOfObject.add((int) c.getX());
                    yOfObject.add((int) c.getY());
                    sizeOfObjects++;
                }
            }
        }

    }
    private void checkClosestPath(Ant calculating){
        int value=9999,i=0;
        int yy=(int)calculating.getY()+(scale/2),xx=(int)calculating.getX()+(scale/2);       //Z MRÓWKI
        Rec calcMatrix=null;
        for(Rec[] cc:matrix){
            for(Rec c:cc){
                if(c.contains(xx,yy)){
                    calcMatrix=c;
                    break;
                }
            }
        }
        if(calcMatrix==null)    return;
        int x=calcMatrix.getXx(),y=calcMatrix.getYy();
        boolean top=y>1?true:false,bottom=y<height/scale?true:false,left=x>1?true:false,right=x<width/scale?true:false;
        int xcalc=0,ycalc=0;

        if(top  && matrix[x][y-1].isPassable()) {
                ycalc-= matrix[x][y - 1].getValue();

          //      System.out.println(" if path 11");

        }
        if(left &&  matrix[x-1][y].isPassable()) {
                xcalc -= matrix[x - 1][y].getValue();

         //       System.out.println("if path 22");

        }
        if(bottom   && matrix[x][y+1].isPassable()) {
                ycalc+= matrix[x][y + 1].getValue();

        //        System.out.println("if path 33");


        }
        if(right    && matrix[x+1][y].isPassable()) {
                xcalc+= matrix[x + 1][y].getValue();
                i = 4;
         //       System.out.println("if path 44");

        }
        System.out.println("x= "+xcalc+" y="+ycalc);
        calculating.setVector(-xcalc,-ycalc);



        /*switch(i){
            case 1:matrix[x][y].setAnt(false);
                //System.out.println("switch1  1 ");
                matrix[x][y-1].setAnt(true);
                break;
            case 2:matrix[x][y].setAnt(false);
                //System.out.println("switch2  2 ");
                matrix[x-1][y].setAnt(true);
                break;
            case 3:matrix[x][y].setAnt(false);
               //System.out.println("switch3  3");
                matrix[x][y+1].setAnt(true);
                break;
            case 4:matrix[x][y].setAnt(false);
                //System.out.println("switch4   4");
                matrix[x+1][y].setAnt(true);
                break;
            case 0://System.out.println("switch 0");
            break;

        }*/

    }
    private void checkVector(){

    }


    public void paint(Graphics g){

        Graphics2D g2d=(Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        paintMatrix(g2d);

    }
    private void paintMatrix(Graphics2D g2d){
        int i=0,j=0;
        for(Rec[] c :matrix){

            for(Rec cc:c){
                int value=0;
                //System.out.println(cc.getValue());
                if (cc.isAnt()) {

                    g2d.setColor(new Color(0, 0, 180));
                    g2d.fill(cc);

                } else if (cc.isEnd()) {
                    g2d.setColor(new Color(255, 0, 0));
                    g2d.fill(cc);
                } else if (!cc.isPassable()) {
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.fill(cc);
                } else {


                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fill(cc);}
                    g2d.setColor(new Color(0,0,180));
                    Font font = new Font("Verdana", Font.PLAIN, 10);
                    g2d.setFont(font);
                    value=cc.getValue();
                    String values=String.valueOf(value);
                    int y=(int) cc.getY();
                    int x=(int) cc.getX();
                    //g2d.drawString(values,x,y);

                }






        }
        for(Ant c:ants){
            g2d.setColor(new Color(40, 0, 180));

            g2d.fill(new Ellipse2D.Double(c.getX(),c.getY(),scale/2,scale/2));
            g2d.setStroke(new BasicStroke());
            g2d.drawLine((int)c.getX(),(int)c.getY(),(int)c.getX()+c.getVector()[1]*2,(int)c.getY()+c.getVector()[0]*2);
        }

    }
    public Rec[][] getMatrix(){
        return matrix;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        makeMoves();
        this.repaint();
    }
}
