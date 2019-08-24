	.data
a:
	10
	.text
main:
    load %x0, $a, %x3
    add %x3, %x0, %x4
    add %x0, %x0, %x6
devide:
    divi %x4, 10, %x4
    beq %x4, %x0, endp
    add %x31, %x6, %x6
    muli %x6, 10, %x6
    jmp devide
endp:
    add %x31, %x6, %x6
    beq %x3, %x6, palindrome
notpalindrome:
    subi %x0, 1, %x10
    end
palindrome:
    addi %x0, 1, %x10
    end