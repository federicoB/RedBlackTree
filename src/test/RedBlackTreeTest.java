import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.log;

public class RedBlackTreeTest {

    private final int treeSize = 128;
  private RedBlackTree<Integer> tree;
    private int[] numbers;

  @Before
  public void setup() {
      numbers = new int[treeSize];
      for (int i = 0; i < treeSize; ++i) {
          numbers[i] = i;
    }
      tree = new RedBlackTree<>(numbers[0]);
      for (int i = 1; i < treeSize; ++i) {
          tree = tree.insert(numbers[i]);
      }
  }

  @Test
  public void testInsertion() throws Exception {
      RedBlackTree<Integer> insertionTestTree = new RedBlackTree<>(numbers[0]);
      for (int i = 1; i < treeSize; ++i) {
          insertionTestTree = insertionTestTree.insert(numbers[i]);
          Assert.assertTrue(insertionTestTree.contains(numbers[i]));
          int height = insertionTestTree.getHeight();
          int numberOfNodes = i + 1;
          double expectedHeight = (2 * (log(numberOfNodes + 1) / log(2)));
          Assert.assertTrue(height <= expectedHeight);
      }
  }

  @Test
  public void testLookUpNode() throws Exception {
      Assert.assertEquals((long) tree.lookUpNode(numbers[treeSize - 1]).getValue(), numbers[treeSize - 1]);
  }

  @Test
  public void testMin() throws Exception {
    Assert.assertEquals(tree.min().getValue(), Collections.min(Arrays.asList(ArrayUtils.toObject(numbers))));
  }

  @Test
  public void testMax() throws Exception {
    Assert.assertEquals(tree.max().getValue(), Collections.max(Arrays.asList(ArrayUtils.toObject(numbers))));
  }

  @Test
  public void testDelete() throws Exception {
      for (int i = 0; i < treeSize - 1; ++i) {
          try {
              tree = tree.delete(numbers[i]);
              Assert.assertFalse(tree.contains(numbers[i]));
          } catch (RedBlackTree.RootDeletionException ex) {
              System.out.println(ex.getMessage());
          }
          int height = tree.getHeight();
          double expectedHeight = (2 * (log(treeSize - i + 1) / log(2)));
          Assert.assertTrue(height <= expectedHeight);
      }
  }
}