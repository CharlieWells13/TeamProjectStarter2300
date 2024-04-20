import java.awt.Graphics2D;
import java.awt.Color;

public class Wall extends LevelTile {

    public Wall(int x, int y, int width, int height){
        super(x, y, width, height);
        this.tileType = 1;
    }

    @Override       // left as override cause idk how loading textures will be easiest
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }

    public void collideX(Player player, LevelTile wall) {
        while (!wall.hitBox.intersects(player.hitBox)) {
            player.hitBox.x += Math.signum(player.getXSpeed());
        }
        player.hitBox.x -= Math.signum(player.getXSpeed());
        player.setXSpeed(0);
    }
    public void collideY(Player player, LevelTile wall) {
        while (!wall.hitBox.intersects(player.hitBox)) {
            player.hitBox.y += Math.signum(player.getYSpeed());
        }
        player.hitBox.y -= Math.signum(player.getYSpeed());
        player.setYSpeed(0);
    }
}
