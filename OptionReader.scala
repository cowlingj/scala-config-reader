import java.util.Scanner
import java.io.IOException
import java.io.File
import java.io.FileNotFoundException

object ConfigToStringPair {

  // Comment character (lines beginning with this are ignored)
  val COMMENT: Char = '#'
  // A map of the options selected as Strings
  val kvStringPairs: Map[String, String] = Map[String, String]()

  kvStringPairs foreach((pair:(String, String)) => { println(pair._1
                                                         + " = "
                                                         + pair._2)})

  /**
    * main method simply runs apply with the first argument as the file name
    * and outputs the results
    */
    def main(args: Array[String]): Unit = {
      for((key, value) <- this(args(0))) {
        println("KEY: " + key + " = VALUE: " + value )
      }
    }

  /**
    * get the options from the file as string key value pairs
    * @param fileName the name of the file to be read for options
    * @return A String, String map of the selected options
    * @throws FileNotFoundException
    * @since Jul 2017
    * @author Jonathan Cowling
   **/
  @throws(classOf[FileNotFoundException])
  def apply(fileName: String): Map[String, String] = {

    // Scanner and line No that the scanner is reading
    val fileScanner: Scanner = new Scanner(new File(fileName))
    var currentLineNo: Int = 0
    // Key, value String pairs representing options
    var optionsSoFar: Map[String, String] = Map[String, String]()

    // read each line and add all non-comment lines to the Map
    while (fileScanner.hasNextLine()) {
      val line: String = fileScanner.nextLine
      currentLineNo += 1
      if (! line.startsWith(COMMENT.toString) && ! line.matches("\\s*"))
        optionsSoFar += (parseLine(line, currentLineNo))
    }
    // return optionsSoFar
    optionsSoFar
  }

  // check the (given) non-comment line is valid:
  // i.e. in form key=value
  // if it is return (key, value)
  @throws(classOf[LineParseException])
  def parseLine(line: String, lineNo: Int): (String, String) = {
    // if the line isn't blank and doesn't contain "=", then the line is
    // invalid (we know this line isn't a comment)
    if (line.matches("!(.*=.*)"))
      throw new LineParseException("Failed to parse line " + lineNo)
    else {
      val kvArray: Array[String] = line.split("=", 2)
      (kvArray(0).trim(), kvArray(1).trim())
    }
  }

}
/**
  * LineParseException - An exception in parsing a particular key, value pair
 **/
case class LineParseException(message: String) extends IOException(message)
