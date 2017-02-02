##Red-Black binary search tree

###Definition
A red-black binary search tree is an interesting data structure.

Here you can see a graphical representation of it:
![Red-black binary search tree](https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Red-black_tree_example.svg/2000px-Red-black_tree_example.svg.png)

Every "Node" can contains generic data and have two children that are nodes themselves.
The left child has a lower value than the parent, the right child a greater one.

The specific rules of red-black tree are:
* Every node has a color: red or black
* The root (a node without parent) is black
* Every leaf (a node without children) is black
* Both children of a black node are red.
* Both subtree of a node has the same number of black nodes.

###Features
They are really efficient to implements dictionaries and sets, especially if you can order the keys.
Those trees have auto-balancing systems so the maximum height is always limited to O(logN) where N is the number of nodes of the tree.
In this way lots of operation can be executed in O(logN) that is a very efficient value.

More precisely a red-black tree with n internal nodes has height at most 2lg(n+1), an interesting proof of this lemma can be found at page. 309 of 
"Introduction to Algorithms 3rd Ed. by Cormen, Leiserson, Rivest, Stein. 2009".

The need of nil black leaves is motivated by the fact that the rotation algorithms are based a lot on "uncles" and those uncles must exist.

The class use Java Generics for support every Comprable element type.

###Code example
```java
//create a tree with the number 42 inside
RedBlackTree<Integer> tree = new RedBlackTree<>(42);
//ad also the number 44
tree.insert(44);
//if the tree contains the number 42
if (tree.contains(42)) {
    //delete it
    tree.delete(42);
}
```

###Motivation and contributing
I've started this project because I was learning about red-black tree at university.
At first, I thought I'd finished the project soon and instead it turned out to be one of the most difficult data structures i've ever implemented. I'm happy to been able to complete it and I learned a lot.
Bugs could still be found and the design can be discussed so i highly appreciate any pull request!

License
-------
This software is released under the GNU GPL v3.0 license.  
A copy of the license is provided in the LICENSE file.
