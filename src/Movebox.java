import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by luke1998 on 2018/3/19.
 * 还有一个box不能覆盖box的检查没做
 */
public class Movebox {

    public static void main(String[] args) throws IOException {
        Movebox movebox = new Movebox();
        String fileName = "./maps/2.map";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        int[] size = Map.getMapSize(bufferedReader, 2);
        int n = size[0];
//        System.out.println(size[0]+""+size[1]);
        int[][] intMap = new int[n][n];
        for (int i = 0; i < n; i++) {
            intMap[i] = Map.getMapLineAsArray(bufferedReader, n);
        }
//        读入map完成
        Map map1 = new Map(n, intMap);
//        创建对象数组完成
        map1.printMap();

        for (; ; ) {
            char direction = movebox.getInstruction();
            movebox.forward(map1, direction);
            map1.printMap();
            if (movebox.checkWin(map1)) {
                System.out.println("YOU WIN !");
                return;
            }
        }
    }

    public boolean checkWin(Map m) {
        List<int[]> bij = new ArrayList(Arrays.asList(m.getBoxesIJ()));
        List<int[]> dij = new ArrayList(Arrays.asList(m.getDestsIJ()));

        Iterator<int[]> iterator = dij.iterator();
        while (iterator.hasNext()) {
            int[] nextdij = iterator.next();
            for (int[] bb : bij) {
                if (Arrays.equals(nextdij, bb)) {
                    iterator.remove();
                }
            }
        }
        return dij.size() == 0;

//        for (int i = 0; i < x; i++) {
//            int di = bij.get(i)[0] - dij.get(i)[0];
//            int dj = bij.get(i)[1] - dij.get(i)[1];
//            boolean tmp = (di == 0 && dj == 0);
//            if (!tmp) return false;
//        }
//        return true;
    }

    private static boolean isSubstring(String str, String target) {
        if (target.length() == 0)
            return false;
        if (str.equals(target))
            return true;
        else
            return isSubstring(str, target.substring(0, target.length() - 1))
                    || isSubstring(str, target.substring(1));
    }

    private char getInstruction() {
        Scanner sc = new Scanner(System.in);
        for (; ; ) {
            System.out.println("请输入wasd中的任意1个字母：");
            String nxchar = sc.nextLine();
            boolean b = isSubstring(nxchar, "wasd");
            boolean c = nxchar.length() == 1;
            if (b && c) return nxchar.toCharArray()[0];
        }
    }

    private void forward(Map m, char direction) {
        HasIndexIJ[][] map = m.getMap();
        int[] pij = m.getPersonIJ();
        Person p = (Person) map[pij[0]][pij[1]];
//        Box b = new Box();
//        int[][] bijs = m.getBoxesIJ();
//        for (int i = 0; i < bijs.length; i++) {
//            int[] bij = bijs[i];
//            b = (Box) map[bij[0]][bij[1]];
//        }
        go(m, direction, p);
    }

    private void go(Map m, char direction, Person p) {
        if (!p.checkWall(m, direction)) {
            boolean pb = p.checkBox(m, direction);
            if (pb) {
                Box b = p.getFrontBox(m, direction);

                boolean bw = b.checkWall(m, direction);
                if (!bw) {
                    b.move(m, direction);
                    p.move(m, direction);
                } else {
                    System.out.println("No Real Move");
                }
            } else {
                p.move(m, direction);
            }
        } else
            System.out.println("Moving Error");
    }

    private void doInstruction(Map m, char direction) {

    }
}

class Moveable extends HasIndexIJ {
    public boolean checkWall(Map m, char direction) {//Wall is in front of box
        return getFrontObj(m, direction) instanceof Wall;
    }

    public HasIndexIJ getFrontObj(Map m, char direction) {
        int[] ij = getIJ();
        int i = ij[0], j = ij[1];
        switch (direction) {
            case 'w':
                return m.getMap()[i - 1][j];
            case 'a':
                return m.getMap()[i][j - 1];
            case 's':
                return m.getMap()[i + 1][j];
            case 'd':
                return m.getMap()[i][j + 1];
        }
        return null;
    }

    public void move(Map m, char direction) {
        int[] ij = getIJ();
        int i = ij[0], j = ij[1];
        m.getMap()[i][j] = new Floor();
        switch (direction) {
            case 'w':
                this.setI(i - 1);
                break;
            case 'a':
                this.setJ(j - 1);
                break;
            case 's':
                this.setI(i + 1);
                break;
            case 'd':
                this.setJ(j + 1);
                break;
        }
        ij = getIJ();
        i = ij[0];
        j = ij[1];
        m.getMap()[i][j] = this;
    }
}

class HasIndexIJ {
    private int i = 0;
    private int j = 0;
    private int syb;

    int[] getIJ() {
        return new int[]{i, j};
    }

    void setI(int i) {
        this.i = i;
    }

    void setJ(int j) {
        this.j = j;
    }

    void setSyb(int syb) {
        this.syb = syb;
    }

    int obj2Int() {
        return syb;
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

class Box extends Moveable {
    Box() {
        this.setSyb(3);
    }
}

class Person extends Moveable {
    Person() {
        this.setSyb(5);
    }

    public boolean checkBox(Map m, char direction) {//Box is in front of person
        return getFrontObj(m, direction) instanceof Box;
    }

    public Box getFrontBox(Map m, char direction) {
        return (Box) getFrontObj(m, direction);
    }
}

class Wall extends HasIndexIJ {
    Wall() {
        this.setSyb(1);
    }
}

class Floor extends Coverable {
    Floor() {
        this.setSyb(2);
    }
}

class Dest extends Floor {
    Dest() {
        this.setSyb(4);
    }
}

class Map {
    private HasIndexIJ[][] map;
    private int n;
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

    public Map(int n0, int[][] intMap) {
        this.map = new HasIndexIJ[n0][n0];
        setN(n0);
        setMap(intMap, n0);
    }

    void appendBox(Box box, Box[] boxes) {

    }


    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
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

    public static int[] getMapLineAsArray(BufferedReader bufferedReader, int n) throws IOException {
        String str = bufferedReader.readLine();
//        char[] spliteStr = str.toCharArray();
//        int[] array = new int[n];
//        for (int i = 0; i < n; i++) {
//            array[i] = Integer.parseInt(String.valueOf(spliteStr[i]));
//        }
//        return array;
        String[] spliteStr = str.split("");
        return stringArray2IntArray(spliteStr, n);
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

    private void setMap(int[][] intMap, int n0) {
        for (int i = 0; i < n0; i++) {
            for (int j = 0; j < n0; j++) {
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(this.getMap()[i][j].obj2Int() + "\t");
            }
            System.out.println();
        }
    }
}