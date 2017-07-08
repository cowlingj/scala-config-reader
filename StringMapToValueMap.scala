
object StringMapToValueMap {

  // A map of the options selected as Strings, Booleans, Chars, Ints or Doubles
  val kvPairs: Map[String, Object] = Map[String, Object]()

  def main(args: Array[String]): Unit = {
    for((key, value) <- this(ConfigToStringPair(args(0)))){
      println("KEY: " + key + " = VALUE: " + value)
    }
  }

  // convert String, String pairs to more relevant pairs
  def apply(stringPairs: Map[String, String]): Map[String, Any] = {
    var relevantPairs: Map[String, Any] = Map[String, Any]()
    for((key, value) <- stringPairs) {
      relevantPairs += (key -> tryToConvertValue(value))
    }
    relevantPairs
  }

  // try to convert the value to a more relevant value
  def tryToConvertValue(value: String): Any = {
  if(value.matches("\\d+")) value.toInt
  else if (value.matches("\\d*\\.\\d+")) value.toDouble
  else if (value.matches("true")) value.toBoolean
  else if (value.matches("false")) value.toBoolean
  else if (value.matches("\\w")) value.charAt(0)
  else value
  }
}
