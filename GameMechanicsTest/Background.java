import javax.swing.*;

public class Background {

    private gamePanel panel;
    private JLabel curBackground;
    
    public Background(gamePanel panel){
        this.panel = panel;

        ImageIcon icon = new ImageIcon("Backgrounds/defaultBackground.png");
        this.curBackground = new JLabel(icon);
        panel.add(curBackground);
    }

    public void setBackground(String fileName){
        ImageIcon icon = new ImageIcon(fileName);
        curBackground.setIcon(icon);
        panel.add(curBackground);
    }
}
