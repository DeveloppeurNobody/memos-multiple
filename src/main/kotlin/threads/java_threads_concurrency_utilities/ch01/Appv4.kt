package threads.java_threads_concurrency_utilities.ch01

object Appv4 {
    @JvmStatic
    fun main(args: Array<String>) {
        val r = Runnable {
            val name = Thread.currentThread().name;
            var count = 0;

            while (!Thread.interrupted()) {
                println("$name : ${count++}");
            }
        }

        val thd1 = Thread(r);
        val thd2 = Thread(r);
        thd1.start();
        thd2.start();

        try {
            Thread.sleep(2000);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        thd1.interrupt();
        thd2.interrupt();
    }
}