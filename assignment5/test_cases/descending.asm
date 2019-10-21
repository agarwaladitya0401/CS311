.data
a:
	40
	20
	50
	60
	80
	30
	10
	70
n:
	8
	.text
main:
	sub %x3, %x3, %x3 //9
	sub %x4, %x4, %x4 //10
	load %x0, $n, %x8 //11
outerloop:
	blt %x3, %x8, innerloop //12
	end //13
	addi %x3, 1, %x4 //14
innerloop:
	addi %x3, 1, %x4 //15
innerloopz:
	blt %x4, %x8, swap //16
	addi %3, 1, %x3 //17
	jmp outerloop //18
swap:
	load %x3, $a, %x5 //19
	load %x4, $a, %x6 //20
	blt %x5, %x6, exchange //21
	addi %x4, 1, %x4 //22
	jmp innerloopz //23
exchange:
	sub %x7, %x7, %x7 //24
	add %x0, %x5, %x7 //25
	store %x6, 0, %x3 //26
	store %x7, 0, %x4 //27
	addi %x4, 1, %x4 //28
	jmp innerloopz //29
