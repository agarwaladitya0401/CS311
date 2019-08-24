	.data
a:
	10
	.text
main:
    load %x0, $a, %x3
    divi %x3, 2, %x4
    addi %x0, 1, %x5
devide:
    addi %x5, 1, %x5
    bgt %x5, %x4, prime
    div %x3, %x5, %x6
    beq %x31, %x0, notprime
    jmp devide
notprime:
    subi %x0, 1, %x10
    end
prime:
    addi %x0, 1, %x10
    end