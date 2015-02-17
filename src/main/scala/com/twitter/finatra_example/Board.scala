package com.twitter.finatra_example

import com.twitter.finatra.Controller

/**
 * Created by a13178 on 2/17/15.
 */
class Board extends Controller {

  get("/board") { request =>
    render.static("board.html").toFuture
  }

}
