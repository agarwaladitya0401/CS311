package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Integer alu,ld,op2;
	boolean isld;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void setEnableDisable()
	{
		MA_RW_Latch.setRW_enable(false);
		IF_EnableLatch.setIF_enable(true);
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			alu=MA_RW_Latch.getalu();
			isld=MA_RW_Latch.getisld();
			
			op2=MA_RW_Latch.getop2();
			
			if(isld==true) {
				ld=MA_RW_Latch.getld();
				containingProcessor.getRegisterFile().setValue(op2,ld);
			}
			else {
				containingProcessor.getRegisterFile().setValue(op2,alu);
			}
			if(MA_RW_Latch.getisend()==true) {
				containingProcessor.getRegisterFile().setValue(0, 0);
				containingProcessor.getRegisterFile().setValue(1, 65535);
				containingProcessor.getRegisterFile().setValue(2, 65535);
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter());
				Simulator.setSimulationComplete(true);
			}
			setEnableDisable();
		}
	}

}
