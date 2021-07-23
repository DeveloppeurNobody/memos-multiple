package org.irsal.app.client.control.service

import java.net.ServerSocket

// TODO connection lost when switching from box to hotspot add mecanism to deal with network switching

// this class is used in DataConnectionConfigurationFactory
class PortService {
    // Logger

    companion object {
        // ports less than 1024 are reserved for operating system.
        const val MIN_PORT = 1024;
        const val MAX_PORT = 65635;
        val groupOfFreePorts: MutableSet<Int> = mutableSetOf();
    }

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