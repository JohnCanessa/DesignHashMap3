
/**
 * Represents a node in the binary search tree (BST).
 */
public class BSTNode {

    // **** class members ****
    int     key;
    int     value;
    BSTNode left;
    BSTNode right;

    /**
     * Constructor
     */
    BSTNode() {
    }

    /**
     * Constructor
     */
    BSTNode(int key, int value) {
        this.key    = key;
        this.value  = value;
    }

    /**
     * Constructor
     */
    BSTNode(int key, int value, BSTNode left, BSTNode right) {
        this.key    = key;
        this.value  = value;
        this.left   = left;
        this.right  = right;
    }

    /**
     * Return a string representation
     */
    @Override
    public String toString() {
        return "(" + this.key + "," + this.value + ") ";
    }
}
