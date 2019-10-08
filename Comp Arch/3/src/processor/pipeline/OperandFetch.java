package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	String opcode,imm1,branchTarget,rs1,rs2,rd1,rd2,rd3,ra;
	Integer op1,op2,imm,bt;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	public int twoscompl(String a)
	{
		
		String s1="";
		for(int i=0;i<a.length();i++)
		{
			if(a.charAt(i)=='1')
			{
				s1=s1+'0';
			}
			else
			{
				s1=s1+'1';
			}
		}
		return (-1*Integer.parseInt(s1,2)-1);
	}
	
	public void performOF()
	{   
		if(IF_OF_Latch.isOF_enable())
		{
			String instr=Integer.toBinaryString(IF_OF_Latch.getInstruction());
			while(instr.length()!=32) {
				instr="0"+instr;
			}

			this.opcode=instr.substring(0,5);
			this.rs1=instr.substring(5,10);
			this.rs2=instr.substring(10,15);
			
			this.rd1=instr.substring(15,20);
			
			this.rd2=this.rs2;
			

			
			this.imm1=instr.substring(15,32);
			char sign1=this.imm1.charAt(0);
			if(sign1=='1') {
				this.imm=twoscompl(this.imm1);
			}
			else {
			this.imm1="000000000000000"+this.imm1;//check;
			
			this.imm=Integer.parseInt(this.imm1,2);
			}
			
			
			this.branchTarget=instr.substring(10,32);
			String sign=this.branchTarget.substring(0,1);
			this.branchTarget=sign+sign+sign+sign+sign+sign+sign+sign+sign+sign+this.branchTarget;
			
			if(sign.equals("1"))
			{
				this.bt= twoscompl(branchTarget);
			}
			else
			{  
				this.bt=Integer.parseInt(this.branchTarget,2);
				
			}
			int flag=1;
			int val = Integer.parseInt(this.opcode, 2);
			switch(val)
			{
				case 1:
				case 3:
				case 5:
				case 7:
				case 9:
				case 19:
					flag=0;//of type 2,e.g addi
				case 13:
				case 15:
				case 17:
				case 21:
					flag=0;//type 2
			}
			this.op1=containingProcessor.getRegisterFile().getValue(Integer.parseInt(this.rs1,2));
			switch(val)
			{
				case 23:
				case 22:
				case 25:
				case 26:
				case 27:
				case 28:
					OF_EX_Latch.setistype(false);
			    	this.op2=Integer.parseInt(this.rd2,2);
			    	switch(val)
			    	{
				    	case 23:
				    	case 25:
				    	case 26:
				    	case 27:
				    	case 28:
				    		this.op2=containingProcessor.getRegisterFile().getValue(Integer.parseInt(this.rd2,2));
				    		break;
			    	}
			    	break;
			    default:
			    	if(flag==0)
			    	{
			    		OF_EX_Latch.setistype(false);
				    	this.op2=Integer.parseInt(this.rd2,2);
				    	switch(val)
				    	{
					    	case 23:
					    	case 25:
					    	case 26:
					    	case 27:
					    	case 28:
					    		this.op2=containingProcessor.getRegisterFile().getValue(Integer.parseInt(this.rd2,2));
					    		break;
				    	}
			    	}
		    
			    	else
			    	{
			    		this.op2=containingProcessor.getRegisterFile().getValue(Integer.parseInt(this.rs2,2));
			    		OF_EX_Latch.setrd(Integer.parseInt(this.rd1,2));
			    		OF_EX_Latch.setistype(true);
			    	}
			}
			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
			OF_EX_Latch.setopcode(opcode);
			OF_EX_Latch.setimm(imm);
			OF_EX_Latch.setop1(op1);
			OF_EX_Latch.setop2(op2);
			OF_EX_Latch.setbt(bt);
		}
	}

}
