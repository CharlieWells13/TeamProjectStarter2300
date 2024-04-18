import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class gamePanel extends JPanel implements ActionListener{
    
    Player player;

    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Grabbable> grabbables = new ArrayList<Grabbable>();

    Timer gameTimer;

    public gamePanel(){
        player = new Player(300, 200, this);

        makeWalls();
        makeGrabbables();

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            
            @Override
            public void run(){
                player.set();
                repaint();
            }

        }, 0, 17);
    }

    // Load ArrayList with walls to be added to level
    public void makeWalls(){
        for(int i = 50; i <= 550; i += 50){
            walls.add(new Wall(i, 350, 50, 50));
        }
        walls.add(new Wall(0, 350, 50, 50));
        walls.add(new Wall(0, 300, 50, 50));
        walls.add(new Wall(0, 250, 50, 50));
        walls.add(new Wall(550, 300, 50, 50));
        walls.add(new Wall(550, 250, 50, 50));
        walls.add(new Wall(300, 150, 50, 30));
    }

    public void makeGrabbables(){
        for(int i = 350; i <= 550; i += 50){
            for(int j = 0; j < 100; j += 50){
                grabbables.add(new Grabbable(i, j, 50, 50));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae){
    }

    // update scene visuals (called once every frame)
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        for(Wall wall : walls){
            wall.draw(g2d);
        }
        for(Grabbable grabbable : grabbables){
            grabbable.draw(g2d);
        }

        player.draw(g2d);
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            player.keyUp = true;
        }
        if(e.getKeyChar() == 's'){
            player.keyDown = true;
        }
        if(e.getKeyChar() == 'a'){
            player.keyLeft = true;
        }
        if(e.getKeyChar() == 'd'){
            player.keyRight = true;
        }
        if(e.getKeyChar() == 'q'){
            player.toggleMode();
        }
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            player.keyUp = false;
        }
        if(e.getKeyChar() == 's'){
            player.keyDown = false;
        }
        if(e.getKeyChar() == 'a'){
            player.keyLeft = false;
        }
        if(e.getKeyChar() == 'd'){
            player.keyRight = false;
        }

        // causes q to be held in order to switch movement mode (remove if you want clean toggle each press)
        // DOES NOT WORK PERFECTLY *********
        if(e.getKeyChar() == 'q'){
            player.toggleMode();
        }
    }
}
