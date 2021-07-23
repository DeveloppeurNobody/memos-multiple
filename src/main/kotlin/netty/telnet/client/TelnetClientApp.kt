package netty.telnet.client

import netty.telnet.executor.ControlConnectionService

object TelnetClientApp {

    @JvmStatic
    fun main(args: Array<String>) {
        val controlConnectionService = ControlConnectionService();
//        controlConnectionService.startANewConnection();
    }
}

