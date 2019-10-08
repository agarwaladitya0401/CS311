package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable,is1;
	Integer imm,op1,op2,bt,rd;
	String opcode;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	public int getimm() {
		return this.imm;
	}
	public int getop1() {
		return this.op1;
	}
	public int getop2() {
		return this.op2;
	}
	public boolean getistype1() {
		return this.is1;
	}
	public int getrd() {
		return this.rd;
	}
	public int getbt() {
		return this.bt;
	}
	public String getopcode() {
		return this.opcode;
	}
	public void setimm(Integer imm) {
		this.imm=imm;
	}
	public void setop1(Integer op1) {
		this.op1=op1;
	}
	public void setop2(Integer op2) {
		this.op2=op2;
	}
	public void setbt(Integer bt) {
		this.bt=bt;
	}
	public void setopcode(String opcode) {
		this.opcode=opcode;
	}
	public void setrd(Integer bt) {
		this.rd=bt;
	}
	public void setistype(boolean bt) {
		this.is1=bt;
	}
	
}
