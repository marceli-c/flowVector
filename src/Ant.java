import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ant extends Rectangle {
    int coordinates[]=new int[2],vector[]=new int[2];
    boolean active=false;
    public Ant(){

    }
    public Ant(int x,int y){
        coordinates[0]=y;
        coordinates[1]=x;
        vector[0]=0; //y
        vector[1]=0; //x
    }
    public Ant(int xy){
        coordinates[0]=coordinates[1]=xy;
    }

    public void activateAnt(boolean active){
        this.active=active;
    }
    public void changeCoordinates(int x, int y){
        coordinates[0]=y;
        coordinates[1]=x;
    }
    public int[] getCoordinates(){
        return this.coordinates;
    }
    public int[] getVector(){return this.vector;}
    public void setVector(int x,int y){
        this.vector[0] += y;
        this.vector[1] += x;
    }
    public void moveAnt(){
        this.translate(this.vector[1],this.vector[0]);
    }
    public void resetVector(){
        this.vector[1]=0;
        this.vector[0]=0;
    }


}
