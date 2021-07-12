package com.performance.demo

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class ReqResUITest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("https://reqres.in")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_3 = Map("Content-Type" -> "application/json")

  val reqResUiScenario: ScenarioBuilder = scenario("ReqResUITest")
    .exec(http("request_0")
      .get("/")
      .headers(headers_0)
      .resources(
        http("request_3")
          .get("/api/users?page=2")
          .headers(headers_3)
      ))
    .pause(10)
    .exec(http("request_138")
      .post("/api/users")
      .headers(headers_3)
      .body(RawFileBody("com/performance/demo/reqresuitest/0138_request.json")))

  setUp(
    reqResUiScenario.inject(constantUsersPerSec(1).during(30))
  ).protocols(httpProtocol)
}