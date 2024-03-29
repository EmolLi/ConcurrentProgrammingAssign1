package q1;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class q1 {

    // The image constructed
    public static BufferedImage img;

    // Image dimensions; you can modify these for bigger/smaller images
    public static int width = 1920;
    public static int height = 1080;

    public static int r;
    public static int c;
    public static boolean multithreaded;
    public static CircleMgmt mgmt;
    public static int[][] drawing;


    public static void main(String[] args) {
        try {
            if (args.length<3)
                throw new Exception("Missing arguments, only "+args.length+" were specified!");
            // arg 0 is the max radius
            r = Integer.parseInt(args[0]);
            // arg 1 is count
            c = Integer.parseInt(args[1]);
            // arg 2 is a boolean
            multithreaded = Boolean.parseBoolean(args[2]);
            // create an image and initialize it to all 0's
            img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

            drawing = new int[height][width];

            mgmt = new CircleMgmt(r, c);

            Thread t0 = new Thread(new CircleDrawingThread(0, drawing, width, height, mgmt));
            Thread t1 = null;


            if (multithreaded) {
                t1 = new Thread(new CircleDrawingThread(1, drawing, width, height, mgmt));
            }

            // timing
            long startTime = System.currentTimeMillis();
            t0.start();
            if (multithreaded) t1.start();


            // Write out the image
            try{
                t0.join();
                if (multithreaded) t1.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            // paint
            try{
                for (int i = 0; i < height; i ++){
                    img.setRGB(0, i, width, 1, drawing[i], 0, width);
                }
            }catch (Exception e){

            }

            long timeDiff = System.currentTimeMillis() - startTime;

            System.out.println("all done Time:" + timeDiff);
            File outputfile = new File("outputimage.png");
            ImageIO.write(img, "png", outputfile);

        } catch (Exception e) {
            System.out.println("ERROR " +e);
            e.printStackTrace();
        }
    }
}
