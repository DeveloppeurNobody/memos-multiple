package threads.cookbook_java9.ch01_thread_management.recipe01_ten_threads

import threads.cookbook_java9.ch01_thread_management.recipe01_ten_threads.task.Calculator
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

/**
 * Main class of the example
 */
object Main {
    private val pathLog = Paths.get("/home/ryu", "log-threads-recipe01.txt");


    /**
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {
        // Thread priority information
        println("""
            Minimum Priority: ${Thread.MIN_PRIORITY}
            Normal Priority: ${Thread.NORM_PRIORITY}
            Maximum Priority: ${Thread.MAX_PRIORITY}
        """.trimIndent());

        val threads = arrayOfNulls<Thread>(10);
        val status = arrayOfNulls<Thread.State>(10);

        (0 until 10).forEach {
            threads[it] = Thread(Calculator());
            if (it % 2 == 0) {
                threads[it]?.priority = Thread.MAX_PRIORITY;
            } else {
                threads[it]?.priority = Thread.MIN_PRIORITY;
            }
            threads[it]?.name = "My Thread $it";
        }

        // Wait for the finalization of the threads. Meanwhile,
        // write for the status of those threads in a file.
        try {
            FileChannel.open(pathLog, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND)).use { fileChannel ->
                // Write the status of the threads
                (0 until 10).forEach {

                    fileChannel.write(ByteBuffer.wrap("Main : Status of Thread $it : ${threads[it]?.state}".toByteArray()));
                    status[it] = threads[it]?.state;
                }
            }
                // Start the ten threads
                threads.forEach { it?.start(); }

                // Wait for the finalization of the threads. We save the status of
                // the threads and only write the status if it changes.
                var finish = false;

                while (!finish) {
                    (0 until 10).forEach {
                        if (threads[it]?.state != status[it]) {
                            writeThreadInfo(
                                threads[it],
                                status[it]
                            );
                            status[it] = threads[it]?.state;
                        }
                    }

                    finish = true;

                    (0 until 10).forEach {
                        finish = finish && (threads[it]?.state == Thread.State.TERMINATED);
                    }

                }

        } catch (e: Exception) {
            System.err.println(e);
        }
    }

    /**
     * This method writes the of a thread in a file
     */
    private fun writeThreadInfo(thread: Thread?, state: Thread.State?) {
        val buffer = ByteBuffer.wrap("""
            Main : Id ${thread?.id} - ${thread?.name}
            Main : Priority: ${thread?.priority}
            Main : Old State: ${thread?.state}
            Main : New State: ${thread?.state}
            Main : ********************************\n
        """.trimIndent().toByteArray());

        FileChannel.open(pathLog, EnumSet.of(StandardOpenOption.CREATE, StandardOpenOption.APPEND)).use { fileChannel ->
            fileChannel.write(buffer);
        }
    }
}