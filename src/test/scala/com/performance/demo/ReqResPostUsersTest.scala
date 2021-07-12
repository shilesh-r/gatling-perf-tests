package com.performance.demo

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder


class ReqResPostUsersTest extends Simulation {

  val httpConfig: HttpProtocolBuilder = http
    .baseUrl("https://reqres.in")

  val postUsersHeader = Map("Content-Type" -> "application/json")

  val reqResPostUsersScenario: ScenarioBuilder = scenario("ReqResPostUsersTest")
    .exec(http("request_138")
      .post("/api/users")
      .headers(postUsersHeader)
      .body(RawFileBody("com/performance/demo/reqresuitest/0138_request.json")))

  setUp(
    reqResPostUsersScenario.inject(constantUsersPerSec(1).during(30))
  ).protocols(httpConfig)

}
