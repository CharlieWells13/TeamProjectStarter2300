import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.swing.*;
import java.util.ArrayList;


public class Player {
    
    private gamePanel panel;
    private Level curLevel;
    private int curLevelNum;
    private ArrayList<LevelTile> levelTiles;
    private ArrayList<Grabbable> grabbables;

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
    int jumpPower = -10;    //-8;
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

    ImageIcon climbingStill = new ImageIcon("AlligatorAnimations/Alligator-Climb-StandStill.png");
    ImageIcon climbingUp = new ImageIcon("AlligatorAnimations/Alligator-Climb-Ascending.gif");


    String currentAnimation = "Standstill";

    Boolean lastFacedRight = true;

    public Player(int x, int y, gamePanel panel){
        this.panel = panel;
        this.x = x;
        this.y = y;

        this.width = 32;
        this.height = 64;
        hitBox = new Rectangle(x, y, width, height);

        this.levelTiles = new ArrayList<LevelTile>();
        this.grabbables = new ArrayList<Grabbable>();

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
    public void toggleOn(){
        for(Grabbable grabbable : grabbables){
            if(hitBox.intersects(grabbable.hitBox)){
                if (keySwitch) {
                keySwitch = !keySwitch;
                }
                break;
            }
        }
    }
    public void toggleOff() {
        for(Grabbable grabbable : grabbables){
            if(hitBox.intersects(grabbable.hitBox)){
                if (!keySwitch) {
                keySwitch = !keySwitch;
                }
                break;
            }
        }
    }

    public int modeCheck(){
        for(Grabbable grabbable : grabbables){
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
        modeCheck();
        leaveCheck();

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
                    for(LevelTile wall : levelTiles){
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
                    for(LevelTile wall : levelTiles){
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
                    for(LevelTile wall : levelTiles){
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
                if(currentAnimation != "Climbing-Still"){
                    playerSprite = climbingStill;
                    currentAnimation = "Climbing-Still";
                }
                yspeed *= 0.8;
            }
            else if(keyUp && !keyDown){
                yspeed--;
                if(currentAnimation != "Climbing-Up"){
                    playerSprite = climbingUp;
                    currentAnimation = "Climbing-Up";
                }
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
        for (LevelTile wall : levelTiles) {
            if (hitBox.intersects(wall.hitBox)) {
                hitBox.x -= xspeed;
                wall.collideX(this, wall);
                x = hitBox.x;
        }
    }
    
        // Vertical Collision Checking
        hitBox.y += yspeed;
        for (LevelTile wall : levelTiles) {
            if (hitBox.intersects(wall.hitBox)) {
                hitBox.y -= yspeed;
                wall.collideY(this, wall);
                y = hitBox.y;
            }
        }
        if (hitCollectable) {
            levelTiles.remove(curCollectable);
            curCollectable = null;
            hitCollectable = false;
            
        }
    }
    
    public void airBornCheck(){
        hitBox.y++;
        isInAir = true;
        for(LevelTile wall : levelTiles){
            if (wall instanceof Wall) {
                if(hitBox.intersects(wall.hitBox)){
                    isInAir = false;
                    break;
                }
            }
        }
        hitBox.y--;
    }

    // check if new level needs to be loaded
    public void leaveCheck(){
        if (y < -55) {
            panel.setLevel(curLevelNum + 1);
            this.y = 750;
            if(!keySwitch){
                toggleOn();
            }
        }
        else if (y > 795) {
            panel.setLevel(curLevelNum - 1);
            this.y = -54;
            if(!keySwitch){
                toggleOn();
            }
        }
    }

    public void draw(Graphics2D g2d){
        playerSprite.paintIcon(panel, g2d, x, y);
    }

    // updates player on the current level
    public void setLevel(Level level, int curLevelNum){
        this.curLevel = level;
        this.curLevelNum = curLevelNum;
        levelTiles = level.getLevelTiles();
        grabbables = level.getGrabbables();
    }
}
