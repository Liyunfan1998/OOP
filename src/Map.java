import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luke1998 on 2018/3/26.
 */
public class Map {
    private HasIndexIJ[][] map;
    private int n;
    private int m;
    private Person person;
    private ArrayList<Box> boxes = new ArrayList<>(n);
    private ArrayList<Dest> dests = new ArrayList<>(n);

    public int[] getPersonIJ() {
        return this.person.getIJ();
    }

    public int[][] getDestsIJ() {
        int x = this.dests.size();
        int[][] dijs = new int[x][2];
        for (int i = 0; i < x; i++) {
            dijs[i] = this.dests.get(i).getIJ();
        }
        return dijs;
    }

    public int[][] getBoxesIJ() {
        int x = this.boxes.size();
        int[][] bijs = new int[x][2];
        for (int i = 0; i < x; i++) {
            bijs[i] = this.boxes.get(i).getIJ();
        }
        return bijs;
    }

    public Map(int n0, int m0, int[][] intMap) {
        this.map = new HasIndexIJ[n0][m0];
        setN(n0);
        setM(m0);
        setMap(intMap, n0, m0);
    }

    void appendBox(Box box, Box[] boxes) {

    }


    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public static int[] stringArray2IntArray(String[] spliteStr, int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(spliteStr[i]);
        }
        return array;
    }


    public static int[] getMapSize(BufferedReader bufferedReader, int n) throws IOException {
        String str = bufferedReader.readLine();
        String[] spliteStr = str.split(" ");
        return stringArray2IntArray(spliteStr, n);
    }

    public static int[] getMapLineAsArray(BufferedReader bufferedReader, int m) throws IOException {
        String str = bufferedReader.readLine();
//        char[] spliteStr = str.toCharArray();
//        int[] array = new int[n];
//        for (int i = 0; i < n; i++) {
//            array[i] = Integer.parseInt(String.valueOf(spliteStr[i]));
//        }
//        return array;
        String[] spliteStr = str.split("");
        return stringArray2IntArray(spliteStr, m);
    }

    public HasIndexIJ getObj(int i) {
        switch (i) {
            case 0:
                return new Floor();
            case 1:
                return new Wall();
            case 2:
                return new Floor();
            case 3:
                Box box = new Box();
                this.boxes.add(box);
                return box;
            case 4:
                Dest dest = new Dest();
                this.dests.add(dest);
                return dest;
            case 5:
                this.person = new Person();
                return person;
        }
        return null;
    }

    public boolean checkNull(Map m) {
        int n = m.n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (m.map[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setMap(int[][] intMap, int n0, int m0) {
        for (int i = 0; i < n0; i++) {
            for (int j = 0; j < m0; j++) {
                this.map[i][j] = getObj(intMap[i][j]);
                this.map[i][j].setI(i);
                this.map[i][j].setJ(j);
                this.map[i][j].setSyb(intMap[i][j]);
//                静态物体，如 Floor，Wall 是不用改动ij的； 只有动态物体 person 和 box 是可以后期改ij
            }
        }
    }

    HasIndexIJ[][] getMap() {
        return this.map;
    }

    void printMap() {
        int n = this.getN();
        int m = this.getM();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(this.getMap()[i][j].obj2Int() + "\t");
            }
            System.out.println();
        }
    }
}