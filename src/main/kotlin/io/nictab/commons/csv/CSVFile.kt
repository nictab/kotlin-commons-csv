package io.nictab.commons.csv

import io.nictab.commons.csv.annotations.CSVElement
import io.nictab.commons.csv.annotations.CSVTransformer
import io.nictab.commons.csv.exceptions.CSVExceptions
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.*


class CSVFile(private val csvData: String, private val separator: String) {

    companion object {
        private val LINE_SEPARATOR = System.lineSeparator()
    }

    fun read(): List<Map<String, String>> {
        val lines = csvData.split(LINE_SEPARATOR)
        val headers = lines[0].split(separator)
        val content = lines.toMutableList()
        content.removeFirst()
        content.removeIf { it.isEmpty() }

        val dataList = mutableListOf<Map<String, String>>()

        content.forEach { line ->
            val lineContent = line.split(separator)
            val lineMap = mutableMapOf<String, String>()
            lineContent.forEachIndexed { index, value ->
                lineMap[headers[index]] = value
            }
            dataList.add(lineMap)
        }

        return dataList
    }

    fun <T : Any> read(csvElement: KClass<T>): List<T> {
        if (!csvElement.hasAnnotation<CSVElement>()) {
            CSVExceptions.throwInvalidCSVElementClassException(csvElement.simpleName)
        }

        val csvContent = this.read()
        val transformers = getTransformersMap(csvElement)
        val dataList = mutableListOf<T>()

        csvContent.forEach { lineMap ->
            val dataInstance = csvElement.createInstance()
            lineMap.forEach { (fieldName, value) ->
                val kProperty = dataInstance::class.declaredMemberProperties.find { it.name == fieldName } as KMutableProperty1?
                if (kProperty != null) {
                    try {
                        kProperty.setter.call(dataInstance, applyTransformer(transformers, fieldName, value, dataInstance))
                    } catch (e: IllegalArgumentException) {
                        CSVExceptions.throwInvalidReceiverTypeException(fieldName)
                    }
                } else {
                    print("$fieldName has not been defined in the specified CSVElement class : ${csvElement.simpleName}\n")
                }
            }
            dataList.add(dataInstance)
        }

        return dataList
    }

    private fun <T : Any> applyTransformer(transformers: Map<String, KCallable<*>>, fieldName: String, fieldValue: String, dataInstance: T): Any {
        val defaultTransformer = {value: String -> value}

        return try {
            transformers[fieldName]?.call(dataInstance, fieldValue) ?: defaultTransformer(fieldValue)
        } catch (e: IllegalArgumentException) {
            CSVExceptions.throwInvalidTransformerException(fieldName)
        }
    }

    private fun <T : Any> getTransformersMap(csvElement: KClass<T>): Map<String, KCallable<*>> {
        val transformersList = csvElement.members.filter { member -> member.hasAnnotation<CSVTransformer>() }
        val transformersMap = mutableMapOf<String, KCallable<*>>()
        transformersList.forEach { transformer ->
            transformer.findAnnotation<CSVTransformer>()?.fields?.forEach { transformersMap[it] = transformer }
        }

        return transformersMap
    }
}