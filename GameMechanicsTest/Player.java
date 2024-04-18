import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Player {
    
    gamePanel panel;

    BufferedImage playerSprite; // uses other style of loading images

    int x, y;
    int width, height;

    double xspeed, yspeed;

    Rectangle hitBox;

    Boolean keyUp = false;
    Boolean keyDown = false;
    Boolean keyLeft = false;
    Boolean keyRight = false;
    Boolean keySwitch = true;

    int maxSpeed = 10;
    int jumpPower = -10;
    double gravity = 0.4;
    int climbingModeSlowDown = 4;

    public Player(int x, int y, gamePanel panel){
        this.panel = panel;
        this.x = x;
        this.y = y;

        this.width = 50;
        this.height = 100;
        hitBox = new Rectangle(x, y, width, height);

        // get player sprite
        try{
            playerSprite = ImageIO.read(new File("testPlayer.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // switches player movement mode
    public void toggleMode(){
        for(Grabbable grabbable : panel.grabbables){
            if(hitBox.intersects(grabbable.hitBox)){
                keySwitch = !keySwitch;
                break;
            }
        }
    }

    public int modeCheck(){
        for(Grabbable grabbable : panel.grabbables){
            if(hitBox.intersects(grabbable.hitBox)){
                return 0;
            }
        }
        keySwitch = true;
        return 1;
    }

    // Player Movement
    public void set(){
        HorizontalMovement();
        VerticleMovement();
        CollisionCheck();

        modeCheck();

        x += xspeed;
        y += yspeed;

        hitBox.x = x;
        hitBox.y = y;
    }

    // Left and Right Movement
    public void HorizontalMovement(){
        if(keyLeft && keyRight || !keyLeft && !keyRight){
            if(!keySwitch){
                xspeed *= 0.8;
            }
            else{
                hitBox.y++;
                for(Wall wall : panel.walls){
                    if(hitBox.intersects(wall.hitBox)){
                        xspeed *= 0.8;
                        break;
                    }
                }
                hitBox.y--;
            }
        }
        else if(keyLeft && !keyRight){
            if(!keySwitch){
                xspeed--;
            }
            else{
                hitBox.y++;
                for(Wall wall : panel.walls){
                    if(hitBox.intersects(wall.hitBox)){
                        xspeed--;
                        break;
                    }
                }
                hitBox.y--;
            }
        }
        else if(!keyLeft && keyRight){
            if(!keySwitch){
                xspeed++;
            }
            else{
                hitBox.y++;
                for(Wall wall : panel.walls){
                    if(hitBox.intersects(wall.hitBox)){
                        xspeed++;
                        break;
                    }
                }
                hitBox.y--;
            }
        }

        if(xspeed > -0.75 && xspeed < 0.75){
            xspeed = 0;
        }

        if(keySwitch){
            if(xspeed > maxSpeed){
                xspeed = maxSpeed;
            }
            else if(xspeed < -maxSpeed){
                xspeed = -maxSpeed;
            }
        }
        else{
            if(xspeed > maxSpeed/climbingModeSlowDown){
                xspeed = maxSpeed/climbingModeSlowDown;
            }
            else if(xspeed < -maxSpeed/climbingModeSlowDown){
                xspeed = -maxSpeed/climbingModeSlowDown;
            }
        }
    }

    // Up and Down Movement     (Contains movement differences for tank and platformer movement types)
    public void VerticleMovement(){
        if(keySwitch){
            //up and down
            if(keyUp){      // only able to jump when on ground
                hitBox.y++;
                for(Wall wall : panel.walls){
                    if(hitBox.intersects(wall.hitBox)){
                        yspeed = jumpPower;
                        break;
                    }
                }
                hitBox.y--;
            }
            yspeed += gravity;    //gravity
        }
        else{
            // tank style 2d movement
            if(keyUp && keyDown || !keyUp && !keyDown){
                yspeed *= 0.8;
            }
            else if(keyUp && !keyDown){
                yspeed--;
            }
            else if(!keyUp && keyDown){
                yspeed++;
            }
            if(yspeed > -0.75 && yspeed < 0.75){
                yspeed = 0;
            }
    
            if(yspeed > maxSpeed/climbingModeSlowDown){
                yspeed = maxSpeed/climbingModeSlowDown;
            }
            else if(yspeed < -maxSpeed/climbingModeSlowDown){
                yspeed = -maxSpeed/climbingModeSlowDown;
            }
        }
    }

    // Collision checking
    public void CollisionCheck(){
        // Horizontal Collission Checking
        hitBox.x += xspeed;
        for(Wall wall : panel.walls){
            if(hitBox.intersects(wall.hitBox)){
                hitBox.x -= xspeed;
                while(!wall.hitBox.intersects(hitBox)){
                    hitBox.x += Math.signum(xspeed);
                }
                hitBox.x -= Math.signum(xspeed);
                xspeed = 0;
                x = hitBox.x;
            }
        }
        
        // Verticle Collission Checking
        hitBox.y += yspeed;
        for(Wall wall : panel.walls){
            if(hitBox.intersects(wall.hitBox)){
                hitBox.y -= yspeed;
                while(!wall.hitBox.intersects(hitBox)){
                    hitBox.y += Math.signum(yspeed);
                }
                hitBox.y -= Math.signum(yspeed);
                yspeed = 0;
                y = hitBox.y;
            }
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(playerSprite, x, y, panel);
    }
}
