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
import java.util.concurrent.ConcurrentHashMap

fun main() {
    val grid = readInput()
        .map(String::toList)

    fun part1(): Int {
        var beams = setOf(grid.first().indexOf('S'))

        var res = 0
        for (line in grid) {
            val newBeams = mutableSetOf<Int>()

            beams.forEach {
                if (line[it] == '^') {
                    newBeams.add(it - 1)
                    newBeams.add(it + 1)
                    res++
                } else {
                    newBeams.add(it)
                }
            }

            beams = newBeams
        }

        return res
    }

    fun part2(): Long {
        val cache = ConcurrentHashMap<Pair<Int, Int>, Long>()

        fun solve(x: Int, y: Int): Long {
            return cache.getOrPut(x to y) {
                when {
                    y == grid.lastIndex -> 1
                    grid[y][x] == '^' -> solve(x - 1, y + 1) + solve(x + 1, y + 1)
                    else -> solve(x, y + 1)
                }
            }
        }

        return solve(grid.first().indexOf('S'), 0)
    }

    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}
