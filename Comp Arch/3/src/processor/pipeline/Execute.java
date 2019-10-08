package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_EnableLatchType IF_EnableLatch;
	Integer op1,op2,imm,bt,a;
	
	String opcode;
	boolean isbt=false,isld=false,isst=false,isrw=true,isend=false;
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,IF_EnableLatchType IF_EnableLatc)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_EnableLatch = IF_EnableLatc;
	}
	public String and(String s1,String s2)
	{   String s3="";
		for (int i=0;i<5;i++) {
			char x = 'a';
			x=' ';
			x=s1.charAt(i);
			if((x=='1')&& (x=='1')) {
					s3=s3+"1";
				}
			else s3=s3+"0";
		}
		return s3;
	}
	public String or(String s1,String s2)
	{   String s3="";
		for (int i=0;i<5;i++) {
			char x = 'a';
			x=' ';
			x=s1.charAt(i);
			if((x=='1')||(x=='1')) {
					s3=s3+"1";
				}
			else s3=s3+"0";
		}
		return s3;
	}
	
	public void ALU()
	{   
		isbt=false;
		isld=false;isst=false;isrw=true;isend=false;
		int val = Integer.parseInt(opcode, 2);
		switch(val)
		{
		case 0:
			a=op1+op2;
			break;

		case 1:
			a=op1+imm;
			break;

		case 2:
			a=op1-op2;
			break;
			
		case 3:
			a=op1-imm;
			break;
		
		case 4:
			a=op1*op2;
			break;

		case 5:
			a=op1*imm;
			break;
			
		case 6:
			a=op1/op2;
			containingProcessor.getRegisterFile().setValue(31,op1%op2);
			break;
			
		case 7:
			a=op1/imm;
			containingProcessor.getRegisterFile().setValue(31,op1%imm);
			break;
			
		case 8:
			a=op1&op2;
			break;
			
		case 9:
			a=op1&imm;
			break;

		case 10:
			a=op1|op2;
			break;
			
		case 11:
			a=op1|imm;
			break;
			
		case 12:
			a=op1^op2;
			break;
			
		case 13:
			a=op1^imm;
			break;
			
		case 14:
			if(op1<op2)
				a=1;
			else
				a=0;
			break;
		case 15:
			if(op1<imm)
				a=1;
			else
				a=0;
			break;
		case 16:
			a=op1<<op2;
			break;
			
		case 17:
			a=op1<<imm;
			break;
			
		case 18:
			a= op1>>>op2;
			break;
		
		case 19:
			a= op1>>>imm;
			break;
			
		case 20: 
			a= op1>>op2;
			break;
			
		case 21:
			a= op1>>imm;
			break;
			
		case 22:
			isld=true;
			a= op1+imm;
			break;
			
		case 23:
			isst=true;
			 a=op2+imm;
			 op2=op1;
			 break;
			 
		case 24:
			isbt=true;
			break;
			
		case 25:
			if((op1-op2)==0) {
			bt=imm;
			isbt=true;
			}
			break;
			
		case 26:
			if(op1!=op2) {
			bt=imm;;
			isbt=true;
			}
			break;
			
		case 27:
			if(op1<op2) {
			bt=imm;;
			isbt=true;
			}
			break;
			
		case 28:
			if(op1>op2) {
			bt=imm;
			isbt=true;
			}
			break;
			
		default:
			isend=true;
			break;
		}
		
	}
	
	public void setEnableDisable()
	{
		OF_EX_Latch.setEX_enable(false);
		if(isbt==true) {
			EX_IF_Latch.setIF_enable(true);

		}
		else if(this.opcode.equals("11001")||this.opcode.equals("11010")||this.opcode.equals("11011")||this.opcode.equals("11100")) {
			IF_EnableLatch.setIF_enable(true);
		}
		else {
		EX_MA_Latch.setMA_enable(true);
		}
	}
	
	public void performEX()
	{ 
		if(OF_EX_Latch.isEX_enable()) {
		bt=OF_EX_Latch.getbt();
		imm=OF_EX_Latch.getimm();
		op1=OF_EX_Latch.getop1();
		op2=OF_EX_Latch.getop2();
		opcode=OF_EX_Latch.getopcode();
		this.ALU();
		if(containingProcessor.getRegisterFile().getProgramCounter()<20) {
		}
		EX_IF_Latch.setbt(isbt,bt);
		
		EX_MA_Latch.setalures(a);
		if(OF_EX_Latch.getistype1()) {
			EX_MA_Latch.setop2(OF_EX_Latch.getrd());
		}
		else
		{
		EX_MA_Latch.setop2(op2);
		}
		EX_MA_Latch.setisend(isend);
		if(isbt==true||isst==true) {
			isrw=false;
		}
		EX_MA_Latch.setis(isld,isst,isrw);
		setEnableDisable();
		
	}
	}

}
