import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by luke1998 on 2018/3/19.
 */
public class movebox {

    public static void main(String[] args) throws IOException {
        String fileName = "./maps/1.map";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        int[] size = Map.getMapLineAsArray(bufferedReader, 2);
        int n = size[0];
//        System.out.println(size[0]+""+size[1]);
        int[][] intMap = new int[n][n];
        for (int i = 0; i < n; i++) {
            intMap[i] = Map.getMapLineAsArray(bufferedReader, n);
        }
//        读入map完成
        Map map1 = new Map(n, intMap);
//        创建对象数组完成

    }

    private static boolean isSubstring(String str, String target) {
        if (target.length() == 0)
            return false;
        if (str.equals(target))
            return true;
        else
            return (isSubstring(str, target.substring(0, target.length() - 1)));
    }

    private char getInstruction() {
        Scanner sc = new Scanner(System.in);
        for (; ; ) {
            System.out.println("请输入wasd中的任意1个字母：");
            String nxchar = sc.next();
            if (isSubstring("wasd", nxchar) && nxchar.length() == 1) return nxchar.toCharArray()[0];
        }
    }

    private void switchPlaces(Map m, HasIndexIJ o1, HasIndexIJ o2) {
//        o1 cover o2
        if (o1 instanceof Moveable && o2 instanceof Coverable) {
            HasIndexIJ[][] map = m.getMap();

        } else System.out.println("Moving Error");
    }

    private void doInstruction(Map m, char instr) {
        switch (instr) {
            case 'w':
            case 'a':
            case 's':
            case 'd':
        }
    }
}

interface Moveable {
    public boolean checkWall();
}

class HasIndexIJ {
    private int i = 0;
    private int j = 0;

    int[] getIJ() {
        return new int[]{i, j};
    }

    void setI(int i) {
        this.i = i;
    }

    void setJ(int j) {
        this.j = j;
    }
}

class Coverable extends HasIndexIJ {
    HasIndexIJ up;
//    DOWN is all floor, not need to set down

    public void setUp(HasIndexIJ up) {
        this.up = up;
    }

    public HasIndexIJ getUp() {
        return up;
    }
}

class Box extends HasIndexIJ implements Moveable {
    @Override
    public boolean checkWall() {
        return false;
    }
}

class Person extends HasIndexIJ implements Moveable {
    @Override
    public boolean checkWall() {
        return false;
    }
}

class Wall extends HasIndexIJ {
}

class Floor extends Coverable {
}

class Dest extends Floor {
}

class Map {
    private HasIndexIJ[][] map;
    private int n;

    public Map(int n0, int[][] intMap) {
        setN(n0);
        setMap(intMap, n0);
    }


    public void setN(int n) {
        this.n = n;
    }

    public int getN(int n) {
        return n;
    }

    public static int[] getMapLineAsArray(BufferedReader bufferedReader, int n) throws IOException {
        String str = bufferedReader.readLine();
        String[] spliteStr = str.split(" ");
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(spliteStr[i]);
        }
        return array;
    }

    public HasIndexIJ getObj(int i) {
        switch (i) {
            case 1:
                return new Wall();
            case 2:
                return new Floor();
            case 3:
                return new Box();
            case 4:
                return new Dest();
            case 5:
                return new Person();
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

    public void setMap(int[][] intMap, int n0) {
        for (int i = 0; i < n0; i++) {
            for (int j = 0; j < n0; j++) {
                this.map[i][j] = getObj(intMap[i][j]);
                this.map[i][j].setI(i);
                this.map[i][j].setJ(j);
            }
        }
    }

    public HasIndexIJ[][] getMap() {
        return this.map;
    }

}