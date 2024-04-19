import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

public class platformerTest{

    public static void main(String[] args) {
        mainFrame frame = new mainFrame();          // make new frame (extends JFrame)
        frame.setSize(900, 900);
        frame.setTitle("PlatformerTest");

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize(); //get dimensions of screen
        
        // puts window in center of the screen (not needed)
        int width = (int)(screenDimension.getWidth()/2 - frame.getSize().getWidth()/2);
        int height = (int)(screenDimension.getHeight()/2 - frame.getSize().getHeight()/2);
        frame.setLocation(width, height);
        
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}