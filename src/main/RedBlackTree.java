/**
 * Created by Federico Bertani on 13/03/16.
 */

/**
 * RedBlackTree is a data structure. It's a binary search tree with auto-balance system.
 *
 * @param <ItemType> the type of data that the tree will contain.
 *                   It must implement the comparable interface and being comparable with his own.
 */
public class RedBlackTree<ItemType extends Comparable<ItemType>> {

    /**
     * The value of the node.
     */
    private ItemType value;
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
     * The color of the node.
     */
    private RBColor color;

    private RedBlackTree<ItemType> nullLeaf;

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
        //create a new nullLeaf for the entire tree
        this.nullLeaf = new RedBlackTree<ItemType>();
        //set the leftchild as null because it doesn't exist yet.
        this.leftChild = nullLeaf;
        //set the rightchild as null because it doesn't exist yet.
        this.rightChild = nullLeaf;
    }

    /**
     * Used for create empty black leaves
     */
    private RedBlackTree() {
        this.value = null;
        this.color = RBColor.BLACK;
        this.leftChild = null;
        this.rightChild = null;
        this.nullLeaf = this;
    }

    /**
     * Create a new Tree with the given value and parent.
     * Use internally for adding nodes to an existing tree.
     *
     * @param value  ItemType: the value of the node to create.
     * @param parent RedBlackTree<ItemType>: the parent of the new node.
     */
    private RedBlackTree(ItemType value, RedBlackTree<ItemType> parent) {
        //set the given value
        this.value = value;
        //set the given parent
        this.parent = parent;
        //set the tree nullleaf as the parent nullleaf to save memory
        this.nullLeaf = parent.nullLeaf;
        //set the leftchild as null because it doesn't exist yet.
        this.leftChild = this.nullLeaf;
        //set the rightchild as null because it doesn't exist yet.
        this.rightChild = this.nullLeaf;
        //set a red color
        this.color = RBColor.RED;
    }

    /**
     * Get the value cointained in the node.
     *
     * @return ItemType: the value of the node.
     */
    public ItemType getValue() {
        return value;
    }

    /**
     * Find the node with the value given or the nearest node if the value is not present in the tree.
     *
     * @param item ItemType: the item to search.
     * @return RedBlackTree&lt;ItemType&gt; : return the searched node if found.
     */
    private RedBlackTree<ItemType> find(ItemType item) {
        //create a comparison variable with the comparison result with the researched item and the current value
        int comparison = this.value.compareTo(item);
        //if the node contains the searched value or is a leaf return himself.
        if ((comparison == 0) || ((this.leftChild == nullLeaf) && (this.rightChild == nullLeaf))) return this;
            //else if the node value is greater than the searched
        else if (comparison > 0) {
            //but the leftchild doesn't exist return this
            if (this.leftChild == nullLeaf) return this;
                //but if the leftchild exist call lookup on him
            else return this.leftChild.find(item);
            //if the node value in greter of the searched instead
        } else {
            //but the rightchil doesn't exist return this
            if (this.rightChild == nullLeaf) return this;
                //but if the leftchild exist call lookup on him
            else return this.rightChild.find(item);
        }
    }

    /**
     * Search if a node contain the given item is contained on the three and if is return it.
     *
     * @param item ItemType: the item to search.
     * @return RedBlackTree&lt;ItemType&gt; : return the searched node if found, null otherwise.
     */
    public RedBlackTree<ItemType> lookUpNode(ItemType item) {
        //find the node with the searched value of the nearest
        RedBlackTree<ItemType> result = this.find(item);
        //if the node found has the value searched the item is in the tree.
        if (result.getValue() == item) return result;
            //otherwise return null
        else return null;
    }

    /**
     * Check if an item is in the tree or not.
     *
     * @param item ItemType: the item to search
     * @return boolean: true if the item is constained in the tree false otherwise.
     */
    public boolean contains(ItemType item) {
        return (this.lookUpNode(item) != null);
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
        //TODO also add case where leftchild is null (min called on nulleaf)
        if (this.leftChild == nullLeaf) return this;
        else return this.leftChild.min();
    }

    /**
     * Get the tree that cointans the maximum item of the tree.
     *
     * @return ItemType: the tree that cointans the the maximum item of the tree.
     */
    public RedBlackTree<ItemType> max() {
        //TODO also add case where rightchild is null (max called on nulleaf)
        if (this.rightChild == nullLeaf) return this;
        else return this.rightChild.max();
    }

    /**
     * Insert a new node on the tree.
     * It return the new root of the tree that can have changed because of balancing.
     *
     * @param item ItemType: the item to insert in the tree.
     * @return RedBlackTree<ItemType>: the new root of the tree.
     */
    public RedBlackTree<ItemType> insert(ItemType item) {
        //get the possibile parent node is the item don't exist already in the tree
        RedBlackTree<ItemType> possibleParentNode = find(item);
        //get the value of this possibile parent node
        ItemType nodeValue = possibleParentNode.getValue();
        //if the value is different from the value to insert this means that the searched node is the nearest(successor or predecessor) to the future position of the inserted node.
        if (nodeValue != item) {
            //create a comparison variable with the comparison result with the researched item and the parent value
            int comparison = nodeValue.compareTo(item);
            //if the current value is grater than the item
            if (comparison > 0) {
                //create a new node/tree on the leftchild
                possibleParentNode.leftChild = new RedBlackTree<>(item, possibleParentNode);
                //balance the leftchild
                possibleParentNode.leftChild.balance();
            } else {
                //create a new node/tree on the rightchild
                possibleParentNode.rightChild = new RedBlackTree<>(item, possibleParentNode);
                //balance the rightchild
                possibleParentNode.rightChild.balance();
            }
        }
        //return the root
        //called on this for reduce complexity of one
        return this.getRoot();
    }

    /**
     * Rotate the rightsubtree to the left.<br>
     * <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a> for better explanation.
     * Call it on the rotation's pivot.
     *
     * @return RedBlackTree<ItemType>: the new root of the tree
     * @see <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a>
     */
    private RedBlackTree<ItemType> rotateleft() {
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
            //return the new root of the tree if the rotation happened
            return rightChild;
        }
        //if nothing rotate return the old root
        return this;
    }

    /**
     * Rotate the rightsubtree to the left.<br>
     * <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a> for better explanation.
     *
     * @return RedBlackTree<ItemType>: the new root of the tree
     * @see <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a>
     */
    private RedBlackTree<ItemType> rotateRight() {
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
            //return the new root of the tree if the rotation happened
            return leftChild;
        }
        //if nothing rotate return the old root
        return this;
    }

    /**
     * Gets the sibiling of the current tree.
     * For example the rightchild if the current tree is a leftchild or the opposite if the current tree is a rightchild.
     *
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
     * Return the root of the tree from which the current node belongs
     *
     * @return RedBlackTree<ItemType>: the root of the tree.
     */
    RedBlackTree<ItemType> getRoot() {
        if (this.parent == null) return this;
        else return this.parent.getRoot();
    }

    /**
     * Balance the tree.
     * In this way all operation are made in log(N).
     * For complete reference see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree#Insertion"> the wiky page </a>
     */
    private void balance() {
        //get the parent
        RedBlackTree<ItemType> parent = this.parent;
        //if the node has no parent
        if (parent == null) {
            //the node is the root so we have to color it black
            this.color = RBColor.BLACK;
        } else
            //if parent exist and is black no problem but if is red grandparent must exist //TODO check this
            if ((parent.color == RBColor.RED) && (parent.parent != null)) {
                //get the grandparent
                RedBlackTree<ItemType> grandParent = parent.parent;
                //get the uncle
                RedBlackTree<ItemType> uncle = parent.getSibiling();
                //if uncle exist //TODO maybe remove this the uncle always exist
                if (uncle != null) {
                    //and is Red
                    if (uncle.color == RBColor.RED) {
                        //set parent and uncle to black
                        parent.color = uncle.color = RBColor.BLACK;
                        //set the grandparent on red.This can cause breaking rules
                        grandParent.color = RBColor.RED;
                        //so call balance on grandparent
                        grandParent.balance();
                    }
                    //if the uncle is not red
                    else {
                        //if we are the rightchild of our parent and his is the leftchild of the grandparent
                        if ((this == parent.rightChild) && (parent == grandParent.leftChild)) {
                            //rotate left
                            parent = parent.rotateleft();
                            //call balance on parent
                            parent.balance();
                            //if we are the leftchild of out parent and his is the leftchild of the grandparent
                        } else if ((this == parent.leftChild) && (parent == grandParent.rightChild)) {
                            //rotate right
                            parent = parent.rotateRight();
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
                            //fix the color of the rotation, set the old grandparent to red
                            //because this and grandparent are now children of parent
                            grandParent.color = RBColor.RED;
                            //and the old parent to black
                            parent.color = RBColor.BLACK;
                        }
                    }

                }
            }
    }

    private void trasplant(RedBlackTree<ItemType> toRemove, RedBlackTree<ItemType> toInsert) {
        //save the parent in a variable
        RedBlackTree<ItemType> parent = toRemove.parent;
        //if the node to remove have a parent we need do change references
        if (parent != null) {
            //if the nodeToRemove is a leftchild set the new leftchild
            if (parent.leftChild == toRemove) parent.leftChild = toInsert;
                //do the opposite if the node to remove is a rightchild
            else parent.rightChild = toInsert;
        }
        //set the correct parent in the node to insert
        toInsert.parent = parent;
    }

    /**
     * Delete a node from the tree containing the given value
     *  It returns alwais the root of the tree. It is discouraged to call delete on a subtree.
     * @param itemToDelete the item to delete from the tree.
     * @return RedBlackTree<ItemType>: the new root of the tree.
     */
    public RedBlackTree<ItemType> delete(ItemType itemToDelete) throws RootDeletionException {
        //if (this.value==itemToDelete) throw new RootDeletionException();
        //get the node to delete
        RedBlackTree<ItemType> toRemove = lookUpNode(itemToDelete);
        //create a variable for keep track of a child node to use as replacer of the node to delete
        RedBlackTree<ItemType> replacer = this;
        //if the node to delete is found
        if (toRemove != null) {
            //if the node has only one or zero child
            if (toRemove.leftChild == nullLeaf || toRemove.rightChild == nullLeaf) {
                //set the not-null child to a transplant child
                replacer = (toRemove.leftChild == nullLeaf) ? toRemove.rightChild : toRemove.leftChild;
                trasplant(toRemove, replacer);
                replacer.fixDelete(toRemove.color);
            } else { //if the node to delete has two children
                //get its successor (the smaller element of the right subtree)
                RedBlackTree<ItemType> childToDelete = toRemove.rightChild.min();
                //copy ONLY the value
                toRemove.value = childToDelete.value;
                //remove the cloned child, this will end up to zero or one child
                childToDelete.delete(childToDelete.value);
            }
        }
        //return the changed tree
        return replacer.getRoot();
    }

    /**
     * Call this on a node after a rotation for check and rebalance the tree.
     * It check if the red-black tree rules are respected.
     *
     * @param toDeleteColor
     */
    private void fixDelete(RBColor toDeleteColor) {
        //If either u or v is red
        if (((toDeleteColor == RBColor.BLACK) && (this.color == RBColor.RED))
                || ((toDeleteColor == RBColor.RED) && (this.color == RBColor.BLACK))) {
            //paint it black. This doesn't change black height
            this.color = RBColor.BLACK;
            //else if both nodes are black
        } else if ((toDeleteColor == RBColor.BLACK) && (this.color == RBColor.BLACK)) {
            //black height has changed. node "this" is double black
            RedBlackTree<ItemType> uncle = this.getSibiling();
            if (uncle.color == RBColor.BLACK) {
                RedBlackTree<ItemType> uncleRedChild;
                //get the red children of the uncle (if exist)
                uncleRedChild = (uncle.leftChild.color == RBColor.RED) ? uncle.leftChild : ((uncle.rightChild.color == RBColor.RED) ? uncle.rightChild : null);
                if (uncleRedChild != null) {
                    //restructuring
                    if (uncle.parent.leftChild == uncle) { //uncle is a leftchildren
                        if (uncle.rightChild == uncleRedChild) {
                            //uncle rightchild is red. uncle leftchild can be black or red, it doesn't matter
                            parent.leftChild = nullLeaf;
                            uncleRedChild.rightChild = parent;
                            uncleRedChild.parent = parent.parent;
                            parent.parent = uncleRedChild;
                            uncleRedChild.leftChild = uncle;
                            uncle.parent = uncleRedChild;
                            uncleRedChild.color = RBColor.BLACK;
                            uncle.rightChild = nullLeaf;
                        } else {
                            parent.rotateRight();
                            uncleRedChild.color = RBColor.BLACK;
                        }
                    } else { //uncle is a rightchildren
                        if (uncle.leftChild == uncleRedChild) {
                            //uncle leftchild is red. uncle rightchild can be black or red, it doesn't matter
                            parent.rightChild = nullLeaf;
                            uncleRedChild.leftChild = parent;
                            uncleRedChild.parent = parent.parent;
                            parent.parent = uncleRedChild;
                            uncleRedChild.rightChild = uncle;
                            uncle.parent = uncleRedChild;
                            uncleRedChild.color = RBColor.BLACK;
                            uncle.leftChild = nullLeaf;
                        } else {
                            parent.rotateleft();
                            uncleRedChild.color = RBColor.BLACK;
                        }
                    }
                } else {
                    //recoloring
                    uncle.color = RBColor.RED;
                    if (parent.color == RBColor.RED) {
                        parent.color = RBColor.BLACK;
                    } else {
                        //else if the parent is black
                        //the parent is now "double black"
                        if (parent.parent != null) {
                            parent.fixDelete(parent.color);
                        }
                    }
                }
            } else {
                //uncle is red so it has two black children
                parent.rotateleft();
                uncle.color = RBColor.BLACK;
                parent.color = RBColor.RED;
                this.fixDelete(toDeleteColor);
            }
        }
    }

    /**
     * Get the height of the tree. The max distance between the root and a leaf.
     *
     * @return int: the height of the tree.
     */
    int getHeight() {
        //TODO maybe not the best complexity, can be improved
        //initialize a counter for left child height
        int leftheight = 1;
        //initialize a counter for right child height
        int rightheight = 1;
        //if the leftchild and the rightchild doesn't exist
        if (leftChild == nullLeaf && rightChild == nullLeaf) {
            //return the heigth of the current node, that is one
            return 1;
            //if a child exist
        } else {
            //if leftchild exist
            if (leftChild != nullLeaf) {
                //sum the height of the leftchild
                leftheight += this.leftChild.getHeight();
            }
            //if the rightchild exist
            if (rightChild != nullLeaf) {
                //sum the height of the rightchild
                rightheight += this.rightChild.getHeight();
            }
            //if the leftheight is greater than the rightheight
            if (leftheight > rightheight) {
                //return the leftheight
                return leftheight;
            } else {
                //otherwise return the rightheight
                return rightheight;
            }
        }
    }


    /**
     * Define an enum for the color of the node.
     */
    private enum RBColor {
        BLACK, RED
    }

    public static class RootDeletionException extends Exception {
        public RootDeletionException() {
            super("Root element cannot be deleted, the tree must have at least one element");
        }
    }


}
