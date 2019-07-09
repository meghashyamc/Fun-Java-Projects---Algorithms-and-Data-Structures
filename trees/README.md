The detailed project description for this project can be found here:

https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php

The summarized description is:

Write a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1) using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point).

<img src="https://coursera.cs.princeton.edu/algs4/assignments/kdtree/kdtree-ops.png" width=600>

PointSET is a simple class similar to TreeSet in Java that supports range search and nearest neighbour search functionality using brute force.

KDTree is a 2-dimensional tree that compares points with a horizontal and vertical divider alternatively. It supports much more optimized range search and nearest neighbour search.

Here are three supporting files that were given to help visualize the working of KDTree:

KDTree visualizer: https://coursera.cs.princeton.edu/algs4/assignments/kdtree/files/KdTreeVisualizer.java<br>
Range search visualizer: https://coursera.cs.princeton.edu/algs4/assignments/kdtree/files/RangeSearchVisualizer.java<br>
Nearest neighbour visualizer: https://coursera.cs.princeton.edu/algs4/assignments/kdtree/files/NearestNeighborVisualizer.java
