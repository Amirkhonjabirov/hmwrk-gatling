package hmwork5

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Actions {

  // 1️⃣ Главная страница (вход) и получение userSession
  val MainPage = exec(
    http("SignOff")
      .get("/cgi-bin/welcome.pl?signOff=true")
      .check(status.is(200))
  ).exec(
    http("Get user session")
      .get("/cgi-bin/nav.pl?in=home")
      .check(
        status.is(200),
        regex("""name="userSession" value="(.+?)"""").saveAs("userSession")
      )
  ).exec { session =>
    println(s"✅ Extracted userSession: ${session("userSession").as[String]}")
    session
  }

  // 2️⃣ Логин
  val Login = exec(
    http("Login")
      .post("/cgi-bin/login.pl")
      .formParam("userSession", "${userSession}")
      .formParam("username", "jojo")
      .formParam("password", "bean")
      .formParam("login.x", "56")
      .formParam("login.y", "10")
      .check(status.is(200))
  )

  // 3️⃣ Переход на страницу "Flights"
  val FlightsPage = exec(
    http("Open Flights Page")
      .get("/cgi-bin/welcome.pl?page=search")
      .check(status.is(200))
  ).pause(1)
    .exec(
      http("Go to Flights menu")
        .get("/cgi-bin/nav.pl?page=menu&in=flights")
        .check(status.is(200))
    ).pause(1)

  // 4️⃣ Поиск рейса
  val SearchFlight = exec(
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
      .check(regex("""name="outboundFlight" value="(.+?)"""").saveAs("outboundFlight")) // извлекаем outboundFlight
  ).exec { session =>
    println(s"✅ Extracted outboundFlight: ${session("outboundFlight").as[String]}")
    session
  }.pause(1)


  // 5️⃣ Выбор рейса
  val ChooseFlight = exec(
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
  ).pause(1)

  // 6️⃣ Покупка билета
  val BuyTicket = exec(
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
  ).pause(1)

  // 7️⃣ Возврат на главную страницу
  val GoHome = exec(
    http("Go Home")
      .get("/cgi-bin/welcome.pl?page=menus")
      .check(status.is(200))
  ).pause(1)
}
