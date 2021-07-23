package coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

object AppCoroutines03_Async_Await {

    // starting a root coroutine
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        println("Start");
        // Creating and starting a Promise called Deferred in Kotlin
        val deferred = async {
            println("--- Start child coroutine");

            delay(5000);
            helloSuspend();

            println("--- End child coroutine");
        }

        // Difference between join and await.
        // join waits for completion of launch.
        // await looks for the returned result.
        deferred.await();
        println("Finished");
    }

    suspend fun helloSuspend() {
        println("Hello Suspending Functions");
    }
}