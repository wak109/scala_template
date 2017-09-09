/* vim: set ts=2 et sw=2 sts=2 fileencoding=utf-8: */
package exceler.test

import scala.util.{Try, Success, Failure}
import org.scalatest._
import org.scalatest.junit.JUnitRunner

import org.junit.runner.RunWith

import org.apache.poi.ss.usermodel._
import org.apache.poi.xssf.usermodel.XSSFWorkbook


import java.io.File
import java.nio.file.{Paths, Files}

import exceler.xls._
import exceler.excel._
import excellib.ImplicitConversions._


@RunWith(classOf[JUnitRunner])
class XlsTableTest
  extends FunSuite with ExcelLibResource
  with ExcelTableSheetFunction {

  import XlsTable._ 

  test("XlsTable.getTopLeftList") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")

    assert(getTopLeftList(sheet,2,1,17,8) == List((2,1), (3,1), (3,3), (3,5), (3,7), (5,3), (5,4), (5,5), (5,6), (7,1), (7,2), (7,3), (7,4), (7,5), (7,6), (7,7), (7,8), (9,2), (9,3), (9,4), (9,6), (9,7), (9,8), (11,2), (11,3), (11,6), (11,7), (11,8), (13,2), (13,3), (13,4), (13,5), (13,6), (13,7), (13,8), (15,1), (15,2), (15,3), (15,4), (15,5), (15,6), (15,7), (15,8), (17,2), (17,3), (17,4), (17,5), (17,6), (17,7), (17,8))) 
  }

  test("getXlsHeight") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")

    assert(getXlsHeight(sheet, 2, 1, 19) == 1)
    assert(getXlsHeight(sheet, 3, 1, 19) == 4)
    assert(getXlsHeight(sheet, 9, 3, 19) == 2)
    assert(getXlsHeight(sheet, 9, 4, 19) == 4)
    assert(getXlsHeight(sheet, 17, 4, 19) == 2)
  }

  test("getXlsWidth") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")

    assert(getXlsWidth(sheet, 2, 1, 9) == 8)
    assert(getXlsWidth(sheet, 17, 8, 9) == 1) 
  }

  test("getXlsRowLineList") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")

    assert(getXlsRowLineList(getTopLeftList(sheet,2,1,17,8)) == 
        List(2, 3, 5, 7, 9, 11, 13, 15, 17))
  }

  test("getXlsColumnLineList") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")

    assert(getXlsColumnLineList(getTopLeftList(sheet,2,1,17,8)) == 
        List(1,2,3,4,5,6,7,8))
  }

  test("getXmlRowSpan,getXmlColumnSpan") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")
    val rowList = getXlsRowLineList(getTopLeftList(sheet,2,1,17,8))
    val columnList = getXlsColumnLineList(getTopLeftList(sheet,2,1,17,8))

    assert(getXmlRowSpan(2,1,rowList) == (0,1))
    assert(getXmlRowSpan(3,4,rowList) == (1,2))

    assert(getXmlColumnSpan(1,4,columnList) == (0,4))
  }

  test("getExcelTableCellList") {
    val file = new File(getClass.getClassLoader.getResource(testWorkbook1).toURI)
    val workbook = WorkbookFactory.create(file)
    val sheet = workbook.getSheet("table2")

    println(XlsTable(sheet,2,1,18,8))
  }
}