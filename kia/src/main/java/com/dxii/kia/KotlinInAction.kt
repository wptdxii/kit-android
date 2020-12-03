package com.dxii.kia

fun main() {
}

class CountingSet<T>(val innerSet: MutableSet<T> = HashSet()) : MutableSet<T> by innerSet {
    var objectAdded = 0

    override fun add(element: T): Boolean {
        objectAdded++
        return innerSet.add(element)
    }
}