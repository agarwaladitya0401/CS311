package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	co_unit controlunit = new co_unit();
	boolean is_end = false;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performOperations()
	{
		controlunit.opcode="";
		controlunit.rs1="";
		controlunit.rs2="";
		controlunit.rd="";
		controlunit.Imm = "";
	}
	
	public void setEnableDisable()
	{
		MA_RW_Latch.setRW_enable(true);
		EX_MA_Latch.setMA_enable(false);
		MA_RW_Latch.setrd(EX_MA_Latch.getrd());
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable() && !is_end) {
			//System.out.println("MA:"+"\n");
			int op2 = EX_MA_Latch.getop2();
			int alures = EX_MA_Latch.getaluRes();
			int ldres=0;
			
			int instruction = EX_MA_Latch.getInstruction();
			controlunit.setInstruction(instruction);
			MA_RW_Latch.setInstruction(instruction);
			
			if(controlunit.opcode.equals("11101")) {
				is_end = true;
			}
				
			
			if(controlunit.isSt()){
				containingProcessor.getMainMemory().setWord( alures, op2);
			}
			else if (controlunit.opcode.equals("10110")){
	
				ldres = containingProcessor.getMainMemory().getWord(alures);
				MA_RW_Latch.setldres(ldres);
			}
			else{
				MA_RW_Latch.setalures(alures);
			}
			setEnableDisable();
		

		}
		else 
			performOperations();
	
	}

}
