import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;

public class IceBlock extends LevelTile{
    public IceBlock(int x, int y, int width, int height, gamePanel panel) {
        super(x, y, width, height, panel);
        this.tileType = 10;
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }

    public void collideX(Player player, LevelTile wall) {
        Rectangle playerHitBox = player.getHitBox();
        while (!wall.hitBox.intersects(playerHitBox)) {
            playerHitBox.x += Math.signum(player.getXSpeed());
        }
        playerHitBox.x -= Math.signum(player.getXSpeed());
        player.setXSpeed(0);
    }
    
    public void collideY(Player player, LevelTile wall) {
        Rectangle playerHitBox = player.getHitBox();
        while (!wall.hitBox.intersects(playerHitBox)) {
            playerHitBox.y += Math.signum(player.getYSpeed());
        }
        playerHitBox.y -= Math.signum(player.getYSpeed());
        player.setYSpeed(0);
    }


}