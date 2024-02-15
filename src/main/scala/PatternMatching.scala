object PatternMatching extends App {
  /*
    - constants
    - wild cards
    - case classes
    - tuples
    - some special magic
   */

  val numbers = List(1)
  numbers match {
    case head :: Nil => println(s"The only element is $head")
    case _ => ???
  }

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = {
      if (person.age < 21) None
      else Some((person.name, person.age))
    }

    def unapply(age: Int): Option[String] = {
      Some(if (age < 21) "minor" else "major")
    }
  }

  val bob = new Person("Bob", 25)
  val description = bob match {
    case Person(name, age) => s"$name: $age yo. designer from San Francisco"
  }
  println(description)
  val legal = bob.age match {
    case Person(status) => s"My legal status is $status"
  }
  println(legal)

  object Exercise {
    val n: Int = 42
    n match {
      case x if x < 10 => "Single digit"
      case x if x % 2 == 0 => "An even number"
      case _ => "No property"
    }

    // ## It's possible to use Boolean instead of Option

    object EvenNumber {
//      def unapply(x: Int): Option[Unit] = if (x % 2 == 0) Some() else None
        def unapply(x: Int): Boolean = x % 2 == 0
    }
    object SingleDigitNumber {
//      def unapply(x: Int): Option[Unit] = if (x < 10) Some() else None
        def unapply(x: Int): Boolean = x < 10
    }
    n match {
//      case SingleDigitNumber(_) => "Single digit"
//      case EvenNumber(_) => "An even number"
      case SingleDigitNumber() => "Single digit"
      case EvenNumber() => "An even number"
      case _ => "No property"
    }

  }


}
