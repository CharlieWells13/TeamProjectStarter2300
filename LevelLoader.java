import java.io.*;  
import java.util.Scanner;  

public class LevelLoader {
    private int[][] levelLayout = new int[25][25];
    private int lvlCount; //made static in case another levelLoader object needs to be

    public LevelLoader() {
        this.lvlCount = 0;
    }

    public void loadLevel(Level level) throws IOException { 
        String levelHead = "level";
        String next;
        String[] levelRow;
        int currentRow = 0;

        levelHead = "levels/" + levelHead + lvlCount + ".tsv";
        //System.out.println(levelHead);
        lvlCount++;
        File levelFile = new File(levelHead);
        Scanner sc = new Scanner(levelFile);
        while (sc.hasNext()) {
            next = sc.nextLine();
            levelRow = next.split("\t");
            for(int i = 0; i < 25; i++) { //converts Strings from csv to ints
                levelLayout[currentRow][i] = Integer.valueOf(levelRow[i]);
            }
            currentRow++; //increments current row
        }
        sc.close();
        level.setLevelLayout(levelLayout);
    }
}
