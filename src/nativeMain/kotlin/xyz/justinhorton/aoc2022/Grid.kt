package xyz.justinhorton.aoc2022

data class Grid<T : Any>(private val data: List<List<T>>) {
    val height: Int
        get() = data.size

    val width: Int
        get() = data.first().size

    fun enumeratePoints(): Sequence<GPoint> {
        return sequence {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    yield(GPoint(x, y))
                }
            }
        }
    }

    fun adjacentPoints(p: GPoint): Sequence<GPoint> {
        val (x, y) = p
        return sequence {
            GPoint(x - 1, y).run { getOrNull(this)?.let { yield(this) } }
            GPoint(x + 1, y).run { getOrNull(this)?.let { yield(this) } }
            GPoint(x, y - 1).run { getOrNull(this)?.let { yield(this) } }
            GPoint(x, y + 1).run { getOrNull(this)?.let { yield(this) } }
        }
    }

    fun pointsInDirection(x: Int, y: Int, dir: Direction): Sequence<GPoint> {
        return sequence {
            when (dir) {
                Direction.Up -> {
                    for (x1 in (x - 1) downTo 0) {
                        yield(GPoint(x1, y))
                    }
                }
                Direction.Down -> {
                    for (x1 in (x + 1) until width) {
                        yield(GPoint(x1, y))
                    }
                }
                Direction.Left -> {
                    for (y1 in (y - 1) downTo 0) {
                        yield(GPoint(x, y1))
                    }
                }
                Direction.Right -> {
                    for (y1 in (y + 1) until width) {
                        yield(GPoint(x, y1))
                    }
                }
            }
        }
    }

    operator fun get(p: GPoint): T {
        return p.let { (x, y) -> data[y][x] }
    }

    operator fun get(xy: Pair<Int, Int>): T {
        return xy.let { (x, y) -> data[y][x] }
    }

    fun getOrNull(p: GPoint): T? {
        return if (p.x < 0 || p.y < 0 || p.x >= width || p.y >= height) {
            null
        } else {
            data[p.y][p.x]
        }
    }

    fun getOrNull(x: Int, y: Int): T? {
        return if (x < 0 || y < 0 || x >= width || y >= height) {
            null
        } else {
            data[y][x]
        }
    }

    override fun toString(): String {
        return data.joinToString(separator = "\n") { it.joinToString(separator = "") }
    }

    companion object {
        fun <T : Any> fromChars(gridStr: String, transform: (Char) -> T): Grid<T> {
            val data = mutableListOf<MutableList<T>>()

            for (l in gridStr.trim().lines()) {
                data.add(l.mapTo(mutableListOf()) { ch -> transform(ch) })
            }

            return Grid(data)
        }
    }
}

enum class Direction {
    Up,
    Down,
    Left,
    Right
}

data class GPoint(val x: Int, val y: Int) {
    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    override fun toString(): String = "($x, $y)"
}
