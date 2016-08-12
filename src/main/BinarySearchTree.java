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

  public BinarySearchTree(ItemType value) {
	this.value = value;
  }

}
