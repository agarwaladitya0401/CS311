package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;
import processor.pipeline.IF_EnableLatchType;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static IF_EnableLatchType IF_EnableLatch;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	@SuppressWarnings("resource")
	static void loadProgram(String assemblyProgramFile) throws IOException
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		InputStream is = null;
	      DataInputStream dis = null;
		
		is = new FileInputStream(assemblyProgramFile);
        dis = new DataInputStream(is);
        int i=0;
        while(dis.available()>0) {
        	
        	int k = dis.readInt();
        	if(i==0) {
        	processor.getRegisterFile().setProgramCounter(k);
        	}
        	else {
        		processor.getMainMemory().setWord(i-1,k);
        	}
        		i++;
        	}
        
        processor.getRegisterFile().setValue(0, 0);
        processor.getRegisterFile().setValue(1, 65535);
        processor.getRegisterFile().setValue(2, 65535);
		}
	
	public static void simulate()
	{
		while(simulationComplete == false)
		{	
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
		}
		
		
		//statistics calculation is done instruction fetch stage
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
