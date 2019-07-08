import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// the aim is to perform T percolation trials on an nxn grid
// and calculate the percolation threshold for each trial, mean and sd

public class PercolationStats {

    private static final double CONFIDENCE_CONST = 1.96;
    private double[] thresholdArray;
    private int T;
    private int rowSize;
    private double mean = 0.0;
    private double stddev = 0.0;

    // perform trials - independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new java.lang.IllegalArgumentException("The grid size and number of trials should both be > 0");
        } else {
            rowSize = n;
            T = trials;

            thresholdArray = new double[trials];
            for (int i = 0; i < trials; i++) {
                thresholdArray[i] = performTrial();

            }

            mean = StdStats.mean(thresholdArray);
            stddev = StdStats.stddev(thresholdArray);
        }
    }

    // sample mean of percolation threshold
    public double mean() {

        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {

        return (mean - ((CONFIDENCE_CONST * stddev) / Math.sqrt(T)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + ((CONFIDENCE_CONST * stddev) / Math.sqrt(T)));
    }

    // randomly opens sites till system percolates, returns percolation threshold
    private double performTrial() {
        int randomRow;
        int randomColumn;
        Percolation percolation = new Percolation(rowSize);
        while (!percolation.percolates()) {
            randomRow = StdRandom.uniform(1, rowSize + 1);
            randomColumn = StdRandom.uniform(1, rowSize + 1);
            percolation.open(randomRow, randomColumn);
        }

        double totalSites = rowSize * rowSize;
        double openSites = percolation.numberOfOpenSites();
        double percolationThreshold = openSites / totalSites;
        return percolationThreshold;
    }

    // test client (described below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, T);

        StdOut.printf("%-30s%-3s", "mean: ", "= ");
        StdOut.println(percolationStats.mean());
        StdOut.printf("%-30s%-3s", "stdev: ", "= ");
        StdOut.println(percolationStats.stddev());
        StdOut.printf("%-30s%-3s", "95% confidence interval: ", "= ");
        StdOut.println("[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");


    }

}
