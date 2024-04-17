import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Board extends JPanel {
    private Timer timer;
    private UserTank userTank;

    public Board() {
        setPreferredSize(new Dimension(800, 600)); // setiing up the board
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        userTank = new UserTank(5);

        add(userTank, BorderLayout.CENTER);

        timer = new Timer (40, new ActionListener() { //just like pacman, every 40 milliseconds, it calls move tank and refreshes the screen
			public void actionPerformed(ActionEvent e) {
                userTank.moveTank();
				repaint();				
			}
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) { // this is called by repaint
        super.paintComponent(g);
        userTank.paintComponent(g);
    }
}

