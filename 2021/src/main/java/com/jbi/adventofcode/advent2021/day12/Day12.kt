package com.jbi.adventofcode.advent2021.day12

import com.jbi.adventofcode.advent2021.DailySolution2021
import java.io.BufferedReader

/**
 * @author Julien BACZYNSKI on 23/11/2021.
 */
object Day12 : DailySolution2021() {
    private lateinit var nodes: MutableMap<String, Node>
    private lateinit var startNode: Node

    override val runWithInput: Boolean
        get() = true

    override val expectedResultP1: Int
        get() = 226
    override val expectedResultP2: Int
        get() = 3509

    override fun part1(reader: BufferedReader): Any {
        val pathes = mutableListOf<MutableList<Node>>()
        val path = mutableListOf(startNode)
        findPathes(path, pathes)
        return pathes.size
    }

    private fun findPathes(path: MutableList<Node>, pathes: MutableList<MutableList<Node>>) {
        val currentNode = path.last()
        if (currentNode.name == "end") {
            pathes.add(path)
        } else {
            for (node in currentNode.targets) {
                if (node.type == Nodetype.MAJOR || !path.contains(node)) {
                    val newPath = mutableListOf<Node>()
                    newPath.addAll(path)
                    newPath.add(node)
                    findPathes(newPath, pathes)
                }
            }
        }
    }


    override fun part2(reader: BufferedReader): Any {
        val pathes = mutableListOf<MutableList<Node>>()
        val path = mutableListOf(startNode)
        findPathes2(path, pathes)
        return pathes.size
    }

    private fun findPathes2(path: MutableList<Node>, pathes: MutableList<MutableList<Node>>) {
        val currentNode = path.last()

        if (currentNode.name == "end") {
            pathes.add(path)
            return
        }
        for (node in currentNode.targets) {
            if (node.name == "start")
                continue
            if (node.type == Nodetype.MAJOR) {
                val newPath = mutableListOf<Node>()
                newPath.addAll(path)
                newPath.add(node)
                findPathes2(newPath, pathes)
            } else {
                var max = 0
                path.filter { it.type == Nodetype.MINOR }.forEach { fNode ->
                    val count = path.count { fNode.name == it.name }
                    if(count > max)
                        max = count
                }
                if (!path.contains(node) || max <= 1) {
                    val newPath = mutableListOf<Node>()
                    newPath.addAll(path)
                    newPath.add(node)
                    findPathes2(newPath, pathes)
                }
            }
        }

    }


    override fun prerunInput(reader: BufferedReader) {
        buildData(reader)
    }

    override fun prerunSample(reader: BufferedReader) {
        buildData(reader)
    }

    private fun buildData(reader: BufferedReader) {
        nodes = mutableMapOf()
        startNode = Node("start")
        nodes[startNode.name] = startNode
        reader.useLines { sequence ->
            sequence.forEach { line ->
                val split = line.split('-')
                var nodeA = nodes[split[0]]
                if (nodeA == null) {
                    nodeA = Node(split[0])
                    nodes[split[0]] = nodeA
                }
                var nodeB = nodes[split[1]]
                if (nodeB == null) {
                    nodeB = Node(split[1])
                    nodes[split[1]] = nodeB
                }
                nodeA.addTarget(nodeB)
                nodeB.addTarget(nodeA)
            }
        }
    }

    class Node(val name: String) {
        val type: Nodetype =
            if (name[0].isLowerCase())
                Nodetype.MINOR
            else
                Nodetype.MAJOR
        val targets = mutableListOf<Node>()

        fun addTarget(node: Node) {
            targets.add(node)
        }
    }

    enum class Nodetype {
        MINOR,
        MAJOR
    }
}