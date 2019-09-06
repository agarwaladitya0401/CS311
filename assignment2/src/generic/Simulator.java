package generic;

import java.io.FileInputStream;
import generic.Operand.OperandType;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	public static String twosCompliment(String bin) {
        String twos = "", ones = "";

        for (int i = 0; i < bin.length(); i++) {
            ones += flip(bin.charAt(i));
        }
        // int number0 = Integer.parseInt(ones, 2);
        StringBuilder builder = new StringBuilder(ones);
        boolean b = false;
        for (int i = ones.length() - 1; i > 0; i--) {
            if (ones.charAt(i) == '1') {
                builder.setCharAt(i, '0');
            } else {
                builder.setCharAt(i, '1');
                b = true;
                break;
            }
        }
        if (!b)
            builder.append("1", 0, 7);

        twos = builder.toString();

        return twos;
    }

// Returns '0' for '1' and '1' for '0'
    public static char flip(char c) {
        return (c == '0') ? '1' : '0';
    }
	
	public static void assemble(String objectProgramFile)
	{
		FileOutputStream fos;
		try {
			//1. open the objectProgramFile in binary mode
			fos = new FileOutputStream(objectProgramFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			//2. write the firstCodeAddress to the file
			byte[] codeAddress = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			bos.write(codeAddress);
			//3. write the data to the file
			for (int i=0; i<ParsedProgram.data.size(); i++) {
				byte[] dataUnit = ByteBuffer.allocate(4).putInt(ParsedProgram.data.get(i)).array();
				bos.write(dataUnit);
			}
			//4. assemble one instruction at a time, and write to the file
			for (int i=0; i<ParsedProgram.code.size(); i++) {
				int inst_opcode = ParsedProgram.code.get(i).getOperationType().ordinal() ;
				String instruction_bits = String.format("%05d", Integer.valueOf(Integer.toBinaryString(inst_opcode)));
				if (inst_opcode == 29) {
					instruction_bits = instruction_bits + String.format("%027d", Integer.valueOf(Integer.toBinaryString(0)));
				} else if (inst_opcode == 24) {
					int inst = ParsedProgram.code.get(i).getDestinationOperand().getValue();
					if (ParsedProgram.code.get(i).destinationOperand.getOperandType() == Operand.OperandType.Register) {
						instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(inst)));
						instruction_bits = instruction_bits + String.format("%022d", Integer.valueOf(Integer.toBinaryString(0)));
					} else {
						instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(0)));
						String op = ParsedProgram.code.get(i).getDestinationOperand().getLabelValue();
						int label_value = ParsedProgram.symtab.get(op);
						int pc = ParsedProgram.code.get(i).getProgramCounter();
						if (label_value - pc < 0) {
							String to_complement = String.format("%022d", Integer.valueOf(Integer.toBinaryString(pc - label_value)));
							instruction_bits = instruction_bits + twosCompliment(to_complement);
						} else {
							instruction_bits = instruction_bits + String.format("%022d", Integer.valueOf(Integer.toBinaryString(label_value - pc)));
						}
					}
				} else if (inst_opcode <= 21 && inst_opcode % 2 == 0) {
					int rs1 = ParsedProgram.code.get(i).getSourceOperand1().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rs1)));
					int rs2 = ParsedProgram.code.get(i).getSourceOperand2().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rs2)));
					int rd = ParsedProgram.code.get(i).getDestinationOperand().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rd)));
					instruction_bits = instruction_bits + String.format("%012d", Integer.valueOf(Integer.toBinaryString(0)));
				} else if (inst_opcode <= 21 && inst_opcode % 2 != 0 || inst_opcode == 22 || inst_opcode == 23) {
					int rs1 = ParsedProgram.code.get(i).getSourceOperand1().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rs1)));
					int rd = ParsedProgram.code.get(i).getDestinationOperand().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rd))); //r21
					int imm = ParsedProgram.code.get(i).getSourceOperand2().getValue();
					instruction_bits = instruction_bits + String.format("%017d", Integer.valueOf(Integer.toBinaryString(imm)));
				} else {
					int rs1 = ParsedProgram.code.get(i).getSourceOperand1().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rs1)));
					int rs2 = ParsedProgram.code.get(i).getSourceOperand2().getValue();
					instruction_bits = instruction_bits + String.format("%05d", Integer.valueOf(Integer.toBinaryString(rs2)));
					String op = ParsedProgram.code.get(i).getDestinationOperand().getLabelValue();
					int label_value = ParsedProgram.symtab.get(op);
					int pc = ParsedProgram.code.get(i).getProgramCounter();
					if (label_value - pc < 0) {
						String to_complement = String.format("%017d", Integer.valueOf(Integer.toBinaryString(pc - label_value)));
						instruction_bits = instruction_bits + twosCompliment(to_complement);
					} else {
						instruction_bits = instruction_bits + String.format("%017d", Integer.valueOf(Integer.toBinaryString(label_value - pc)));
					}
				}
				int inst_intmap = (int) Long.parseLong(instruction_bits, 2);
				byte[] inst_bitmap = ByteBuffer.allocate(4).putInt(inst_intmap).array();
				bos.write(inst_bitmap);
			}
			//5. close the file
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
