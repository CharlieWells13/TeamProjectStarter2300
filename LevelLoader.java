import java.io.*;  
import java.util.Scanner;  

public class LevelLoader {
    private int[][] level;
    private int lvlCount;

    public LevelLoader() {
        this.level = new int[100][100];
        this.lvlCount = 0;
    }
    public void loadLevel() throws IOException {
        String levelHead = "level";
        String next;
        String[] levelRow;
        int currentRow = 0;

        levelHead = "levels\\" + levelHead + lvlCount + ".csv";
        System.out.println(levelHead);
        lvlCount++;
        File level = new File(levelHead);
        Scanner sc = new Scanner(level);
        while (sc.hasNext()) {
            next = sc.nextLine();
            levelRow = next.split(",");
            for(int i = 0; i < 100; i++) {
                this.level[currentRow][i] = Integer.valueOf(levelRow[i]);
            }
            currentRow++;
        }
        sc.close();

    }
    public int[][] getCurrentLevel() {
        return this.level;
    }

}
