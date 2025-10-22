package hmwork5

import io.gatling.core.Predef._
import scala.concurrent.duration._

class Debug extends Simulation {
  val scn = CommonScenario()
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(hmwork5.httpProtocol)
}
