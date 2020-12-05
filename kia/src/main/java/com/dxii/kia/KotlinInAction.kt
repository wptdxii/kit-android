package com.dxii.kia

data class Person(val name: String, var age: Int)

fun main() {
    val persons = arrayListOf(
        Person("Tom", 18),
        Person("Jack", 20)
    )

    var max: Person? = null
    max = persons.maxByOrNull({ p: Person -> p.age })
    max = persons.maxByOrNull() { p: Person -> p.age }
    max = persons.maxByOrNull { p: Person -> p.age }
    max = persons.maxByOrNull { p -> p.age }
    max = persons.maxByOrNull { it.age }
}

