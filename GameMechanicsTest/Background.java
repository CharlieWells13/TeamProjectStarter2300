import javax.swing.*;

public class Background {
    
    public Background(gamePanel panel){
        ImageIcon icon = new ImageIcon("testBackground.png");   // uses one style of loading images
        panel.add(new JLabel(icon));
    }
}
