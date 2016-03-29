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

        return null;
    }

    /**
     * Get the minimum item of the tree.
     *
     * @return ItemType: the minimum item of the tree.
     */
    public RedBlackTree<ItemType> min() {

        return null;
    }

    public RedBlackTree<ItemType> max() {

        return null;
    }

    /**
     * Define an enum for the color of the node.
     */
    private enum RBColor {
        BLACK, RED
    }


}
