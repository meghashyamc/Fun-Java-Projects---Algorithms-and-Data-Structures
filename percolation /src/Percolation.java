import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*

We model a percolation system using an n-by-n grid of sites.
Each site is either open or blocked. A full site is an open site that can be
connected to an open site in the top row via a chain of
neighboring (left, right, up, down) open sites. We say the system percolates if
there is a full site in the bottom row. In other words, a system percolates if
we fill all open sites connected to the top row and that process fills some open
 site on the bottom row.
 */

public class Percolation {

    private boolean[] grid;
    private int count; // the number of open sites
    private WeightedQuickUnionUF wuf;
    private WeightedQuickUnionUF wuf1;
    private int gridSize;
    private int rowSize;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("The grid should have a side length greater than 0");
        } else {
            rowSize = n;

            gridSize = n * n;

            // we have two imaginary sites 0 and gridSize+1
            // we assume all the open sites in the top row are connected to 0
            // we assume all the open sites in the bottom row (wuf only) are connected to gridSize+1
            wuf = new WeightedQuickUnionUF(gridSize + 2); // 0 and n^2+1 are two imaginary sites to help with the percolates method
            wuf1 = new WeightedQuickUnionUF(gridSize + 1); // there is only one imaginary site in this grid (the top one)

            count = 0; // number of open sites
            grid = new boolean[gridSize + 2];
            for (int i = 0; i < gridSize + 2; i++) {
                grid[i] = false;  // false indicates a blocked site
            }

        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        validateIndices(row, col);
        int index = convertToIndex(row, col);

        if (!grid[index]) { // true indicates an open site
            grid[index] = true;
            connectAdjacentRoots(index);
            count++;

        }

    }


    // is site (row, col) open?
    public boolean isOpen(int row, int col) {

        validateIndices(row, col);
        int index = convertToIndex(row, col);

        return grid[index];

    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        int index = convertToIndex(row, col);
        // wuf1 is used here as its bottom row is not connected to grid+1 index
        // if wuf were used, it would give erroneous results because of grid+1 connections
        return wuf1.connected(0, index);

    }


    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return (wuf.connected(0, gridSize + 1));
    }

    private int convertToIndex(int row, int col) {

        return ((rowSize * (row - 1)) + col);
    }

    // connects a site to its adjacents
    // if a site is in top row, connects it to imaginary site 0
    // if a site is in bottom row, connects it to imaginary site gridSize+1 (only for wuf)
    private void connectAdjacentRoots(int index) {

        int topIndex = index - rowSize;
        int leftIndex = index - 1;
        int rightIndex = index + 1;
        int bottomIndex = index + rowSize;

        // case when the imaginary 0 index is connected to a site in the first row
        if ((topIndex <= 0)) {
            wuf.union(0, index);
            wuf1.union(0, index);

        }

        if ((topIndex > 0) && grid[topIndex]) {
            wuf.union(index, topIndex);
            wuf1.union(index, topIndex);

        }

        if (((leftIndex % rowSize) != 0) && grid[leftIndex]) {
            wuf.union(index, leftIndex);
            wuf1.union(index, leftIndex);
        }

        if (((index % rowSize) != 0) && grid[rightIndex]) {
            wuf.union(index, rightIndex);
            wuf1.union(index, rightIndex);
        }

        if ((bottomIndex < (gridSize + 1)) && grid[bottomIndex]) {
            wuf.union(index, bottomIndex);
            wuf1.union(index, bottomIndex);
        }

        // case when the imaginary grid+1 index is connected to a site in the bottom row
        // wuf1 is not connected because it is used to see if a site is full and connecting its bottom row
        // to grid+1 would lead to faulty results for isFull
        if (bottomIndex >= (gridSize + 1)) {
            wuf.union(index, gridSize + 1);

        }
    }

    private void validateIndices(int row, int col) {
        if ((row <= 0) || (row > rowSize) || (col <= 0) || (col > rowSize)) {

            throw new java.lang.IllegalArgumentException("Row and column size should be between 1 and " + rowSize);
        }

    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation perco1 = new Percolation(2);

        perco1.open(1, 1);
        StdOut.println("Opened 1, 1");
        perco1.open(2, 2);
        StdOut.println("Opened 2, 2");
        perco1.open(1, 2);
        StdOut.println("Opened 1, 2");

        StdOut.println("Number of open sites: " + perco1.numberOfOpenSites());
        StdOut.println("Percolates? " + perco1.percolates());
        StdOut.println("Is 2, 1 open? " + perco1.isOpen(2, 1));
        StdOut.println("Is 2, 1 full? " + perco1.isFull(2, 1));
    }


}
