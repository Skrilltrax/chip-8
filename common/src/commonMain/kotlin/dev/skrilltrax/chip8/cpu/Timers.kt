package dev.skrilltrax.chip8.cpu

class Timers {
    private var delayTimer = 0
    private var soundTimer = 0

    fun tick() {
        if (delayTimer > 0) {
            delayTimer--
        }
        if (soundTimer > 0) {
            soundTimer--
        }
    }
}