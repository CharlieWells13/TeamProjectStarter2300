import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Tank extends JComponent {

    protected int x,y;
    protected int dx, dy;

    protected int speed;
    protected int hp;

    public void moveTank(){
    }


    public Tank(int speed){
        this.speed = speed;
        this.hp = 10;

    }

    public Tank(int speed, int hp){
        this.speed = speed;
        this.hp = hp;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}