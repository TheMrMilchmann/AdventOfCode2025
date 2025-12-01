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
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val offsets = readInput()
        .filter(String::isNotBlank)
        .map {
            var n = it.drop(1).toInt()
            if (it.first() == 'L') n *= -1
            n
        }

    data class Click(val pos: Int, val endOfInstruction: Boolean)

    val clicks = sequence {
        var click = Click(50, false)

        offsets.forEach { offset ->
            for (i in 1..offset.absoluteValue) {
                val p = (click.pos + 100 + offset.sign) % 100
                click = Click(p, endOfInstruction = i == offset.absoluteValue)
                yield(click)
            }
        }
    }

    println("Part 1: ${clicks.filter { it.endOfInstruction }.count { it.pos == 0 }}")
    println("Part 2: ${clicks.count { it.pos == 0 }}")
}
