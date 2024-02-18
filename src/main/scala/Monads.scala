object Monads extends App{
  // Monad Laws:
  //
  // 1. Left-identity:   Monad(x).flatMap(f) = f(x)
  // 2. Right-identity:  Monad(x).flatMap(unit) = monad(x)
  // 3. Associativity:
  // Monad(x).flatMap(f).flatMap(g) = Monad(x).flatMap(x => f(x).flatMap(fx => g(fx))

  trait Try[+A] {
    def flatMap[B](f: A => Try[B]): Try[B]
  }
  object Try {
    def apply[A](value: => A): Try[A] = {
      try {
        Success(value)
      } catch {
        case e: Throwable => Fail(e)
      }
    }
  }

  case class Success[+A](value: A) extends Try[A] {
    override def flatMap[B](f: A => Try[B]): Try[B] = {
      try {
        f(value)
      } catch {
        case e: Throwable => Fail(e)
      }
    }
  }

  case class Fail(e: Throwable) extends Try[Nothing] {
    override def flatMap[B](f: Nothing => Try[B]): Try[B] = this
  }

  /* Left-identity
    Success(x).flatMap(f) = f(x) // proved
    Fail(e).flatMap(f) = f(x) // proved
   */

  /* Right-identity
    Success(x).flatMap(x => apply(x)) = Success(x) // proved
    Fail(e).flatMap(unit) = Fail(e) // proved
   */

  /* Associativity
    Success(x).flatMap(f).flatMap(g) =
      f(x).flatMap(g)
    Success(x).flatMap(x => f(x).flatMap(g)) =
      f(x).flatMap(g)
    f(x).flatMap(g) = f(x).flatMap(g) // proved

    Fail(e).flatMap(f).flatMap(g) = Fail(e)
    Fail(e).flatMap(x => f(x).flatMap(g)) = Fail(e) // proved
   */

  // Implement a Lazy[T] monad (unit/apply, flatMap)
  object Exercise1 {

  }

  // Implement map and flatten via flatMap
  object Exercise2 {
    trait SomeMonad[A] {
      def flatMap[B](f: A => SomeMonad[B]): SomeMonad[B] = ???

      def map[B](f: A => B): SomeMonad[B] = ???
      def flatten(m: SomeMonad[SomeMonad[A]]): SomeMonad[A] = ???
    }
  }
}
