
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuPanel extends JPanel implements ActionListener {
    

    public menuPanel() {
        
        setPreferredSize(new Dimension(800, 600)); // Set preferred size to match gamePanel

        JLabel directionsLabel = new JLabel("Directions: Move with W A S D");
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(this); // Add action listener to the start game button

        setLayout(new BorderLayout());
        add(directionsLabel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }    
}
