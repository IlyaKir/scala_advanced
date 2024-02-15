object DarkSyntaxSugar extends App {

  private val helloWorld: String = "Hello, world"

  // #1 Methods with single parameter
  object Sugar1 {
    def singleArgMethod(arg: Int): String = s"number: ${arg}"
    val description = singleArgMethod {
      // some code
      42
    }
  }

  // #2 Single abstract method
  object Sugar2 {
    trait Action {
      def act(x: Int): Int
    }
    val anActionInstance = new Action {
      override def act(x: Int): Int = x * 2
    }
    val magicActionInstance: Action = (x: Int) => x * 2

    val aThreadInstance = new Thread(new Runnable {
      override def run(): Unit = println(helloWorld)
    })
    val magicThreadInstance:Thread = new Thread(() => println(helloWorld))

    abstract class AnAbstractClass {
      def implemented: Int = 42
      def f(arg: Int): Unit
    }
    val anAbstractInstance: AnAbstractClass = (arg: Int) => println(helloWorld)
  }

  // #3 The :: and #:: methods are special
  // Last character decides associativity of method
  object Sugar3 {
    val prependedList = 1 :: List(2, 3)

    1 :: 2 :: 3 :: List(4, 5)
    List(4, 5).::(3).::(2).::(1) // equivalent

    class MyStream[T] {
      def -->:(value: T): MyStream[T] = this
    }
    val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]
  }

  // #4 Multi-words method naming
  object Sugar4 {
    class Person(name: String) {
      def `and then said`(phrase: String) = s"$name said $phrase"
    }
    val lilly = new Person("Lilly")
    lilly.`and then said`(helloWorld)
  }

  // #5 Infix types
  object Sugar5 {
    class Composite[A, B]
    val composite: Int Composite String = ???

    class -->[A, B]
    val towards: Int --> String = ???
  }

  // #6 update() is very special, much like apply()
  object Sugar6 {
    val anArray = Array(1, 2, 3)
    anArray(2) = 7 // rewritten to anArray.update(2, 7)
  }

  // #7 Setters for mutable containers
  object Sugar7 {
    class Mutable {
      private var internalMember: Int = 0
      def member: Int = internalMember
      def member_=(value: Int): Unit = {
        internalMember = value
      }
    }

    val aMutableContainer = new Mutable
    aMutableContainer.member = 42
  }
}