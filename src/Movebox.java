import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by luke1998 on 2018/3/19.
 * 2018-03-20   还有一个box不能覆盖box的检查没做
 * 2018-03-22   现在做的map都是n*n，要改成n*m （OK）
 * 2018-03-22   改成允许人移动到终点，人离开以后还要返回dest （OK）
 */
public class Movebox {

    public static void main(String[] args) throws IOException {
        Movebox movebox = new Movebox();
        String fileName = "./maps/3.map";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        int[] size = Map.getMapSize(bufferedReader, 2);
        int n = size[1];
        int m = size[0];
//        System.out.println(size[0]+""+size[1]);
        int[][] intMap = new int[n][m];
        for (int i = 0; i < n; i++) {
            intMap[i] = Map.getMapLineAsArray(bufferedReader, m);
        }
//        读入map完成
        Map map1 = new Map(n, m, intMap);
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
                if (b.checkBox(m, direction)) {
                    System.out.println("Cannot Move Two Boxes At One Time");
                    return;
                }
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
/*
    private void go(Map m, char direction, Box box) {
        if (!box.checkWall(m, direction)) {
            boolean bb = box.checkBox(m, direction);
            if (bb) {
                Box b = box.getFrontBox(m, direction);
                if (b.checkBox(m, direction)) {
                    go(m, direction, b);
                }
                boolean bw = b.checkWall(m, direction);
                if (!bw) {
                    b.move(m, direction);
                    box.move(m, direction);
                } else {
                    System.out.println("No Real Move");
                }
            } else {
                box.move(m, direction);
            }
        } else
            System.out.println("Moving Error");
    }
*/

    private void doInstruction(Map m, char direction) {
    }
}