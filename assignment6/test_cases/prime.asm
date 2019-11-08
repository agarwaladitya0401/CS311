.data
a:
  11
	.text
main:
	load %x0, $a, %x3 //1
	sub %x4, %x4, %x4 //2
	divi %x3, 2, %x4 //3
	sub %x6, %x6, %x6 //4
	addi %x6, 2, %x6 //5
for:
	bgt %x6, %x4, prime //6
	div %x3, %x6, %x7 // 7
	beq %x0, %x31, notprime //8
	addi %x6, 1, %x6 //9
	jmp for //10
prime:
	sub %x10, %x10, %x10 //11
	addi %x10, 1, %x10 //12
	end //13
notprime:
		sub %x10, %x10, %x10 //14
		subi %x10, 1, %x10 //15
		end //15
