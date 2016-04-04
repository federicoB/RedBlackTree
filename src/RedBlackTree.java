/**
 * Created by Federico Bertani on 13/03/16.
 */

/**
 * RedBlackTree is a data structure. It's a binary search tree with auto-balance system.
 *
 * @param <ItemType> the type of data that the tree will contain.
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
     * Use this for create the root.
     *
     * @param value ItemType: the value of the node to create.
     */
    public RedBlackTree(ItemType value) {
        //set the given value
        this.value = value;
        //the color of the root is black
        this.color = RBColor.BLACK;
        //set the leftchild as null because it doesn't exist yet.
        this.leftChild = null;
        //set the rightchild as null because it doesn't exist yet.
        this.rightChild = null;
    }

    /**
     * Create a new Tree with the given value and color.
     * Use this for creating every node.
     *
     * @param value ItemType: the value of the node to create.
     * @param color RBColor: the color of the new tree/node.
     */
    public RedBlackTree(ItemType value, RBColor color) {
        //set the given value
        this.value = value;
        //set the given color
        this.color = color;
        //set the leftchild as null because it doesn't exist yet.
        this.leftChild = null;
        //set the rightchild as null because it doesn't exist yet.
        this.rightChild = null;
    }

    /**
     * Search if a node contain the given item is contained on the three and if is return it.
     *
     * @param item ItemType: the item to search.
     * @return RedBlackTree&lt;ItemType&gt; : return the searched node if found, null otherwise.
     */
    public RedBlackTree<ItemType> lookUpNode(ItemType item) {
        //if the tree is a leaf and doens't cointains nothing return null
        if (this.value == null) return null;
            //otherwise if the node has a value
        else {
            //create a comparison variable with the comparison result with the researched item and the current value
            int comparison = this.value.compareTo(item);
            //if the node contains the searched value return himself.
            if (comparison == 0) return this;
                //else if the node value is less than the searched
            else if (comparison < 0) {
                //but the leftchild doesn't exist return null
                if (this.leftChild == null) return null;
                    //but if the leftchild exist call lookup on him
                else return this.leftChild.lookUpNode(item);
                //if the node value in greater of the searched instead
            } else {
                //but the rightchil doesn't exist return null
                if (this.rightChild == null) return null;
                    //but if the leftchild exist call lookup on him
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
        //save in a variable the value of the comparison for fast comparing
        int comparison = this.value.compareTo(item);
        //if the item is less than the current value
        if (comparison < 0) {
            //and the leftchild doesn't exist
            if (this.leftChild == null) {
                //create a new Tree on the leftchild
                this.leftChild = new RedBlackTree<ItemType>(item);
                //balance the leftchild
                this.leftChild.balance();
            } else {
                //otherwise call insert on the leftchild
                this.leftChild.insert(item);
            }
        }
        //if the item to insert in greater than the current value
        else {
            //and the rightchild doesn't exist
            if (this.rightChild == null) {
                //create a new Tree on the rightchild
                this.rightChild = new RedBlackTree<ItemType>(item);
                //balance the new rightchild.
                this.rightChild.balance();
            } else {
                //otherwise call insert on the rightchild
                this.rightChild.insert(item);
            }
        }
    }

    /**
     * Rotate the rightsubtree to the left.<br>
     * <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a> for better explanation.
     *
     * @see <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a>
     */
    private void rotateleft() {
        //if the node has a rightchild
        if (this.rightChild != null) {
            //get the rightchild
            RedBlackTree<ItemType> rightChild = this.rightChild;
            //get the parent
            RedBlackTree<ItemType> parent = this.parent;
            //substitute the rightchild with the richilds's leftchild
            this.rightChild = rightChild.leftChild;
            //if the rightchild's leftchild exist set its parent to the current node
            if (rightChild.leftChild != null) rightChild.leftChild.parent = this;
            //set the rightchild's leftchild to the current note
            rightChild.leftChild = this;
            //the parent of the current node become his rightchild
            this.parent = rightChild;
            //the rightchild parent become the parent of the current node
            rightChild.parent = parent;
            //if the current node is not the root
            if (parent != null) {
                //and the current node was his leftchild
                if (parent.leftChild == this) {
                    //change the parent leftchild
                    parent.leftChild = rightChild;
                    //or if the current node was his rightchild
                } else {
                    //change the parent rightchild
                    parent.rightChild = rightChild;
                }
            }
        }
    }

    /**
     * Rotate the rightsubtree to the left.<br>
     * <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a> for better explanation.
     * @see <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a>
     */
    private void rotateRight() {
        //if the node has a leftchild
        if (this.leftChild != null) {
            //get the leftchild
            RedBlackTree<ItemType> leftChild = this.leftChild;
            //get the parent
            RedBlackTree<ItemType> parent = this.parent;
            //substitute the leftchild with the leftchild's rightchild
            this.leftChild = leftChild.rightChild;
            //if the leftchild's rightchild exist set its parent to the current node
            if (leftChild.rightChild != null) leftChild.rightChild.parent = this;
            //set the leftchild's rightchild to the current node
            leftChild.rightChild = this;
            //the parent of the current node become his leftchild
            this.parent = leftChild;
            //the leftchild's parent becomes the parent of the current node
            leftChild.parent = parent;
            //if the current node is not a root
            if (parent != null) {
                //and the current node was his leftchild
                if (parent.leftChild == this) {
                    //change the parent leftchild
                    parent.leftChild = leftChild;
                    //or if the current node was his rightchild
                } else {
                    //change the parent rightchild
                    parent.rightChild = leftChild;
                }
            }
        }
    }

    /**
     * Gets the sibiling of the current tree.
     * For example the rightchild if the current tree is a leftchild or the opposite if the current tree is a rightchild.
     * @return RedBlackTree<ItemType>: the sibiling of the current tree.
     */
    private RedBlackTree<ItemType> getSibiling() {
        //create an initial sibiling with null value
        RedBlackTree<ItemType> sibiling = null;
        //check if the parent of the current node is not null
        if (this.parent != null) {
            //check if the leftchild of the parent exist and if it's the current node
            if (this.parent.leftChild != null && this.parent.leftChild == this) {
                //if it is then the sibiling will be the rightchild
                sibiling = this.parent.rightChild;
                //if the rightchild doesn't exist it's not a problem
            } else {
                //otherwise it will be the lefchild
                sibiling = this.parent.leftChild;
                //if the leftchild doesn't exist it's not a problem
            }
        }
        return sibiling;
    }

    /**
     * Balance the tree.
     * In this way all operation are made in log(N).
     * For complete reference see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree#Insertion"> the wiky page </a>
     */
    private void balance() {
        //get the parent
        RedBlackTree<ItemType> parent = this.parent;
        //if parent exist and grandparent exist
        if ((parent != null) && (parent.parent != null)) {
            //get the grandparent
            RedBlackTree<ItemType> grandParent = parent.parent;
            //get the uncle
            RedBlackTree<ItemType> uncle = parent.getSibiling();
            //if uncle exist
            if (uncle != null) {
                //and is Red
                if (uncle.color == RBColor.RED) {
                    //set parent and uncle to black
                    parent.color = uncle.color = RBColor.BLACK;
                    //set the grandparent on red this can cause breaking rules
                    grandParent.color = RBColor.RED;
                    //so call balance on grandparent
                    grandParent.balance();
                }
                //if the uncle is not red
                else {
                    //if we are the rightchild of our parent and his is the leftchild of the grandparent
                    if ((this == parent.rightChild) && (parent == grandParent.leftChild)) {
                        //rotate left
                        parent.rotateleft();
                        //call balance on parent
                        parent.balance();
                        //if we are the leftchild of out parent and his is the leftchild of the grandparent
                    } else if ((this == parent.leftChild) && (parent == grandParent.rightChild)) {
                        //rotate right
                        parent.rotateRight();
                        //call balance on parent
                        parent.balance();
                    } else {
                        //if we are a family of leftchilders
                        if ((this == parent.leftChild) && (parent == grandParent.leftChild)) {
                            //rotate right on grandparent
                            grandParent.rotateRight();
                            //or if we are a family of rightchilders
                        } else if ((this == parent.rightChild) && (parent == grandParent.rightChild)) {
                            //rotate left on grandparent
                            grandParent.rotateleft();
                        }
                    }
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
