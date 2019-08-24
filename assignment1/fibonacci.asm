	.data
n:
	100
	.text
main:
    load %x0, $n, %x3
    addi %x0, 0, %x4
    addi %x0, 1, %x5
    addi %x0, 2, %x8
    addi %x0, 65535, %x7
    store %x4, 0, %x7
    subi %x7, 1, %x7
    beq %x3, %x5, endp
    store %x5, 0, %x7
    subi %x7, 1, %x7
    beq %x3, %x8, endp
loop:
    addi %x8, 1, %x8 
    add %x4, %x5, %x6
    store %x6, 0, %x7
    subi %x7, 1, %x7
    beq %x3, %x8, endp
    add %x5, %x0, %x4
    add %x6, %x0, %x5
    jmp loop
endp:
    end