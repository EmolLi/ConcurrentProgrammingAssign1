package q1;

/**
 * Created by emol on 1/29/18.
 */
public class CircleMgmt {
    private boolean[] drawing;
    private Circle[] circles;
    private Object lockCnt;
    private int r;
    private int c;



    public CircleMgmt(int r, int c) {
        lockCnt = new Object();
        circles = new Circle[2];
        this.r = r;
        this.c = c;
    }

    public Circle drawNewCircle(int id) {
        // come up with a new circle
        Circle newCircle = new Circle(r);

        synchronized (circles) {
            circles[id] = null;
            circles.notify();

            if (c <= 0) {
                return null;    // no more circle
            }


            while (newCircle.overlap(circles[1 - id])) {
                try {
                    circles.wait(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//        synchronized (lockCnt){
//            if (c<=0) return null;
            c--;
            circles[id] = newCircle;
        }
//        }
        return newCircle;
    }


}
