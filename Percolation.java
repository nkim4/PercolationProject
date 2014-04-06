public class Percolation
{
    // ...
    // your data members here
	private int[][] blockArray;
	private int N;
	private WeightedQuickUnionUF UFgrid;
	private WeightedQuickUnionUF UFgridFull;
    // ...
    // create N-by-N grid, with all sites blocked.
	/**
	 * Constructor of the Percolation class
	 * Precondition: None
	 * Postcondition: An object of the class Percolation
	 * @param N: The size of a N by N grid
	 */
    public Percolation(int N)
    {
    	blockArray = new int[N][N];
    	// Make 2 WQUUF objects one has no bottom
    	UFgrid = new WeightedQuickUnionUF(N * N + 2);
    	UFgridFull = new WeightedQuickUnionUF(N * N + 1);
    	this.N = N;
    	// Connects the Top
    	for (int a = 1; a < N + 1; a++){
    		UFgrid.union(a, 0);
    		UFgridFull.union(a, 0);
    	}
    	// Connects the Bottom
    	for (int a = N * N - N; a < N * N + 1; a++){
    		UFgrid.union(a, N * N + 1);
    	}
    }
    /**
     * A simple helper method, converts 2D array 
     * coordinates to the longer 1D array coordinate system
     * Preconditions: Parameters
     * @param i:x coordinate
     * @param j:y coordinate
     * @param N:The number of rows or columns in the N by N grid
     * @return: The converted coordinate
     */
    private int DDtoD(int i, int j, int N){
		return N * i + j + 1;
	}
    /**
     * open site (row i, column j) if it is not already
     * Precondition: Object of the class
     * Postcondition: A opened block at the given coordinates
     * @param i: x coordinate
     * @param j: y coordinate
     */
    public void open(int i, int j) 
    {
    	if (i > N || i < 1 || j > N || j < 1){
    		throw new IndexOutOfBoundsException(i + " " + j);
    	}
    	int I = i - 1;
    	int J = j - 1;
    	blockArray[I][J] = 1;
    	if(I != 0 && blockArray[I - 1][J] == 1){
    		UFgrid.union((DDtoD(I, J, N)), (DDtoD(I - 1, J, N)));
    		UFgridFull.union((DDtoD(I,J,N)), (DDtoD(I - 1,J,N)));
    	}
    	if(I != N - 1 && blockArray[I + 1][J] == 1){
    		UFgrid.union((DDtoD(I,J,N)), (DDtoD(I + 1, J, N)));
    		UFgridFull.union((DDtoD(I,J,N)), (DDtoD(I + 1,J,N)));
    	}
    	if(J != 0 && blockArray[I][J - 1] == 1){
    		UFgrid.union((DDtoD(I, J, N)), (DDtoD(I, J - 1, N)));
    		UFgridFull.union((DDtoD(I,J,N)), (DDtoD(I,J - 1,N)));
    	}
    	if(J != N - 1 && blockArray[I][J + 1] == 1){
    		UFgrid.union((DDtoD(I, J, N)), (DDtoD(I, J + 1 ,N)));
    		UFgridFull.union((DDtoD(I, J, N)), (DDtoD(I, J + 1,N)));
    	}
    }
    /**
     *  is site (row i, column j) open?
     *  Precondition: Object of the class
     *  Postcondition: None
     * @param i: x coordinate
     * @param j: y coordinate
     * @return: True or false depending if the block is open
     */
    public boolean isOpen(int i, int j){
        if(blockArray[i - 1][j - 1] == 1){
        	return true;}
        else{
        	return false;
        }
    }

    /**
     * is site (row i, column j) full?
     * Precondition: Object of the class
     * Postcondition: None 
     * @param i: X coordinate
     * @param j: Y coordinate
     * @return: True or false depending if the block is full
     */
    public boolean isFull(int i, int j)
    {
        return (UFgridFull.connected(DDtoD(i - 1, j - 1, N), 0) && blockArray[i - 1][j - 1] == 1
        		&& UFgrid.connected(DDtoD(i - 1, j - 1, N), 0));
    }

    /**
     * does the system percolate?
     * Precondition: Object of the class
     * Postcondition: None
     * @return: True or false depending on if it percolates
     */
    public boolean percolates()
    {
    	if(UFgrid.connected(0, N * N)){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
}
