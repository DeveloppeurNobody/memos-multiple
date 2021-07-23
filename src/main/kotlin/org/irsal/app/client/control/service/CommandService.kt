package org.irsal.app.client.control.service

import org.irsal.app.client.GenerateLogger
import io.netty.channel.ChannelHandlerContext
import org.springframework.session.Session

class CommandService(val ctx: ChannelHandlerContext,
                     var session: Session? = null) {

    val LOG = GenerateLogger.getLoggerForClass(this);
    val networkService = NetworkService();

    companion object {
        var isCommandSequenceGroup = false;
    }

    fun startServerForActiveDataConnection() {

    }

    fun startClientForPassiveDataConnection() {

    }

}