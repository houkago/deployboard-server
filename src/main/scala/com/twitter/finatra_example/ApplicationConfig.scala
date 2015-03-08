package com.twitter.finatra_example

/**
 *
 *
 * @author horimislime
 */
object ApplicationConfig {

  def githubClientID(): String = {
    return sys.env.get("GITHUB_CLIENT_ID").get
  }

  def githubClientSecret(): String = {
    return sys.env.get("GITHUB_CLIENT_SECRET").get
  }
}
