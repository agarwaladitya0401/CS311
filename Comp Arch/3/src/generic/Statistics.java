package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	public static int numberOfInstructions=0;
	static int numberOfCycles=0;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("processor speed = " + (double)numberOfInstructions/numberOfCycles);
			
			// TODO add code here to print statistics in the output file
			//System.out.println(numberOfInstructions);
			//System.out.println(numberOfCycles);
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}
	public int getNumberOfInstructions() {
		return Statistics.numberOfInstructions;
	}

	public void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
}
