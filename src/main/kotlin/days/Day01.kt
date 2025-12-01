/*
 * Copyright (c) 2025 Leon Linhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package days

import utils.*

fun main() {
    val instructions = readInput()
        .filter(String::isNotBlank)
        .map {
            Instruction(
                dir = if (it.first() == 'R') Direction.R else Direction.L,
                amount = it.drop(1).toInt()
            )
        }

    val positions = sequence {
        var c = 50L
        for (instruction in instructions) {
            val t = (instruction.amount / 100) + 1
            c += (100 * t)

            var amount = instruction.amount
            if (instruction.dir == Direction.L) amount *= -1

            c += amount
            c %= 100

            yield(c)
        }
    }

    println("Part 1: ${positions.count { it == 0L }}")
}

data class Instruction(val dir: Direction, val amount: Int)

enum class Direction { L, R }
