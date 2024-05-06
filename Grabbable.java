import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Color;

public class Grabbable extends LevelTile {

    BufferedImage texture;

    public Grabbable(int x, int y, int width, int height, gamePanel panel, int textureNum){
        super(x, y, width, height, panel);
        this.tileType = 2;

        try{
            String textureName = "StoneClimbableTexture";
            if(textureNum == 1){
                textureName = "StoneClimbableTexture";
            }
            else if(textureNum == 2){
                //textureName = "CementClimableTexture"; (doesnt exist yet)
            }

            String fileName = "Textures/" + textureName + ".png";
            texture = ImageIO.read(new File(fileName));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override   // left as override cause idk how loading textures will be easiest
    public void draw(Graphics2D g2d){
        g2d.drawImage(texture, x, y, panel);
    }
}
