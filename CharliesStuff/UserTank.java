import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;

class UserTank extends Tank { // this class extends Tank and makes the tank moveable via keypresses
    private List<Integer> pressedKeys = new ArrayList<>();

    public UserTank(int speed) {
        super(speed);
        setFocusable(true);
        setPreferredSize(new Dimension(30, 30));
        addKeyListener(new TankKeyAdapter());
    }

    public void moveTank() {

        dx = 0;
        dy = 0;

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
    }

    private class TankKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (!pressedKeys.contains(key)) {
                pressedKeys.add(key);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            pressedKeys.remove((Integer) key);
        }
    }
}


