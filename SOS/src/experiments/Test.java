package experiments;

import algorithms.SimulatedAnnealing;
import benchmarks.BaseFunctions;
import interfaces.Experiment;
import interfaces.Algorithm;
import interfaces.Problem;

public class Test extends Experiment
{
	
	public Test(int probDim)
	{
		super(probDim,"CIO_Task2");
		setNrRuns(30);

		Algorithm a;// ///< A generic optimiser.
		Problem p;// ///< A generic problem.

		// Add simulated annealing optimiser to test
		a = new SimulatedAnnealing();
		a.setParameter("initialTemperature", 10.0);
		a.setParameter("coolingRate", 0.8);
		add(a); //add it to the list

		// Add DeJong Sphere problem to test
		p = new BaseFunctions.DeJongSphere(probDim);
		p.setFID("DeJongSpehere");
		add(p);

		// Add Schwefel problem to test
		p = new BaseFunctions.Schwefel(probDim);
		p.setFID("Schwefel");
		add(p);

		// Add Rastrigin problem to test
		p = new BaseFunctions.Rastrigin(probDim);
		p.setFID("Rastrigin");
		add(p);

		// Add Michalewicz problem to test
		p = new BaseFunctions.Michalewicz(probDim);
		p.setFID("Michalewicz");
		add(p);
	}
}