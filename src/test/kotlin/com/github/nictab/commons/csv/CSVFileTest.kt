package com.github.nictab.commons.csv

import com.github.nictab.commons.csv.test.model.Name
import com.github.nictab.commons.csv.test.model.element.PersonCSV
import com.github.nictab.commons.csv.test.utils.TestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CSVFileTest {

    companion object {
        private const val PERSON_CSV_FILENAME = "persons"
        private const val PERSON_CSV_SEPARATOR = ","
        private val personCSVFileContent = TestUtils.getCSVContentFromTestResources(PERSON_CSV_FILENAME)
    }

    @Test
    fun `CSVFile read() returns valid listOfMap`() {
        val csvFile = CSVFile(personCSVFileContent, PERSON_CSV_SEPARATOR)

        val mapDataList = csvFile.read()

        assertEquals(mapOf("id" to "1", "name" to "nic", "age" to "22"), mapDataList[0])
        assertEquals(mapOf("id" to "2", "name" to "max", "age" to "28"), mapDataList[1])
        assertEquals(mapOf("id" to "3", "name" to "john", "age" to "21"), mapDataList[2])
        assertEquals(mapOf("id" to "4", "name" to "sarah", "age" to "24"), mapDataList[3])
        assertEquals(mapOf("id" to "5", "name" to "marc", "age" to "32"), mapDataList[4])
    }

    @Test
    fun `CSVFile read(PersonCSV KClass)`() {
        val csvFile = CSVFile(personCSVFileContent, PERSON_CSV_SEPARATOR)
        val person1 = PersonCSV()
        person1.id = 1
        person1.name = Name("nic")
        person1.age = 22
        val person2 = PersonCSV()
        person2.id = 2
        person2.name = Name("max")
        person2.age = 28
        val person3 = PersonCSV()
        person3.id = 3
        person3.name = Name("john")
        person3.age = 21
        val person4 = PersonCSV()
        person4.id = 4
        person4.name = Name("sarah")
        person4.age = 24
        val person5 = PersonCSV()
        person5.id = 5
        person5.name = Name("marc")
        person5.age = 32

        val dataList = csvFile.read(PersonCSV::class)

        assertEquals(person1, dataList[0])
        assertEquals(person2, dataList[1])
        assertEquals(person3, dataList[2])
        assertEquals(person4, dataList[3])
        assertEquals(person5, dataList[4])
    }
}