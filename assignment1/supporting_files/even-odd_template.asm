	.data
a:
	1
	.text
main:
	load %x0, $a, %x10
	addi %x0, 2, %x2
loop:
	blt %x10, %x2, success
	subi %x10, 2, %x10
	jmp loop
success:
	beq %x10, %x0, even
	subi %x10, 2, %x10
	end
even:
	addi %x10, 1, %x10
	end