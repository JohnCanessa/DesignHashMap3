import java.util.List;

/**
 * BST class
 */
public class BST {

	// **** root of BST ****
	BSTNode	tree;

    /**
     * constructor
     */
	public BST() {
		this.tree = null;
	}
	
	/**
	 * constructor
	 */
	public BST(int key, int value) {
		this.tree = new BSTNode(key, value);
	}

	/**
	 * Insert value into BST.
	 * Entry point for recursive method.
	 */
	public BSTNode insert(int key, int value) {

		// **** check if tree is empty ****
		if (this.tree == null) {
			this.tree = new BSTNode(key, value);
			return this.tree;
		}

		// **** look for the place in the BST to insert the new node ****
		insert(this.tree, key, value);

		// **** return the BST ****
		return this.tree;
	}

	/**
	 * Insert value into BST.
     * Recursive call.
	 */
	private void insert(BSTNode root, int key, int value) {

		// **** add this node as the root of the BST ****
		if (root == null) {
			root = new BSTNode(key, value);
			return;
		}
		
		// **** deal with the left child ****
		if (key <= root.key) {
			if (root.left == null) {
				root.left = new BSTNode(key, value);
				return;
			} else {
				insert(root.left, key, value);
			}
		}
		
		// **** deal with the right child ****
		else {
			if (root.right == null) {
				root.right = new BSTNode(key, value);
				return;
			}
			else {
				insert(root.right, key, value);
			}
		}
	}

	/**
	 * In-order tree traversal.
	 * Entry point for recursive method.
	 */
	public void inOrder() {
		inOrder(this.tree);
		System.out.println();
	}

	/**
	 * In-order BST traversal.
	 * Recursive call
	 */
    private void inOrder(BSTNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.toString());
            inOrder(node.right);
        }
	}

	/**
	 * Find the depth of a BT.
	 * Entry point for recursive call
	 */
	public int depth() {
		return depth(this.tree);
	}
	
	/**
	 * Determine the depth of a BT.
	 * Recursive call
	 */
	private int depth(BSTNode root) {

		// **** end condition ****
		if (root == null)
			return 0;

		// **** ****
		return Math.max(depth(root.left) + 1, depth(root.right) + 1);
	}

	/**
	 * find the in order successor (min value)
	 */
	private BSTNode inOrderSuccessor(BSTNode root) {
		if (root.left == null)
			return root;
		else
			return inOrderSuccessor(root.left);
	}

	/**
	 * Search for node and delete it from the BST.
	 * Entry point for recursive call.
	 */
	public BSTNode delete(int key) {

		// **** ****
		BSTNode node = delete(this.tree, key);

		// **** update BST root (if needed) ****
		if (tree != null && tree.key == key)
			tree = node;

		// **** return BST root node ****
		return node;
	}

	/**
	 * Search for node and delete it from the BST
	 * Recursive call.
	 */
    private BSTNode delete(BSTNode root, int key) {
     
        // **** check for a null root ****
        if (root == null) {
            return root;
        }
         
        // **** search for the node on the left side of the BST ****
		if (key < root.key) {
			root.left = delete(root.left, key);
		}

		// **** search for the node on the right side of the BST **** 
		else if (key > root.key) {
            root.right = delete(root.right, key);
        }
         
        // **** found node to delete ****
        else {
                         
            // **** node to delete has both children ****
            if ((root.left != null) && (root.right != null)) {
                 
                // **** save the root (being deleted) ****
                BSTNode temp = root;        
                 
                // **** find the in order successor (min val on right tree) ****
                BSTNode node = inOrderSuccessor(temp.right);
 
                // **** replace the value of the root with one from the node ****
				root.key 	= node.key;
				root.value 	= node.value;
                     
                // **** delete the node ****
                root.right = delete(root.right, root.key);
            }
 
            // **** node to delete only has a right child ****
            else if (root.left == null) {
                return root = root.right;
            }
 
            // **** node to delete only has left child ****
            else if (root.right == null) {
                return root = root.left;
            }
        }
         
        // **** return root node ****
        return root;
	}
	

	/**
	 * In-order BST traversal.
	 * Collects in a list the nodes in the specified BST.
	 * Recursive call.
	 */
    public void toList(BSTNode node, List<BSTNode> lst) {
        if (node != null) {

			// **** traverse left sub tree ****
			toList(node.left, lst);
			
			// **** add node to list ****
			lst.add(node);
			
			// **** traverse right tree ****
            toList(node.right, lst);
        }
	}

	/**
	 * Look for the specified node in the BST.
	 * Entry point for recursive call.
	 */
	public BSTNode contains(int key) {

		// **** root of BST ****
		BSTNode node = this.tree;

		// **** check if key in BST ****
		return contains(node, key);
	}

	/**
	 * Look for the specified node in the BST.
	 * Recursive call.
	 */
	private BSTNode contains(BSTNode node, int key) {

		// **** end condition ****
		if (node == null)
			return null;

		// **** found node with key ****
		if (node.key == key)
			return node;

		// **** recurse left ****
		BSTNode f = contains(node.left, key);

		// **** node found ****
		if (f != null)
			return f;

		// **** recurse right ****
		f = contains(node.right, key);

		// **** return node (if found) ****
		return f;
	}
}
