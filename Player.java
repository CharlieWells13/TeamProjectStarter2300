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

    private ImageIcon playerSprite;

    private int x, y;
    private int width, height;
    private int collectableCount;

    private double xspeed, yspeed;

    private Rectangle hitBox;

    private Boolean keyUp = false;
    private Boolean keyDown = false;
    private Boolean keyLeft = false;
    private Boolean keyRight = false;
    private Boolean isPlatformer = true;

    private Boolean hitCollectable = false;
    private LevelTile curCollectable = null;

    private int maxSpeed = 5;
    private int jumpPower = -10;    //-8;
    private double gravity = 0.4;
    private int climbingModeSlowDown = 4;

    private Boolean isInAir = false;

    private ImageIcon walkLeft = new ImageIcon("AlligatorAnimations/Alligator-Walk-Left.gif");
    private ImageIcon walkRight = new ImageIcon("AlligatorAnimations/Alligator-Walk-Right.gif");
    private ImageIcon standstillRight = new ImageIcon("AlligatorAnimations/Alligator-Standstill-Right.png");
    private ImageIcon standstillLeft = new ImageIcon("AlligatorAnimations/Alligator-Standstill-Left.png");
    private ImageIcon freefallLeft = new ImageIcon("AlligatorAnimations/Alligator-FreeFall-Left.png");
    private ImageIcon freefallRight = new ImageIcon("AlligatorAnimations/Alligator-FreeFall-Right.png");
    private ImageIcon jump = new ImageIcon("AlligatorAnimations/Alligator-Jump.gif");

    private ImageIcon climbingStill = new ImageIcon("AlligatorAnimations/Alligator-Climb-StandStill.png");
    private ImageIcon climbingUp = new ImageIcon("AlligatorAnimations/Alligator-Climb-Ascending.gif");


    private String currentAnimation = "Standstill";

    private Boolean lastFacedRight = true;

    public Player(int x, int y, gamePanel panel){
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.collectableCount = 0;

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

    public void setKeyUp(Boolean bool){
        this.keyUp = bool;
    }

    public void setKeyDown(Boolean bool){
        this.keyDown = bool;
    }

    public void setKeyLeft(Boolean bool){
        this.keyLeft = bool;
    }

    public void setKeyRight(Boolean bool){
        this.keyRight = bool;
    }

    public Rectangle getHitBox(){
        return hitBox;
    }

    public void setHitCollectable(Boolean bool){
        hitCollectable = bool;
    }

    public void setCurCollectable(LevelTile tile){
        curCollectable = tile;
    }

    // switches player movement mode to climbing mode if over grabbable surface
    public void toggleOn(){
        for(Grabbable grabbable : grabbables){
            if(hitBox.intersects(grabbable.hitBox)){
                if (isPlatformer) {
                    isPlatformer = false;
                }
                break;
            }
        }
    }

    // switches player movement mode to platformer mode
    public void toggleOff() {
        isPlatformer = true;
    }

    // make sure player is only climbing on grabbable surface
    public int modeCheck(){
        for(Grabbable grabbable : grabbables){
            if(hitBox.intersects(grabbable.hitBox)){
                return 0;
            }
        }
        isPlatformer = true;
        return 1;
    }

    // check if player is air born
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
            if(!isPlatformer){
                toggleOn();
            }
        }
        else if (y > 795) {
            panel.setLevel(curLevelNum - 1);
            this.y = -54;
            if(!isPlatformer){
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

    // Updating player position and state
    public void set(){
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
        // if both left and right are released or pressed at the same time
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

            // incrementally decrease speed (only when on ground in platformer mode)
            if(!isPlatformer){
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
        // moving left
        else if(keyLeft && !keyRight){
            if(!isInAir){
                if(currentAnimation != "Walk-Left"){
                    playerSprite = walkLeft;
                    currentAnimation = "Walk-Left";
                    lastFacedRight = false;
                }
            }
            // increase speed left
            if(!isPlatformer){
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
        // moving right
        else if(!keyLeft && keyRight){
            if(!isInAir){
                if(currentAnimation != "Walk-Right"){
                    playerSprite = walkRight;
                    currentAnimation = "Walk-Right";
                    lastFacedRight = true;
                }
            }
            // increase speed right
            if(!isPlatformer){
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

        // prevent player drift
        if(xspeed > -0.75 && xspeed < 0.75){
            xspeed = 0;
        }

        // cap speed in each movement mode
        if(isPlatformer){
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
        if(isPlatformer){
            if(keyUp){      //working on a way to add jump animation, is tricky because so short
                if(!isInAir){
                    yspeed = jumpPower;
                }
            }
            else if (!keyUp && keyDown) {
                yspeed++;
            }
            yspeed += gravity;    //gravity

            // terminal velocity
            if(yspeed > 15){
                yspeed = 15;
            }
            else if(yspeed < -15){
                yspeed = -15;
            }
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

            // prevent player drift
            if(yspeed > -0.75 && yspeed < 0.75){
                yspeed = 0;
            }
            
            // cap to slower speed when climbing
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

        // check if collectable has been collected
        if (hitCollectable) {
            levelTiles.remove(curCollectable);
            curCollectable = null;
            hitCollectable = false;
            collectableCount++;
            System.out.println("Collected " + collectableCount);
        }
    }
}
