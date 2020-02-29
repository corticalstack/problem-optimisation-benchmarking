package algorithms;
//in this part you can import the functionalities that yuo need to use for implementing your algorithm

import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
import utils.random.RandUtils;
import utils.algorithms.Misc;

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
		double temperature = getParameter("initialTemperature");
		double coolingRate = getParameter("coolingRate");

		double temperatureThreshold = 1;

		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();

		FTrend FT = new FTrend();
		double[] xcb = new double[problemDimension];  // current x
		double fcb;                                   // current fitness of x

		double[] xnew = new double[problemDimension]; // new x
		double fnew;                                   // new fitness of x

		double loss;
		double probability;

		int i = 0;

		// Begin with random configuation
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
		//while ((i < maxEvaluations) && (temperature > temperatureThreshold)) {  // Prevent t < tmin
		while ((i < maxEvaluations)) {
			i++;
			xnew = generateRandomSolution(bounds, problemDimension);
			xnew = Misc.toro(xnew, bounds);  // Always saturate within search space
			fnew = problem.f(xnew);

			loss = fcb - fnew;
			probability = Math.exp(loss / temperature);
			if (fnew < fcb) {  // Pairwise comparison - if change in energy is decreasing then accept the new solution
				FT.add(i, fnew);
				fcb = fnew;
				xcb = xnew;
			}

			if ((fnew < fcb) || (RandUtils.random() < probability)) {
				fcb = fnew;

			}
			temperature = temperature * coolingRate;  // Note this is a linear schedule
			// System.out.println("Temp is : " + temperature);
		}

		finalBest = xcb;
		return FT; // Return the fitness trend

	}
}




