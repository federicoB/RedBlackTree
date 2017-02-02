/*
 * Created by Federico Bertani on 26/07/16.
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

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.log;

public class RedBlackTreeTest {

    private final int treeSize = 1729;
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
            tree = tree.delete(numbers[i]);
            Assert.assertFalse(tree.contains(numbers[i]));
            int height = tree.getHeight();
            double expectedHeight = (2 * (log(treeSize - i + 1) / log(2)));
            Assert.assertTrue(height <= expectedHeight);
        }
    }
}