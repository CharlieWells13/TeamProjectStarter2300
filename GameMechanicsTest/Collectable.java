import java.awt.Color;
import java.awt.Graphics2D;

public class Collectable extends LevelTile{

    public Collectable(int x, int y, int width, int height, gamePanel panel) {
        super(x, y, width, height, panel);
        tileType = 9;
        //TODO Auto-generated constructor stub
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }
    public void collideX(Player player, LevelTile wall) {
        player.hitCollectable = true;
        player.curCollectable = wall;

    }
    public void collideY(Player player, LevelTile wall) {
        player.hitCollectable = true;
        player.curCollectable = wall;
    }

}
