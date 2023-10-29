import java.awt.*;
public class Rec extends Rectangle{


    int value=1,xx,yy;
    boolean passable=true,ant=false,start=false,end=false;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getXx() {
        return xx;
    }

    public void setXx(int xx) {
        this.xx = xx;
    }

    public int getYy() {
        return yy;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean isAnt() {
        return ant;
    }

    public void setAnt(boolean ant) {
        this.ant = ant;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Rec(int xx, int yy){
        this.xx=xx;
        this.yy=yy;

    }

}
