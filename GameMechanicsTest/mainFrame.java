import javax.swing.*;

public class mainFrame extends JFrame {

    public mainFrame(){
        gamePanel panel = new gamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        Background background = new Background(panel);
        this.add(panel);

        addKeyListener(new inputChecker(panel));
    }
}
