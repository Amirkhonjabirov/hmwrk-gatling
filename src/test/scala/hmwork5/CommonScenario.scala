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

    // 1️⃣ Главная страница и получение userSession
    .exec(Actions.MainPage)
    .pause(1)
    .exec(Actions.GetUserSession)
    .pause(1)

    // 2️⃣ Логин
    .exec(Actions.Login)
    .pause(1)

    // 3️⃣ Переход на страницу Flights
    .exec(Actions.OpenFlightsPage)
    .pause(1)
    .exec(Actions.GoToFlightsMenu)
    .pause(1)

    // 4️⃣ Поиск и выбор рейса
    .exec(Actions.SearchFlight)
    .pause(1)
    .exec(Actions.ChooseFlight)
    .pause(1)

    // 5️⃣ Покупка билета
    .exec(Actions.BuyTicket)
    .pause(1)

    // 6️⃣ Возврат на главную
    .exec(Actions.GoHome)
}

