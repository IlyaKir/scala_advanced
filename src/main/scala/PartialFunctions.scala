import scala.io.Source

object PartialFunctions extends App{
  /**
   * Exercise: Construct a PF instance (anonymous class)
   */

  val pfManualInstance = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x match {
      case 1 | 2 | 5 => true
      case _ => false
    }
    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 56
      case 5 => 999
    }
  }

  /**
   * Exercise: Dumb chatbot as a PF
   */
  val pfBot: String => String = {
    case "Hello" => "Hi, I'm bot"
    case "Goodbye" | "Bye" => "Bye, human"
    case line => s"You said: \"$line\""
  }
  Source.stdin.getLines().map(pfBot).foreach(println)
}
