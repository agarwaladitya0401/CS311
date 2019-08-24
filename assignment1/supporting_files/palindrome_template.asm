	.data
a:
	1234321
	.text
main:
	load %x0, $a, %x2
	load %x0, $a, %x3
	addi %x0, 0, %x9
	addi %x0, 10, %x10
loop:
	div %x3, %x10, %x4
	mul %x4, %x10, %x4
	sub %x3, %x4, %x5
	mul %x9, %x10, %x9
	add %x9, %x5, %x9
	blt %x3, %x10, check
	div %x3, %x10, %x3
	jmp loop
check:
	beq %x2, %x9, palindrome
	subi %x0, 1, %x10
	end
palindrome:
	addi %x0, 1, %x10
	end