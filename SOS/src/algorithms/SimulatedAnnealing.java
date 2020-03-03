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
 * S (Hooke Jeeves) single solution deterministic algorithm
 */
public class SimulatedAnnealing extends Algorithm //This class implements the algorithm. Every algorithm will have to contain its specific implementation within the method "execute". The latter will contain a main loop performing the iterations, and will have to return the fitness trend (including the final best) solution. Look at this examples before implementing your first algorithm.
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
			//if (fnew < fcb) {  // Pairwise comparison - if change in energy is decreasing then accept the new solution
			//	FT.add(i, fnew);
			//	fcb = fnew;
			//	xcb = xnew;
			//}

			if ((fnew < fcb) || (RandUtils.random() < probability)) {  // Or move to random new pointy
				FT.add(i, fnew);
				fcb = fnew;
				xcb = xnew;
			}
			temperature = temperature * coolingRate;

		}
		finalBest = xcb;
		return FT; // Return the fitness trend
	}

	private double setInitialTemp (Problem problem, int maxEvaluations)  throws Exception {
		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();
		int i = 0;
		int maxIter = (int)(maxEvaluations * 0.001);

		double[] fcurrSubset = new double[maxIter + 1];
		double[] xcurr;
		double fcurr;

		while (i < maxIter) {
			i++;
			xcurr = generateRandomSolution(bounds, problemDimension);
			fcurr = problem.f(xcurr);
			fcurrSubset[i] = fcurr;
		}

		return StatUtils.percentile(fcurrSubset, 95);
	}
}

