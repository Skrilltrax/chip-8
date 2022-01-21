package dev.skrilltrax.chip8.cpu

enum class Opcode(val code: String) {
    SYS("0x0NNN"),
    CLS("0x00E0"),
    RET("0x00EE"),
    JP("0x1NNN"),
    CALL("0x2NNN"),
    SE_VX("0x3XKK"),
    SNE_VX("0x4XKK"),
    SE_VX_VY("0x5XY0"),
    LD_VX("0x6XKK"),
    ADD_VX("0x7XKK"),
    LD_VX_VY("0x8XY0"),
    OR_VX_VY("0x8XY1"),
    AND_VX_VY("0x8XY2"),
    XOR_VX_VY("0x8XY3"),
    ADD_VX_VY("0x8XY4"),
    SUB_VX_VY("0x8XY5"),
    SHR_VX_VY("0x8XY6"),
    SUBN_VX_VY("0x8XY7"),
    SHL_VX_VY("0x8XYE"),
    SNE_VX_VY("0x9XY0"),
    LD_I("0xANNN"),
    JP_V0("0xBNNN"),
    RND_VX("0xCXKK"),
    DRW_VX_VY("0xDXYN"),
    SKP_VX("0xEX9E"),
    SKNP_VX("0xEXA1"),
    LD_VX_DT("0xFX07"),
    LD_VX_K("0xFX0A"),
    LD_DT_VX("0xFX15"),
    LD_ST_VX("0xFX18"),
    ADD_I_VX("0xFX1E"),
    LD_F_VX("0xFX29"),
    LD_B_VX("0xFX33"),
    LD_I_VX("0xFX55"),
    LD_VX_I("0xFX65"),
    ;

    companion object {

        fun getOpcode(opcode: Int): Opcode {
            return when(opcode and 0xF000) {
              0x0000 -> {
                when(opcode) {
                  0x00E0 -> CLS
                  0x00EE -> RET
                  else -> SYS
                }
              }
              0x00E0 -> CLS
              0x00EE -> RET
              0x1000 -> JP
              0x2000 -> CALL
              0x3000 -> SE_VX
              0x4000 -> SNE_VX
              0x5000 -> SE_VX_VY
              0x6000 -> LD_VX
              0x7000 -> ADD_VX
              0x8000 -> {
                when(opcode and 0x000F) {
                  0x0000 -> LD_VX_VY
                  0x0001 -> OR_VX_VY
                  0x0002 -> AND_VX_VY
                  0x0003 -> XOR_VX_VY
                  0x0004 -> ADD_VX_VY
                  0x0005 -> SUB_VX_VY
                  0x0006 -> SHR_VX_VY
                  0x0007 -> SUBN_VX_VY
                  0x000E -> SHL_VX_VY
                  else -> throw IllegalArgumentException("Unknown opcode: ${opcode.toString(16)}")
                }
              }
              0x9000 -> SNE_VX_VY
              0xA000 -> LD_I
              0xB000 -> JP_V0
              0xC000 -> RND_VX
              0xD000 -> DRW_VX_VY
              0xE000 -> {
                when(opcode and 0x00FF) {
                  0x009E -> SKP_VX
                  0x00A1 -> SKNP_VX
                  else -> throw IllegalArgumentException("Unknown opcode: ${opcode.toString(16)}")
                }
              }
              0xF000 -> {
                when(opcode and 0x00FF) {
                  0x0007 -> LD_VX_DT
                  0x000A -> LD_VX_K
                  0x0015 -> LD_DT_VX
                  0x0018 -> LD_ST_VX
                  0x001E -> ADD_I_VX
                  0x0029 -> LD_F_VX
                  0x0033 -> LD_B_VX
                  0x0055 -> LD_I_VX
                  0x0065 -> LD_VX_I
                  else -> throw IllegalArgumentException("Unknown opcode: ${opcode.toString(16)}")
                }
              }
              else -> throw IllegalArgumentException("Unknown opcode: ${opcode.toString(16)}")
            }
        }
    }
}