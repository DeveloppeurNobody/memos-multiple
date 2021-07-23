package sockets

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket


object InetAddressTester {

    @JvmStatic
    fun main(args: Array<String>) {
        val inetAddress = InetAddress.getLocalHost()
        println("IP Address : ${inetAddress.hostAddress}")
        println("Host Name  : ${inetAddress.hostName}");

        var localAddressForRemoteClient: InetAddress? = null;
        Socket().use {
            it.connect(InetSocketAddress("google.com", 80))
            localAddressForRemoteClient = it.localAddress;
        }

        println("local adress for outcast: $localAddressForRemoteClient");
    }
}