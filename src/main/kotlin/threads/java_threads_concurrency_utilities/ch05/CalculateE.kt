package threads.java_threads_concurrency_utilities.ch05

import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Supplier

object CalculateE {
    const val LASTITER = 17;

    @JvmStatic
    fun main(args: Array<String>) {
        val executor: ExecutorService = Executors.newFixedThreadPool(1);
        var callable: Callable<BigDecimal> = Callable {
            val mc = MathContext(100, RoundingMode.HALF_UP)
            var result = BigDecimal.ZERO
            for (i in 0..LASTITER) {
                var factorial: BigDecimal = BigDecimal(i);
                result = result.add(factorial);
            }

            result;
        }

        //val taskFuture: Future<BigDecimal> = executor.submit(callable)
      
        try {


        } catch (e: Exception) {
            System.err.println(e);
        }
    }
}