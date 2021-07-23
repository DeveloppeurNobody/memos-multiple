package threads.java_threads_concurrency_utilities.ch01

object Appv2 {
    @JvmStatic
    fun main(args: Array<String>) {
        var r: Runnable = Runnable {
            var name = Thread.currentThread().name;
            var count = 0;
            while (!Thread.interrupted()) {
                println("$name : $count");
            }
        }

        var thdA = Thread(r);
        var thdB = Thread(r);
        thdA.start();
        thdB.start();

        while (true) {
            var n = Math.random();
            if (n in 0.49999999..0.50000001) {
                break;
            }
        }
        thdA.interrupt();
        thdB.interrupt();
    }
}