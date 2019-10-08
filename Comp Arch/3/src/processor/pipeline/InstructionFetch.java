package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	static Statistics stat=new Statistics();
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch; 
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	} 
	
	public void enableDisableLatches()
	{
		IF_EnableLatch.setIF_enable(false);
		IF_OF_Latch.setOF_enable(true);
		EX_IF_Latch.setIF_enable(false);
	}
	
	public void performCommonOperations(int currentPC)
	{
		int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
		IF_OF_Latch.setInstruction(newInstruction);
		containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable()||EX_IF_Latch.isIF_enable())
		{	stat.setNumberOfInstructions(stat.getNumberOfInstructions()+1);
			stat.setNumberOfCycles(Statistics.numberOfInstructions*5);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			if(EX_IF_Latch.isbt==true)
			{
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter()+EX_IF_Latch.offset-1);
				currentPC = containingProcessor.getRegisterFile().getProgramCounter();
				performCommonOperations(currentPC);
			}
			else if(IF_EnableLatch.isIF_enable())
				performCommonOperations(currentPC);
			enableDisableLatches();
			
		}
		else {
			//Do Nothing
		}
	}

}
