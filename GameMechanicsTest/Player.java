
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

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

    int maxSpeed = 5;
    int jumpPower = -8;
    double gravity = 0.4;
    int climbingModeSlowDown = 4;

    public Player(int x, int y, gamePanel panel){
        this.panel = panel;
        this.x = x;
        this.y = y;

        this.width = 32;
        this.height = 32;
        hitBox = new Rectangle(x, y, width, height);

        // get player sprite
        try{
            playerSprite = ImageIO.read(new File("placeholder.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public void setPos(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
    public double getXSpeed() {
        return this.xspeed;
    }
    public double getYSpeed() {
        return this.yspeed;
    }
    public void setXSpeed(double newSpeed) {
        this.xspeed = newSpeed;
    }
    public void setYSpeed(double newSpeed) {
        this.yspeed = newSpeed;
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
                xspeed *= 0.5;
            }
            else{
                hitBox.y++;
                for(LevelTile wall : panel.walls){
                    if (wall instanceof Wall) {
                    if(hitBox.intersects(wall.hitBox)){
                        xspeed *= 0.5;
                        break;
                    }
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
                for(LevelTile wall : panel.walls){
                    if (wall instanceof Wall) {
                    if(hitBox.intersects(wall.hitBox)){
                        xspeed--;
                        break;
                    }
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
                for(LevelTile wall : panel.walls){
                    if (wall instanceof Wall) {
                    if(hitBox.intersects(wall.hitBox)){
                        xspeed++;
                        break;
                    }
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
                for(LevelTile wall : panel.walls){
                    if (wall instanceof Wall) {
                        if(hitBox.intersects(wall.hitBox)){
                            yspeed = jumpPower;
                            break;
                        }
                    }

                }
                hitBox.y--;
            }
            else if (!keyUp && keyDown) {
                yspeed++;
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
                yspeed+=2;
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
    public void CollisionCheck() {
        
        // Horizontal Collision Checking
        hitBox.x += xspeed;
        for (LevelTile wall : panel.walls) {
            if (hitBox.intersects(wall.hitBox)) {
                hitBox.x -= xspeed;
                wall.collideX(this, wall);
                x = hitBox.x;
        }
    }
        // Vertical Collision Checking
        hitBox.y += yspeed;
        for (LevelTile wall : panel.walls) {
            if (hitBox.intersects(wall.hitBox)) {
                hitBox.y -= yspeed;
                wall.collideY(this, wall);
                y = hitBox.y;
            }
        }
    }
    

    public void draw(Graphics2D g2d){
        g2d.drawImage(playerSprite, x, y, panel);
    }
}
