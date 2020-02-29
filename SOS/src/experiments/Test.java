package experiments;

import algorithms.SimulatedAnnealing;
import algorithms.SimulatedAnnealingFC;
import benchmarks.BaseFunctions;
import interfaces.Experiment;
import interfaces.Algorithm;
import interfaces.Problem;

public class Test extends Experiment
{
	
	public Test(int probDim)
	{
		super(probDim,"CIO_Task2_altmisc");
		setNrRuns(1000);

		Algorithm a;// ///< A generic optimiser.
		Problem p;// ///< A generic problem.

		// Add simulated annealing optimiser to test
		// JP to do - think of smart ways to set iniital temp and set cooling strategy
		// JP just try different annealing temps e.g 100 200 300 400 500 600 700 800
		a = new SimulatedAnnealing();
		a.setParameter("initialTemperature", 600.0);
		a.setParameter("coolingRate", 0.99);
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