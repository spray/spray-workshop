Step 4
------

 * Change the calculation from step 3 to return the result of the calculation and return the result as json

   * Create a case class to represent the calculation domain, e.g.

```scala
case CalculatedSum(a: Int, b: Int, sum: Int)
```

   * Add spray-json dependency by adding this line to `build.sbt`:

```scala
"io.spray" %% "spray-json" % "1.2.5"
```
   * Type `reload` in sbt to reload the sbt configuration or just restart sbt manually

   * Write a JsonFormat for the domain model to make it able to automatically convert to json
```scala
  object CalculatedSum {
    import spray.json.DefaultJsonProtocol._
    implicit val sumJsonFormat = jsonFormat3(CalculatedSum.apply _)
  }
```
   * Add an import to SprayJsonSupport:

```scala
import spray.httpx.SprayJsonSupport._
```

   * In the calculation route, complete the request with the domain model directly

```scala
complete(CalculatedSum(a, b, a + b))
```

  * View the result in the browser

 * (Write tests to check the behavior)
