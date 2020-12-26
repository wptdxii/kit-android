package com.rocbillow.kia

class Person(private val firstName: String, private val lastName: String) {

  override fun equals(other: Any?): Boolean {
    val otherPerson = other as? Person ?: return false
    return otherPerson.firstName == firstName &&
        otherPerson.lastName == lastName
  }
}

