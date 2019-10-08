package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Integer aluresult,op2,ldres;
	boolean isrw=true,isld=false,isend=false;
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch,IF_EnableLatchType if_enableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = if_enableLatch;
	}
	
	public void setEnableDisable(boolean isld, boolean isend, Integer ldres, Integer aluresult, Integer op2)
	{
		MA_RW_Latch.setRW_enable(true);
		MA_RW_Latch.setisld(isld);
		MA_RW_Latch.setisend(isend);
		MA_RW_Latch.setld(ldres);
		MA_RW_Latch.setalu(aluresult);
		MA_RW_Latch.setop2(op2);
	}
	public void performMA()
	{

		if(EX_MA_Latch.isMA_enable())
		{
			aluresult=EX_MA_Latch.getalures();
			op2=EX_MA_Latch.getop2();
			
			isld=EX_MA_Latch.getisld();
			if(EX_MA_Latch.getisst()==true) {
				containingProcessor.getMainMemory().setWord(aluresult,op2);
				IF_EnableLatch.setIF_enable(true);
			}
			if(EX_MA_Latch.getisld()==true) {
				ldres=containingProcessor.getMainMemory().getWord(aluresult);
			}
			isrw=EX_MA_Latch.getisrw();
			isend=EX_MA_Latch.getisend();
			
			EX_MA_Latch.setMA_enable(false);
			if(isrw==true)
				setEnableDisable(isld, isend, ldres, aluresult, op2);
		}
	}

}
