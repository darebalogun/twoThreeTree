/**
 * Advanced implementation of a table using a 2-3 tree. Allows for insertion, traversal and deletion of integers into a set
 * Created by Oludare Balogun on 8/14/2017.
 */
public class TwoThreeTree <E, K extends Comparable<K>>{

    // Reference to root of 2-3 tree
    private TwoThreeNode<E,K> root;

    /**
     * Constructor creates a new 2-3 tree object
     */
    public TwoThreeTree(){
        root = null;
    }

    /**
     * Insert into the 2-3 tree recursively
     * @param value integer to be inserted into the set
     * @param root reference to the recursive root of the 2-3 tree
     */
    private void recuInsert(K key, E value, TwoThreeNode<E,K> root) {
        if (root.getRightData() != null) { // Current node is a 3-Node
            if (key.compareTo(root.getLeftData().getKey()) < 0 && root.getLeftChild() != null) { // Check left child
                recuInsert(key, value, root.getLeftChild());
            } else if (key.compareTo(root.getRightData().getKey()) > 0 && root.getRightChild() != null) { // Check right child
                recuInsert(key, value, root.getRightChild());
            } else if (root.getMiddleChild() != null) { // Check middle child
                recuInsert(key, value, root.getMiddleChild());
            } else { // Must be at a leaf
                split(key, value, root);
            }
        } else { // Current Node is a 2 Node
            if (key.compareTo(root.getLeftData().getKey()) < 0 && root.getLeftChild() != null) { //Check left child
                recuInsert(key, value, root.getLeftChild());
            } else if (root.getMiddleChild() != null) { //Check middle child
                recuInsert(key, value, root.getMiddleChild());
            } else { // At a leaf
                split(key, value, root);
            }
        }
    }

    /**
     * Split function called by insertion function when node to be inserted into has been found
     * @param value integer value to be inserted into the tree
     * @param root reference to the current node being inserted into
     */
    private void split(K key, E value, TwoThreeNode<E,K> root){
        if (root.getRightData() == null) { // Insertion location is a 2-Node so just insert into node to make it 3-Node
            if (key.compareTo(root.getLeftData().getKey()) < 0){ // insert value into left value and bump other one to right
                root.setRightData(root.getLeftData());
                root.setLeftData(new KeyValuePair(key, value));
            } else { // insert value into right value
                root.setRightData(new KeyValuePair(key, value));
            }
            root.setRightChild(root.temp);

        } else if (root.getParent() == null) { // Insertion node is a 3-Node that is the root of the tree so split 3 node into 2-Node with 2 children
            TwoThreeNode smallLeft, smallRight, bigLeft, bigRight;
            smallLeft = root.getLeftChild();

            if (root.temp != null && root.temp.getLeftData().getKey().compareTo(root.getMiddleChild().getLeftData().getKey()) > 0){  // Assign reference to the child nodes before splitting
                smallRight = root.getMiddleChild();
                bigLeft = root.getRightChild();
                bigRight = root.temp;
            } else {
                smallRight = root.temp;
                bigLeft = root.getMiddleChild();
                bigRight = root.getRightChild();
            }

            // Split the 3-Node, promote the middle value and link the remaining 2 2-Node to their children
            if (key.compareTo(root.getLeftData().getKey()) < 0) {
                root.setLeftChild(new TwoThreeNode(value, key,null, null,smallLeft,null,smallRight,root));
                if (smallLeft != null) { // Link children to their new parent's if not null
                    smallLeft.parent = root.getLeftChild();
                    smallRight.parent = root.getLeftChild();
                }
                root.setMiddleChild(new TwoThreeNode(root.getRightData(), root.getRightData().getKey(),null, null, bigLeft, null, bigRight,root));
                if (bigLeft != null) { // Link children to their new parent's if not null
                    bigLeft.parent = root.getMiddleChild();
                    bigRight.parent = root.getMiddleChild();
                }
                root.setRightData(null);
                root.setRightChild(null);
            } else if (key.compareTo(root.getRightData().getKey()) > 0){
                root.setLeftChild(new TwoThreeNode(root.getLeftData().getValue(), root.getLeftData().getKey(),null, null,smallLeft,null,smallRight,root));
                if (smallLeft != null) { // Link children to their new parent's if not null
                    smallLeft.parent = root.getLeftChild();
                    smallRight.parent = root.getLeftChild();
                }
                root.setMiddleChild(new TwoThreeNode(value, key, null,null, bigLeft, null, bigRight,root));
                if (bigLeft != null) { // Link children to their new parent's if not null
                    bigLeft.parent = root.getMiddleChild();
                    bigRight.parent = root.getMiddleChild();
                }
                root.setLeftData(root.getRightData());
                root.setRightData(null);
                root.setRightChild(null);
            } else {
                root.setLeftChild(new TwoThreeNode(root.getLeftData().getValue(), root.getLeftData().getKey(), null,null,smallLeft,null,smallRight,root));
                if (smallLeft != null) {
                    smallLeft.parent = root.getLeftChild();
                    smallRight.parent = root.getLeftChild();
                }
                root.setMiddleChild(new TwoThreeNode(root.getRightData().getValue(), root.getRightData().getKey(), null,null, bigLeft, null, bigRight,root));
                if (bigLeft != null) {
                    bigLeft.parent = root.getMiddleChild();
                    bigRight.parent = root.getMiddleChild();
                }
                root.setLeftData(new KeyValuePair(key, value));
                root.setRightData(null);
                root.setRightChild(null);
            }
        } else { // Insertion node is a 3-Node that is not the root. Split the 3-Node into 2 two nodes and promote the middle value up to its parent.
           KeyValuePair temp;
            TwoThreeNode smallLeft, smallRight, bigLeft, bigRight;
            smallLeft = root.getLeftChild();
            if (root.temp != null && root.temp.getLeftData().getKey().compareTo(root.getMiddleChild().getLeftData().getKey()) > 0){
                smallRight = root.getMiddleChild();
                bigLeft = root.getRightChild();
                bigRight = root.temp;
            } else {
                smallRight = root.temp;
                bigLeft = root.getMiddleChild();
                bigRight = root.getRightChild();
            }
            if (key.compareTo(root.getLeftData().getKey()) < 0) { // Split and promote left child
                temp = root.getLeftData();
                root.setLeftData(new KeyValuePair(key, value));
            } else if (key.compareTo(root.getRightData().getKey()) > 0) {
                temp = root.getRightData();
                root.setRightData(new KeyValuePair(key, value));
            } else {
                temp = new KeyValuePair(key, value);
            }
            root.getParent().temp = new TwoThreeNode(root.getRightData().getValue(), root.getRightData().getKey(), null, null, bigLeft, null, bigRight, root.getParent());
            root.setRightData(null);
            root.setRightChild(null);
            // Assign new nodes to their children
            root.setLeftChild(smallLeft);
            root.setMiddleChild(smallRight);
            // If child nodes are not null. Assign child nodes to parents
            if (smallLeft != null){
                smallLeft.parent = root;
                smallRight.parent = root;
                bigLeft.parent = root.getParent().temp;
                bigRight.parent = root.getParent().temp;
            }
            split((K) temp.getKey(), (E) temp.getValue(), root.getParent());
        }
    }

    /**
     * Insert into 2-3 tree
     * @param value int to be inserted into the tree
     */
    public void insert(K key, E value){
        if (root == null){
            root = new TwoThreeNode(value, key, null,null,null,null,null, null);
        } else {
            recuInsert(key, value, root);
        }
    }

    /**
     * Recursively delete values from the tree by swapping with in order successor and then merge or redistribute
     * @param key key of value to be deleted from the tree
     * @param root root of the tree or subtree
     */
    private void recuDelete(K key, TwoThreeNode<E,K> root){
        if (root == null){ // Not found or tree is empty
            return;
        }
        if (root.getRightData() == null) { //Node being checked is 2-Node
            if (key.compareTo(root.getLeftData().getKey()) < 0) { //Check left subtree
                recuDelete(key,root.getLeftChild());
            } else if (key.compareTo(root.getLeftData().getKey()) > 0) { // Check middle subtree
                recuDelete(key, root.getMiddleChild());
            } else { //Node contains value to be deleted
                if (root.getMiddleChild() != null){ // Node has children swap node with in order successor
                    root.setLeftData(minValue(root.getMiddleChild()).getLeftData());
                    recuDelete((K) root.getLeftData().getKey(),root.getMiddleChild());
                } else { // node is a leaf
                    if (root.getLeftData().getKey().compareTo(key) != 0){
                        return;
                    }
                    if (root.getParent().getRightData() != null){ // Root is child of a 3 node
                        if (root == root.getParent().getMiddleChild()) { // root is middle child of 3 node
                            KeyValuePair temp = root.getParent().getLeftData();
                            root.getParent().setLeftData(root.getParent().getRightData());
                            root.getParent().setRightData(null);
                            root.getParent().setMiddleChild(root.getRightChild());
                            root.getParent().setRightChild(null);
                            root.getParent().getLeftChild().setRightData(temp);
                        } else if (root == root.getParent().getLeftChild()){ // root is left child of 3 node
                            KeyValuePair temp = root.getParent().getLeftData();
                            root.getParent().setLeftData(root.getParent().getRightData());
                            root.getParent().setRightData(null);
                            root.getParent().setLeftChild(root.getParent().getMiddleChild());
                            root.getParent().setMiddleChild(root.getParent().getRightChild());
                            root.getParent().setRightChild(null);
                            root.getParent().getLeftChild().setRightData(root.getParent().getLeftChild().getLeftData());
                            root.getParent().getLeftChild().setLeftData(temp);
                        } else { // root is right child of 3 node
                            KeyValuePair temp = root.getParent().getRightData();
                            root.getParent().setRightData(null);
                            root.getParent().setRightChild(null);
                            root.getParent().getMiddleChild().setRightData(temp);
                            //root.parent = null;
                        }
                    } else { // root is child of 2 node
                        if (root.getParent().getMiddleChild() == root){ // root is right child of 2 node
                            if (root.getParent().getLeftChild().getRightData() != null) { // Root's sibling on the left is a 3 node
                                KeyValuePair temp = root.getParent().getLeftData();
                                root.getParent().setLeftData(root.getParent().getLeftChild().getRightData());
                                root.getParent().getLeftChild().setRightData(null);
                                root.setLeftData(temp);
                            } else { // Root's sibling on the left is a 2 node
                                root.getParent().getLeftChild().setRightData(root.getParent().getLeftData());
                                root.getParent().setLeftData(null);
                                merge(root.getParent().getParent(),root.getParent().getLeftChild());
                            }
                        } else { // Root is left child of 2 Node
                            if (root.getParent().getLeftChild().getRightData() != null) { // Root's sibling on the right is a 3 node
                                KeyValuePair temp = root.getParent().getLeftData();
                                root.getParent().setLeftData(root.getParent().getRightChild().getLeftData());
                                root.getParent().getRightChild().setLeftData(root.getParent().getRightChild().getRightData());
                                root.getParent().getRightChild().setRightData(null);
                                root.setLeftData(temp);
                            } else { // Root's sibling on the right is a 2 node
                                root.getParent().getRightChild().setRightData(root.getParent().getLeftData());
                                root.getParent().setLeftData(null);
                                merge(root.getParent().getParent(),root.getParent().getRightChild());
                            }
                        }
                    }
                }
            }
        } else { // Node being checked is 3-Node (easy case)
            if (key.compareTo(root.getLeftData().getKey()) < 0) { // Check left subtree
                recuDelete(key,root.getLeftChild());
            } else if (key.compareTo(root.getRightData().getKey()) > 0) { // Check right subtree
                recuDelete(key, root.getRightChild());
            } else if (key.compareTo(root.getLeftData().getKey()) > 0 && key.compareTo(root.getRightData().getKey()) < 0){ //Check middle subtree
                recuDelete(key, root.getMiddleChild());
            } else { // Node contains value to be deleted
                if (root.getLeftData().getKey().compareTo(key) != 0 || root.getRightData().getKey().compareTo(key) != 0){ // Not found, return
                    return;
                }
                if (key.compareTo(root.getLeftData().getKey()) == 0  && root.getMiddleChild() != null){ //value is smaller value in node and node has children
                    root.setLeftData(minValue(root.getMiddleChild()).getLeftData());
                    recuDelete((K) root.getLeftData().getKey(),root.getMiddleChild());
                } else if (key.compareTo(root.getRightData().getKey()) == 0 && root.getRightChild() != null) { // value is larger value in node and node has children
                    root.setRightData(minValue(root.getRightChild()).getLeftData());
                    recuDelete((K) root.getRightData().getKey(),root.getRightChild());
                } else { // node is a leaf
                    if (root.getLeftData().getKey().compareTo(key) == 0){ //Delete smaller value
                        root.setLeftData(root.getRightData());
                        root.setRightData(null);
                    } else { // Delete larger value
                        root.setRightData(null);
                    }
                }
            }
        }
    }

    /**
     * Called by the recursive delete function if value to be deleted is a 2 node and its parent is a 2 node
     * @param root parent of node to be deleted
     * @param child reference to merged leaf node
     */
    private void merge(TwoThreeNode root, TwoThreeNode child){
            if (root.getMiddleChild().getLeftData() == null){ // merging on the right
                if (root.getLeftChild().getRightData() == null) { // 2 node on left side of root
                    if (root.getParent().getLeftChild() == root) {
                        root.getParent().setLeftChild(root.getLeftChild());
                    } else if (root.getParent().getMiddleChild() == root) {
                        root.getParent().setMiddleChild(root.getLeftChild());
                    } else {
                        root.getParent().setRightChild(root.getLeftChild());
                    }
                    root.getLeftChild().setRightData(root.getLeftData());
                    root.setRightChild(child);
                } else { // 3 node on left side of root
                    KeyValuePair temp = child.getLeftData();
                    root.getMiddleChild().setLeftData(temp);
                    root.getMiddleChild().getMiddleChild().setLeftData(child.getRightData());
                    child.setRightData(null);
                    root.setLeftData(maxValue(root.getLeftChild()).getLeftData());
                    recuDelete((K) root.getLeftData().getKey(),root.getLeftChild());
                }
            } else if (root.getLeftChild().getLeftData() == null){ //merging on the left
                if (root.getMiddleChild().getRightData() == null) { // 2 node on the right side of root
                    if (root.getParent().getLeftChild() == root) {
                        root.getParent().setLeftChild(root.getMiddleChild());
                    } else if (root.getParent().getMiddleChild() == root) {
                        root.getParent().setMiddleChild(root.getMiddleChild());
                    } else {
                        root.getParent().setRightChild(root.getMiddleChild());
                    }
                    root.getMiddleChild().setRightData(root.getMiddleChild().getLeftData());
                    root.getMiddleChild().setRightChild(root.getMiddleChild().getMiddleChild());
                    root.getMiddleChild().setLeftData(root.getLeftData());
                    root.getMiddleChild().setLeftChild(child);
                } else { // 3 node on the right side of root
                    KeyValuePair temp = child.getRightData();
                    root.getLeftChild().setLeftData(temp);
                    child.setRightData(null);
                    root.getLeftChild().getMiddleChild().setLeftData(root.getLeftData());
                    root.setLeftData(minValue(root.getMiddleChild()).getLeftData());
                    recuDelete((K) root.getLeftData().getKey(),root.getMiddleChild());
                }
           }
    }

    /**
     * Helper function that returns in order successor
     * @param root path of subtree immediately greater than value of current node
     * @return node containing in-order successor
     */
    private static TwoThreeNode minValue (TwoThreeNode root) {
        if (root.getLeftChild() == null){
            return root;
        } else {
            return minValue(root.getLeftChild());
        }
    }

    /**
     * Helper function that returns in order predecessor
     * @param root path of subtree immediately less than value of current node
     * @return node containing in-order predecessor
     */
    private static TwoThreeNode maxValue (TwoThreeNode root) {
        if (root.getMiddleChild() == null){
            return root;
        } else if (root.getRightChild() != null) {
            return maxValue(root.getRightChild());
        } else {
            return maxValue(root.getMiddleChild());
        }
    }

    /**
     * Deletes value specified from the set
     * @param key key of value to be deleted from the set
     */
    public void delete(K key){
        if (root == null){ // Do nothing, empty tree
            return;
        } else {
            recuDelete(key, root);
        }
    }

    /**
     * Inorder traversal algorithm for the 2-3 tree
     * @param root reference to the root of the tree
     */
    public static void recuPrint(TwoThreeNode root){
        if (root.getLeftChild() == null){
            System.out.println(root.getLeftData().getValue());
            if (root.getRightData() != null){
                System.out.println(root.getRightData().getValue());
            }
        } else if (root.getRightChild() != null){
            recuPrint(root.getLeftChild());
            System.out.println(root.getLeftData().getValue());
            recuPrint(root.getMiddleChild());
            System.out.println(root.getRightData().getValue());
            recuPrint(root.getRightChild());
        } else {
            recuPrint(root.getLeftChild());
            System.out.println(root.getLeftData().getValue());
            recuPrint(root.getMiddleChild());
        }
    }

    /**
     * Prints contents of the set
     */
    public void printTree(){
        System.out.println();
        recuPrint(root);
    }

    public static void main(String[] args) {
        TwoThreeTree tree = new TwoThreeTree();

        // Add 1 to 10 to the set
        for (int i = 0; i < 10; i++) {
            tree.insert(new Integer(i + 1), new Integer(i + 1));
        }

        //Delete 3 then print
       // tree.delete(3);
        tree.printTree();

        // Delete 7 then print
        //tree.delete(7);
        //tree.printTree();

        //Delete 13?
       // tree.delete(13);
    }
}
