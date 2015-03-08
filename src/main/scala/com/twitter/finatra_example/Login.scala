package com.twitter.finatra_example

import deployboard.config._
import deployboard.DeployboardController
import scaldi.Injector

/**
 * Created by a13178 on 2/16/15.
 */
class Login(implicit val injector:Injector) extends DeployboardController {

  val domain: String = inject[String]('domain)
  val boardPagePath: String = inject[String]('boardPagePath)
  val githubConfig: GitHubAppConfig = inject[GitHubAppConfig]

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
            "client_id" -> githubConfig.clientId,
            "client_secret" -> githubConfig.clientSecret,
            "redirect_uri" -> s"http://${domain}${boardPagePath}"
          )
        )
      case None => route.get("/top")
    }
  }

  get("/github_login") { request =>
    redirect(s"https://github.com/login/oauth/authorize?client_id=${githubConfig.clientId}&redirect_uri=http://${domain}${boardPagePath}").toFuture
  }

  get("/api/top") { request =>
    render.json(Map("oauth_url" ->
      (s"https://github.com/login/oauth/authorize?client_id=${githubConfig.clientId}&redirect_uri=http://${domain}${boardPagePath}"))
    ).toFuture
  }

}
