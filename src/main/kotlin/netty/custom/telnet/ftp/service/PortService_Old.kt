package netty.custom.telnet.ftp.service

import java.net.InetSocketAddress
import java.net.ServerSocket

// this class is used in DataConnectionConfigurationFactory
class PortService_Old {
    // Logger

    companion object {
        // ports less than 1024 are reserved for operating system.
        const val MIN_PORT = 1024;
        const val MAX_PORT = 65635;

        const val VALUE_MAX_OF_8_BITS = 256;

        val groupOfFreePorts: MutableSet<Int> = mutableSetOf();
    }

    @Throws(Exception::class)
    fun encode(socketAddress: InetSocketAddress): String {

        var addressAsString: String = socketAddress
            .address
            .toString();

        if (addressAsString.contains('/')) {
            addressAsString = addressAsString.replace("/", "");
        }


        val port = socketAddress.port;

        // port divide by 256
        val p1 = port / VALUE_MAX_OF_8_BITS;
        // when port is divide by 256 we can get a reminder of this operation, we get it by modulo
        val p2 = port % VALUE_MAX_OF_8_BITS;

        val addressAsH1toH4 = addressAsString.replace(".", ",")


        // result should be h1,h2,h3,h3,p4,p5 e.g. 127,0,0,1,4,0
        return "${addressAsH1toH4},${p1},${p2}";
    }

    ////////////////////////////////////////////////////////

    // TODO Danger access multiThread can be problem here !!
    @Throws(Exception::class)
    @Synchronized fun getFreePort(): Int {
        try {
            // check if list of free ports already exists
            if (groupOfFreePorts.isEmpty()) {
                for (i in MIN_PORT..MAX_PORT) {
                    // check if port is available.
                    if (checkPortUnbound(i)) {
                        groupOfFreePorts.add(i);
                    }
                }
            }

            // value of free port
            val freePort = groupOfFreePorts.elementAt(0);

            // to avoid using same port for another user!!
            groupOfFreePorts.remove(freePort);

            println("free port is: $freePort");
            return freePort;

        } catch (ex: Exception) {
            throw ex;
        }
    }

    @Throws(Exception::class)
    private fun checkPortUnbound(port: Int): Boolean {
        try {
            ServerSocket(port)
                .use {
                    it.reuseAddress = true;
                    // not exception mean this port is free.
                    return true;
                }
        } catch (ex: Exception) {
            // port probably in use, check next;
            return false;
        }
    }

    // TODO check if user call releasePort() and another user call getFreePort() is OK!
    @Throws(Exception::class)
    fun releasePort(port: Int): Boolean {
        try {
            if (groupOfFreePorts.contains(port)) {
                groupOfFreePorts.remove(port);
                return true;
            } else {
                throw Exception("port does not exists in list of freePorts");
            }
        } catch (ex: Exception) {
            throw ex;
        }
    }
}