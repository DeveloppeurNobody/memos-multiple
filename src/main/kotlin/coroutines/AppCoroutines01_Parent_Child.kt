package coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object AppCoroutines01_Parent_Child {
    suspend fun helloSuspend() {
        println("Hello Suspending Functions")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Start")

        // parent coroutine
        runBlocking {

            // child coroutine
            launch {
                delay(2000);
                helloSuspend()
                println("Inside launch");
            }
        }

        println("Finished")
        //coroutines.helloSuspend() //this won't compile
    }
}