package com.twitter.finatra_example

import com.twitter.finatra.Controller

/**
 * Created by a13178 on 2/16/15.
 */
class Login extends Controller {

  val domain: String = "deployboard.herokuapp.com"
  val boardPagePath: String = "/#/dashboard"
  val clientId: String = ApplicationConfig.githubClientID()
  val clientSecret: String = ApplicationConfig.githubClientSecret()

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
            "redirect_uri" -> ("http://" + domain + boardPagePath))
        )
      case None => route.get("/top")
    }
  }

  get("/github_login") { request =>
    println("https://github.com/login/oauth/authorize?client_id="+clientId+"&redirect_uri=http://" + domain + boardPagePath)
    redirect("https://github.com/login/oauth/authorize?client_id="+clientId+"&redirect_uri=http://" + domain + boardPagePath).toFuture
  }

  get("/api/top") { request =>
    render.json(Map("oauth_url" ->
      ("https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=http://" + domain + boardPagePath))
    ).toFuture
  }

}
