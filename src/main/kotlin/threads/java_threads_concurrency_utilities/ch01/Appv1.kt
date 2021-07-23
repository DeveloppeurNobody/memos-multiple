package threads.java_threads_concurrency_utilities.ch01

object Appv1 {
    @JvmStatic
    fun main(args: Array<String>) {
        var isDaemon: Boolean = args.isNotEmpty();
        var r: Runnable = Runnable {
            var thd: Thread = Thread.currentThread();
            while (true) {
                println(String.format("%s is %salive and in %s state %n", thd.name, thd.isAlive, thd.state));
            }
        }

        var t1: Thread = Thread(r, "thd1");
        if (isDaemon) {
            t1.isDaemon = true;
            println("${t1.name} is ${t1.isAlive} alive and in ${t1.name} state ${t1.state}");
        }

        var t2: Thread = Thread(r);
        t2.name = "thd2";
        if (isDaemon) {
            println("${t2.name} is ${t2.isAlive} alive and in ${t2.name} state ${t2.state}");
        }
        t1.start();
        t2.start();
    }
}