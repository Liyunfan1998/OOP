import java.util.Arrays;

/**
 * Created by luke1998 on 2018/3/26.
 */
public class Moveable extends HasIndexIJ {
    public boolean checkWall(Map m, char direction) {//Wall is in front of box
        return getFrontObj(m, direction) instanceof Wall;
    }

    public boolean checkBox(Map m, char direction) {//Box is in front of person
        return getFrontObj(m, direction) instanceof Box;
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

    public Box getFrontBox(Map m, char direction) {
        return (Box) getFrontObj(m, direction);
    }


    public boolean checkDest(Map m, int i, int j) {
        int[][] dijs = m.getDestsIJ();
        for (int[] dij : dijs) {
            if (Arrays.equals(dij, new int[]{i, j})) {
                return true;
            }
        }
        return false;
    }

    public void move(Map m, char direction) {
        int[] ij = getIJ();
        int i = ij[0], j = ij[1];
//   2018-03-22   改成允许人移动到终点，人离开以后还要返回dest
        int[][] dijs = m.getDestsIJ();
        m.getMap()[i][j] = new Floor();
        if (checkDest(m, i, j)) {
            m.getMap()[i][j] = new Dest();
        }

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