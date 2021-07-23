import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import java.util.concurrent.ThreadLocalRandom

object HotPublisher {
    val source1: DirectProcessor<String> = DirectProcessor.create();
    val source2: DirectProcessor<String> = DirectProcessor.create();

    val source1WithError: DirectProcessor<String> = DirectProcessor.create();

    @JvmStatic
    fun main(args: Array<String>) {
        Thread(r1).start();
        Thread(r2).start();
        Thread(r1WithError).start();

        // Simple subscription
//        source1.subscribe(::println)
//        source2.subscribe(::println);



        /*
         * The concat method works as shown here.
         * It connects to the data sources sequentially in the given order
         * and collects all the data emitted from the sources. That is,
         * it first collects all the data from source1, then connects to source2 and collects the data and so on.
         */

        // concat - concat 2 sources and direct the emitted data through single pipeline
//        Flux
//            .concat(source1, source2)
//            .subscribe(::println);

        // concatWith
//        source1
//            .concatWith(source2)
//            .subscribe(::println);

        // concatDelayError
//        source1WithError.subscribe(::println);

        // Zip will work as long as both sources emit data. Any of the sources completes/throws error, it will stop.
        Flux
            .zip(source1, source2)
            .subscribe {
                // Zip passes the tuple object downstream – we can get the specific object we are interested in as shown below.
                println("""
                    First  : ${it.t1}
                    Second : ${it.t2}
                """.trimIndent());
            }

    }

     val r1 = Runnable {
        repeat(5) {
            val idle = ThreadLocalRandom.current().nextInt(100, 1000).toLong()
            Thread.sleep(idle);

            source1.onNext("source 1 - $it");
        }

        source1.onComplete();
    }

    val r1WithError = Runnable {
        repeat(5) {
            val idle = ThreadLocalRandom.current().nextInt(100, 1000).toLong()
            Thread.sleep(idle);

            source1WithError.onNext("source 1 - $it");
        }

        source1WithError.onError(RuntimeException("source1 error"));
    }

    val r2 = Runnable {
        repeat(10) {
            val idle = ThreadLocalRandom.current().nextInt(100, 1000).toLong();
            Thread.sleep(idle);

            source2.onNext("source 2 - $it");
        }

        source2.onComplete();
    }


}