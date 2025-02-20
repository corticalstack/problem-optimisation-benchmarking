/** @file RunExperiments.java
 *  
 *  @author Fabio Caraffini
*/
import java.util.Vector; 
import interfaces.Experiment;
import static utils.RunAndStore.resultsFolder;
import experiments.*;

/** 
* This class contains the main method and has to be used for launching experiments.
*/
public class RunExperiments
{
	
	
	/** 
	* Main method.
	* This method has to be modified in order to launch a new experiment.
	*/
	public static void main(String[] args) throws Exception
	{
		resultsFolder();

		Vector<Experiment> experiments = new Vector<Experiment>();////!< List of problems 

		//@@@ MODIFY THIS PART @@@
		experiments.add(new Test( 10));
		experiments.add(new Test( 30));
		experiments.add(new Test( 50));
		//@@@@@@
	
		for(Experiment experiment : experiments)
		{
			//experiment.setShowPValue(true);
			experiment.startExperiment();
			System.out.println();
			experiment = null;
		}
	
		
		
	}
	
	

}
