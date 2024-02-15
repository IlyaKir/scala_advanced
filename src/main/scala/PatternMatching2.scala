object PatternMatching2 extends App {

  // infix patterns (only works for 2 things in pattern)
  object InfixPatterns {
    case class Or[A, B](a: A, b: B) // like Either
    val either = Or(2, "two")
    val humanDescription = either match {
      //    case Or(number, string) => s"$number is written as $string"
      case number Or string => s"$number is written as $string"
    }
  }

  object DecomposingSequences {
    // decomposing sequences
    val numbers = List(1)
    val vararg = numbers match {
      case List(1, _*) => "Starting with 1"
    }

    abstract class MyList[+A] {
      def head: A = ???
      def tail: MyList[A] = ???
    }
    case object Empty extends MyList[Nothing]
    case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

    object MyList {
      def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
        if (list == Empty) Some(Seq.empty)
        else unapplySeq(list.tail).map(list.head +: _)
      }
    }

    val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
    val decomposed = myList match {
      case MyList(1, 2, _*) => "Starting with 1, 2"
      case _ => "Something else"
    }
  }

  // custom return types for unapply (NOT AN OPTION)
  object CustomReturnTypes {
    class Person(val name: String, val age: Int)
    // 2 methods ARE REQUIRED:
    //    isEmpty: Boolean
    //    get: SomeType
    abstract class Wrapper[T] {
      def isEmpty: Boolean = ???
      def get: T = ???
    }

    object PersonWrapper {
      def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
        override def isEmpty: Boolean = false
        override def get: String = person.name
      }
    }

    val bob = new Person("Bob", 25)
    println(bob match {
      case PersonWrapper(name) => s"This person's name is $name"
      case _ => "An alien"
    })
  }
}
