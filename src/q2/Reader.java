package q2;

import static q2.Main.root;
import static q2.Modifier.*;

/**
 * Created by emol on 2/6/18.
 */

public class Reader implements Runnable {
    private int id;
    private StringBuffer sb;

    public Reader(int id){
        this.id = id;
    }
    @Override
    public void run() {
        sb = new StringBuffer();
        while (true){
            if (!traverseTree()) break;
        }

        // terminate
        emittedResult[id] = sb.toString();
    }
    private boolean traverseTree(){
        if (terminated) return false;

        boolean leftdone = false;
        Node cur = root;
        // Start traversal from root
        while (cur != null) {
            // If left child is not traversed, find the leftmost child
            if (!leftdone) {
                while (true) {
                    enter();
                    if (cur.leftChild != null) {
                        cur = cur.leftChild;
                        exit();
                    }
                    else break;
                }
                exit();

            }

            // read
            sb.append(cur.val + " ");
            try {
                Thread.sleep((int) (Math.random() * 15) + 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Mark left as done
            leftdone = true;

            // If right child exists
            enter();
            if (cur.rightChild != null) {
                leftdone = false;
                cur = cur.rightChild;
                exit();
                continue;
            }

            exit();
            // If right child doesn't exist, move to parent
            if (cur.parent != null) {
                // If this node is right child of its parent, visit parent's parent first
                while (cur.parent != null && !cur.isLeftChild)
                    cur = cur.parent;

                if (cur.parent == null)
                    break;
                cur = cur.parent;
            } else
                break;
        }

        sb.append("\n");
        return true;
    }


    // ============ ME: Peterson's algo===================

    private void enter(){
        Rflag = true;
        turn = 0;
        while (turn == 0 && Wflag){
            // spin
        }

    }

    private void exit(){
        Rflag = false;
    }
}
