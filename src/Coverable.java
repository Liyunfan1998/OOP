/**
 * Created by luke1998 on 2018/3/26.
 */
public class Coverable extends HasIndexIJ {
    HasIndexIJ up;
//    DOWN is all floor, not need to set down

    public void setUp(HasIndexIJ up) {
        this.up = up;
    }

    public HasIndexIJ getUp() {
        return up;
    }
}