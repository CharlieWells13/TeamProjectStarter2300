import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameRunner { // sets up the game
    public static void main(String[] args) {
        /*JFrame frame = new JFrame("Tanks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Board board = new Board();

        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
        
        LevelLoader lv = new LevelLoader();
        try {
            lv.loadLevel();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int[][] testLevel = lv.getCurrentLevel();

        for (int i = 0; i <testLevel.length; i++) {
            for (int j = 0; j < testLevel.length; j++) {
                System.out.print(testLevel[i][j]);
            }
            System.out.println();
        }


    
    
    }
}
