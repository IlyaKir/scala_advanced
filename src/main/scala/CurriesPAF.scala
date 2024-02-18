import scala.util.Random

object CurriesPAF extends App{

  object CurriedFunctions {
    // curried functions
    val curriedAdd: Int => Int => Int =
      x => y => x + y

    val add3 = curriedAdd(3) // y => 3 + y
    val add35 = curriedAdd(3)(5)
  }

  object CurriedMethods {
    def curriedAdd(x: Int)(y: Int): Int = x + y

    // doesn't work without type annotation
    val add4: Int => Int = curriedAdd(4)

    val add5 = curriedAdd(5) _ // Int => Int
  }

  // lifting = ETA-expansion
  // Transforming method to function calls lifting
  // So, functions != methods (JVM limitation)
  object Lifting {
    def inc(x: Int): Int = x + 1

    // Compiler rewrites it as `x => inc(x)`
    List(1, 2, 3).map(inc) // lifting (or ETA-expansion)
  }


  // Implement add7 using below function and methods.
  object Exercise1 {
    val addFunction = (x: Int, y: Int) => x + y
    def addMethod(x: Int, y: Int): Int = x + y
    def addMethodCurried(x: Int)(y: Int) = x + y

    object AddFunctionSolution {
      val add7: Int => Int = y => addFunction(7, y)
      // compiler rewrites it as `y => addFunction(7, y)`
      // internally this is the same as `add7`
      val add7_alt = addFunction(7, _: Int)

      val add7_v2 = addFunction.curried(7)
    }

    object AddMethodSolution {
      val add7 = y => addMethod(7, y)
      // compiler rewrites it as `y => addMethod(7, y)`
      // internally this is the same as `add7`
      val add7_alt = addMethod(7, _: Int)

      // Doesn't work. Method `curried works only for Functions`
      //val add7_v2 = addMethod.curried(7)
    }

    object AddMethodCurriedSolution {
      val add7 = addMethodCurried(7) _
      // alternative syntax
      // internally this is the same as `add7`
      val add7_alt = addMethodCurried(7)(_) // PAF - alternative syntax

      val add7_v3: Int => Int = addMethodCurried(7)
    }
  }

  object Underscore {
    def concatenate2(a: String, b: String): String = a + b

    // b: String => concatenate2("Hello, I'm ", b)
    val insertName = concatenate2("Hello, I'm ", _: String)
    val helloIlia: String = insertName("Ilia")

    def concatenate3(a: String, b: String, c: String): String = a + b + c

    // (b: String, c: String) => concatenate3("Hello, I'm ", b, c)
    val insertNameSurname = concatenate3("Hello, I'm ", _: String, _: String)
  }


  // Process a list of numbers and return their string representations with different formats.
  // Use the %4.2f, %8.6f and %14.12f with a curried formatter function.
  object Exercise2 {
    val example = "%4.2f".format(Math.PI)

    def curriedFormatter(format: String)(number: Double): String = format.format(number)

    val f4d2 = curriedFormatter("%4.2f") _
    val f8d6 = curriedFormatter("%8.6f") _
    val f14d12 = curriedFormatter("%14.12f") _

    val list: List[Double] = List.fill(4)(Random.nextDouble())
    println(list.map(f4d2).mkString("\n"))
    println(list.map(f8d6).mkString("\n"))
    println(list.map(f14d12).mkString("\n"))
  }

  // Difference between
  //    - `functions` vs `methods`
  //    - parameters: `by-name` vs `0-lambda`
  object Exercise3 {
    def method: Int = 42
    def parenMethod(): Int = 42

    def byName(n: => Int): Int = n + 1
    def byFunction(f: () => Int): Int = f() + 1

    /*
    Calling `byName` and `byFunction`
      - int
      - method
      - parenMethod
      - lambda
      - partially applied function
     */
    object byNameSolution {
      byName(42) // int - ok
      byName(method) // method - ok
      byName(parenMethod()) // parenMethod - ok
      byName(parenMethod) // parenMethod - ok but beware: it's equal to byName(parenMethod)
      // byName(() => 42) // lambda - not ok
      byName((() => 42)()) // lambda ok
      //byName(parenMethod _) // PAF not ok - () => parenMethod()
    }
    object byFunctionSolution {
      // byFunction(42) // int - not ok

      // compiler doesn't do ETA-expansion here
      // byFunction(method) // method - not ok because `method` actually evaluated to its value 42

      // compiler does ETA-expansion here
      byFunction(parenMethod) // parenMethod - ok

      byFunction(() => 42) // lambda - ok

      byFunction(parenMethod _) // PAF ok
    }
  }
}
