import java.awt.Graphics2D;
import java.util.Random;
import java.awt.Color;

public class Bouncer extends LevelTile {

    public Bouncer(int x, int y, int width, int height, gamePanel panel) {
        super(x, y, width, height, panel);
        this.tileType = 7;
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }

    public void collideX(Player player, LevelTile wall) {
        player.setXSpeed(player.getXSpeed() *-2);
        player.setYSpeed(-4);
    }
    
    public void collideY(Player player, LevelTile wall) {
        player.setYSpeed(-10);
        if (player.getXSpeed() == 0) {
            Random r = new Random();
            player.setXSpeed(r.nextDouble(-5,5));
        }
    }
    
}
