package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe02_count_down_latch_videoconference.task

import java.util.concurrent.CountDownLatch

/**
 * This class implements the controller of the Videoconference
 *
 * It uses a CountDownLatch to control the arrival of all
 * the participants in the conference
 */
class Videoconference(val number: Int) : Runnable {

    private val controller = CountDownLatch(number);

    /**
     * this is the main method of the Controller of the videoConference.
     * It waits for all the participants and starts the conference
     */
    override fun run() {
        println("VideoConference: Initialization: ${controller.count}");
        try {
            // Wait for all participants
            controller.await();

            // Starts the conference
            println("""
                VideoConference: All the participants have come
                VideoConference: Let's start...
                
                VideoConference in progress.
                
                VideoConference done!
            """.trimIndent());
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
    }

    /**
     * This method is called by every participant when
     * he incorporates to the VideoConference
     * @param name name of participant
     */
    fun arrive(name: String) {
        println("$name has arrived.");

        // This method uses the countDown method to decrement the internal counter
        // of the CountDownLatch
        controller.countDown();
        println("VideoConference: Wainting for ${controller.count}");
    }
}