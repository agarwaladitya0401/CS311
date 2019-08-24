	.data
n:
	12
	.text
main:
	addi %x0, 0, %x2
	addi %x0, 1, %x3
	load %x0, $n, %x4
	addi %x0, 65535, %x5
loop:
	store %x2, 0, %x5
	subi %x4, 1, %x4
	subi %x5, 1, %x5
	beq %x4, %x0, endl
	add %x2, %x0, %x6
	add %x3, %x0, %x2
	add %x6, %x3, %x3
	jmp loop
endl:
	end