package deployboard.controller

import deployboard.DeployboardController
import deployboard.exception._
import scaldi.Injector

/**
 *
 * @author stormcat24
 */
class Global(implicit val injector: Injector) extends DeployboardController {

  notFound { request =>
    val response = Map(
      "status" -> 404,
      "message" -> "Not Found"
    )
    render.status(404).json(response).toFuture
  }

  error { request =>
    val errorResponse = request.error match {
      case Some(e:BadRequestException) =>
        render.status(400).json(Map("status" -> 400, "message" -> e.getMessage))
      case Some(e:UnauthorizedException) =>
        render.status(401).json(Map("status" -> 401, "message" -> e.getMessage))
      case Some(e) =>
        e.printStackTrace
        render.status(500).json(Map("status" -> 500, "message" -> "Internal Server Error"))
      case _ =>
        render.status(500).json(Map("status" -> 500, "message" -> "Internal Server Error"))
    }

    errorResponse.toFuture
  }
}
