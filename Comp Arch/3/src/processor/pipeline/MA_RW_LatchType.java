package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable,isld,isend;
	Integer alu,ld,op2;

	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	public int getalu() {
		return this.alu;
	}
	public void setalu(Integer alu) {
		this.alu=alu;
	}
	public int getld() {
		return this.ld;
	}
	public void setld(Integer ld) {
		this.ld=ld;
	}
	public int getop2() {
		return this.op2;
	}
	public void setop2(Integer ld) {
		this.op2=ld;
	}
	public boolean getisld() {
		return this.isld;
	}
	public void setisld(boolean ld) {
		this.isld=ld;
	}
	public boolean getisend() {
		return this.isend;
	}
	public void setisend(boolean ld) {
		this.isend=ld;
	}

}
