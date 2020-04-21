
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridSize;
    private int openCount;
    private boolean[] openSites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backwash;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        gridSize = n;
        uf = new WeightedQuickUnionUF(encode(gridSize, gridSize) + 2); // add two for each virtual site
        backwash = new WeightedQuickUnionUF(encode(gridSize, gridSize) + 1); // backwash doesn't have a sink
        openSites = new boolean[encode(gridSize, gridSize) + 2]; // add two for each virtual site
        openSites[0] = true; // initializes virtual site at the top to be open
        for (int i = 1; i < openSites.length; i++) openSites[i] = false; // initializes rest of sites to be blocked
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            openSites[encode(row, col)] = true;
            openCount++;

            if (row == 1) {
                uf.union(0, encode(row, col));
                backwash.union(0, encode(row, col));
            }
            if (row == gridSize) uf.union(openSites.length-1, encode(row, col)); // union the sites in normal uf but not in backwash

            // check and union all four adjacent sites
            checkAndUnion(row, col, row+1, col);
            checkAndUnion(row, col, row, col+1);
            checkAndUnion(row, col, row-1, col);
            checkAndUnion(row, col, row, col-1);
        }
    }

    private void checkAndUnion(int r1, int c1, int r2, int c2) {
        if ((r2 <= gridSize && r2 >= 1) && (c2 <= gridSize && c2 >= 1)) // within bounds
            if (isOpen(r2, c2)) {
                uf.union(encode(r1, c1), encode(r2, c2));
                backwash.union(encode(r1, c1), encode(r2, c2));
            }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[encode(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int id = encode(row, col);
        return (backwash.find(0) == backwash.find(id)); // check if site is connected to source and NOT sink
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return (uf.find(0) == uf.find(openSites.length-1)); // check if source and sink are connected
    }

    private void validate(int x, int y) {
        if (x < 1 || x > gridSize) throw new IllegalArgumentException();
        if (y < 1 || y > gridSize) throw new IllegalArgumentException();
    }

    private int encode(int x, int y) {
        return this.gridSize * (x-1) + y;
    }

    // test client (optional)
    public static void main(String[] args) {
        ;
    }
}