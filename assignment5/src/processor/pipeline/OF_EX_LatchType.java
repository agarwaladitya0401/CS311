package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	
	boolean EX_enable, EX_busy;
	Instruction instruction;
	boolean NOP;
	public OF_EX_LatchType()
	{
		EX_enable = false;
		NOP = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public Instruction getInstruction() {
		return this.instruction;
	}
	
	public boolean getIsNOP() {
		return NOP;
	}
	
	public void setIsNOP(boolean is_NOP) {
		NOP = is_NOP;
	}

	public boolean isEX_busy() {
		return EX_busy;
	}

	public void setEX_busy(boolean eX_busy){
		 EX_busy = eX_busy;
	}

}
