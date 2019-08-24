	.data
a:
	10
	.text
main:
    load %x0, $a, %x3
    divi %x3, 2, %x4
    beq  %x31, %x0, even
    addi %x0, 1, %x10
    end
even:
    subi %x0, 1, %x10
    end