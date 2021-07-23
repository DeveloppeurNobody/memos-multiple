package threads.cookbook_java9.ch01_thread_management.recipe04_sleeping_and_resume.task

import java.lang.Exception
import java.time.Instant
import java.util.concurrent.TimeUnit

class ConsoleClock : Runnable {
    override fun run() {
        (0 until 10).forEach {
            println("${Instant.now()}");
            try {
                // Sleeping during one second
                TimeUnit.SECONDS.sleep(1);
            } catch (e: Exception) {
                println("The FileClock has been interrupted");
            }
        }
    }
}

