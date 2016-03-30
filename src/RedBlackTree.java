/**
 * Created by Federico Bertani on 13/03/16.
 */

/**
 * RedBlackTree is a type of Data structure. It's a binary search tree with auto-balance.
 *
 * @param <ItemType extends Comparable<ItemType>> the type of data that the tree will contain.
 *                  It must implement the comparable interface and being comparable with his own.
 */
public class RedBlackTree<ItemType extends Comparable<ItemType>> {

    /**
     * The parent tree.
     */
    private RedBlackTree<ItemType> parent;

    /**
     * The left subtree.
     */
    private RedBlackTree<ItemType> leftChild;
    /**
     * The right subtree.
     */
    private RedBlackTree<ItemType> rightChild;
    /**
     * The value of the node.
     */
    private ItemType value;
    /**
     * The color of the node.
     */
    private RBColor color;

    /**
     * Create a new Tree with a given value.
     *
     * @param value ItemType: the value of the node to create.
     */
    public RedBlackTree(ItemType value) {
        this.value = value;
        this.color = RBColor.BLACK;
        this.leftChild = null;
        this.rightChild = null;
    }

    public RedBlackTree(ItemType value, RBColor color) {
        this.value = value;
        this.color = color;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Search if a node containg the given item is contained on the three and if is return it.
     *
     * @param item ItemType: the item to search.
     * @return RedBlackTree&lt;ItemType&gt; : return the searched node if found, null otherwise.
     */
    public RedBlackTree<ItemType> lookUpNode(ItemType item) {
        if (this.value == null) return null;
        else {
            int comparison = this.value.compareTo(item);
            if (comparison == 0) return this;
            else if (comparison < 0) {
                if (this.leftChild == null) return null;
                else return this.leftChild.lookUpNode(item);
            } else {
                if (this.rightChild == null) return null;
                else return this.rightChild.lookUpNode(item);
            }
        }
    }

    /**
     * Get the next nearest node.
     *
     * @return RedBlackTree&lt;ItemType&gt; : the successor node.
     */
    private RedBlackTree<ItemType> successorNode() {
        return this.rightChild.min();
    }

    /**
     * Get the previous nearest node.
     *
     * @return RedBlackTree&lt;ItemType&gt; : the predecessor node.
     */
    private RedBlackTree<ItemType> predecessorNode() {
        return this.leftChild.max();
    }

    /**
     * Get the tree that cointans the minimum item of the tree.
     *
     * @return ItemType: the tree that cointans the the minimum item of the tree.
     */
    public RedBlackTree<ItemType> min() {
        if (this.leftChild == null) return this;
        else return this.leftChild.min();
    }

    /**
     * Get the tree that cointans the maximum item of the tree.
     *
     * @return ItemType: the tree that cointans the the maximum item of the tree.
     */
    public RedBlackTree<ItemType> max() {
        if (this.rightChild == null) return this;
        else return this.rightChild.max();
    }

    /**
     * Insert a new node on the tree.
     *
     * @param item ItemType: the item to insert in the tree.
     */
    public void insert(ItemType item) {

    }

    public void rotateleft() {
        if (this.rightChild != null) {
            RedBlackTree<ItemType> rightChild = this.rightChild;
            RedBlackTree<ItemType> parent = this.parent;
            this.rightChild = rightChild.leftChild;
            if (rightChild.leftChild != null) rightChild.leftChild.parent = this;
            rightChild.leftChild = this;
            this.parent = rightChild;
            rightChild.parent = parent;
            if (parent != null) {
                if (parent.leftChild == this) {
                    parent.leftChild = rightChild;
                } else {
                    parent.rightChild = rightChild;
                }
            }
        }
    }

    public void rotateRight() {
        if (this.leftChild != null) {
            RedBlackTree<ItemType> leftChild = this.leftChild;
            RedBlackTree<ItemType> parent = this.parent;
            this.leftChild = leftChild.rightChild;
            if (leftChild.rightChild != null) leftChild.rightChild.parent = this;
            leftChild.rightChild = this;
            this.parent = leftChild;
            leftChild.parent = parent;
            if (parent != null) {
                if (parent.leftChild == this) {
                    parent.leftChild = leftChild;
                } else {
                    parent.rightChild = leftChild;
                }
            }
        }
    }

    /**
     * Define an enum for the color of the node.
     */
    private enum RBColor {
        BLACK, RED
    }


}
