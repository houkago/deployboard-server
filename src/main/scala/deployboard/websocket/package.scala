package deployboard

import com.twitter.concurrent.Offer
import com.twitter.finagle.dispatch.SerialServerDispatcher
import com.twitter.finagle.netty3.Netty3Listener
import com.twitter.finagle.server.DefaultServer
import com.twitter.util.{Future, Promise}
import java.net.URI
import org.jboss.netty.handler.codec.http.websocketx.WebSocketVersion
import java.net.SocketAddress
import com.twitter.finagle._
import org.jboss.netty.channel.{ChannelPipelineFactory, Channels}
import org.jboss.netty.handler.codec.http._


/**
 *
 * @author stormcaat24
 */
package object websocket {

  case class WebSocket(
      messages: Offer[String],
      binaryMessages: Offer[Array[Byte]],
      uri: URI,
      headers: Map[String, String] = Map.empty[String, String],
      remoteAddress: SocketAddress = new SocketAddress {},
      version: WebSocketVersion = WebSocketVersion.V13,
      onClose: Future[Unit] = new Promise[Unit],
      close: () => Unit = { () => ()})

  case class WebSocketCodec() extends CodecFactory[WebSocket, WebSocket] {
    def server = Function.const {
      new Codec[WebSocket, WebSocket] {
        def pipelineFactory = new ChannelPipelineFactory {
          def getPipeline = {
            val pipeline = Channels.pipeline()
            pipeline.addLast("decoder", new HttpRequestDecoder)
            pipeline.addLast("encoder", new HttpResponseEncoder)
            pipeline.addLast("handler", new WebSocketServerHandler)
            pipeline
          }
        }
      }
    }

    def client = Function.const {
      new Codec[WebSocket, WebSocket] {
        def pipelineFactory = new ChannelPipelineFactory {
          def getPipeline = {
            val pipeline = Channels.pipeline()
            pipeline.addLast("decoder", new HttpResponseDecoder)
            pipeline.addLast("encoder", new HttpRequestEncoder)
            pipeline.addLast("handler", new WebSocketClientHandler)
            pipeline
          }
        }
      }
    }
  }

  object WebSocketListener extends Netty3Listener[WebSocket, WebSocket](
    "websocket", WebSocketCodec().server(ServerCodecConfig("websocketserver", new SocketAddress {})).pipelineFactory
  )

  object WebSocketServer extends DefaultServer[WebSocket, WebSocket, WebSocket, WebSocket](
    "websocketsrv", WebSocketListener, new SerialServerDispatcher(_, _)
  )

  object HttpWebSocket extends Server[WebSocket, WebSocket] {
    override def serve(addr: SocketAddress, service: ServiceFactory[WebSocket, WebSocket]): ListeningServer = WebSocketServer.serve(addr, service)
  }
}
