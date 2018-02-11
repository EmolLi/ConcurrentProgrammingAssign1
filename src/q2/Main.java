package q2;

import q3.Test;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by emol on 2/6/18.
 * Peterson's algo for ME
 */
public class Main {
    public static Node root;
    public static int initHeight = 5;


    public static void main(String[] args) {
        // t0: constructor thread
        Thread t0 = new Thread(new Modifier()); // modifier is responsible for constructing the tree, creating the two reader threads, and modify the tree


        t0.start();

    }

}
