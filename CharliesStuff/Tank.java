import javax.swing.*;
import java.awt.*;

public class Tank extends JComponent { //base tank class,pretty simple, tank draws itself, unlike Pacman
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int speed;

    public Tank(int speed) {
        this.speed = speed;
        setPreferredSize(new Dimension(30, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        //repaint();
    }
}