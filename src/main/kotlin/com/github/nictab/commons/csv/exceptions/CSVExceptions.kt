package com.github.nictab.commons.csv.exceptions

object CSVExceptions {

    fun throwInvalidCSVElementClassException(className: String?) {
        throw InvalidCSVElementClassException("Class must be a valid CSVElement. Missing @CSVElement on the specified output class type $className")
    }

    fun throwInvalidReceiverTypeException(fieldName: String) {
        throw InvalidReceiverTypeException("Invalid output type generated. Please use @CSVTransformers or verify output type of applied transformer on field $fieldName")
    }

    fun throwInvalidTransformerException(fieldName: String) {
        throw InvalidTransformerException("Transformer method must have 1 parameter of type String for field: $fieldName")
    }
}