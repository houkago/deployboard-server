package com.twitter.finatra_example

import com.twitter.finatra.Controller

/**
 * Created by a13178 on 2/16/15.
 */
class Login extends Controller {

  val domain: String = "deployboard:7070"
  val boardPagePath: String = "/board"
  val clientId: String = ApplicationConfig.githubClientID()
  val clientSecret: String = ApplicationConfig.githubClientSecret()

  get("/top") { request =>
    render.static("top.html").toFuture
  }
  println(clientId)
  println(clientSecret)
  get("/") { request =>
    request.params.get("code") match {
      case Some(code) =>
        println(code)
        route.post("https://github.com/login/oauth/access_token",
          Map(
            "code" -> code,
            "client_id" -> clientId,
            "client_secret" -> clientSecret,
            "redirect_uri" -> ("http://" + domain + boardPagePath))
        )
      case None => route.get("/top")
    }
  }

  get("/github_login") { request =>
    redirect("https://github.com/login/oauth/authorize?client_id=d714c5d2ea309df367f9&redirect_uri=http://deployboard:7070/board").toFuture
  }

}
