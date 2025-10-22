package hmwork5

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().scn
}

class CommonScenario {

  // Feeder для случайного выбора городов
  val feeder = Iterator.continually(Map(
    "departCity" -> List("Paris", "Seattle", "Denver", "London", "Frankfurt")(scala.util.Random.nextInt(5)),
    "arriveCity" -> List("Zurich", "New York", "San Francisco", "Sydney", "Tokyo")(scala.util.Random.nextInt(5))
  ))

  val scn: ScenarioBuilder = scenario("Common scenario")
    .feed(feeder)
    .exec(Actions.MainPage)
    .pause(1)
    .exec(Actions.Login)
    .pause(1)
    .exec(Actions.FlightsPage)
    .pause(1)
    .exec(Actions.SearchFlight)
    .pause(1)
    .exec(Actions.ChooseFlight)
    .pause(1)
    .exec(Actions.BuyTicket)
    .pause(1)
    .exec(Actions.GoHome)
}
