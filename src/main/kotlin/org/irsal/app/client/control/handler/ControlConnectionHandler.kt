package org.irsal.app.client.control.handler


import org.irsal.app.client.GenerateLogger
import org.irsal.app.client.control.state.ControlConnectionState
import org.irsal.app.client.control.service.ControlConnectionService.CRLF
import org.irsal.app.client.control.reply.PassReply
import org.irsal.app.client.control.reply.UserReply
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class ControlConnectionHandler : SimpleChannelInboundHandler<String>() {
    val LOG = GenerateLogger.getLoggerForClass(this);

    var controlConnectionState = ControlConnectionState.NOT_CONNECTED;
    var currentCtx: ChannelHandlerContext? = null;

    override fun channelActive(ctx: ChannelHandlerContext?) {
        currentCtx = ctx;
        controlConnectionState = ControlConnectionState.CONNECTED;
        LOG.info("channelActive()");
    }

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: String?) {
        LOG.info("channelRead0()#msg: $msg");

        processMessage(msg!!);

    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
       LOG.info("channelInactive()");
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        LOG.error("exceptionCaught()");
        cause?.printStackTrace();
        ctx?.close();
    }

    private fun processMessage(msg: String) {
        LOG.info("processMessage()#connectionState: $controlConnectionState");

        // not loggedIn - try to loggedIn first
        when {
            controlConnectionState != ControlConnectionState.LOGGED_IN -> {
                makeConnection(msg)
            }
            controlConnectionState == ControlConnectionState.LOGGED_IN -> {
                doCommand();
            }
            controlConnectionState == ControlConnectionState.WAIT_FOR_REPLY -> {
                LOG.info(msg);
            }
        }
    }

    private fun makeConnection(msg: String) {
        LOG.info("makeConnection()");
        when (controlConnectionState) {
            ControlConnectionState.CONNECTED -> {
                // update state
                controlConnectionState = ControlConnectionState.NOT_LOGGED_USER;

                // connected send user and name;
                sendMessageAsTelnet("user frank");
            }
            ControlConnectionState.NOT_LOGGED_USER -> {
                // check reply for user command
                if (UserReply.checkResponse(msg)) {
                    // update state
                    controlConnectionState = ControlConnectionState.NOT_LOGGED_PASS

                    // send pass as next command
                    sendMessageAsTelnet("pass admin");
                }
            }
            ControlConnectionState.NOT_LOGGED_PASS -> {
                // check reply for user command
                if (PassReply.checkResponse(msg)) {
                    // update state
                    controlConnectionState = ControlConnectionState.LOGGED_IN;
                }
            }
        }
    }

    private fun doCommand() {
        sendMessageAsTelnet("pwd ");
        controlConnectionState = ControlConnectionState.WAIT_FOR_REPLY;
    }

    private fun sendMessageAsTelnet(msg: String = "") {
        currentCtx!!.writeAndFlush("$msg$CRLF");
    }
}
