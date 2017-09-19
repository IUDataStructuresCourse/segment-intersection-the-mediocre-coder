# Project: Segment Intersection

We recommend that you work in a team of 2-3 students on this project.

Introduction
------------

In this project, we consider a classic problem in computational
geometry: Given a set of n line segments, identify an intersecting
pair, if one exists. The naive approach is to check all possible pairs
for intersection. That is, for every line segment check whether it
intersects with every other line segment. We can determine if two
lines intersect in O(1) time, so this algorithm requires O(n2) time to
examine all pairs. A better approach is to use a Line Sweep Algorithm,
which solves the problem in O(n log n) time.

A line segment is described by two endpoints, which we shall refer to
as the left endpoint (shown in green) and the right endpoint (shown in
red). In the first step of the algorithm, we sort all the endpoints by
their x-coordinates. This requires O(n log n) time in the worst
case. We then sweep a vertical line from left to right across the
plane. Each time the sweep line (shown as a dashed gray line) touches
an endpoint, the algorithm processes the event.

The main data structure is a binary search tree that, at any given
time, contains just those line segments that intersect the sweep
line. The segments are ordered in the tree according to the
y-coordinate of the point of where the segment intersects the sweep
line. For the sweep line shown in the screenshot, there are 7
intersecting segments in the tree at this point in time. For each
endpoint the sweep line encounters as it passes across the plane, it
stops and takes one of the following actions depending on whether the
endpoint is green or red:

1. [green = left endpoint] Insert the line segment associated with
    this endpoint into the tree. Check for an intersection of this
    newly entered line segment with the one immediately above it on
    the sweep line. If they intersect, then highlight the two segments
    and stop. (This is the situation shown in the screenshot.)
    Otherwise, check for an intersection of this line segment with the
    one immediately below it on the sweep line. If they intersect,
    then highlight the two segments and stop. Otherwise, proceed with
    the sweep.

2. [red = right endpoint] Find the line segments immediately above and
    below this one on the sweep line. If they intersect, then
    highlight the two segments and stop. Otherwise, remove the line
    segment associated with this red endpoint from the tree and
    proceed with the sweep.

In the worst case, we need to process O(n) endpoints. To achieve our
desired complexity of O(n log n), we need to process each endpoint in
O(log n) time. If we use a self-balancing binary search tree (such as
an AVL tree) to hold the line segments intersecting the sweep line,
then we can find the segments that are above and below a given segment
in O(log n) time.

Code Base
---------

Implementations of the GUI and the Line Sweep Algorithm are provided
to you in full. Your task is to complete the implementation of the
`BinarySearchTree` and `AVLTree` class. The `BinarySearchTree` class
is an elaboration of the class described in lecture and that you used
in lab. The `AVLTree` class is a subclass of `BinarySeachTree` and
overrides the `insert()` and `remove()` methods to ensure that the tree remains
balanced at all times (which gives us the O(log n) time bound we
crave).

Below is a summary of the components in the code base. The `Testing`
class provides you with a comprehensive suite of unit tests, which
gives you a nice roadmap for how best to develop the project. If you
look at the first test, you see that it's checking some required
modifications to the Node class inside `BinarySearchTree`, so that's
the best place for you to start coding. You can then proceed in an
orderly fashion by working your way through the unit tests.

However, before you begin writing any code, you need to understand the
design of the interfaces in `Tree.java`. So, that's the first place
you should look.

* `AVLTree` is a class representing a height-balanced tree. Since
  `AVLTree` is a subclass of `BinarySearchTree`, you will need a fully
  functioning implementation of `BinarySearchTree` before you can begin
  working on this class. However, this is the most interesting and
  most important part of this entire project, so make sure you allow
  yourself enough time to work on it.

* `BinarySearchTree` is a generic class corresponding to an ordered
  binary tree. The relation on which the data in the tree is organized
  is specified by a function of type `BiPredicate` and provided at
  construction time. Nodes in the tree are represented by the inner
  `Node` class. So that we can use `Node` as a return value from
  `search()`, `Node` implements the `Location` interface. A `Node`
  contains the usual fields: `key`, `left`, and `right`. You will add
  two more fields: `parent` (which points to the node's parent in the
  tree) and `height` (which is the height of the subtree rooted at
  this node).

Recall from the pre-lecture assignment that we can maintain the height
information in the tree nodes so that it is immediately available to
us whenever we need it. This means that when a new node is inserted
or removed from the tree, we may have to adjust the heights of the nodes along
the path up to the root.

Each node maintains a pointer to its parent node. We will require this
information when implementing the `AVLTree` later. The root has no
parent, so its parent field will be null.

Additionally, a Node must provide operations to access its inorder
predecessor and inorder successor, manifested by the `getBefore()` and
`getAfter()` methods. These methods are used by the Line Sweep
Algorithm to determine the line segments that are immediately above or
below the current segment.

* `Constants` [read-only] is an interface containing a few global
  constants for the project.

* `Driver` [read-only] is the main entry for the project. This is where
  you go to launch the GUI, draw the line segments, and run the sweep
  algorithm.

* `GUI` [read-only] is the class that implements the graphical user
  interface.

* `LineSegment` [read-only] is a class that represents a line
  segment. In this same file, you will find class definitions for
  Endpoint (and its subclasses, LeftEndpoint and RightEndpoint), and
  the SweepLine.

* `Sweeper` [read-only] is the class that implements the Line Sweep
  Algorithm. Be sure to read through this code to help you understand
  how the algorithm works.

* `StudentTest` (in the `test` directory) is the suite of unit tests that you will use to guide your
  development work. Feel free to add additional tests. At a minimum, add four tests, for
  insert and remove on a BST and an AVL tree with at least 100 keys that are randomly generated.

* `Tree` [read-only] is an interface that describes the Tree ADT. In
  this same file, you will find the interface definition for Location,
  which is used by BinarySearchTree.search() to report the result of a
  look up.

Submission
----------

Make sure that your project compiles. Commit and push the all source code to
your repository before the due date and . This is the only way that the project is
accepted for grading. Please do not send your code to any of the
instructors by email. If you miss the submission deadline, you have
one week to submit your work late.



