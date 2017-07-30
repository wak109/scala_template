/* vim: set ts=4 et sw=4 sts=4 fileencoding=utf-8: */
import scala.collection._
import scala.language.implicitConversions
import scala.util.control.Exception._
import scala.util.{Try, Success, Failure}

import org.apache.poi.ss.usermodel._
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.io._
import java.nio.file._

import CommonLib.ImplicitConversions._
import ExcelLib.ImplicitConversions._
import ExcelLib.Rectangle.ImplicitConversions._


trait ExcelTableFunction extends TableFunction[ExcelRectangle] {
    val tableFunction = new FunctionImpl

    class FunctionImpl extends Function {
        override def getCross(row:ExcelRectangle, col:ExcelRectangle) = {
            ExcelRectangle(
                row.sheet, row.top, col.left, row.bottom, col.right)
        }
    
        override def getHeadRow(rect:ExcelRectangle):(
            ExcelRectangle, Option[ExcelRectangle]) = {
            (for {
                rownum <- (rect.top until rect.bottom).toStream
                cell <- rect.sheet.getCellOption(rownum, rect.left)
                if cell.hasBorderBottom
            } yield rownum).headOption match {
                case Some(num) => (
                    ExcelRectangle(rect.sheet, rect.top,
                        rect.left, num, rect.right),
                    Some(ExcelRectangle(rect.sheet, num + 1,
                        rect.left, rect.bottom, rect.right)))
                case _ => (rect, None)
            }
        }
    
        override def getHeadCol(rect:ExcelRectangle):(
                ExcelRectangle,Option[ExcelRectangle]) = {
            (for {
                colnum <- (rect.left until rect.right).toStream
                cell <- rect.sheet.getCellOption(rect.top, colnum)
                if cell.hasBorderRight
            } yield colnum).headOption match {
                case Some(num) => (
                    ExcelRectangle(rect.sheet, rect.top,
                        rect.left, rect.bottom, num),
                    Some(ExcelRectangle(rect.sheet, rect.top,
                        num + 1, rect.bottom, rect.right)))
                case _ => (rect, None)
            }
        }
    
        override def getValue(rect:ExcelRectangle):Option[String] =
            (for {
                colnum <- (rect.left to rect.right).toStream
                rownum <- (rect.top to rect.bottom).toStream
                value <- rect.sheet.cell(rownum, colnum)
                            .getValueString.map(_.trim)
            } yield value).headOption
    
        override def getTableName(rect:ExcelRectangle)
                : (Option[String], ExcelRectangle) = {
            // Table name outside of Rectangle
            (rect.top match {
                case 0 => (None, rect)
                case _ => (rect.sheet.cell(rect.top - 1, rect.left)
                        .getValueString, rect)
            }) match {
                case (Some(name), _) => (Some(name), rect)
                case (None, _) => {
                    // Table name at the top of Rectangle
                    val (rowHead, rowTail) = this.getHeadRow(rect)
                    val (rowHeadLeft, rowHeadRight) =
                            this.getHeadCol(rowHead)

                    rowHeadRight match {
                        case Some(_) => (None, rect)
                        case None => (this.getValue(rowHeadLeft),
                                rowTail) match {
                            case (name, Some(tail)) => (name, tail)
                            case (name, None) => (None, rect)
                        }
                    }
                }
            }
        }
    
        override def mergeRect(rectL:List[ExcelRectangle]):ExcelRectangle = {
            val head = rectL.head
            val last = rectL.last
            ExcelRectangle(
                head.sheet, head.top, head.left, last.bottom, last.right)
        }
    }
}
