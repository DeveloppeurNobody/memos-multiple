package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe02_count_down_latch_videoconference.task

import java.util.concurrent.TimeUnit

/**
 * This class implements a participant in the VideoConference
 */
class Participant(val conference: Videoconference,
                  val name: String) : Runnable {

    /**
     * Core method of the participant.
     * Waits a random time and joins the VideoConference
     */
    override fun run() {
        val duration: Long = (Math.random() * 10).toLong();
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        conference.arrive(name);
    }
}

