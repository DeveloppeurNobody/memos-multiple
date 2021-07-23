package coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


object AppCoroutines04_FetchExample {

    // root coroutine
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        println("main() attempt to fetch")
        println("main() #creating and starting first job")

        val job = async {
            println("--- start async")
            var response = fetch("https://www.journaldev.com");
            println("--- end async $response");
        }

        println("main() #creating and starting second job")
        val secondJob = launch {
            println("--- second job waiting for first job to finish");
            job.await();
        }

        println("main() #secondJob.join");
        secondJob.join();
    }

    suspend fun fetch(url: String): String {
        println("fetch()")
        delay(2000);
        return """
            <div>Hello</div>
        """.trimIndent()
    }
}