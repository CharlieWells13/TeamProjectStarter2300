import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

public class gamePanel extends JPanel implements ActionListener{
    
    Player player;

    LevelLoader lv;
    int[][] currentLevel;

    ArrayList<Wall> walls = new ArrayList<Wall>();
    ArrayList<Grabbable> grabbables = new ArrayList<Grabbable>();
    int spawnX;
    int spawnY;
    Timer gameTimer;

    public gamePanel(){
        player = new Player(300, 200, this);

        lv = new LevelLoader();
        try {
            lv.loadLevel();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        currentLevel = lv.getCurrentLevel();
        makeLevel();

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
    public void makeLevel(){
        int xPos = 0;
        int yPos = 0;
        for (int[] curRow : this.currentLevel) {
            for (int curBox : curRow) {
                if (curBox == 1) {
                    walls.add(new Wall (xPos, yPos, 16, 16));
                }
                else if (curBox == 2) {
                    grabbables.add(new Grabbable(xPos, yPos, 16, 16));
                }
                else if (curBox == 3) {
                    player.setPos(xPos, yPos);
                }
                xPos = xPos + 16;
            }
            xPos = 0;
            yPos = yPos + 16;
        }
    }

    public void drawNextLevel() { 
        walls = new ArrayList<Wall>();
        grabbables = new ArrayList<Grabbable>();
        try {
            lv.loadLevel();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        currentLevel = lv.getCurrentLevel();
        makeLevel();

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
        if (e.getKeyChar() == 'n') { //can be removed later, meant for debugging
            drawNextLevel();
            
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
