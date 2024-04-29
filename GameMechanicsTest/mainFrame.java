import javax.swing.*;

public class mainFrame extends JFrame {

    public mainFrame(){
        this.setSize(814, 825);
        gamePanel panel = new gamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        this.add(panel);

        addKeyListener(new inputChecker(panel));
    }
}
