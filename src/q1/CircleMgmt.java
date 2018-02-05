package q1;

/**
 * Created by emol on 1/29/18.
 */
public class CircleMgmt {
    private Circle[] circles;
    private int r;
    private int c;


    public CircleMgmt(int r, int c) {
        circles = new Circle[2];
        this.r = r;
        this.c = c;
    }

    public synchronized Circle drawNewCircle(int id) {
        if (c <= 0) {
            return null;    // no more circle
        }

        circles[id] = null;
        notify();

        // come up with a new circle
        Circle newCircle = new Circle(r);


        while (newCircle.overlap(circles[1 - id])) {
            try {
                wait(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        c--;
        circles[id] = newCircle;
        return newCircle;
    }


}
