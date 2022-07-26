﻿<h1 align="center">kotlin-commons-csv</h1>

Kotlin CSV Reader/Writer.

# Design goals

### 1. Simple interface

* Simple usage
* Easy to setup
* Annotations-Based design

### 2. Automatic handling of mapping to and from objects

* Handling CSV files and manipulating data from CSV can be time consuming on large CSV Files,
  Kotlin-commons-csv focused on reducing complexity of manipulating such files.

# Usage

## Download

### Gradle

```txt
Not available for download yet. In development.
```

### Maven

```txt
Not available for download yet. In development.
```

## Examples

### CSV Read examples

#### Simple case

Read content from CSV File String with headers into a Custom CSV Element

```csv
  id,name,age
  1,Ben,22
  2,John,31
  3,Max,18
    ...
```

```kotlin
// Custom CSV Element
@CSVElement
class Person {

  // Fields
  @CSVField(name = "id")
  lateinit var id: String

  @CSVField(name = "name")
  lateinit var name: String

  @CSVField(name = "age")
  lateinit var age: String
}

// read from `String` into Person Class
val csvContent = csv.toString()

val csvFile = CSVFile(csvContent, separator = ",")
val csvDataList = csvFile.read(Person::class)

// Result : 
// [
//  Person(id=1, name=Ben, age=22), entry 1
//  Person(id=2, name=John, age=31)  entry 2
//  ...
// ]
```

Note : It is recommended to override equals, toString, hashCode in custom CSVElement classes

Read content from CSV File String with headers into a list of maps

```kotlin
// read from `String`
val csvContent = csv.toString()

val csvFile = CSVFile(csvContent, separator = ",")
val csvDataList = csvFile.read()

// Result : 
// [
//  {header1=value1, header2=value2, ...}, entry 1
//  {header1=value1, header2=value2, ...}  entry 2
//  ...
// ]
```

#### Customize

When creating custom CSVElement classes and CSVFields, 
you can apply transformation onto CSV Fields content with @CSVTransformers :
```kotlin
  // Custom fields transformers
  @CSVTransformer(fields = ["id", "age"])
  fun transformToInt(value: String) = value.toInt()

  @CSVTransformer(fields = ["name"])
  fun transformToName(value: String) = Name(value)
```

Note : Kotlin doesn't allow lateinit on primitive types. 
Here you can use Kotlin Delegates to workaround the restriction.

```kotlin
// CSVElement with @CSVTransformers functions.
@CSVElement
class Person {

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
    return (other is Person)
            && id == other.id
            && name == other.name
            && age == other.age
  }
}
```

## Authors
Project created and developed by [nictab](https://github.com/nictab).

# Miscellaneous

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

Copyright © 2022 [nictab](https://github.com/nictab).

***
_This project is inspired ❤️ by [kotlin-csv](https://github.com/doyaaaaaken/kotlin-csv)_
