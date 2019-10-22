package processor.pipeline;

import generic.Simulator;
import processor.Processor;
import processor.Clock;
import generic.Statistics;
import generic.Element;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Event;
import generic.Event.EventType;
import configuration.Configuration;

public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor,
							IF_EnableLatchType iF_EnableLatch,
							IF_OF_LatchType iF_OF_Latch,
							EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	@Override
	public void handleEvent(Event e)
	{
		if (e.getEventType() == EventType.MemoryResponse)
		{
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			int newInstruction = event.getValue();
			System.out.println("IF is enabled: " + Integer.toBinaryString(newInstruction));
			IF_OF_Latch.setInstruction(newInstruction);
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);
			IF_OF_Latch.setOF_busy(false);
		}
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{
			if(IF_EnableLatch.isIF_busy())
			{
				return;
			}

			if(EX_IF_Latch.getIS_enable())
			{
				int newPC = EX_IF_Latch.getPC();
				containingProcessor.getRegisterFile().setProgramCounter(newPC);
				EX_IF_Latch.setIS_enable(false);
			}

			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			System.out.println("currentPC " + Integer.toString(currentPC));

			Simulator.getEventQueue().addEvent(
				new MemoryReadEvent(
					Clock.getCurrentTime() + Configuration.mainMemoryLatency,
					this,
					containingProcessor.getMainMemory(),
					currentPC));

			IF_EnableLatch.setIF_busy(true);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions() + 1);

		}
	}

}
