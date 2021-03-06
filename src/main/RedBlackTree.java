/*
 * Created by Federico Bertani on 13/03/16.
 * Copyright (c) 2016 Federico Bertani
 * This file is part of RedBlackTree.
 * RedBlackTree is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * RedBlackTree is a data structure. It's a binary search tree with auto-balance system.
 *
 * @param <ItemType> the type of data that the tree will contain.
 *                   It must implement the comparable interface and being comparable with his own.
 */
public class RedBlackTree<ItemType extends Comparable<ItemType>> {

    private final RedBlackTree<ItemType> nullLeaf;
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
        this.nullLeaf = new RedBlackTree<>();
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
     * Get the value contained in the node.
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
            //if the node value in greater of the searched instead
        } else {
            //but the rightchild doesn't exist return this
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
        if (result.getValue().compareTo(item) == 0) return result;
            //otherwise return null
        else return null;
    }

    /**
     * Check if an item is in the tree or not.
     *
     * @param item ItemType: the item to search
     * @return boolean: true if the item is contained in the tree false otherwise.
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
     * Get the tree that contains the minimum item of the tree.
     *
     * @return ItemType: the tree that contains the the minimum item of the tree.
     */
    public RedBlackTree<ItemType> min() {
        if (this.leftChild == nullLeaf) return this;
        else return this.leftChild.min();
    }

    /**
     * Get the tree that contains the maximum item of the tree.
     *
     * @return ItemType: the tree that contains the the maximum item of the tree.
     */
    public RedBlackTree<ItemType> max() {
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
        //get the possible parent node is the item don't exist already in the tree
        RedBlackTree<ItemType> possibleParentNode = find(item);
        //get the value of this possible parent node
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
                possibleParentNode.leftChild.balanceInsertion();
            } else {
                //create a new node/tree on the rightchild
                possibleParentNode.rightChild = new RedBlackTree<>(item, possibleParentNode);
                //balance the rightchild
                possibleParentNode.rightChild.balanceInsertion();
            }
        }
        //return the root
        //called on this for reduce complexity of one
        return this.getRoot();
    }

    /**
     * Rotate the right sub-tree to the left.<br>
     * <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a> for better explanation.
     * Call it on the rotation's pivot.
     *
     * @return RedBlackTree<ItemType>: the new root of the tree
     * @see <a href="https://upload.wikimedia.org/wikipedia/commons/2/23/Tree_rotation.png">image</a>
     */
    private RedBlackTree<ItemType> rotateLeft() {
        //if the node has a rightchild
        if (this.rightChild != null) {
            //get the rightchild
            RedBlackTree<ItemType> rightChild = this.rightChild;
            //get the parent
            RedBlackTree<ItemType> parent = this.parent;
            //substitute the rightchild with the rightchild's leftchild
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
     * Rotate the right sub-tree to the left.<br>
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
     * Gets the sibling of the current tree.
     * For example the rightchild if the current tree is a leftchild or the opposite if the current tree is a rightchild.
     *
     * @return RedBlackTree<ItemType>: the sibling of the current tree.
     */
    private RedBlackTree<ItemType> getSibling() {
        //create an initial sibling with null value
        RedBlackTree<ItemType> sibling = null;
        //check if the parent of the current node is not null
        if (this.parent != null) {
            //check if the leftchild of the parent exist and if it's the current node
            if (this.parent.leftChild != null && this.parent.leftChild == this) {
                //if it is then the sibling will be the rightchild
                sibling = this.parent.rightChild;
                //if the rightchild doesn't exist it's not a problem
            } else {
                //otherwise it will be the leftchild
                sibling = this.parent.leftChild;
                //if the leftchild doesn't exist it's not a problem
            }
        }
        return sibling;
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
     * For complete reference see <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree#Insertion"> the Wikipedia page </a>
     */
    private void balanceInsertion() {
        //get the parent
        RedBlackTree<ItemType> parent = this.parent;
        //if the node has no parent
        if (parent == null) {
            //the node is the root so we have to color it black
            this.color = RBColor.BLACK;
        } else
            //if parent exist and is black no problem but it's red some other controls are need to be made
            if (parent.color == RBColor.RED) {
                //get the grandparent (it always exist if parent is red) (the root can't be red)
                RedBlackTree<ItemType> grandParent = parent.parent;
                //get the uncle (if grandparent exist, uncle must exist)
                RedBlackTree<ItemType> uncle = parent.getSibling();
                //if uncle is red
                if (uncle.color == RBColor.RED) {
                    //set parent and uncle color to black
                    parent.color = uncle.color = RBColor.BLACK;
                    //set the grandparent on red.This can cause breaking rules.
                    grandParent.color = RBColor.RED;
                    //so call balance on grandparent
                    grandParent.balanceInsertion();
                }
                //else if the uncle is not red
                else {
                    //if we are the rightchild of our parent and his is the leftchild of the grandparent
                    if ((this == parent.rightChild) && (parent == grandParent.leftChild)) {
                        //rotate left
                        parent = parent.rotateLeft();
                        //call balance on parent
                        parent.balanceInsertion();
                        //if we are the leftchild of out parent and his is the leftchild of the grandparent
                    } else if ((this == parent.leftChild) && (parent == grandParent.rightChild)) {
                        //rotate right
                        parent = parent.rotateRight();
                        //call balance on parent
                        parent.balanceInsertion();
                    } else {
                        //if we are a family of leftchildren
                        if ((this == parent.leftChild) && (parent == grandParent.leftChild)) {
                            //rotate right on grandparent
                            grandParent.rotateRight();
                            //or if we are a family of rightchilders
                        } else if ((this == parent.rightChild) && (parent == grandParent.rightChild)) {
                            //rotate left on grandparent
                            grandParent.rotateLeft();
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

    private void transplant(RedBlackTree<ItemType> toRemove, RedBlackTree<ItemType> toInsert) {
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
     * It returns always the root of the tree. It is discouraged to call delete on a subtree.
     * It not possible to entirely delete a tree. At least an element must be present.
     *
     * @param itemToDelete the item to delete from the tree.
     * @return RedBlackTree<ItemType>: the new root of the tree.
     */
    public RedBlackTree<ItemType> delete(ItemType itemToDelete) {
        //get the node to delete
        RedBlackTree<ItemType> toRemove = lookUpNode(itemToDelete);
        //create a variable for keep track of a child node to use as replacer of the node to delete
        RedBlackTree<ItemType> replacer = this;
        //if the node to delete is found and the node is not the last element of the tree
        if (toRemove != null) {
            //if the node has only one or zero child
            if (toRemove.leftChild == nullLeaf || toRemove.rightChild == nullLeaf) {
                //set the not-null child to a transplant child
                replacer = (toRemove.leftChild == nullLeaf) ? toRemove.rightChild : toRemove.leftChild;
                //if the node is not the last element of the tree
                if (replacer != nullLeaf || toRemove.parent != null) {
                    transplant(toRemove, replacer);
                    replacer.balanceDeletion(toRemove.color);
                }
            } else { //if the node to delete has two children
                //get its successor (the smaller element of the right subtree)
                RedBlackTree<ItemType> childToDelete = toRemove.successorNode();
                //copy ONLY the value
                toRemove.value = childToDelete.value;
                //remove the cloned child, this will end up to the case zero or one child
                childToDelete.delete(childToDelete.value);
            }
        }
        //return the changed tree
        return replacer.getRoot();
    }

    /**
     * Call this on a node after a rotation for check and re-balance the tree.
     * It check if the red-black tree rules are respected.
     *
     * @param deletedColor: the color of the deleted node
     * @see <a href="cs.purdue.edu/homes/ayg/CS251/slides/chap13c.pdf">Red black tree deletion from Purdue University</a>
     */
    private void balanceDeletion(RBColor deletedColor) {
        //If either node1 or node2 is red
        if (((deletedColor == RBColor.BLACK) && (this.color == RBColor.RED))
                || ((deletedColor == RBColor.RED) && (this.color == RBColor.BLACK))) {
            //paint it black. This doesn't change black height
            this.color = RBColor.BLACK;
            //else if both nodes are black
        } else if ((deletedColor == RBColor.BLACK) && (this.color == RBColor.BLACK)) {
            //by deletion the black height has changed. node "this" is now "double black"
            //get the sibling
            RedBlackTree<ItemType> sibling = this.getSibling();
            //if the sibling color is black
            if (sibling.color == RBColor.BLACK) {
                //get the red children of the sibling
                RedBlackTree<ItemType> uncleRedChild = (sibling.leftChild.color == RBColor.RED) ? sibling.leftChild : ((sibling.rightChild.color == RBColor.RED) ? sibling.rightChild : null);
                //if a red children exist
                if (uncleRedChild != null) {
                    //restructuring
                    //if sibling is a leftchildren
                    if (sibling.parent.leftChild == sibling) {
                        //if the red child is a rightchild
                        if (sibling.rightChild == uncleRedChild) {
                            //sibling rightchild is red. sibling leftchild can be black or red, it doesn't matter
                            parent.leftChild = uncleRedChild.rightChild;
                            uncleRedChild.rightChild.parent = parent;
                            uncleRedChild.rightChild = parent;
                            uncleRedChild.parent = parent.parent;
                            parent.parent = uncleRedChild;
                            sibling.rightChild = uncleRedChild.leftChild;
                            uncleRedChild.leftChild.parent = sibling;
                            uncleRedChild.leftChild = sibling;
                            sibling.parent = uncleRedChild;
                        } else { //or if is a leftchild
                            parent.rotateRight();
                        }
                    } else { //or if the sibling is a rightchildren
                        //if the red child is a leftchild
                        if (sibling.leftChild == uncleRedChild) {
                            //sibling leftchild is red. sibling rightchild can be black or red, it doesn't matter
                            //symmetric case
                            parent.rightChild = uncleRedChild.leftChild;
                            uncleRedChild.parent = parent;
                            uncleRedChild.leftChild = parent;
                            uncleRedChild.parent = parent.parent;
                            parent.parent = uncleRedChild;
                            sibling.leftChild = uncleRedChild.rightChild;
                            uncleRedChild.rightChild.parent = sibling;
                            uncleRedChild.rightChild = sibling;
                            sibling.parent = uncleRedChild;
                        } else { //or if it is a rightchild
                            parent.rotateLeft();
                        }
                    }
                    //color compensation, remove "double black" status on this
                    uncleRedChild.color = RBColor.BLACK;
                } else {
                    //recoloring
                    //sibling is black and has two black children
                    sibling.color = RBColor.RED;
                    if (parent.color == RBColor.RED) {
                        // a red parent can't have red children so paint it black
                        parent.color = RBColor.BLACK;
                    } else {
                        //else if the parent is black
                        //the parent is now "double black" because we have recolored its black children
                        //check if the parent is't the root
                        if (parent.parent != null) {
                            //calla balance deletion on parent for fixing a double black problem
                            parent.balanceDeletion(parent.color);
                        }
                    }
                }
            } else {
                //adjustment
                //sibling is red so it has two black children
                parent.rotateLeft();
                //now there is red parent (the old sibling) with two black children (one is the old parent)
                //paint the parent black
                sibling.color = RBColor.BLACK;
                //paint one children red
                parent.color = RBColor.RED;
                //color compensation is not happened, this is still double black. Call balance for a recoloring.
                this.balanceDeletion(deletedColor);
            }
        }
    }

    /**
     * Get the height of the tree. The max distance between the root and a leaf.
     *
     * @return int: the height of the tree.
     */
    int getHeight() {
        //nullleaf has height 0, other nodes has height 1 + max of children height
        if (this == nullLeaf) return 0;
        else return 1 + Math.max(leftChild.getHeight(), rightChild.getHeight());
    }


    /**
     * Define an enum for the color of the node.
     */
    private enum RBColor {
        BLACK, RED
    }


}
