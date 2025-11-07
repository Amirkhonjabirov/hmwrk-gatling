package hmwork5

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Actions {

  // 1️⃣ Главная страница (вход)
  val MainPage =
    http("SignOff")
      .get("/cgi-bin/welcome.pl?signOff=true")
      .check(status.is(200))

  // Получение userSession
  val GetUserSession =
    http("Get user session")
      .get("/cgi-bin/nav.pl?in=home")
      .check(
        status.is(200),
        regex("""name="userSession" value="(.+?)"""").saveAs("userSession")
      )

  // 2️⃣ Логин
  val Login =
    http("Login")
      .post("/cgi-bin/login.pl")
      .formParam("userSession", "${userSession}")
      .formParam("username", "jojo")
      .formParam("password", "bean")
      .formParam("login.x", "56")
      .formParam("login.y", "10")
      .check(status.is(200))

  // 3️⃣ Переход на страницу "Flights"
  val OpenFlightsPage =
    http("Open Flights Page")
      .get("/cgi-bin/welcome.pl?page=search")
      .check(status.is(200))

  val GoToFlightsMenu =
    http("Go to Flights menu")
      .get("/cgi-bin/nav.pl?page=menu&in=flights")
      .check(status.is(200))

  // 4️⃣ Поиск рейса
  val SearchFlight =
    http("Search Flight")
      .post("/cgi-bin/reservations.pl")
      .formParam("advanceDiscount", "0")
      .formParam("depart", "${departCity}")
      .formParam("departDate", "10/23/2025")
      .formParam("arrive", "${arriveCity}")
      .formParam("returnDate", "10/24/2025")
      .formParam("numPassengers", "1")
      .formParam("seatPref", "None")
      .formParam("seatType", "Coach")
      .formParam("findFlights.x", "70")
      .formParam("findFlights.y", "10")
      .formParam(".cgifields", "roundtrip")
      .formParam(".cgifields", "seatType")
      .formParam(".cgifields", "seatPref")
      .check(status.is(200))
      .check(regex("""name="outboundFlight" value="(.+?)"""").saveAs("outboundFlight"))

  // 5️⃣ Выбор рейса
  val ChooseFlight =
    http("Choose Flight")
      .post("/cgi-bin/reservations.pl")
      .formParam("outboundFlight", "${outboundFlight}")
      .formParam("numPassengers", "1")
      .formParam("advanceDiscount", "0")
      .formParam("seatType", "Coach")
      .formParam("seatPref", "None")
      .formParam("reserveFlights.x", "66")
      .formParam("reserveFlights.y", "6")
      .check(status.is(200))

  // 6️⃣ Покупка билета
  val BuyTicket =
    http("Buy Ticket")
      .post("/cgi-bin/reservations.pl")
      .formParam("firstName", "Bean")
      .formParam("lastName", "Jojo")
      .formParam("address1", "Street of John Dow")
      .formParam("address2", "City")
      .formParam("pass1", "Bean Jojo")
      .formParam("creditCard", "")
      .formParam("expDate", "")
      .formParam("oldCCOption", "")
      .formParam("numPassengers", "1")
      .formParam("seatType", "Coach")
      .formParam("seatPref", "None")
      .formParam("outboundFlight", "${outboundFlight}")
      .formParam("advanceDiscount", "0")
      .formParam("returnFlight", "")
      .formParam("JSFormSubmit", "off")
      .formParam("buyFlights.x", "43")
      .formParam("buyFlights.y", "0")
      .formParam(".cgifields", "saveCC")
      .check(status.is(200))

  // 7️⃣ Возврат на главную страницу
  val GoHome =
    http("Go Home")
      .get("/cgi-bin/welcome.pl?page=menus")
      .check(status.is(200))
}
