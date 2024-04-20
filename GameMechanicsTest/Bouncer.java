import java.awt.Graphics2D;
import java.awt.Color;

public class Bouncer extends LevelTile {

    public Bouncer(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.tileType = 7;
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }
    
}
