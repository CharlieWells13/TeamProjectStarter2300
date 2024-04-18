import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;

public class Grabbable {
    int x, y;
    int width, height;

    Rectangle hitBox;

    public Grabbable(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitBox = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, width, height);
        g2d.fillRect(x, y, width, height);
    }
}
