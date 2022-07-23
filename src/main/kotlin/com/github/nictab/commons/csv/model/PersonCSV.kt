package com.github.nictab.commons.csv.model

import com.github.nictab.commons.csv.annotations.CSVElement
import com.github.nictab.commons.csv.annotations.CSVField
import com.github.nictab.commons.csv.annotations.CSVTransformer
import kotlin.properties.Delegates

@CSVElement
class PersonCSV {

    // Fields
    @CSVField(name = "id")
    var id by Delegates.notNull<Int>()

    @CSVField(name = "name")
    lateinit var name: Name

    @CSVField(name = "age")
    var age by Delegates.notNull<Int>()

    // Custom fields transformers
    @CSVTransformer(fields = ["id", "age"])
    fun transformToInt(value: String) = value.toInt()

    @CSVTransformer(fields = ["name"])
    fun transformToName(value: String) = Name(value)

    override fun toString(): String {
        return "Person(id=${this.id}, name=${this.name}, age=${this.age})"
    }

    override fun equals(other: Any?): Boolean {
        return (other is PersonCSV)
                && id == other.id
                && name == other.name
                && age == other.age
    }
}