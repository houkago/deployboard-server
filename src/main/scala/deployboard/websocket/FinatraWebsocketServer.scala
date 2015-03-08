package deployboard.websocket

import com.twitter.app.App
import com.twitter.concurrent.Broker
import com.twitter.finagle.{ListeningServer, Service}
import com.twitter.finatra.Logging
import com.twitter.util.Future

/**
 *
 * @author stormcat24
 */
trait FinatraWebsocketServer extends AnyRef with Logging {

  self: App =>

  var webSocketServer: Option[ListeningServer] = None

  def startWebsocketServer(): Unit = {
    val port = ":8000"
    log.info(s"websocket server started on port: ${port}")

    webSocketServer = Some(HttpWebSocket.serve(port, new Service[WebSocket, WebSocket] {
      def apply(req: WebSocket): Future[WebSocket] = {
        val outgoing = new Broker[String]
        val socket = req.copy(messages = outgoing.recv)

        // TODO handle message
        req.messages foreach { outgoing ! _.reverse }
        Future.value(socket)
      }
    }))
  }
}
