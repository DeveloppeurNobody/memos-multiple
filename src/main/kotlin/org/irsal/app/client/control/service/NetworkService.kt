package org.irsal.app.client.control.service

import org.irsal.app.client.data.enums.DataConnectionState
import org.irsal.app.client.data.enums.DataType
import org.irsal.app.client.data.enums.TransferMode
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture

class NetworkService {
    val nettyService = NettyService();

    companion object {
        // Per default is dataType is ASCII
        var dataType = DataType.ASCII;

        // Per default transfer is stream
        var transferMode = TransferMode.STREAM;

        // Per default data connection is null;
        var dataConnectionState: DataConnectionState = DataConnectionState.UNKNOWN;

        var clientFailedChannelFuture: ChannelFuture? = null;
        const val VALUE_MAX_OF_8_BITS = 256;

        var remoteClientActive: Bootstrap? = null;
    }
}