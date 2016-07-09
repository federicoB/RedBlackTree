##Red-Black binary search tree

###Definition
A red-black binary search tree is an interesting data structure.

Here you can see a graphical representation of it:
![Red-black binary search tree](https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Red-black_tree_example.svg/2000px-Red-black_tree_example.svg.png)

Every "Node" can contains generic data and have two children that are nodes themselves.
The left child has a lower value than the parent, le right child a greater.

The specific rules of red-black tree are:
1. Every node has a color: red or black
2. The root (a node without parent) is black
3. Every leaf (a node without children) is black
4. Both children of a black node are red.
5. Both subtree of a node has the same number of black nodes.

###Features
They are really efficient to implements dictionaries and sets, especially if you can order the keys.
Those trees have auto-balancing systems so the maximum height is always limited to O(logN) where N is the number of nodes of the tree.
In this way lots of operation can be executed in O(logN) that is a very efficient value.
