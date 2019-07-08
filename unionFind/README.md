The detailed project description for this project can be found here:

https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php

The summarized description is:

Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.

The model: We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row.

If sites are independently set to be open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates?

The Percolation class models a percolation system and the PercolationStats class performs experiments T times and helps calculate the mean percolation threshold and standard deviation in percolation thresholds.

<b><i>Note: A percolation visualizer (to visualize the output) can be downloaded by clicking on the 'project' tab on the project description page.</b></i>
