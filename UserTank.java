import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class UserTank extends Tank{

    protected ArrayList<Integer> pressedKeys = new ArrayList<Integer>();

    public UserTank(int speed){
        super(speed);
        setFocusable(true);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Focus gained");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("Focus lost");
            }
        });
    }

    public UserTank(int speed, int hp){
        super(speed, hp);
        setFocusable(true);
    }     

    public void addTankKeyAdapter() {
        addKeyListener(new TankKeyAdapter());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30); 
    }


    public void moveTank() {
        //System.out.println("Move Called");
        dx = 0;
        dy = 0;
        //System.out.println(isFocusable() + " " + hasFocus() + " " + isVisible() );

        System.out.println("X: " + x);
        System.out.println("Y: " + y);

        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            dx -= 1;
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            dx += 1;
        }
        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            dy -= 1;
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            dy += 1;
        }

        if ((dx != 0 && dy == 0) || (dx == 0 && dy != 0)) {

            x += dx * speed;
            y += dy * speed;
        } 
        else if (dx != 0 && dy != 0) {
            int scaledSpeed = (int) Math.round(speed / Math.sqrt(2));

            x += dx * scaledSpeed;
            y += dy * scaledSpeed;
        }

        repaint();

    }

    public class TankKeyAdapter extends KeyAdapter {
        public TankKeyAdapter(){
            System.out.println("Created");
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key pressed: " + e.getKeyCode());
            int key = e.getKeyCode();
            if (!pressedKeys.contains(key)) {
                pressedKeys.add(key);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();
            pressedKeys.remove(Integer.valueOf(key));
        }
    }

}