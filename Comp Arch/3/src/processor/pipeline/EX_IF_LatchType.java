package processor.pipeline;

public class EX_IF_LatchType {
	public
	boolean IF_enable;
	Integer offset=0;
	boolean isbt;
	
	public EX_IF_LatchType()
	{
		IF_enable=false;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean if_enable) {
		IF_enable = if_enable;
	}
	public void setbt(boolean isbrt,Integer bt) {
		this.isbt=isbrt;
		if(isbrt==true) {
			offset=bt;
			//IF_EnableLatch.setIF_enable(true);
		}
		else
		{	this.isbt=false;
			//System.out.println("hdgfhhsd");
			offset=0;
		}
	}


}
