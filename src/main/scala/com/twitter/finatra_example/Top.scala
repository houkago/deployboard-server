package com.twitter.finatra_example

import com.twitter.finatra.Controller

/**
 * Created by a13178 on 2/16/15.
 */
class Top extends Controller {

  val domain: String = "daployboard:7070"
  val boardPagePath: String = "/board"
  val clientId: String = "d714c5d2ea309df367f9"
  val clientSecret: String = "7b47841de80e71cf782751248f821ab3ec389c4f"

  get("/top") { request =>
    render.static("top.html").toFuture
  }

  get("/") { request =>
    request.params.get("code") match {
      case Some(code) =>
        println(code)
        route.post("https://github.com/login/oauth/access_token",
          Map(
            "code" -> code,
            "client_id" -> clientId,
            "client_secret" -> clientSecret,
            "redirect_uri" -> ("http://deployboard:7070/top" + domain + boardPagePath))
        )
        render.static("board.html").toFuture
      case None => route.get("/top")
    }
  }
}
