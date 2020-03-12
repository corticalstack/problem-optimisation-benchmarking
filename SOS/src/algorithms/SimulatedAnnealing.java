package algorithms;
//in this part you can import the functionalities that yuo need to use for implementing your algorithm

import interfaces.Algorithm;
import interfaces.Problem;
import org.apache.commons.math3.stat.StatUtils;
import utils.RunAndStore.FTrend;
import utils.random.RandUtils;
import utils.algorithms.Misc;
import java.util.Arrays;

import static utils.MatLab.max;
import static utils.MatLab.min;
import static utils.algorithms.Misc.generateRandomSolution;

/**
 * Simulated Annealing optimiser
 */
public class SimulatedAnnealing extends Algorithm
{
	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception
	{
		double temperature = setInitialTemp(problem, maxEvaluations);  // Set maximum temp from random solution subset
		double coolingRate = getParameter("coolingRate");
		double temperatureThreshold = 1;

		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();

		FTrend FT = new FTrend();

		// Current solution and fitness
		double[] xcb;
		double fcb;

		// New solution and fitness
		double[] xnew;
		double fnew;

		double loss;
		double probability;

		int i = 0;
		// Begin with random solution configuration
		if (initialSolution != null)
		{
			xcb = initialSolution;
		    fcb = initialFitness;
		}
		else
		{
			xcb = generateRandomSolution(bounds, problemDimension);
			fcb = problem.f(xcb);
			i++;
		}

		FT.add(0, fcb); // Store the initial guess

		//Decrease until budget comnsumed or cool until minimum temperature reached
		while ((i < maxEvaluations) && (temperature > temperatureThreshold)) {  // Prevent t < tmin
			i++;
			xnew = generateRandomSolution(bounds, problemDimension);
			xnew = Misc.toro(xnew, bounds);  // Always saturate solution within search space
			fnew = problem.f(xnew);

			loss = fcb - fnew;
			probability = Math.exp(loss / temperature);

			// Pairwise comparison - if change in energy is decreasing then accept the new solution
			//                     - or move to random new point nearby
			if ((fnew < fcb) || (RandUtils.random() < probability)) {
				FT.add(i, fnew);
				fcb = fnew;
				xcb = xnew;
			}

			// Exponential cooling schedule implemented as current temperature multiplied by fixed factor
			temperature = temperature * coolingRate;

		}
		finalBest = xcb;
		return FT; // Return the fitness trend
	}

	private double setInitialTemp (Problem problem, int maxEvaluations)  throws Exception {
		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();
		int i = 0;

		// Sample 10% of search space to determine starting max temperature
		int maxIter = (int)(maxEvaluations * 0.1);

		double[] fcurrSubset = new double[maxIter + 1];
		double[] xcurr;
		double fcurr;

		// Build up sample subset of temps
		while (i < maxIter) {
			i++;
			xcurr = generateRandomSolution(bounds, problemDimension);
			fcurr = problem.f(xcurr);
			fcurrSubset[i] = fcurr;
		}

		// Use 95th percentile as starting max temperature
		return StatUtils.percentile(fcurrSubset, 95);
	}
}

