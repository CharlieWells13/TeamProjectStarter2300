import java.awt.Graphics2D;
import java.awt.Color;
//import javax.swing.ImageIcon;

public class Trapper extends LevelTile{

    //private ImageIcon texture;

    public Trapper(int x, int y, int width, int height, gamePanel panel) {
        super(x, y, width, height, panel);
        this.tileType = 8;
        
        //this.texture = new ImageIcon("Textures/SpinWheel.gif");
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.CYAN);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);

        //texture.paintIcon(panel, g2d, x, y);
    }

    public void collideX(Player player, LevelTile wall) {
        int tempSpeed = 0;
        if (player.getXSpeed() > 0) {
            tempSpeed = -1;
        }
        else {
            tempSpeed = 1;
        }
        player.setXSpeed(tempSpeed);
    }

    public void collideY(Player player, LevelTile wall) {
        int tempSpeed = 0;
        if (player.getYSpeed() > 0) {
            tempSpeed = -1;
        }
        else {
            tempSpeed = 10;
        }
        player.setYSpeed(tempSpeed);
        if (player.getXSpeed() == 0) {
            if (player.lastFacing()) {
                tempSpeed = 1;
            }
            else {
                tempSpeed = -1;
            }
            player.setXSpeed(tempSpeed);
        }

    }
}
