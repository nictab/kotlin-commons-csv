package com.github.nictab.commons.csv

import com.github.nictab.commons.csv.model.PersonCSV
import java.io.BufferedReader
import java.io.FileReader


fun main(vararg args: String) {
    val csvContent = getCSVContentFromResources("persons")

    val csvFile = CSVFile(csvContent, ",")
    val csvDataList = csvFile.read(PersonCSV::class)

    csvDataList.forEach { person ->
        print(person.toString() + "\n")
    }
}

private fun getCSVContentFromResources(fileName: String): String {
    val csvFile = BufferedReader(FileReader("./src/main/resources/csv/$fileName.csv"))
    val csvContent: String
    csvFile.use { csv ->
        val sb = StringBuilder()
        var line = csv.readLine()

        while (line != null) {
            sb.append(line)
            sb.append(System.lineSeparator())
            line = csv.readLine()
        }
        csvContent = sb.toString()
    }

    return csvContent
}