package com.jbi.adventofcode.advent2020.day21

import com.jbi.adventofcode.advent2020.DailySolution2020
import java.io.BufferedReader
import java.lang.StringBuilder

/**
 * @author Julien BACZYNSKI on 12/21/20.
 */
object Day21 : DailySolution2020() {

    private lateinit var menu: MutableList<MenuLine>
    private lateinit var allergens: MutableMap<String, MutableSet<String>>
    private lateinit var ingredients: MutableSet<String>
    override val expectedResultP1: Any
        get() = 5
    override val expectedResultP2: Any
        get() = "mxmxvkd,sqjhc,fvjkl"


    override fun part1(reader: BufferedReader): Any {
        val treated: MutableList<String> = mutableListOf()
        //now we simply check our candidates, if any rule has only one candidate we remove it from all other
        while (treated.size != allergens.size) {
            allergens.forEach { allergen ->
                if (!treated.contains(allergen.key) && allergen.value.size == 1) {
                    allergens.forEach { allergen2 ->
                        if (allergen != allergen2)
                            allergen2.value.remove(allergen.value.elementAt(0))
                    }
                    treated.add(allergen.key)
                }
            }
        }
        var safeIngredients = mutableListOf<String>()
        safeIngredients.addAll(ingredients)
        safeIngredients.removeAll(allergens.map { it.value.elementAt(0) })
        var count = 0
        menu.forEach { menuLine ->
            count += safeIngredients.intersect(menuLine.ingredients).size
        }
        return count
    }

    override fun part2(reader: BufferedReader): Any {
        val allergensSorted = mutableListOf<String>().apply {
            addAll(allergens.keys)
            sort()
        }
        var sb  = StringBuilder()
        allergensSorted.forEach{
            sb.append(",${allergens[it]!!.elementAt(0)}")
        }

        return sb.removePrefix(",").toString()
    }

    private fun readData(reader: BufferedReader) {
        menu = mutableListOf()
        allergens = mutableMapOf()
        ingredients = mutableSetOf()
        var ingredientsList: MutableSet<String>
        var allergensList: MutableSet<String>
        reader.useLines { sequence ->
            sequence.forEach { line ->
                ingredientsList = mutableSetOf()
                allergensList = mutableSetOf()
                val splits = line.split(" (contains ")
                ingredientsList.addAll(splits[0].split(' '))
                allergensList.addAll(splits[1].removeSuffix(")").split(", "))
                menu.add(MenuLine(ingredientsList, allergensList))
                ingredients.addAll(ingredientsList)
                allergensList.forEach {
                    if (allergens.containsKey(it)) {
                        allergens[it] = ingredientsList.intersect(allergens[it]!!).toMutableSet()
                    } else {
                        allergens[it] = ingredientsList
                    }
                }
            }
        }
    }

    override fun prerunSample(reader: BufferedReader) {
        readData(reader)
    }

    override fun prerunInput(reader: BufferedReader) {
        readData(reader)
    }

    data class MenuLine(val ingredients: Set<String>, val allergens: Set<String>)
}
