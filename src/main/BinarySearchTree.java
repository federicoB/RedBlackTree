/**
 * Created by Federico Bertani on 12/08/16.
 */


public class BinarySearchTree<ItemType extends Comparable<ItemType>> {

  /**
   * The value of the node.
   */
  ItemType value;

  /**
   * The parent tree.
   */
  BinarySearchTree<ItemType> parent;

  /**
   * The left subtree.
   */
  BinarySearchTree<ItemType> leftChild;

  /**
   * The right subtree.
   */
  BinarySearchTree<ItemType> rightChild;

  BinarySearchTree(ItemType value) {
	this.value = value;
	this.rightChild = null;
	this.leftChild = null;
	this.parent = null;
  }

  public BinarySearchTree(ItemType value, RedBlackTree<ItemType> parent) {
	this.value = value;
	this.parent = parent;
	this.rightChild = null;
	this.leftChild = null;
  }

  /**
   * Get the value cointained in the node.
   *
   * @return ItemType: the value of the node.
   */
  ItemType getValue() {
	//return the value of the node
	return value;
  }

  /**
   * Find the node with the value given or the nearest node if the value is not present in the tree.
   *
   * @param item ItemType: the item to search.
   * @return BinarySearchTree&lt;ItemType&gt; : return the searched node if found.
   */
  BinarySearchTree<ItemType> find(ItemType item) {
	//create a comparison variable with the comparison result with the researched item and the current value
	int comparison = this.value.compareTo(item);
	//if the node contains the searched value or is a leaf return himself.
	if ((comparison == 0) || ((this.leftChild == null) && (this.rightChild == null))) return this;
	  //else if the node value is greater than the searched
	else if (comparison > 0) {
	  //but the leftchild doesn't exist return this
	  if (this.leftChild == null) return this;
		//but if the leftchild exist call lookup on him
	  else return this.leftChild.find(item);
	  //if the node value in greter of the searched instead
	} else {
	  //but the rightchil doesn't exist return this
	  if (this.rightChild == null) return this;
		//but if the leftchild exist call lookup on him
	  else return this.rightChild.find(item);
	}
  }

  /**
   * Get the next nearest node.
   *
   * @return BinarySearchTree&lt;ItemType&gt; : the successor node.
   */
  private BinarySearchTree<ItemType> successorNode() {
	return this.rightChild.min();
  }

  /**
   * Get the previous nearest node.
   *
   * @return BinarySearchTree&lt;ItemType&gt; : the predecessor node.
   */
  private BinarySearchTree<ItemType> predecessorNode() {
	return this.leftChild.max();
  }

  /**
   * Get the tree that cointans the minimum item of the tree.
   *
   * @return ItemType: the tree that cointans the the minimum item of the tree.
   */
  public BinarySearchTree<ItemType> min() {
	if (this.leftChild == null) return this;
	else return this.leftChild.min();
  }

  /**
   * Get the tree that cointans the maximum item of the tree.
   *
   * @return ItemType: the tree that cointans the the maximum item of the tree.
   */
  public BinarySearchTree<ItemType> max() {
	if (this.rightChild == null) return this;
	else return this.rightChild.max();
  }


  /**
   * Check if an item is in the tree or not.
   *
   * @param item ItemType: the item to search
   * @return boolean: true if the item is constained in the tree false otherwise.
   */
  boolean contains(ItemType item) {
	return (this.lookUpNode(item) != null);
  }

  /**
   * Search if a node contain the given item is contained on the three and if is return it.
   *
   * @param item ItemType: the item to search.
   * @return RedBlackTree&lt;ItemType&gt; : return the searched node if found, null otherwise.
   */
  public BinarySearchTree<ItemType> lookUpNode(ItemType item) {
	//find the node with the searched value of the nearest
	BinarySearchTree<ItemType> result = this.find(item);
	//if the node found has the value searched the item is in the tree.
	if (result.getValue() == item) return result;
	  //otherwise return null
	else return null;
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
	if (leftChild == null && rightChild == null) {
	  //return the heigth of the current node, that is one
	  return 1;
	  //if a child exist
	} else {
	  //if leftchild exist
	  if (leftChild != null) {
		//sum the height of the leftchild
		leftheight += this.leftChild.getHeight();
	  }
	  //if the rightchild exist
	  if (rightChild != null) {
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
   * Return the root of the tree from which the current node belongs
   *
   * @return RedBlackTree<ItemType>: the root of the tree.
   */
  BinarySearchTree<ItemType> getRoot() {
	if (this.parent == null) return this;
	else return this.parent.getRoot();
  }

}
