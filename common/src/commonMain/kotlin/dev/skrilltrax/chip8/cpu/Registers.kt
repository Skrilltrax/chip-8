package dev.skrilltrax.chip8.cpu

import dev.skrilltrax.chip8.cpu.CPU.Companion.PROGRAM_START_ADDRESS

@OptIn(ExperimentalUnsignedTypes::class)
class Registers {
    var v = IntArray(16) { 0 } // 16 8-bit registers
    var i = 0 // Index register
    var pc = PROGRAM_START_ADDRESS // Program counter
}