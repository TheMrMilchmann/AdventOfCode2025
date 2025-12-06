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
    val input = readInput()
        .let { lines ->
            val cxs = lines.maxBy { it.length }.indices
                .filter { c -> lines.all { line -> line.length <= c || line[c].isWhitespace() } } + Int.MAX_VALUE

            buildList {
                var start = 0

                for (cx in cxs) {
                    add(lines.map { line -> line.substring(start, minOf(line.length, cx)) })
                    start = cx
                }
            }
        }

    fun List<String>.solve(): Long {
        val multiply = '*' in last()
        val op: Long.(Long) -> Long = if (multiply) Long::times else Long::plus

        var acc = if (multiply) 1L else 0L
        for (r in 0 until (this.size - 1)) {
            acc = op(acc, this[r].trim().toLong())
        }

        return acc
    }

    println("Part 1: ${input.sumOf { it.solve() }}")
}
