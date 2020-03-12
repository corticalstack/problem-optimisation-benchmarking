package experiments;

import algorithms.SimulatedAnnealing;
import algorithms.CMAES;
import algorithms.ISPO;
import benchmarks.BaseFunctions;
import interfaces.Experiment;
import interfaces.Algorithm;
import interfaces.Problem;

public class Test extends Experiment
{
	
	public Test(int probDim)
	{
		super(probDim,"CIO_Task2");
		setNrRuns(200);

		Algorithm a;  // A generic optimiser
		Problem p;    // A generic problem

		a = new SimulatedAnnealing();
		a.setParameter("coolingRate", 0.9998);
		add(a); //add it to the list

//		a = new ISPO();
//		a.setParameter("p0", 1.0);
//		a.setParameter("p1", 10.0);
//		a.setParameter("p2", 2.0);
//		a.setParameter("p3", 4.0);
//		a.setParameter("p4", 1e-5);
//		a.setParameter("p5", 30.0);
//		add(a); //add it to the list
//
//		a = new CMAES();
//		add(a);

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