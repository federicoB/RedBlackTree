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
  RedBlackTree<ItemType> parent;
  /**
   * The left subtree.
   */
  RedBlackTree<ItemType> leftChild;
  /**
   * The right subtree.
   */
  RedBlackTree<ItemType> rightChild;

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
  public ItemType getValue() {
	//return the value of the node
	return value;
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

}
