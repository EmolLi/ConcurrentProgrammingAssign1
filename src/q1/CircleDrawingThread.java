package q1;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

/**
 * Created by emol on 1/29/18.
 */
public class CircleDrawingThread implements Runnable{
    // The image constructed
    public BufferedImage img;

    // Image dimensions; you can modify these for bigger/smaller images
    public int width = 1920;
    public int height = 1080;

    public int id;

    public CircleMgmt mgmt;

    public CircleDrawingThread(int id, BufferedImage img, int width, int height, CircleMgmt mgmt){
        this.id = id;
        this.img = img;
        this.width = width;
        this.height = height;
        this.mgmt = mgmt;
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

                    // paint
                    try{

                        img.setRGB(x,y, -16777216);
                    }catch (Exception e){
                        System.out.println(x + "  " + y);
                    }
                }
            }
        }
    }
}
