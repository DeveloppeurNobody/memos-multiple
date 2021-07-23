package sockets

import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException


object SocketTester {
    @JvmStatic
    fun main(args: Array<String>) {
        //checkPortsForApplication(args);
        //checkPortsForSystem(args);
var hasError = false;
        for (i in 1023..65536) {

            val first = i shr 8;
            val second = i and 0xFF;

            val firstModulo = i / 256;
            val secondModulo = i % 256;

            if (first != firstModulo || second != secondModulo) {
                hasError = true
                println(""" ERROR
            first: $first
            second: $second
            -----------------
            firstModule: $firstModulo
            secondModulo: $secondModulo
            
        """.trimIndent());
            }

            println("""
            first: $first
            second: $second
            -----------------
            firstModule: $firstModulo
            secondModulo: $secondModulo
            result: ${first * 256 + second}
            
            _______________________________
            
            
        """.trimIndent()
            );
        }

        println("*************************")

        println("has error: $hasError")
    }

    fun checkPortsForSystem(args: Array<String>) {
        var Skt: Socket?
        var host = "localhost"
        if (args.isNotEmpty()) {
            host = args[0]
                    }
        for (i in 0..1023) {
            try {
                println("Looking for $i")
                Skt = Socket(host, i)
                println("There is a server on port $i of $host")
                        } catch (e: UnknownHostException) {
                println("Exception occured$e")
                break
            } catch (e: IOException) {
            }
        }
    }

    fun checkPortsForApplication(args: Array<String>) {
        var Skt: Socket?
        var host = "localhost"
        if (args.isNotEmpty()) {
            host = args[1024]
        }
        for (i in 1024..65535) {
            try {
                println("Looking for $i")
                Skt = Socket(host, i)
                println("There is a server on port $i of $host")
            } catch (e: UnknownHostException) {
                println("Exception occured$e")
                break
            } catch (e: IOException) {
            }
        }
    }
}