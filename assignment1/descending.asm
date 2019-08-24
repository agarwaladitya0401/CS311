	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
    load %x0, $n, %x7
    addi %x0, 1, %x10
    beq %x7, %x10, endp
    add %x0, %x0, %x9
mainstep:
    addi %x9, 1, %x9
    addi %x0, 1, %x8
    subi %x0, 1, %x3
    addi %x0, 0, %x4
loop:
    addi %x8, 1, %x8
    addi %x3, 1, %x3
    addi %x4, 1, %x4
    load %x3, $a, %x5
    load %x4, $a, %x6
    blt %x5, %x6, swap
    beq %x7, %x8, endl
    jmp loop
swap:
    store %x5, $a, %x4
    store %x6, $a, %x3
    beq %x7, %x8, endl
    jmp loop
endl:
    blt %x9, %x7, mainstep
endp:
    end
