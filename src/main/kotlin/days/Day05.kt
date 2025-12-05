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
    val (freshIngredients, availableIngredients) = readInput()
        .let { lines ->
            val freshIngredients = lines.takeWhile(String::isNotBlank)
                .map { line ->
                    val (f, l) = line.split('-')
                    f.toLong()..l.toLong()
                }
            val availableIngredients = lines.drop(freshIngredients.size + 1)
                .takeWhile(String::isNotBlank)
                .map(String::toLong)

            freshIngredients to availableIngredients
        }

    fun part2(): Long {
        val ranges = freshIngredients
            .sortedBy(LongRange::first)

        val res = mutableListOf<LongRange>()

        var i = 0
        while (i < ranges.size) {
            var first = ranges[i].first
            var last = ranges[i].last
            i++

            while (i < ranges.size && ranges[i].first <= (last + 1)) {
                first = minOf(first, ranges[i].first)
                last = maxOf(ranges[i].last, last)
                i++
            }

            res += first..last
        }

        return res.sumOf { it.last - it.first + 1 }
    }

    println("Part 1: ${availableIngredients.count { ingredient -> freshIngredients.any { range -> ingredient in range } }}")
    println("Part 2: ${part2()}")
}
