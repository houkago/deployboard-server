package deployboard

import config._
import scaldi.Module

/**
 * lightweight DI container
 * @author stormcat24
 */
class ServerModule extends Module {

  // About scaldi http://scaldi.org/learn/

  // const
  bind [String] identifiedBy 'domain to "deployboard.herokuapp.com"
  bind [String] identifiedBy 'boardPagePath to "/#/dashboard"

  bind [GitHubAppConfig] to ((for {
    clientId <- Option(System.getProperty("GITHUB_CLIENT_ID"))
    clientSecret <- Option(System.getProperty("GITHUB_CLIENT_SECRET"))
  } yield (GitHubAppConfig(clientId, clientSecret))) match {
    case None => throw new RuntimeException("""require 'GITHUB_CLIENT_ID' and 'GITHUB_CLIENT_SECRET'""")
    case Some(config) => config
  })

}
