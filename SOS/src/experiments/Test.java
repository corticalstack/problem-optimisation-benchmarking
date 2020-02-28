package experiments;

import algorithms.SimulatedAnnealing;
import benchmarks.BaseFunctions;
import interfaces.Experiment;
import interfaces.Algorithm;
import interfaces.Problem;
//import algorithms.*; 
import algorithms.ISPO; 
import algorithms.CMAES;
import benchmarks.BaseFunctions.Alpine; 
import benchmarks.BaseFunctions.Ackley;
import benchmarks.BaseFunctions.Rosenbrock;
import benchmarks.BaseFunctions.DeJongSphere;

public class Test extends Experiment
{
	
	public Test(int probDim)
	{
		super(probDim,"CIO_Task2");
		setNrRuns(30);

		Algorithm a;// ///< A generic optimiser.
		Problem p;// ///< A generic problem.

		a = new SimulatedAnnealing();
		a.setParameter("initialTemperature", 10.0);
		a.setParameter("coolingRate", 0.5);
		add(a); //add it to the list

		// Add DeJong Sphere problem
		p = new BaseFunctions.DeJongSphere(probDim);
		p.setFID("DeJongSpehere");
		add(p);

		// Add Schwefel problem
		p = new BaseFunctions.Schwefel(probDim);
		p.setFID("Schwefel");
		add(p);

		// Add Rastrigin problem
		p = new BaseFunctions.Rastrigin(probDim);
		p.setFID("Rastrigin");
		add(p);

		// Add Michalewicz problem
		p = new BaseFunctions.Michalewicz(probDim);
		p.setFID("Michalewicz");
		add(p);

	}
}
