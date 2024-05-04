import java.util.ArrayList;


public class Level {

    gamePanel panel;

    private int levelNum;
    private String backgroundName;

    public int[][] levelLayout = new int[25][25];
    private ArrayList<LevelTile> levelTiles = new ArrayList<LevelTile>();
    private ArrayList<Grabbable> grabbables = new ArrayList<Grabbable>();

    public Level(int levelNum, gamePanel panel){
        this.panel = panel;
        this.levelNum = levelNum;
        this.backgroundName = "Backgrounds/Background" + levelNum + ".png";
    }

    // returns array of level tiles
    public ArrayList<LevelTile> getLevelTiles(){
        return levelTiles;
    }

    // returns array of level tiles
    public ArrayList<Grabbable> getGrabbables(){
        return grabbables;
    }

    // returns file name for level's background
    public String getBackgroundName(){
        return backgroundName;
    }

    public void setLevelLayout(int[][] layout){
        levelLayout = layout.clone();
        makeLevel();
    }

    // Load ArrayList with walls to be added to level
    public void makeLevel(){
        int xPos = 0;
        int yPos = 0;
        for (int[] curRow : this.levelLayout) {
            for (int curBox : curRow) {
                if (curBox == 1) {
                    levelTiles.add(new Wall (xPos, yPos, 32, 32, panel));
                }
                else if (curBox == 2) {
                    grabbables.add(new Grabbable(xPos, yPos, 32, 32, panel));
                }
                else if (curBox == 6) {
                    levelTiles.add(new Bouncer (xPos, yPos, 32, 32, panel));
                }
                else if (curBox == 7) {
                    levelTiles.add(new Trapper(xPos, yPos, 32, 32, panel));
                }
                else if (curBox == 8) {
                    levelTiles.add(new Collectable(xPos, yPos, 32, 32, panel));
                }
                else if (curBox == 9) {
                    levelTiles.add(new IceBlock(xPos, yPos, 32, 32, panel));
                }
                xPos = xPos + 32;
            }
            xPos = 0;
            yPos = yPos + 32;
        }
    }
}
