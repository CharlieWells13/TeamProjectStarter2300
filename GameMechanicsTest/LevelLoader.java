import java.io.*;  
import java.util.Scanner;  

public class LevelLoader {
    private int[][] level;
    private static int lvlCount; //made static in case another levelLoader object needs to be

    public LevelLoader() {
        this.level = new int[50][50];
        LevelLoader.lvlCount = 0;
    }
    public void loadLevel() throws IOException { 
        String levelHead = "level";
        String next;
        String[] levelRow;
        int currentRow = 0;

        levelHead = "levels\\" + levelHead + lvlCount + ".tsv";
        System.out.println(levelHead);
        lvlCount++;
        File level = new File(levelHead);
        Scanner sc = new Scanner(level);
        while (sc.hasNext()) {
            next = sc.nextLine();
            levelRow = next.split("\t");
            for(int i = 0; i < 50; i++) { //converts Strings from csv to ints
                this.level[currentRow][i] = Integer.valueOf(levelRow[i]);
            }
            currentRow++; //increments current row
        }
        sc.close();

    }
    public int[][] getCurrentLevel() {
        return this.level;
    }

}
