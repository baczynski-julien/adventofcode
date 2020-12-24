package com.jbi.advent2020.day20

import com.jbi.advent2020.DailySolution
import java.io.BufferedReader
import kotlin.math.sqrt

/**
 * @author Julien BACZYNSKI on 12/20/20.
 */
object Day20 : DailySolution() {

    override val runWithInput: Boolean
        get() = true
    private lateinit var tiles: MutableList<Tile>

    /*
    Tile 1489:
    ##.#.#....
    ..##...#..
    .##..##...
    ..#...#...
    #####...#.
    #..#.#.#.#
    ...#.#.#..
    ##.#...##.
    ..##.##.##
    ###.##.#..
     */
    override val expectedResultP1: Any
        get() = 20899048083289L
    override val expectedResultP2: Any
        get() = 273

    override fun prerunSample(reader: BufferedReader) {
        tiles = readData(reader)
        var tile = Tile(
            12,
            arrayOf(
                IntArray(3) { i -> i + 1 },
                IntArray(3) { i -> i + 4 },
                IntArray(3) { i -> i + 7 })
        )
        log("----INITIAL----")
        tile.log()
        log("----ROTATED----")
        tile.rotateRight()
        tile.log()
        tile.rotateRight()
        tile.rotateRight()
        tile.rotateRight()
        log("----FLIP H----")
        tile.flipHorizontal()
        tile.log()
        tile.flipHorizontal()
        log("----FLIP V----")
        tile.flipVertical()
        tile.log()

    }

    override fun prerunInput(reader: BufferedReader) {
        tiles = readData(reader)
    }

    override fun part1(reader: BufferedReader): Any {
        return tiles.filter { currentTile ->
            val a = currentTile.getTop()
            val b = currentTile.getRight()
            val c = currentTile.getBottom()
            val d = currentTile.getLeft()

            tiles.forEach { otherTile ->
                if (otherTile.id != currentTile.id) {
                    val a2 = otherTile.getTop()
                    val a2R = a2.reversedArray()
                    val b2 = otherTile.getRight()
                    val b2R = b2.reversedArray()
                    val c2 = otherTile.getBottom()
                    val c2R = c2.reversedArray()
                    val d2 = otherTile.getLeft()
                    val d2R = d2.reversedArray()
                    if (
                        a.contentEquals(a2) || a.contentEquals(a2R)
                        || a.contentEquals(b2) || a.contentEquals(b2R)
                        || a.contentEquals(c2) || a.contentEquals(c2R)
                        || a.contentEquals(d2) || a.contentEquals(d2R)
                    ) {
                        currentTile.matchTop = otherTile.id
                    } else if (
                        b.contentEquals(a2) || b.contentEquals(a2R)
                        || b.contentEquals(b2) || b.contentEquals(b2R)
                        || b.contentEquals(c2) || b.contentEquals(c2R)
                        || b.contentEquals(d2) || b.contentEquals(d2R)
                    ) {
                        currentTile.matchRight = otherTile.id
                    } else if (
                        c.contentEquals(a2) || c.contentEquals(a2R)
                        || c.contentEquals(b2) || c.contentEquals(b2R)
                        || c.contentEquals(c2) || c.contentEquals(c2R)
                        || c.contentEquals(d2) || c.contentEquals(d2R)
                    ) {
                        currentTile.matchBottom = otherTile.id
                    } else if (
                        d.contentEquals(a2) || d.contentEquals(a2R)
                        || d.contentEquals(b2) || d.contentEquals(b2R)
                        || d.contentEquals(c2) || d.contentEquals(c2R)
                        || d.contentEquals(d2) || d.contentEquals(d2R)
                    ) {
                        currentTile.matchLeft = otherTile.id
                    }
                }
            }
            currentTile.matchCount == 2
        }.fold(1L) { acc, tile -> acc * tile.id }
    }

    override fun part2(reader: BufferedReader): Any {
        //actually rebuilding the image, taking the first 2 matching border tile, manipulating it so it's borders right and bottom are matching, and building image from there
        val sizeOfImage = sqrt(tiles.size.toDouble()).toInt()
        val image: Array<Array<Tile?>> = Array(sizeOfImage) { Array(sizeOfImage) { null } }
        val firstImage = tiles.first { it.matchCount == 2 }
        while (firstImage.matchRight == 0 || firstImage.matchBottom == 0) {
            firstImage.rotateRight()
        }
        // tiles.remove(firstImage)
        image[0][0] = firstImage
        for (i in image.indices)
            for (j in image.indices) {
                if (i != 0 || j != 0)
                    image[i][j] = findAndRotateTile(image, i, j)
            }

        var trueSizeOfTile = image[0][0]!!.data.size - 2 // removing border
        var trueImage: Array<IntArray> =
            Array(sizeOfImage * trueSizeOfTile) { IntArray(sizeOfImage * trueSizeOfTile) { 0 } }
        var targetI = 0
        var targetJ = 0
        for (i in trueImage.indices) {
            for (j in trueImage.indices) {
                targetI = (i / trueSizeOfTile)
                targetJ = (j / trueSizeOfTile)
                trueImage[i][j] =
                    image[targetI][targetJ]!!.data[1 + i % trueSizeOfTile][1 + j % trueSizeOfTile]
            }
        }
        var trueTile = Tile(0, trueImage)
        var countBeast = trueTile.countBeast()
        var i = 0
        while(countBeast == 0){
            if(i> 0 && i%4 ==0){
                trueTile.flipVertical()
            }else if (i>0 && i%8 == 0){
                trueTile.flipHorizontal()
            }else{
                trueTile.rotateRight()
            }
            countBeast = trueTile.countBeast()
            i++
        }
        trueTile.log()
        return trueTile.getSharpCount()
    }

    private fun findAndRotateTile(image: Array<Array<Tile?>>, i: Int, j: Int): Tile {
        //to find the proper image we need to find the one match the right side of the last one if first line and the top one if other line
        var lastTile: Tile
        var matchingTiles: List<Tile>
        var matchingTile: Tile
        if (i == 0) { //top line, checking right side of last tile
            lastTile = image[i][j - 1]!!
            //we must find the matchingTile and rotate it until our last tile id is in correct position (left)
            matchingTile = findTile(lastTile.matchRight)
            while (matchingTile.matchLeft != lastTile.id)
                matchingTile.rotateRight()
            if (!lastTile.getRight().contentEquals(matchingTile.getLeft()))
                matchingTile.flipHorizontal()
        } else {
            lastTile = image[i - 1][j]!!
            //we must find the matchingTile and rotate it until our last tile id is in correct position (top)
            matchingTile = findTile(lastTile.matchBottom)
            while (matchingTile.matchTop != lastTile.id)
                matchingTile.rotateRight()
            if (!lastTile.getBottom().contentEquals(matchingTile.getTop()))
                matchingTile.flipVertical()
        }
        tiles.remove(matchingTile)
        return matchingTile
    }

    private fun findTile(id: Int): Tile = tiles.first { it.id == id }

    private fun readData(reader: BufferedReader): MutableList<Tile> {
        val list = mutableListOf<Tile>()
        var current = Array(10) { IntArray(10) { 0 } }
        var id = 0
        var i = 0
        reader.useLines { sequence ->
            sequence.forEach { line ->
                if (line.isNotEmpty()) {
                    if (line.startsWith('T')) {
                        current = Array(10) { IntArray(10) { 0 } }
                        id = line.substring(5..8).toInt()
                        i = 0
                    } else {
                        line.forEachIndexed { index, c ->
                            current[i][index] = if (c == '.') 0 else 1
                        }
                        i++
                    }
                } else {
                    list.add(Tile(id, current))
                }
            }
        }
        list.add(Tile(id, current))
        return list
    }

    private data class Tile(val id: Int, var data: Array<IntArray>) {
        var matchTop = 0
        var matchRight = 0
        var matchBottom = 0
        var matchLeft = 0
        var matchCount: Int = 0
            get() = listOf(matchTop, matchRight, matchBottom, matchLeft).count { it != 0 }

        fun getLeft(): IntArray {
            return data.foldIndexed(IntArray(10) { 0 }) { index, acc, item ->
                acc[index] = item[0]; acc
            }
        }

        fun getRight(): IntArray {
            return data.foldIndexed(IntArray(10) { 0 }) { index, acc, item ->
                acc[index] = item[9]; acc
            }
        }

        fun getTop(): IntArray {
            return data[0]
        }

        fun getBottom(): IntArray {
            return data[9]
        }

        fun rotateRight() {
            transpose()
            revertRows()
            matchTop = matchLeft.also {
                matchLeft = matchBottom
                matchBottom = matchRight
                matchRight = matchTop
            }
        }

        fun flipVertical() {
            revertRows()
            matchLeft = matchRight.also { matchRight = matchLeft }
        }

        fun flipHorizontal() {
            revertCols()
            matchTop = matchBottom.also { matchBottom = matchTop }
        }

        private fun transpose() {
            val transpose = Array(data.size) { IntArray(data.size) }
            for (i in data.indices)
                for (j in data.indices)
                    transpose[i][j] = data[j][i]
            data = transpose
        }

        private fun revertRows() {
            for (element in data)
                element.reverse()
        }

        private fun revertCols() {
            for (i in 0 until data.size / 2) {
                data[i] = data[data.size - 1 - i].also { data[data.size - 1 - i] = data[i] }
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Tile
            if (id != other.id) return false
            return true
        }

        override fun hashCode(): Int {
            return id
        }

        fun log() {
            for (i in data.indices) {
                var sb = StringBuilder()
                for (j in data.indices)
                    sb.append(if (data[i][j] == 1) '#' else if (data[i][j] == 2) 'O' else '.')
                log(sb.toString())
            }
        }

        fun getSharpCount(): Int {
            var count = 0
            for (i in data.indices)
                for (j in data.indices)
                    if (data[i][j] == 1)
                        count++
            return count
        }

        /**
         * beast look like that
         *                  #
         *#    ##    ##    ###
         * #  #  #  #  #  #
         *
         */
        fun countBeast(): Int {
            var count = 0
            for (i in 0 until data.size - 2)
                for (j in 18 until data.size - 1) {
                    if (data[i][j] == 1
                        && data[i + 1][j - 18] == 1
                        && data[i + 1][j - 13] == 1
                        && data[i + 1][j - 12] == 1
                        && data[i + 1][j - 7] == 1
                        && data[i + 1][j - 6] == 1
                        && data[i + 1][j - 1] == 1
                        && data[i + 1][j] == 1
                        && data[i + 1][j + 1] == 1
                        && data[i + 2][j - 17] == 1
                        && data[i + 2][j - 14] == 1
                        && data[i + 2][j - 11] == 1
                        && data[i + 2][j - 8] == 1
                        && data[i + 2][j - 5] == 1
                        && data[i + 2][j - 2] == 1
                    ) {
                        count++
                        data[i][j] = 2
                        data[i + 1][j - 18] = 2
                        data[i + 1][j - 13] = 2
                        data[i + 1][j - 12] = 2
                        data[i + 1][j - 7] = 2
                        data[i + 1][j - 6] = 2
                        data[i + 1][j - 1] = 2
                        data[i + 1][j] = 2
                        data[i + 1][j + 1] = 2
                        data[i + 2][j - 17] = 2
                        data[i + 2][j - 14] = 2
                        data[i + 2][j - 11] = 2
                        data[i + 2][j - 8] = 2
                        data[i + 2][j - 5] = 2
                        data[i + 2][j - 2] = 2
                    }
                }
            return count
        }
    }
}
