package hmwork5

import io.gatling.core.Predef._
import scala.concurrent.duration._

class Debug extends Simulation {

  val maxStableUsers = 120
  val stepUsers = maxStableUsers / 10 // шаг 10%

  val scn = CommonScenario()

  setUp(
    scn.inject(
      incrementConcurrentUsers(stepUsers)
        .times(10)
        .eachLevelLasting(90.seconds)
        .separatedByRampsLasting(30.seconds)
    )
  )
    .protocols(hmwork5.httpProtocol)
    .maxDuration(20.minutes)
}
