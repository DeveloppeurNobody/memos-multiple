package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe02_count_down_latch_videoconference

import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe02_count_down_latch_videoconference.task.Participant
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe02_count_down_latch_videoconference.task.Videoconference

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates a VideoConference with 10 participants.
        val conference = Videoconference(10);

        // Creates a thread to run the VideoConference and start it
        val threadConference = Thread(conference);
        threadConference.start();

        // Creates ten participants, a thread for each one and starts them
        repeat(10) {
            val participant = Participant(conference, "Participant $it");
            Thread(participant).start();
        }
    }
}