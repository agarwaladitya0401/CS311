package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;
import generic.Statistics;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static EventQueue eventQueue;

	public static EventQueue getEventQueue()
	{
		return eventQueue;
	}
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		try
		{
			loadProgram(assemblyProgramFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		eventQueue = new EventQueue();
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile) throws IOException
	{
		InputStream is = null;
		try
		{
			is = new FileInputStream(assemblyProgramFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		DataInputStream dis = new DataInputStream(is);

		int address = -1;
		while(dis.available() > 0)
		{
			int next = dis.readInt();
			if(address == -1)
			{
				processor.getRegisterFile().setProgramCounter(next);
			}
			else
			{
				processor.getMainMemory().setWord(address, next);
			}
			address += 1;
		}
        
        processor.getRegisterFile().setValue(0, 0);
        processor.getRegisterFile().setValue(1, 65535);
        processor.getRegisterFile().setValue(2, 65535);
        
        //System.out.println(processor.getRegisterFile().getProgramCounter());
        //String output = processor.getMainMemory().getContentsAsString(0, 15);
        //System.out.println(output);
	}
			
	public static void simulate()
	{
		while(simulationComplete == false)
		{
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			
			System.out.println("==================================");
		}
		
		Statistics.setNumberOfCycles(Clock.getCurrentTime());
		System.out.println("Number of Cycles: " + Statistics.getNumberOfCycles());
		System.out.println("Number of OF Stalls: " + (Statistics.getNumberOfInstructions() - Statistics.getNumberOfRegisterWriteInstructions()));
		System.out.println("Number of Wrong Branch Instructions: " + Statistics.getNumberOfBranchTaken());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
