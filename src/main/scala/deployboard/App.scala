package deployboard

import com.twitter.finatra._
import com.twitter.util.Await
import com.twitter.finatra.{config => finatraConfig}
import deployboard.websocket.FinatraWebsocketServer
import scaldi.Injectable

object App extends FinatraServer with FinatraWebsocketServer with Injectable {

  implicit val module = new ServerModule

  inject[Seq[Controller]].foreach(register(_))

  override def start(): Unit = {
    if (!finatraConfig.pidPath().isEmpty) {
      writePidFile()
    }

    if (!finatraConfig.port().isEmpty) {
      startHttpServer()
    }

    if (!finatraConfig.adminPort().isEmpty) {
      startAdminServer()
    }

    if (!finatraConfig.sslPort().isEmpty) {
      startSecureServer()
    }

    startWebsocketServer

    server       map { Await.ready(_) }
    adminServer  map { Await.ready(_) }
    secureServer map { Await.ready(_) }
  }
}
