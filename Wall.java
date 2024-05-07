import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.Rectangle;

public class Wall extends LevelTile {

    private BufferedImage wallTexture;

    public Wall(int x, int y, int width, int height, gamePanel panel, int textureNum){
        super(x, y, width, height, panel);
        this.tileType = 1;

        try{
            Random rand = new Random();
            int randNum = rand.nextInt(100);
            String type;
            String textureName = "CementTexture1"; // default texture (can change)
            if(randNum > 47){
                type = "1";
            }
            else if(randNum > 7){
                type = "2";
            }
            else if(randNum > 4){
                type = "3";
            }
            else{
                type = "4";
            }

            if(textureNum == 1){
                textureName = "StoneTexture" + type;
            }
            else if(textureNum == 2){
                textureName = "CementTexture" + type;
            }
            else if(textureNum == 3){
                textureName = "StoneArrowTexture";
            }

            String fileName = "Textures/" + textureName + ".png";
            wallTexture = ImageIO.read(new File(fileName));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override       // left as override cause idk how loading textures will be easiest
    public void draw(Graphics2D g2d){
        g2d.drawImage(wallTexture, x, y, panel);
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
