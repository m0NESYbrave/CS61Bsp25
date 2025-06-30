import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private WeightedQuickUnionUF uf1, uf2;
    private boolean[] open;
    private int N;
    private int numOfOpenSites;
    private int topRow;
    private int bottomRow;
    private int topRowOfUF2;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        uf1 = new WeightedQuickUnionUF(N * N + 2); // two virtual sites
        uf2 = new WeightedQuickUnionUF(N * N + 1); // only a top virtual site
        open = new boolean[N * N];
        this.N = N;
        numOfOpenSites = 0;
        topRow = N * N;
        bottomRow = N * N + 1;
        topRowOfUF2 = N * N;

        for (int i = 0; i < N; i++) {
            uf1.union(site(0, i), topRow);
            uf1.union(site(N - 1, i), bottomRow);
            uf2.union(site(0, i), topRowOfUF2);
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        /* If blocked, then open. */
        if (!isOpen(row, col)) {
            open[site(row, col)] = true;
            numOfOpenSites++;

            /* Must judge bound conditions first.
            *  Man it's not if-else, what am I doing.
            * */
            if (row >= 1 && isOpen(row - 1, col)) { // union with the up
                uf1.union(site(row, col), site(row - 1, col));
                uf2.union(site(row, col), site(row - 1, col));
            }
            if (row <= N - 2 && isOpen(row + 1, col)) { // union with the down
                uf1.union(site(row, col), site(row + 1, col));
                uf2.union(site(row, col), site(row + 1, col));
            }
            if (col >= 1 && isOpen(row, col - 1)) { // union with the left
                uf1.union(site(row, col), site(row, col - 1));
                uf2.union(site(row, col), site(row, col - 1));
            }
            if (col <= N - 2 && isOpen(row, col + 1)) { // union with the right
                uf1.union(site(row, col), site(row, col + 1));
                uf2.union(site(row, col), site(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[site(row, col)];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        /*  We add another disjoint set uf2 to implement isFull().
        *   If still use uf1, will lead to backwash.
        *   Backwash is when percolates, all open sites connected to open sites
        *   on the bottom row will be full.
        * */
        return uf2.connected(site(row, col), topRowOfUF2) && isOpen(row, col); // top row should open
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return uf1.connected(topRow, bottomRow);
    }

    /**
     * Return the index of the parent array
     * @param row 0 to N - 1
     * @param col 0 to N - 1
     * @return an integer
     */
    private int site(int row, int col) {
        return row * N + col;
    }

    /**
     * Corner case
     * @param row row
     * @param col col
     */
    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row >= N || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }
}
