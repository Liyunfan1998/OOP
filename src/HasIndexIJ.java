/**
 * Created by luke1998 on 2018/3/26.
 */
public class HasIndexIJ {
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