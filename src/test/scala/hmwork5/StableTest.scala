package hmwork5

import io.gatling.core.Predef._
import scala.concurrent.duration._

class StableTest extends Simulation {

  val targetUsers = 96  // 80% от максимума 120
  val testDuration = 1.hour

  val scn = CommonScenario()

  setUp(
    scn.inject(
      rampConcurrentUsers(0) to targetUsers during (2.minutes),
      constantConcurrentUsers(targetUsers) during testDuration
    )
  )
    .protocols(hmwork5.httpProtocol)
    .maxDuration(testDuration)
}