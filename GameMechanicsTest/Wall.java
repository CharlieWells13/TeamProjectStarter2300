import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Wall extends LevelTile {

    BufferedImage wallTexture;

    public Wall(int x, int y, int width, int height, gamePanel panel){
        super(x, y, width, height, panel);
        this.panel = panel;
        this.tileType = 1;

        try{
            Random rand = new Random();
            int randNum = rand.nextInt(100);
            String type;
            if(randNum > 55){
                type = "1";
            }
            else if(randNum > 10){
                type = "2";
            }
            else if(randNum > 5){
                type = "3";
            }
            else{
                type = "4";
            }
            String textureName = "CementTexture" + type;
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

        //g2d.setColor(Color.BLACK);
        //g2d.drawRect(x, y, width, height);
        //g2d.fillRect(x, y, width, height);
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
