package q2;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import static q2.Main.initHeight;
import static q2.Main.root;

/**
 * Created by emol on 2/6/18.
 */
public class Modifier implements Runnable{
    static public boolean terminated;  // set this to false if all the threads need to terminate
    static public volatile boolean Rflag;
    static public volatile boolean Wflag;
    static public volatile int turn;
    static public String[] emittedResult;
    long startTime;

    public int initCnt = 0;
    @Override
    public void run() {

        // construct tree structure
        root = new Node();
        growTree(root, 0);
        assignVals(root);


        Thread t1 = new Thread(new Reader(0));
        Thread t2 = new Thread(new Reader(1));

        emittedResult = new String[2];
        initME();
        t1.start();
        t2.start();


        // also writer thread
        startTime = System.currentTimeMillis();

        while(!terminated){
            writerRoutine();    // one traverse of the tree
        }

        // terminate
        try{
            t1.join();
            t2.join();
            // FIXME: collect the threads emit result
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("A: " + emittedResult[0]);
        System.out.println("B: " + emittedResult[1]);

    }


    private boolean checkTimeOut(){
        if (System.currentTimeMillis() - startTime >= 5000){
            terminated = true;
            // FIXME
            return true;
        }
        return false;
    }


    // ============ ME: Peterson's algo===================
    private void initME(){
        Rflag = false;
        Wflag = false;
        turn = 0;
    }

    private void enter(){
        Wflag = true;
        turn = 1;
        while (turn == 1 && Rflag){
            // spin
        }

    }

    private void exit(){
        Wflag = false;
    }


    // ============ modifier==============================
    private void writerRoutine(){
        // traverse tree
        Node n = root;
        float pred = Integer.MIN_VALUE;
        float succ = Integer.MAX_VALUE;

        while (true){

            if (checkTimeOut()) return;
            boolean left = ThreadLocalRandom.current().nextInt(0, 10) < 5;
            boolean a;  // action
            if (n.leftChild != null) {
                a = ThreadLocalRandom.current().nextInt(0, 10) < 1;
                if (a){
                    // delete
                    enter();
                    n.leftChild = null;
                    exit();
                    // sleep and restart
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return;
                }
            }
            else {
                // no left child
                a = ThreadLocalRandom.current().nextInt(0, 10) < 4;

                if (a){
                    // add left child node
                    Node leftChild = new Node();
                    leftChild.parent = n;
                    leftChild.val = n.val - pred > 1 ? n.val - 1 : (pred + n.val) * 0.5f;
                    leftChild.isLeftChild = true;

                    enter();
                    n.leftChild = leftChild;
                    exit();

                    // sleep and restart
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return;
                }
            }


            if (n.rightChild != null) {
                a = ThreadLocalRandom.current().nextInt(0, 10) < 1;
                if (a){

                    enter();
                    n.rightChild = null;
                    exit();

                    // sleep and restart
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return;
                }
            }
            else {
                // no right child
                a = ThreadLocalRandom.current().nextInt(0, 10) < 4;

                if (a){
                    // add left child node
                    Node rightChild = new Node();
                    rightChild.parent = n;
                    rightChild.val = succ - n.val > 1 ? n.val + 1 : (succ + n.val) * 0.5f;

                    rightChild.isLeftChild = false;

                    enter();
                    n.rightChild = rightChild;
                    exit();

                    // sleep and restart
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return;
                }
            }




            // no action, continue exploring the tree
            if (left) {
                if (n.leftChild == null) return;    // we have reached the end

                succ = n.val;
                n = n.leftChild;
            }
            else {
                if (n.rightChild == null) return;

                pred = n.val;
                n = n.rightChild;
            }
        }
    }



    // ========== construct tree =====================
    private void growTree(Node n, int level){
        if (level == initHeight) return;
        n.leftChild = new Node();
        n.leftChild.parent = n;
        n.leftChild.isLeftChild = true;

        n.rightChild = new Node();
        n.rightChild.parent = n;
        n.rightChild.isLeftChild = false;

        growTree(n.leftChild, level + 1);
        growTree(n.rightChild, level + 1);
    }

    private void assignVals(Node n){
        if (n == null) return;
        assignVals(n.leftChild);

        n.val = initCnt;
        initCnt ++;

        assignVals(n.rightChild);
    }


}
