package dev.skrilltrax.chip8.cpu

class Stack {

    private val stack = mutableListOf<Int>()

    fun push(value: Int) {
        stack.add(value)
    }

    fun pop(): Int {
        return stack.removeAt(stack.size - 1)
    }

    fun peek(): Int {
        return stack[stack.size - 1]
    }

    fun isEmpty(): Boolean {
        return stack.isEmpty()
    }
}