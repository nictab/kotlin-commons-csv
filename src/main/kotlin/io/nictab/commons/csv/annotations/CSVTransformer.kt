package io.nictab.commons.csv.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class CSVTransformer(val fields: Array<String>)
