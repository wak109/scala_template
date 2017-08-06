/* vim: set ts=2 et sw=2 sts=2 fileencoding=utf-8: */
import scala.util.control.Exception._
import scala.util.{Try, Success, Failure}

import java.io._

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.{Option => CmdOption}
import org.apache.commons.cli.{Options => CmdOptions}
import org.apache.commons.cli.ParseException

import Exceler._

object Main {

  val DEFAULT_PORT = 8080
  val DEFAULT_EXCEL_DIR = "."

  val description = """Scala Excel"""
  val site = """https://github.com/wak109/exceler"""

  def stripClassName(clsname:String):String = {
    val Pattern = """^(.*)\$$""".r
    clsname match {
      case Pattern(m) => m
      case x => x
    }
  }

  def printUsage() : Unit = {
    val formatter = new HelpFormatter()

    formatter.printHelp(
      stripClassName(this.getClass.getCanonicalName),
      "\n" + this.description + "\n\noptions:\n",
      makeOptions(),
      "\nWeb: " + this.site,
      true)
  }

  def makeOptions() : CmdOptions = {
    val options = new CmdOptions()

    options.addOption("h", false, "Show help")
    options.addOption("p", true, "Listen port")
    options.addOption("d", true, "Excel directory")

    return options
  }

  /**
   *
   */
  def parseCommandLine(args:Array[String])
        :Try[(List[String], Boolean, Int, String)] =
    Try {
      val parser = new DefaultParser()
      val cl = parser.parse(makeOptions(), args)
      (
        cl.getArgs.toList,
        cl.hasOption('h'),
        if (cl.hasOption('p')) cl.getOptionValue('p').toInt
          else DEFAULT_PORT,
        if (cl.hasOption('d')) cl.getOptionValue('d')
          else DEFAULT_EXCEL_DIR
      )
    }

  /**
   *
   */
  def main(args:Array[String]) : Unit  = 
    parseCommandLine(args) match {
      case Success(a) => a match {
        case (_,true,_,_) => printUsage() 
        case (Nil,_,port,dir) => JettyLauncher.run(port)
        case (args,_,port,dir) => args(0) match {
          case "query" => readExcelTable(
            args(1), args(2), args(3), args(4), args(5)) match {
              case Success(_) => None
              case Failure(e) => println(e.getMessage); printUsage()
            }
          case _ => printUsage()
        }
      }
      case Failure(e) => println(e.getMessage); printUsage()
    }
}
