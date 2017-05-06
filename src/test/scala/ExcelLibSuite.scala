/* vim: set ts=4 et sw=4 sts=4 fileencoding=utf-8: */
import scala.util.{Try, Success, Failure}
import org.scalatest._

import org.apache.poi.ss.usermodel._
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.nio.file.{Paths, Files}

import ExcelLib._
import ExcelTable._

class ExcelLibSuite extends FunSuite with BeforeAndAfterEach {
  
    val testSheet = "test"
    val testMessage = "Hello, world!!"

    override def beforeEach() {
    }
    
    override def afterEach() {
    }
    
    //////////////////////////////////////////////////////////////// 
    // Workbook Tests
    //
    test("Workbook.saveAs_") {
        val testFile = "Test_Workbook_saveAs.xlsx"

        val workbook = new XSSFWorkbook()
        workbook.saveAs_(testFile)
        assert(Files.exists(Paths.get(testFile)))

        Files.deleteIfExists(Paths.get(testFile))
    }

    test("Workbook.getSheet_") {
        val workbook = new XSSFWorkbook()

        workbook.getSheet_(testSheet) match {
            case Some(_) => assert(false)
            case None => assert(true)
        }

        workbook.createSheet_(testSheet) match {
            case Success(_) => assert(true)
            case Failure(e) => assert(false)
        }

        workbook.getSheet_(testSheet) match {
            case Some(_) => assert(true)
            case None => assert(false)
        }

        /* Can't create sheets of same name */
        workbook.createSheet_(testSheet) match {
            case Success(_) => assert(false)
            case Failure(e) => assert(true)
        }
    }

    test("Workbook.sheet_") {
        val workbook = new XSSFWorkbook()

        Try(workbook.sheet_(testSheet)) match {
            case Success(s) => assert(s.getSheetName() == testSheet)
            case Failure(e) => assert(false)
        }

        Try(workbook.sheet_(testSheet)) match {
            case Success(s) => assert(s.getSheetName() == testSheet)
            case Failure(e) => assert(false)
        }
        assert(workbook.sheet_(testSheet) == workbook.getSheet(testSheet))
    }

    test("Workbook.findCellStyle_") {
        val workbook = new XSSFWorkbook()
        val cellStyle = workbook.sheet_(testSheet).cell_(5, 5).getCellStyle
        val tuple = cellStyle.toTuple_

        assert(workbook.findCellStyle_(tuple).nonEmpty)

        assert(workbook.findCellStyle_(
            (tuple._1, tuple._2, tuple._3, BorderStyle.THIN, tuple._5,
            tuple._6, tuple._7, tuple._8, tuple._9, tuple._10)
            ).isEmpty)

        var newStyle = workbook.createCellStyle
        newStyle.cloneStyleFrom(cellStyle)
        newStyle.setBorderRight(BorderStyle.THIN)

        assert(workbook.findCellStyle_(
            (tuple._1, tuple._2, tuple._3, BorderStyle.THIN, tuple._5,
            tuple._6, tuple._7, tuple._8, tuple._9, tuple._10)
            ).nonEmpty)
    }


    //////////////////////////////////////////////////////////////// 
    // Sheet Tests
    //
    test("Sheet.getRow_,Sheet.row_") {
        val workbook = new XSSFWorkbook()
        val sheet = workbook.sheet_(testSheet)

        sheet.getRow_(0) match {
            case Some(r) => assert(false)
            case None => assert(true)
        }

        val row = sheet.row_(0)

        sheet.getRow_(0) match {
            case Some(r) => assert(true)
            case None => assert(false)
        }
    }

    test("Sheet.getCell_") {
        val sheet = (new XSSFWorkbook()).sheet_(testSheet)

        sheet.getCell_(0, 0) match {
            case Some(c) => assert(false)
            case None => assert(true)
        }

        val cell = sheet.cell_(0, 0)

        sheet.getCell_(0, 0) match {
            case Some(c) => assert(true)
            case None => assert(false)
        }
    }

    //////////////////////////////////////////////////////////////// 
    // Row Tests
    //
    test("Row.getCell_") {
        val row = (new XSSFWorkbook()).sheet_(testSheet).row_(100)
        
        row.getCell_(100) match {
            case Some(c) => assert(false)
            case None => assert(true)
        }

        val cell = row.cell_(100)

        row.getCell_(100) match {
            case Some(c) => assert(true)
            case None => assert(false)
        }
    }

    //////////////////////////////////////////////////////////////// 
    // Cell Tests
    //
    test("Cell.getValue_") {

        val workbook = new XSSFWorkbook()
        (
            for { 
                sheet <- workbook.createSheet_(testSheet)
                row = sheet.createRow(0)
                cell = row.createCell(0)
            } yield cell
        ) match { 
            case Success(cell) => {
                cell.setCellValue(testMessage)
            }
            case Failure(e) => println(e.getMessage())
        } 

        (
            for {
                sheet <- workbook.getSheet_(testSheet)
                row = sheet.getRow(0)
                cell = row.getCell(0)
            } yield cell
        ) match {
            case Some(cell) => {
                assert(cell.getValue_ == testMessage)
            }
            case None => assert(false)
        }
    }

    test("Cell.getUppper,Cell.upper_ etc") {
        val cell = (new XSSFWorkbook()).sheet_(testSheet).cell_(100, 100)

        assert(cell.getUpperCell_.isEmpty)
        assert(cell.getLowerCell_.isEmpty)
        assert(cell.getLeftCell_.isEmpty)
        assert(cell.getRightCell_.isEmpty)

        cell.upperCell_
        cell.lowerCell_
        cell.leftCell_
        cell.rightCell_

        assert(!cell.getUpperCell_.isEmpty)
        assert(!cell.getLowerCell_.isEmpty)
        assert(!cell.getLeftCell_.isEmpty)
        assert(!cell.getRightCell_.isEmpty)

    }

    test("Cell.getUpperStream_ etc") {
        val cell = (new XSSFWorkbook()).sheet_(testSheet).cell_(4, 4)

        assert(cell.getUpperStream_.take(10).toList.length == 5)
        assert(cell.getLowerStream_.take(10).toList.length == 10)
        assert(cell.getLeftStream_.take(10).toList.length == 5)
        assert(cell.getRightStream_.take(10).toList.length == 10)

        assert(cell.upperStream_.take(10).toList.length == 5)
        assert(cell.lowerStream_.take(10).toList.length == 10)
        assert(cell.leftStream_.take(10).toList.length == 5)
        assert(cell.rightStream_.take(10).toList.length == 10)

        assert(cell.getUpperStream_.take(10).toList.length == 5)
        assert(cell.getLowerStream_.take(10).toList.length == 10)
        assert(cell.getLeftStream_.take(10).toList.length == 5)
        assert(cell.getRightStream_.take(10).toList.length == 10)
    }
}