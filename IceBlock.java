import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class IceBlock extends LevelTile{

    BufferedImage texture;

    public IceBlock(int x, int y, int width, int height, gamePanel panel) {
        super(x, y, width, height, panel);
        this.tileType = 10;

        try{
            String fileName = "Textures/IceTexture.png";
            texture = ImageIO.read(new File(fileName));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(texture, x, y, panel);
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