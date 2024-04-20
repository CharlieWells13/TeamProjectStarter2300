import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;

public abstract class LevelTile {
    int x, y;
    int width, height;
    int tileType;
    Rectangle hitBox;

    public LevelTile(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }
    public void collideX(Player player, LevelTile wall) {

    }
    public void collideY(Player player, LevelTile wall) {
        
    }
}
