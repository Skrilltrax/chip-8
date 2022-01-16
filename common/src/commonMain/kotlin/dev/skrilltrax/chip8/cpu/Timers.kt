package dev.skrilltrax.chip8.cpu

class Timers {
    var delayTimer = 0
    var soundTimer = 0

    fun tick() {
        if (delayTimer > 0) {
            delayTimer--
        }
        if (soundTimer > 0) {
            soundTimer--
        }
    }
}