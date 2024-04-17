import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Board extends JPanel {
    private int tankX = 0;
    private int tankY = 0;
    private int speed = 5;

    private Timer timer;
    private ArrayList<Tank> tanks = new ArrayList<>();

    public Board() {
        setPreferredSize(new Dimension(800, 600));
        initBoard();
        initVariables();
        setFocusTraversalPolicy(new ContainerOrderFocusTraversalPolicy());
        setFocusable(true);

        JLabel infoLabel = new JLabel("Test");
        infoLabel.setSize(100, 20);
        infoLabel.setLocation(10, 10); // Adjust location as needed
        add(infoLabel);
    }

    public void addTank(Tank tank){
        tanks.add(tank);
        this.add(tank);
        tank.requestFocus();
    }

    public void addUserTank(UserTank tank) {
        tanks.add(tank);
        tank.addTankKeyAdapter();
        add(tank);
        tank.setVisible(true);
        repaint();
    }


    public void initBoard() {
        setBackground(Color.black);
        setDoubleBuffered(true);
    }

    public void initVariables() {
        timer = new Timer(40, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            for (Tank tank : tanks) {
                tank.moveTank();
            }
            repaint();
            }
        });
        timer.start();
    }
}

