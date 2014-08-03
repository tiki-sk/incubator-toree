package com.ibm.spark.kernel.protocol.v5.socket

import akka.actor.{Actor, ActorLogging}
import akka.util.ByteString
import akka.zeromq.ZMQMessage
import com.ibm.spark.kernel.protocol.v5.{ActorLoader, KernelMessage}

/**
 * The server endpoint for shell messages specified in the IPython Kernel Spec
 * @param socketFactory A factory to create the ZeroMQ socket connection
 */
class Shell(socketFactory: SocketFactory, actorLoader: ActorLoader) extends Actor with ActorLogging {
  log.debug("Created new Shell actor")
  val socket = socketFactory.Shell(context.system, self)
  override def receive: Receive = {
    case zmqMessage: ZMQMessage =>
      println("SHELL RECEIVING: " +
        zmqMessage.frames.map((byteString: ByteString) => new String(byteString.toArray)).mkString("\n"))
      val message: KernelMessage = zmqMessage
      actorLoader.loadRelayActor() ! message
    case message: KernelMessage =>
      val zmqMessage: ZMQMessage = message
      log.debug("Sending shell message")
      println("SHELL SENDING: " +
        zmqMessage.frames.map((byteString: ByteString) => new String(byteString.toArray)).mkString("\n"))
      socket ! zmqMessage
  }
}
