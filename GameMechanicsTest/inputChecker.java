import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class inputChecker extends KeyAdapter {
    
    gamePanel panel;

    public inputChecker(gamePanel panel){
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e){
        panel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        panel.keyReleased(e);
    }
}
