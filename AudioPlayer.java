import java.io.File; 
import java.io.IOException;
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
  
public class AudioPlayer {

    Clip sampleClip;
    AudioInputStream audioInputStream;

    public AudioPlayer()  throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        this.audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds/cartoonJumpSound.wav").getAbsoluteFile()); 
         
        sampleClip = AudioSystem.getClip(); 
        
        sampleClip.open(audioInputStream); 
          
        //sampleClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playJump(){
        sampleClip.start();
        sampleClip.setMicrosecondPosition(0); 
    }

}