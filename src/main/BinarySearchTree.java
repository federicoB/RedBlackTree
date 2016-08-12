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
  }

  public BinarySearchTree(ItemType value, RedBlackTree<ItemType> parent) {
    this.value = value;
    this.parent = parent;
  }

  /**
   * Get the value cointained in the node.
   *
   * @return ItemType: the value of the node.
   */
  public ItemType getValue() {
    return value;
  }

}
