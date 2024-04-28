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

    private int NUM_LEVELS = 2;     // number of levels in the game (IMPORTANT THIS IS ACCURATE)
    
    private Player player;

    private Background background;

    private ArrayList<Level> levels = new ArrayList<Level>();
    private int curLevelNum;
    
    private Timer gameTimer;

    public gamePanel(){
        player = new Player(50, 705, this);
        this.background = new Background(this);

        loadLevels();

        setLevel(0);
        player.setLevel(levels.get(0));

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            
            @Override
            public void run(){
                player.set();
                repaint();
            }

        }, 0, 17);
    }

    // loads levels[] with all the levels
    public void loadLevels(){
        LevelLoader lvlLoader = new LevelLoader();
        for(int i = 0; i < NUM_LEVELS; i++){
            Level level = new Level(i, this);
            try {
                lvlLoader.loadLevel(level);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            levels.add(level);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae){
    }

    // update scene visuals (called once every frame)
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        ArrayList<LevelTile> tiles = levels.get(curLevelNum).getLevelTiles();
        ArrayList<Grabbable> grabbables = levels.get(curLevelNum).getGrabbables();

        for(LevelTile tile : tiles){
            tile.draw(g2d);
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
        /*
        if (e.getKeyChar() == 'n') { //can be removed later, meant for debugging
            drawNextLevel();
            
        }
        if (e.getKeyChar() == 'r') { //resets current level, also meant for debugging
            makeLevel();
        }
        */
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

    public void setLevel(int levelNum){
        this.curLevelNum = levelNum;
        background.setBackground(levels.get(curLevelNum).getBackgroundName());
    }
}
