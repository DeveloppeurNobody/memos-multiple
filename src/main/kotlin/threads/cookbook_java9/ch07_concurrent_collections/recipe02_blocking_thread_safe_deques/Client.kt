package threads.cookbook_java9.ch07_concurrent_collections.recipe02_blocking_thread_safe_deques

import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

class Client(val requestList: LinkedBlockingDeque<String>) : Runnable {

    override fun run() {
        repeat(3) { i ->
            repeat(5) {j ->
                val request = StringBuilder();
                request.append(i);
                request.append(":");
                request.append(j);

                try {
                    requestList.put(request.toString());
                } catch (ie: InterruptedException) {
                    System.err.println(ie);
                }
                println("Client added: $request at ${Date()}");
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        }
        println("Client: End.");
    }
}