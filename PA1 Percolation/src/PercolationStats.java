import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] xValues;
    private double vMean;
    private double vStdDev;
    private int expTimes;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Illegal Argument");
        xValues = new double[T];
        expTimes = T;

        for (int i = 0; i < T; i++) {
            int gridnum = 0;
            Percolation p = new Percolation(N);
            boolean percolate_statue = false;
            while (!percolate_statue) {
                int xindex = StdRandom.uniform(1, N + 1);
                int yindex = StdRandom.uniform(1, N + 1);
                if (!p.isOpen(xindex, yindex)) {
                    p.open(xindex, yindex);
                    percolate_statue = p.percolates();
                    gridnum++;
                }
            }
            xValues[i] = (double) gridnum / (double) (N * N);
        }
        vMean = StdStats.mean(xValues);
        vStdDev = StdStats.stddev(xValues);
    }

    // sample mean of percolation threshold
    public double mean() {
        return vMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return vStdDev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double Lo = vMean - 1.96 * vStdDev / Math.sqrt(expTimes);
        return Lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double Hi = vMean + 1.96 * vStdDev / Math.sqrt(expTimes);
        return Hi;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new java.lang.IllegalArgumentException();
        }
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);
        double mean = ps.mean();
        double stddev = ps.stddev();

        double common = 1.96 * stddev / Math.sqrt((double) T);
        double confLow = mean - common;
        double confHigh = mean + common;

        StdOut.println("mean                    = " + Double.toString(mean));
        StdOut.println("stddev                  = " + Double.toString(stddev));
        StdOut.println("95% confidence interval = " + Double.toString(confLow) + ", " + Double.toString(confHigh));
    }
}