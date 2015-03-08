package deployboard.controller

import deployboard.DeployboardController
import scaldi.Injector

/**
 * Created by a13178 on 2/17/15.
 */
class Board(implicit val injector:Injector) extends DeployboardController {

  get("/board") { request =>
    render.static("board.html").toFuture
  }

}
