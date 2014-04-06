import java.util.Random;

public class PercolationStats
{
    // ...
    // your data members here
    // ...
	private Percolation test;
	private double total;
	private double blocks;
	private Random ran;
	private double N;
	private double T;
	private double totalSquared;
	private double totalBlocks;
	
    /**
     * perform T independent computational experiments on an N-by-N grid
     * Precondition: None
     * Postcondition: Object of the class
     * @param N: Number of rows and columns in the N by N grid
     * @param T: Number of times you want to run the tests
     */
    public PercolationStats(int N, int T)
    {
    	this.N = N;
    	this.T = T;
    	totalBlocks = N * N;
    	ran = new Random();
    	// runs the test T times
    	for (int i = 0; i < T; i++) {
    		test = new Percolation(N);
    		while(!test.percolates()){
    			int x = ran.nextInt(N) + 1;
    			int y = ran.nextInt(N) + 1;
    			if (!test.isOpen(x, y)) {
    				test.open(x, y);
    				blocks++;
    			}
    		}
    		total = total + (blocks / (totalBlocks));
    		totalSquared = totalSquared + Math.pow((blocks / 
    		(totalBlocks)), 2);
    		blocks = 0;
    	}
    }

    /**
     * sample mean of percolation threshold.
     * Precondition: Object of the class
     * Postcondition: None
     * @return: returns the mean value of the tests
     */
    public double mean()
    {
        return total / T;
    }

    /**
     * sample standard deviation of percolation threshold
     * Precondition: Object of the class
     * Postcondition: None
     * @return: The standard deviation of the tests
     */
    public double stddev()
    {
    	return Math.sqrt((totalSquared - T * Math.pow(this.mean(),2))/(T - 1));
    }

    /**
     * returns lower bound of the 95% confidence interval
     * Precondition: Object of the class
     * Postcondition: None
     * @return: returns the lower bound of the 95% confidence interval
     */
    public double confidenceLo()
    {
        return this.mean() - ((1.96 * this.stddev())/Math.sqrt(T));
    }

    /**
     * returns upper bound of the 95% confidence interval
     * Precondition: Object of the class
     * Postcondition: None
     * @return: returns the upper bound of the 95% confidence interval
     */
    public double confidenceHi()
    {
        return this.mean() + ((1.96 * this.stddev())/Math.sqrt(T));
    }
    public static void main(String[] args) {
    	// Creates the object that runs the tests
    	PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
    	// Prints the results in a neat manner
    	System.out.println("Mean                    = " + test.mean());
    	System.out.println("Stddev                  = " + test.stddev());
    	System.out.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());
    	
    }
}
