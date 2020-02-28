/** @file Misc.java
 * MISCELLANEOUS
 *  @author Fabio Caraffini
*/
package utils.algorithms;


import static utils.MatLab.max;
import static utils.MatLab.min;
import utils.random.RandUtils;

/**
 * This class contains useful miscellaneous methods.
*/	
public class Misc
{

	/**
	 * Saturation on bounds of the search space.
	 * 
	 * @param x solution to be saturated.
	 * @param bounds search space boudaries.
	 * @return x_tor corrected solution.
	 */
	public static double[] saturate(double[] x, double[][] bounds)
	{
		int n = x.length;
		double[] x_sat = new double[n];
		for (int i = 0; i < n; i++)
			x_sat[i] = min(max(x[i], bounds[i][0]), bounds[i][1]);
		return x_sat;
	}
	
	/**
	 * Clone a solution.
	 * 
	 * @param x solution to be duplicated.
	 * @return xc cloned solution.
	 */
	public static double[] clone(double[] x)
	{
		int n=x.length;
		double[] xc = new double[n];
		for (int i = 0; i < n; i++)
			xc[i] = x[i];
		return xc;
	}
	/**
	 * Rounds x to the nearest integer towards zero.
	 */
	public static int fix(double x)
	{
		return (int) ((x >= 0) ? Math.floor(x) : Math.ceil(x));  
	}
	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (general case).
	 * @param n problem dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[][] bounds, int n)
	{
			double[] r = new double[n];
		    for (int i = 0; i<n; i++)
		      r[i]= (int)(Math.random() * (bounds[i][1]-bounds[i][0])) + bounds[i][0];
			return r;
	}

	/**
	 * Random point in bounds.
	 * 
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @param n problam dimension.
	 * @return r randomly generated point.
	 */
	public static double[] generateRandomSolution(double[] bounds, int n)
	{
		double[] r = new double[n];
		for (int i = 0; i < n; i++)
			r[i]= (int)(Math.random() * (bounds[1]-bounds[0])) + bounds[0];
		return r;
	}
	/**
	 * Toroidal correction within the search space
	 * 
	 * @param x solution to be corrected.
	 * @param bounds search space boundaries (hyper-parallelepiped).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[] bounds)
	{
		double[] xCor = new double[x.length];
		double[] xOut = new double[x.length];

		for (int i = 0; i < x.length; i++) {
			xCor[i] = (x[i] - bounds[0]) / (bounds[1] - bounds[0]);
			if (xCor[i] > 1) {
				xCor[i] = xCor[i] - fix(xCor[i]);
			} else if (xCor[i] < 0) {
				xCor[i] = 1 - Math.abs(xCor[i] - fix(xCor[i]));
			}
			xOut[i] = bounds[0] + (xCor[i] * (bounds[1] - bounds[0]));
		}
		return xOut;
	}

	/**
	 * Toroidal correction within search space
	 * 
	 * @param x solution to be corrected.
	 * @param bounds search space boundaries (general case).
	 * @return x_tor corrected solution.
	 */
	public static double[] toro(double[] x, double[][] bounds)
	{
		double[] xCor = new double[x.length];
		double[] xOut = new double[x.length];

		for (int i = 0; i < x.length; i++) {
			xCor[i] = (x[i] - bounds[i][0]) / (bounds[i][1] - bounds[i][0]);
			if (xCor[i] > 1) {
				xCor[i] = xCor[i] - fix(xCor[i]);
			} else if (xCor[i] < 0) {
				xCor[i] = 1 - Math.abs(xCor[i] - fix(xCor[i]));
			}
			xOut[i] = bounds[i][0] + (xCor[i] * (bounds[i][1] - bounds[i][0]));
		}
		return xOut;
	}



}
		
	


