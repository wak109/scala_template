/* vim: set ts=4 et sw=4 sts=4 fileencoding=utf-8: */
import scala.util.{Try, Success, Failure}
import org.scalatest._

import org.apache.poi.ss.usermodel._
import org.apache.poi.xssf.usermodel.XSSFWorkbook


import java.io.File
import java.nio.file.{Paths, Files}

import ExcelLib.ImplicitConversions._


class ExcelTableSuite2 extends FunSuite with ExcelLibResource {
  
    def createStringEqual(s:String) = (x:String) => x == s

    test("new TableQueryImpl") {
        val workbook = new XSSFWorkbook
        val sheet = workbook.sheet("test")

        val table:TableQueryImpl = new RectangleImpl(sheet, 10, 10, 20, 20)

        assert(table.rowList.length == 1)
        assert(table.colList.length == 1)
    }

    test("TableCellImpl (1x1)") {
        val workbook = new XSSFWorkbook
        val sheet = workbook.sheet("test")

        val cell = new TableCellImpl(new RectangleImpl(sheet, 5, 5, 5, 5))

        assert(cell.getSingleValue.isEmpty)
        sheet.cell(5, 5).setCellValue("foo")
        assert(cell.getSingleValue.isDefined)

        assert(cell.value == "foo")
    }

    test("TableCellImpl (5x5)") {
        val workbook = new XSSFWorkbook
        val sheet = workbook.sheet("test")

        val cell = new TableCellImpl(new RectangleImpl(sheet, 5, 5, 9, 9))

        assert(cell.getSingleValue.isEmpty)
        sheet.cell(7, 7).setCellValue("foo")
        assert(cell.getSingleValue.isDefined)

        assert(cell.value == "foo")
    }

    test("TableQueryImpl.queryRow") {
        val workbook = new XSSFWorkbook
        val sheet = workbook.sheet("test")
        val table = new TableQueryImpl(
                new RectangleImpl(sheet, 10, 10, 20, 20))

        table.drawOuterBorder(BorderStyle.THIN)
        table.drawHorizontalLine(2, BorderStyle.THIN)
        table.drawHorizontalLine(5, BorderStyle.THIN)
        table.drawHorizontalLine(7, BorderStyle.THIN)

        table.drawVerticalLine(2, BorderStyle.THIN)
        table.drawVerticalLine(5, BorderStyle.THIN)
        table.drawVerticalLine(7, BorderStyle.THIN)

        sheet.cell(10,10).setCellValue("row1")
        sheet.cell(14,10).setCellValue("row2")
        sheet.cell(16,20).setCellValue("foo")
        sheet.cell(17,10).setCellValue("row3")
        sheet.cell(17,20).setCellValue("foo")

        assert(table.queryRow(_ == "row1").apply(0).topRow == 10)
        assert(table.queryRow(
            List((x:String) => x == "row1")).apply(0).topRow == 10)
        assert(table.queryRow(_ == "row2").apply(0).topRow == 12)
        assert(table.queryRow(_ == "row3").apply(0).topRow == 17)
        assert(table.queryRow(_ == "foo").isEmpty)

        sheet.cell(10,14).setCellValue("col2")
        sheet.cell(20,16).setCellValue("bar")
        sheet.cell(10,17).setCellValue("col3")
        sheet.cell(20,17).setCellValue("bar")

        assert(table.queryColumn(_ == "col2")(0).leftCol == 12)
        assert(table.queryColumn(
            List((x:String) => x == "col2")).apply(0).leftCol == 12)
        assert(table.queryColumn(_ == "col3")(0).leftCol == 17)
        assert(table.queryColumn(_ == "bar").isEmpty)

        sheet.cell(17,17).setCellValue("hello")
    }
}