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

fun main() {
    data class Vec2(val x: Long, val y: Long)

    val points = readInput()
        .map { line ->
            val (x, y) = line.split(',').map(String::toLong)
            Vec2(x, y)
        }

    println("Part 1: ${points.flatMap { a -> points.mapNotNull { b -> if (a != b) a to b else null } }.maxOf { (a, b) -> ((a.x - b.x).absoluteValue + 1) * ((a.y - b.y).absoluteValue + 1) }}")
}
