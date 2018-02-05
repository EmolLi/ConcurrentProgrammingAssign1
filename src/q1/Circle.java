package q1;

import java.util.Map;

/**
 * Created by emol on 1/29/18.
 */
public class Circle {
    int radius;
    int originX;
    int originY;

    public static int width = 1920;
    public static int height = 1080;

    public Circle(int r){
        radius = (int) (Math.random() * (r - 1)) + 1;
        originX = (int) (Math.random() * width);
        originY = (int) (Math.random() * height);
    }

    public boolean overlap(Circle c){
        if (c == null) return false;
        return Math.pow(this.originY - c.originY, 2) + Math.pow(this.originX - c.originX, 2) <= Math.pow(this.radius + c
                .radius, 2);
    }
}
