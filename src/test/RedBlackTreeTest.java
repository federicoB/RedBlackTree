import com.sun.deploy.util.ArrayUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

public class RedBlackTreeTest {

  private RedBlackTree<Integer> tree;
  private int[] numbers = {3,1,5};

  @Before
  public void setup() {
    tree = new RedBlackTree<Integer>(numbers[0]);
    for (int i = 1;i<numbers.length;i++) {
      tree.insert(numbers[i]);
    }
  }

  @Test
  public void testContains() {
    Assert.assertTrue(tree.contains(numbers[1]));
  }

  @Test
  public void testGetValue() throws Exception {
    Assert.assertEquals((long)tree.getValue(),numbers[0]);
  }

  @Test
  public void testLookUpNode() throws Exception {
    Assert.assertEquals((long)tree.lookUpNode(numbers[1]).getValue(),numbers[1]);
  }

  @Test
  public void testMin() throws Exception {
    //TODO add apache commons to dependacies so you can use ArrayUtils and convert primitive array to list
    // Assert.assertEquals((long)tree.min().getValue(), Collections.min(Arrays.asList()));
  }

  @Test
  public void testMax() throws Exception {
    Assert.assertEquals((long)tree.max().getValue(),54);
  }

  @Test
  public void testInsert() throws Exception {
    tree.insert(24);
    Assert.assertTrue(tree.contains(24));
  }
}