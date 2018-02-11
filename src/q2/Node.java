package q2;

/**
 * Created by emol on 2/6/18.
 */
public class Node {
    Node parent;
    volatile Node leftChild;
    volatile Node rightChild;
    float val;  // we only change the value of node when we create it. Therefore there is no need to declare volatile for it
    boolean isLeftChild;
}

