package q1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

/**
 * Created by emol on 1/29/18.
 */
public class CircleDrawingThread implements Runnable{
    // The image constructed
    public int[][] drawing;

    // Image dimensions; you can modify these for bigger/smaller images
    public int width = 1920;
    public int height = 1080;

    public int id;

    public CircleMgmt mgmt;
    public Color[] colors;
    private int colorCnt;

    public CircleDrawingThread(int id, int[][] drawing, int width, int height, CircleMgmt mgmt){
        this.id = id;
        this.drawing = drawing;
        this.width = width;
        this.height = height;
        this.mgmt = mgmt;
        this.colors = new Color[]{Color.black, Color.blue, Color.cyan, Color.DARK_GRAY, Color.green, Color.magenta, Color.orange, Color.pink, Color.orange, Color.yellow, Color.RED, Color.WHITE};
        this.colorCnt = this.colors.length;
    }

    @Override
    public void run() {
        Circle c = mgmt.drawNewCircle(this.id);
        while (c!= null){
            drawCircle(c.originX, c.originY, c.radius);
            c = mgmt.drawNewCircle(this.id);
        }

        System.out.println(this.id + " Done!");
    }

    private void drawCircle(int originX, int originY, int radius){
        int color = colors[(int)(Math.random()*colorCnt)].getRGB();
        for (int i = - radius; i <= radius; i++){
            for (int j = - radius; j<= radius; j++){
                if (i*i + j*j <= radius*radius){

                    // wrap x
                    int x = originX + i;
                    if (x < 0) x = width + x;
                    if (x >= width) x = x - width;
                    // wrap y
                    int y = originY + j;
                    if (y < 0) y = height + y;
                    if (y >= height) y = y - height;

                    drawing[y][x] = color;
                    // paint
//                    try{
//                        img.setRGB(x, y, 1, 1, new int[]{color}, 0, 1);
//                        img.setRGB(x,y, color);
//                    }catch (Exception e){
//                        System.out.println(x + "  " + y);
//                    }
                }
            }
        }

    }
//    }
}
