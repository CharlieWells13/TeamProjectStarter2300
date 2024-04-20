import java.awt.Graphics2D;
import java.awt.Color;

public class Trapper extends LevelTile{

    public Trapper(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.tileType = 8;

    }
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.CYAN);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }


    public void collideX(Player player, LevelTile wall) {
        int tempSpeed = 0;
        if (player.getXSpeed() > 0) {
            tempSpeed = -1;
        }
        else {
            tempSpeed = 1;
        }
        player.setXSpeed(0);
        player.setYSpeed(-3);
        player.setXSpeed(tempSpeed*5);
    }
    public void collideY(Player player, LevelTile wall) {
        int tempSpeed = 0;
        if (player.getXSpeed() > 0) {
            tempSpeed = -1;
        }
        else {
            tempSpeed = 1;
        }
        player.setXSpeed(0);
        player.setYSpeed(-3);
        player.setXSpeed(tempSpeed*5);
    }
}
