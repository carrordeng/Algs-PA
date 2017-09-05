import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final static int virtualTop = 0; // define the index of the virtualTop grid
    private int n;                           // the width & length of the system
    private int virtualBottom;               // the index of the virtualBottom grid
    private int gridnum;                     // total amount of grids(including Top & Bottom)
    private boolean isPercolated;            // whether the system is Percolate
    private boolean[] grid;                  // the open/close status of grids
    private WeightedQuickUnionUF unionInstance;
    private WeightedQuickUnionUF backWash;   // instance for backwash recheck

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("Illegal Argument");
        isPercolated = false;
        this.n = n;                          // copy the number of grids
        gridnum = n * n + 2;
        virtualBottom = n * n + 1;
        unionInstance = new WeightedQuickUnionUF(gridnum);
        backWash = new WeightedQuickUnionUF(gridnum - 1);
        grid = new boolean[gridnum];
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(3, 1);
        perc.open(3, 3);
        System.out.println(perc.percolates());
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        isValidBounds(row, col);             // bounds check
        if (isOpen(row, col))                // if is already open, return
            return;
        int grid_serial = index_to_serial(row, col);
        grid[grid_serial] = true;
        // if row==1 || col==n link the grid to the virtual top || bottom
        if (row == 1) {
            unionInstance.union(virtualTop, grid_serial);
            backWash.union(virtualTop, grid_serial);
        }
        if (row == n) {
            unionInstance.union(virtualBottom, grid_serial);
        }
        // if the 4 grids around the target grid exist && open union grids
        int[] rowdiff = {-1, 0, 0, 1};
        int[] coldiff = {0, -1, 1, 0};
        for (int i = 0; i <= 3; i++) {
            int temprow = row + rowdiff[i];
            int tempcol = col + coldiff[i];
            if (isPosValid(temprow, tempcol) && isOpen(temprow, tempcol)) {
                int around_grid_serial = index_to_serial(temprow, tempcol);
                unionInstance.union(grid_serial, around_grid_serial);
                backWash.union(grid_serial, around_grid_serial);
            }
        }
    }

    // determine whether row || col is within boundaries
    private void isValidBounds(int row, int col) {
        if (row < 1 || row > n)
            throw new IndexOutOfBoundsException("Row index out of bounds");
        if (col < 1 || col > n)
            throw new IndexOutOfBoundsException("Column index out of bounds");
    }

    private int index_to_serial(int row, int col) {
        isValidBounds(row, col);             // bounds check
        int serial;
        serial = (row - 1) * n + col;
        return serial;
    }

    private boolean isPosValid(int row, int col) {
        if (row >= 1 && row <= n && col >= 1 && col <= n)
            return true;
        return false;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValidBounds(row, col);             // bounds check
        return (grid[index_to_serial(row, col)] == false ? false : true);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        isValidBounds(row, col);             // bounds check
        return backWash.connected(virtualTop, index_to_serial(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        int opennum = 0;
        for (int i = 1; i <= virtualBottom - 1; i++) {
            if (grid[i] == true)
                opennum++;
        }
        return opennum;
    }

    // does the system percolate?
    public boolean percolates() {
        if (isPercolated)
            return true;
        else if (unionInstance.connected(virtualTop, virtualBottom)) {
            isPercolated = true;
            return true;
        } else
            return false;
    }
}