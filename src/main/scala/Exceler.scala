/* vim: set ts=4 et sw=4 sts=4 fileencoding=utf-8: */
import scala.util.control.Exception._
import scala.util.{Try, Success, Failure}

import org.apache.poi.ss.usermodel._
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.io._
import java.nio.file._

object FileOp {

    def createDirectories(filename:String) : Path = {
        val dir = Paths.get(filename).getParent()
        if (dir != null)
            Files.createDirectories(dir)
        else
            null
    }

    def exists(filename:String) : Boolean = {
        Files.exists(Paths.get(filename))
    }
}

object ExcelImplicits {

        //
        // BorderTop
        // BorderBottom
        // BorderLeft
        // BorderRight
        // Foreground Color
        // Background Color
        // FillPatten
        // Horizontal Alignment
        // Vertical Alignment
        // Wrap Text
        //
    type CellStyleTuple = (
            BorderStyle, 
            BorderStyle,
            BorderStyle,
            BorderStyle,
            Color,
            Color,
            FillPatternType,
            HorizontalAlignment,
            VerticalAlignment,
            Boolean)

    implicit class WorkbookImplicit(workbook:Workbook)  {

        def saveAs_(filename:String): Unit =  {
            FileOp.createDirectories(filename)
            val out = new FileOutputStream(filename)
            workbook.write(out)
            out.close()
        }

        def getSheet_(name:String):Option[Sheet] = Option(workbook.getSheet(name))

        def createSheet_(name:String):Try[Sheet] = Try(workbook.createSheet(name)) 

        def sheet_(name:String):Sheet = {
            this.getSheet_(name) match {
                case Some(s) => s
                case None => this.createSheet_(name) match {
                    case Success(s) => s
                    case Failure(e) => throw e
                }
            }
        }

        def findCellStyle_(tuple:CellStyleTuple):Option[CellStyle] = (
            for {
                i <- (0 until workbook.getNumCellStyles).toStream
                val cellStyle = workbook.getCellStyleAt(i)
                if cellStyle.toTuple_ == tuple
                } yield cellStyle
        ).headOption
    }

    implicit class SheetImplicit(sheet:Sheet) {

        def getRow_(rownum:Int):Option[Row] = Option(sheet.getRow(rownum))

        def row_(rownum:Int):Row = {
            this.getRow_(rownum) match {
                case Some(r) => r
                case None => sheet.createRow(rownum)
            }
        }

        def getCell_(rownum:Int, colnum:Int):Option[Cell] = 
            sheet.getRow_(rownum).flatMap(_.getCell_(colnum))

        def cell_(rownum:Int, colnum:Int):Cell = sheet.row_(rownum).cell_(colnum)
    }

    implicit class RowImplicit(row:Row) {

        def getCell_(colnum:Int):Option[Cell] = Option(row.getCell(colnum))

        def cell_(colnum:Int):Cell = row.getCell(colnum, Row.CREATE_NULL_AS_BLANK)
    }

    implicit class CellImplicit(cell:Cell) {

        def getValue_():Any = {
            cell.getCellType match {
                case Cell.CELL_TYPE_BLANK => cell.getStringCellValue
                case Cell.CELL_TYPE_BOOLEAN => cell.getBooleanCellValue
                case Cell.CELL_TYPE_ERROR => cell.getErrorCellValue
                case Cell.CELL_TYPE_FORMULA => cell.getCellFormula
                case Cell.CELL_TYPE_NUMERIC => cell.getNumericCellValue
                case Cell.CELL_TYPE_STRING => cell.getStringCellValue
            }
        }

        def getUpperCell_():Option[Cell] = {
            val rownum = cell.getRowIndex
            if (rownum > 0)
                cell.getSheet.getCell_(rownum - 1, cell.getColumnIndex)
            else
                None
        }

        def getLowerCell_():Option[Cell] =
            cell.getSheet.getCell_(cell.getRowIndex + 1, cell.getColumnIndex)

        def getLeftCell_():Option[Cell] = {
            val colnum = cell.getColumnIndex
            if (colnum > 0)
                cell.getSheet.getCell_(cell.getRowIndex, colnum - 1)
            else
                None
        }

        def getRightCell_():Option[Cell] =
            cell.getSheet.getCell_(cell.getRowIndex, cell.getColumnIndex + 1)


        def upperCell_():Cell =
            cell.getSheet.cell_(cell.getRowIndex - 1, cell.getColumnIndex)

        def lowerCell_():Cell =
            cell.getSheet.cell_(cell.getRowIndex + 1, cell.getColumnIndex)

        def leftCell_():Cell =
            cell.getSheet.cell_(cell.getRowIndex, cell.getColumnIndex - 1)

        def rightCell_():Cell =
            cell.getSheet.cell_(cell.getRowIndex, cell.getColumnIndex + 1)
        

        def getUpperStream_():Stream[Cell] = {
            Stream.cons(cell, cell.getUpperCell_ match {
                case Some(next) => next.getUpperStream_
                case None => Stream.empty
            })
        }

        def getLowerStream_():Stream[Cell] = {
            Stream.cons(cell, cell.getLowerCell_ match {
                case Some(next) => next.getLowerStream_
                case None => Stream.empty
            })
        }

        def getLeftStream_():Stream[Cell] = {
            Stream.cons(cell, cell.getLeftCell_ match {
                case Some(next) => next.getLeftStream_
                case None => Stream.empty
            })
        }

        def getRightStream_():Stream[Cell] = {
            Stream.cons(cell, cell.getRightCell_ match {
                case Some(next) => next.getRightStream_
                case None => Stream.empty
            })
        }

        def upperStream_():Stream[Cell] = {
            Stream.cons(cell, Try(cell.upperCell_) match {
                case Success(next) => next.upperStream_
                case Failure(e) => Stream.empty
            })
        }

        def lowerStream_():Stream[Cell] = {
            Stream.cons(cell, Try(cell.lowerCell_) match {
                case Success(next) => next.lowerStream_
                case Failure(e) => Stream.empty
            })
        }

        def leftStream_():Stream[Cell] = {
            Stream.cons(cell, Try(cell.leftCell_) match {
                case Success(next) => next.leftStream_
                case Failure(e) => Stream.empty
            })
        }

        def rightStream_():Stream[Cell] = {
            Stream.cons(cell, Try(cell.rightCell_) match {
                case Success(next) => next.rightStream_
                case Failure(e) => Stream.empty
            })
        }

        def setBorderTop_(borderStyle:BorderStyle):Unit = {
            val cellStyle = cell.getCellStyle
            val t = cellStyle.toTuple_
            val newTuple = (borderStyle, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10)
            val workbook = cell.getSheet.getWorkbook

            workbook.findCellStyle_(newTuple) match {
                case Some(s) => cell.setCellStyle(s)
                case None => {
                    val newStyle = workbook.createCellStyle
                    newStyle.setBorderTop(borderStyle)
                    cell.setCellStyle(newStyle)
                }
            }
        }

        def setBorderBottom_(borderStyle:BorderStyle):Unit = {
            val cellStyle = cell.getCellStyle
            val t = cellStyle.toTuple_
            val newTuple = (t._1, borderStyle, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10)
            val workbook = cell.getSheet.getWorkbook

            workbook.findCellStyle_(newTuple) match {
                case Some(s) => cell.setCellStyle(s)
                case None => {
                    val newStyle = workbook.createCellStyle
                    newStyle.setBorderBottom(borderStyle)
                    cell.setCellStyle(newStyle)
                }
            }
        }

        def setBorderLeft_(borderStyle:BorderStyle):Unit = {
            val cellStyle = cell.getCellStyle
            val t = cellStyle.toTuple_
            val newTuple = (t._1, t._2, borderStyle, t._4, t._5, t._6, t._7, t._8, t._9, t._10)
            val workbook = cell.getSheet.getWorkbook

            workbook.findCellStyle_(newTuple) match {
                case Some(s) => cell.setCellStyle(s)
                case None => {
                    val newStyle = workbook.createCellStyle
                    newStyle.setBorderLeft(borderStyle)
                    cell.setCellStyle(newStyle)
                }
            }
        }

        def setBorderRight_(borderStyle:BorderStyle):Unit = {
            val cellStyle = cell.getCellStyle
            val t = cellStyle.toTuple_
            val newTuple = (t._1, t._2, t._3, borderStyle, t._5, t._6, t._7, t._8, t._9, t._10)
            val workbook = cell.getSheet.getWorkbook

            workbook.findCellStyle_(newTuple) match {
                case Some(s) => cell.setCellStyle(s)
                case None => {
                    val newStyle = workbook.createCellStyle
                    newStyle.setBorderRight(borderStyle)
                    cell.setCellStyle(newStyle)
                }
            }
        }

        def hasBorderBottom_():Boolean = {
            (cell.getCellStyle.getBorderBottomEnum != BorderStyle.NONE) ||
                (cell.getLowerCell_.map(_.getCellStyle.getBorderTopEnum != BorderStyle.NONE) match {
                    case Some(b) => b
                    case None => false
                })
        }

        def hasBorderTop_():Boolean = {
            (cell.getRowIndex == 0) ||
            (cell.getCellStyle.getBorderTopEnum != BorderStyle.NONE) ||
                (cell.getUpperCell_.map(_.getCellStyle.getBorderBottomEnum != BorderStyle.NONE) match {
                    case Some(b) => b
                    case None => false
                })
        }

        def hasBorderRight_():Boolean = {
            (cell.getCellStyle.getBorderRightEnum != BorderStyle.NONE) ||
                (cell.getRightCell_.map(_.getCellStyle.getBorderLeftEnum != BorderStyle.NONE) match {
                    case Some(b) => b
                    case None => false
                })
        }

        def hasBorderLeft_():Boolean = {
            (cell.getColumnIndex == 0) ||
            (cell.getCellStyle.getBorderLeftEnum != BorderStyle.NONE) ||
                (cell.getLeftCell_.map(_.getCellStyle.getBorderRightEnum != BorderStyle.NONE) match {
                    case Some(b) => b
                    case None => false
                })
        }

        def isOuterBottom_():Boolean = {
            val cellStyle = cell.getCellStyle
            val lowerCellStyle:Option[CellStyle] = cell.getLowerCell_.map(_.getCellStyle)

            (cellStyle.getBorderBottomEnum != BorderStyle.NONE) &&
            (lowerCellStyle match {
                case Some(style) =>
                    (style.getBorderRightEnum == BorderStyle.NONE) &&
                    (style.getBorderLeftEnum == BorderStyle.NONE)
                case None => true
            })
        }

        def isOuterTop_():Boolean = {
            val cellStyle = cell.getCellStyle
            val upperCellStyle = cell.getUpperCell_.map(_.getCellStyle)

            (cell.getRowIndex == 1 || cellStyle.getBorderTopEnum != BorderStyle.NONE) &&
            (upperCellStyle match {
                case Some(style) =>
                    (style.getBorderRightEnum == BorderStyle.NONE) &&
                    (style.getBorderLeftEnum == BorderStyle.NONE)
                case None => true
            })
        }

        def isOuterRight_():Boolean = {
            val cellStyle = cell.getCellStyle
            val rightCellStyle = cell.getRightCell_.map(_.getCellStyle)

            (cellStyle.getBorderRightEnum != BorderStyle.NONE) &&
            (rightCellStyle match {
                case Some(style) =>
                    (style.getBorderTopEnum == BorderStyle.NONE) &&
                    (style.getBorderBottomEnum == BorderStyle.NONE)
                case None => true
            })
        }

        def isOuterLeft_():Boolean = {
            val cellStyle = cell.getCellStyle
            val leftCellStyle = cell.getLeftCell_.map(_.getCellStyle)

            (cell.getColumnIndex == 1 || cellStyle.getBorderLeftEnum != BorderStyle.NONE) &&
            (leftCellStyle match {
                case Some(style) =>
                    (style.getBorderTopEnum == BorderStyle.NONE) &&
                    (style.getBorderBottomEnum == BorderStyle.NONE)
                case None => true
            })
        }
    }

    implicit class CellStyleImplicit(cellStyle:CellStyle) {

        def toTuple_():(
            BorderStyle, 
            BorderStyle,
            BorderStyle,
            BorderStyle,
            Color,
            Color,
            FillPatternType,
            HorizontalAlignment,
            VerticalAlignment,
            Boolean) = (
                cellStyle.getBorderTopEnum,
                cellStyle.getBorderBottomEnum,
                cellStyle.getBorderLeftEnum,
                cellStyle.getBorderRightEnum,
                cellStyle.getFillForegroundColorColor,
                cellStyle.getFillBackgroundColorColor,
                cellStyle.getFillPatternEnum,
                cellStyle.getAlignmentEnum,
                cellStyle.getVerticalAlignmentEnum,
                cellStyle.getWrapText)
    }
}

object ExcelerWorkbook {

    def open(filename:String): Workbook =  {
        WorkbookFactory.create(new File(filename))
    }

    def create(): Workbook = new XSSFWorkbook()
}
