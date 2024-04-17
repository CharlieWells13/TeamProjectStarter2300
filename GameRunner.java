import javax.swing.*;

public class GameRunner{
    public static void main(String[] args){

        JFrame frame = new JFrame("Tanks");
        frame.setFocusable(true);
        frame.setVisible(true); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board board = new Board();
        
        board.initBoard();
        board.initVariables();
        board.setFocusable(true);

        UserTank t1 = new UserTank(5);

        board.addUserTank(t1);

    
        frame.add(board); 
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        

    }
}