	.data
a:
	4
	.text
main:
	load %x0, $a, %x2
	addi %x0, 2, %x3
	subi %x2, 1, %x4
	beq %x2, %x3, prime
loop:
	div %x2, %x3, %x5
	mul %x5, %x3, %x5
	beq %x2, %x5, notprime
	addi %x3, 1, %x3
	bgt %x3, %x4, prime
	jmp loop
notprime:
	subi %x0, 1, %x10
	end
prime:
	addi %x0, 1, %x10
	end