import java.awt.Graphics2D;
import java.awt.Color;

public class Grabbable extends LevelTile {

    public Grabbable(int x, int y, int width, int height){
        super(x, y, width, height);
        this.tileType = 2;
    }

    @Override   // left as override cause idk how loading textures will be easiest
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }
}
