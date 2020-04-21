import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    private double[] proportions;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        proportions = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                p.open(x, y);
            }
            proportions[i] = (1.0*p.numberOfOpenSites())/(n*n);
        }
        mean = StdStats.mean(proportions);
        stddev = StdStats.stddev(proportions);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double a = mean();
        double b = (1.96*stddev())/Math.sqrt(proportions.length);
        return a - b;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double a = mean();
        double b = (1.96*stddev())/Math.sqrt(proportions.length);
        return a + b;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats pStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean\t\t\t\t\t= " + pStats.mean());
        System.out.println("stddev\t\t\t\t\t= " + pStats.stddev());
        System.out.println("95% confidence interval\t= [" + pStats.confidenceLo() + ", " + pStats.confidenceHi() + "]");
    }

}