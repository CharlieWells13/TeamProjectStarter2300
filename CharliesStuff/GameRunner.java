import javax.swing.*;
import java.awt.*;

public class GameRunner { // sets up the game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tanks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Board board = new Board();

        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
