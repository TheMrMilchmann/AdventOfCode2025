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
    data class Vec3(val x: Long, val y: Long, val z: Long)

    fun distanceSq(p1: Vec3, p2: Vec3): Long {
        fun sq(a: Long, b: Long) = (a - b) * (a - b)
        return sq(p1.x, p2.x) + sq(p1.y, p2.y) + sq(p1.z, p2.z)
    }

    val points = readInput()
        .map { line ->
            val (x, y, z) = line.split(',').map(String::toLong)
            Vec3(x, y, z)
        }

    data class Edge(val a: Int, val b: Int, val distance: Long) {
        override fun hashCode(): Int = a * b
        override fun equals(other: Any?): Boolean = when {
            this === other -> true
            other !is Edge -> false
            else -> (a == other.a && b == other.b) || (a == other.b && b == other.a)
        }
    }

    fun part1(): Int {
        val edges = points.withIndex()
            .flatMap { (iA, a) -> points.withIndex().mapNotNull { (iB, b) -> if (a != b) Edge(iA, iB, distanceSq(a, b)) else null } }
            .sortedBy { it.distance }
            .distinct()

        val parentIndices = IntArray(points.size) { it }
        val ranks = IntArray(points.size)

        tailrec fun find(i: Int): Int {
            if (parentIndices[i] == i) return i
            return find(parentIndices[i])
        }

        fun union(p: Int, q: Int): Boolean {
            val pRoot = find(p)
            val qRoot = find(q)
            if (qRoot == pRoot) return false

            if (ranks[qRoot] < ranks[pRoot]) {
                parentIndices[pRoot] = qRoot
            } else if (ranks[qRoot] > ranks[pRoot]) {
                parentIndices[qRoot] = pRoot
            } else {
                parentIndices[qRoot] = pRoot
                ranks[pRoot]++
            }

            return true
        }

        for (edge in edges.take(1000)) {
            union(edge.a, edge.b)
        }

        return parentIndices.indices.groupBy { find(it) }
            .values
            .sortedByDescending(List<*>::size)
            .take(3)
            .map(List<*>::size)
            .reduce(Int::times)
    }


    fun part2(): Long {
        val edges = points.withIndex()
            .flatMap { (iA, a) -> points.withIndex().mapNotNull { (iB, b) -> if (a != b) Edge(iA, iB, distanceSq(a, b)) else null } }
            .sortedBy { it.distance }
            .distinct()

        val parentIndices = IntArray(points.size) { it }
        val ranks = IntArray(points.size)

        tailrec fun find(i: Int): Int {
            if (parentIndices[i] == i) return i
            return find(parentIndices[i])
        }

        fun union(p: Int, q: Int): Boolean {
            val pRoot = find(p)
            val qRoot = find(q)
            if (qRoot == pRoot) return false

            if (ranks[qRoot] < ranks[pRoot]) {
                parentIndices[pRoot] = qRoot
            } else if (ranks[qRoot] > ranks[pRoot]) {
                parentIndices[qRoot] = pRoot
            } else {
                parentIndices[qRoot] = pRoot
                ranks[pRoot]++
            }

            return true
        }

        for (edge in edges) {
            if (union(edge.a, edge.b)) {
                if (parentIndices.groupBy { find(it) }.values.size == 1) {
                    return points[edge.a].x * points[edge.b].x
                }
            }
        }

        error("")
    }


    println("Part 1: ${part1()}")
    println("Part 2: ${part2()}")
}
