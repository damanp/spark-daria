package com.github.mrpowers.spark.daria.sql

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructField

object DataFrameExt {

  implicit class DataFrameMethods(df: DataFrame) {

    def printSchemaInCodeFormat(): Unit = {
      val fields = df.schema.map { (f: StructField) =>
        s"""StructField("${f.name}", ${f.dataType}, ${f.nullable})"""
      }

      println("StructType(")
      println("  List(")
      println("    " + fields.mkString(",\n    "))
      println("  )")
      println(")")
    }

    def composeTransforms(
      transforms: List[(DataFrame => DataFrame)]
    ): DataFrame = {
      transforms.foldLeft(df) { (memoDF, t) =>
        memoDF.transform(t)
      }
    }

    def containsColumn(colName: String): Boolean = {
      df.schema.fieldNames.contains(colName)
    }

  }

}

