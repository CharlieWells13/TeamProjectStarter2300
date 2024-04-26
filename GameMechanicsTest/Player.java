
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class Player {
    
    gamePanel panel;

    ImageIcon playerSprite;

    int x, y;
    int width, height;

    double xspeed, yspeed;

    Rectangle hitBox;

    Boolean keyUp = false;
    Boolean keyDown = false;
    Boolean keyLeft = false;
    Boolean keyRight = false;
    Boolean keySwitch = true;
    Boolean hitCollectable = false;

    int maxSpeed = 5;
    int jumpPower = -8;
    double gravity = 0.4;
    int climbingModeSlowDown = 4;
    LevelTile curCollectable = null;

    Boolean isInAir = false;

    ImageIcon walkLeft = new ImageIcon("AlligatorAnimations/Alligator-Walk-Left.gif");
    ImageIcon walkRight = new ImageIcon("AlligatorAnimations/Alligator-Walk-Right.gif");
    ImageIcon standstillRight = new ImageIcon("AlligatorAnimations/Alligator-Standstill-Right.png");
    ImageIcon standstillLeft = new ImageIcon("AlligatorAnimations/Alligator-Standstill-Left.png");
    ImageIcon freefallLeft = new ImageIcon("AlligatorAnimations/Alligator-FreeFall-Left.png");
    ImageIcon freefallRight = new ImageIcon("AlligatorAnimations/Alligator-FreeFall-Right.png");
    ImageIcon jump = new ImageIcon("AlligatorAnimations/Alligator-Jump.gif");


    String currentAnimation = "Standstill";

    Boolean lastFacedRight = true;

    public Player(int x, int y, gamePanel panel){
        this.panel = panel;
        this.x = x;
        this.y = y;

        this.width = 32;
        this.height = 64;
        hitBox = new Rectangle(x, y, width, height);

        // get player sprite
        playerSprite = standstillRight;

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
        //System.out.println(this.y);
        HorizontalMovement();
        VerticleMovement();
        CollisionCheck();
        airBornCheck();
        leaveCheck();

        modeCheck();

        x += xspeed;
        y += yspeed;

        hitBox.x = x;
        hitBox.y = y;
    }

    // Left and Right Movement
    public void HorizontalMovement(){
            if(keyLeft && keyRight || !keyLeft && !keyRight){
                if(!isInAir){
                    if(!currentAnimation.equals("Standstill-Left") && !currentAnimation.equals("Standstill-Right")){
                        if(lastFacedRight){
                            playerSprite = standstillRight;
                            currentAnimation = "Standstill-Right";
                        }
                        else{
                            playerSprite = standstillLeft;
                            currentAnimation = "Standstill-Left";
                        }
                    }   
                }

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
            else if(keyLeft && !keyRight){ //moving left
                if(!isInAir){
                    if(currentAnimation != "Walk-Left"){
                        playerSprite = walkLeft;
                        currentAnimation = "Walk-Left";
                        lastFacedRight = false;
                    }
                }
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
            else if(!keyLeft && keyRight){//moving right
                if(!isInAir){
                    if(currentAnimation != "Walk-Right"){
                        playerSprite = walkRight;
                        currentAnimation = "Walk-Right";
                        lastFacedRight = true;
                    }
                }
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

            if(isInAir){
                if(!currentAnimation.equals("FreeFall-Left") && !currentAnimation.equals("FreeFall-Right")){
                    if(lastFacedRight){
                        playerSprite = freefallRight;
                        currentAnimation = "FreeFall-Right";
                    }
                    else{
                        playerSprite = freefallLeft;
                        currentAnimation = "FreeFall-Left";
                    }
                } 
            }

            if(xspeed > -0.75 && xspeed < 0.75){
                xspeed = 0;
            }

        if(keySwitch){
            if(!isInAir){
                if(xspeed > maxSpeed){
                    xspeed = maxSpeed;
                }
                else if(xspeed < -maxSpeed){
                    xspeed = -maxSpeed;
                }
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
            if(keyUp){      //working on a way to add jump animation, is tricky because so short
                if(!isInAir){
                    yspeed = jumpPower;
                }
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
        if (hitCollectable) {
            panel.walls.remove(curCollectable);
            curCollectable = null;
            hitCollectable = false;
            
        }
    }
    
    public void airBornCheck(){
        hitBox.y++;
        isInAir = true;
        for(LevelTile wall : panel.walls){
            if (wall instanceof Wall) {
                if(hitBox.intersects(wall.hitBox)){
                    isInAir = false;
                    break;
                }
            }
        }
        hitBox.y--;
    }
    public void leaveCheck() {
        if (y < -63) {
            this.y = 700;
            panel.drawNextLevel();
        }
        else if (y > 800) {
            this.y = -62;
            LevelLoader.lvlCount = LevelLoader.lvlCount -2;
            panel.drawNextLevel();
        }
    }

    public void draw(Graphics2D g2d){
        playerSprite.paintIcon(panel, g2d, x, y);
    }
}
