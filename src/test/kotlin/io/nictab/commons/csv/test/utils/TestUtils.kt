package io.nictab.commons.csv.test.utils

import java.io.BufferedReader
import java.io.FileReader

object TestUtils {

    fun getCSVContentFromTestResources(fileName: String): String {
        val csvFile = BufferedReader(FileReader("./src/test/resources/csv/$fileName.csv"))
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
}