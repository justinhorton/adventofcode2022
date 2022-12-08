package xyz.justinhorton.aoc2022

data class Grid<T : Any>(private val data: List<List<T>>) {
    val height: Int
        get() = data.size

    val width: Int
        get() = data.first().size

    fun enumerateCoordinates(): Sequence<Pair<Int, Int>> {
        return sequence {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    yield(x to y)
                }
            }
        }
    }

    fun adjacentCoordinates(x: Int, y: Int, dir: Direction): Sequence<Pair<Int, Int>> {
        return sequence {
            when (dir) {
                Direction.Up -> {
                    for (x1 in (x - 1) downTo 0) {
                        yield(x1 to y)
                    }
                }
                Direction.Down -> {
                    for (x1 in (x + 1) until width) {
                        yield(x1 to y)
                    }
                }
                Direction.Left -> {
                    for (y1 in (y - 1) downTo 0) {
                        yield(x to y1)
                    }
                }
                Direction.Right -> {
                    for (y1 in (y + 1) until width) {
                        yield(x to y1)
                    }
                }
            }
        }
    }

    operator fun get(coords: Pair<Int, Int>): T {
        return coords.let { (x, y) -> data[y][x] }
    }

    fun getOrNull(x: Int, y: Int): T? {
        return if (x < 0 || y < 0 || x >= width || y >= height) {
            null
        } else {
            data[y][x]
        }
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
