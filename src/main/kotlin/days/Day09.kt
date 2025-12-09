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
import kotlin.math.*

fun main() {
    data class Vec2(val x: Long, val y: Long)
    data class Line(val a: Vec2, val b: Vec2)

    fun Line.intersects(a: Vec2, b: Vec2): Boolean {
        val (sX, sY) = this.a
        val (eX, eY) = this.b

        val rectMinX = minOf(a.x, b.x) + 1
        val rectMaxX = maxOf(a.x, b.x) - 1
        val rectMinY = minOf(a.y, b.y) + 1
        val rectMaxY = maxOf(a.y, b.y) - 1

        val dX = eX - sX
        val dY = eY - sY

        var tStart = 0.0
        var tEnd = 1.0

        fun clip(p: Double, q: Double): Boolean {
            if (p == 0.0) {
                if (q < 0) return false
                return true
            }

            val t = q / p

            if (p < 0) {
                tStart = maxOf(tStart, t)
            } else {
                tEnd = minOf(tEnd, t)
            }

            return tStart <= tEnd
        }

        if (!clip(-dX.toDouble(), -(rectMinX - sX).toDouble())) return false
        if (!clip( dX.toDouble(),  (rectMaxX - sX).toDouble())) return false
        if (!clip(-dY.toDouble(), -(rectMinY - sY).toDouble())) return false
        if (!clip( dY.toDouble(),  (rectMaxY - sY).toDouble())) return false

        return true
    }

    val points = readInput()
        .map { line ->
            val (x, y) = line.split(',').map(String::toLong)
            Vec2(x, y)
        }

    val lines = (points + points.first())
        .windowed(2)
        .map { (a, b) -> Line(a, b) }

    println("Part 1: ${points.flatMap { a -> points.mapNotNull { b -> if (a != b) a to b else null } }.maxOf { (a, b) -> ((a.x - b.x).absoluteValue + 1) * ((a.y - b.y).absoluteValue + 1) }}")
    println("Part 2: ${points.flatMap { a -> points.mapNotNull { b -> if (a != b) a to b else null } }.filter { (a, b) -> lines.none { line -> line.intersects(a, b) } }.maxOf { (a, b) -> ((a.x - b.x).absoluteValue + 1) * ((a.y - b.y).absoluteValue + 1) }}")
}
