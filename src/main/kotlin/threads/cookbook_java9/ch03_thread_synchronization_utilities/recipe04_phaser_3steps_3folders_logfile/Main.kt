package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe04_phaser_3steps_3folders_logfile

import java.util.concurrent.Phaser

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates a Phaser with three participants.
        val phaser = Phaser(3);

        // Creates 3 FileSearch objects; Each of theme search in different directory
        val system = FileSearch("/var/log", "log", phaser);
        val apps = FileSearch("/home/ryu", "log", phaser);
        val documents = FileSearch("/home/ryu/Documents", "log", phaser);

        // Creates a thread to run the system FileSearch and starts it
        val systemThread = Thread(system, "System");
        systemThread.start();

        // Creates a thread to run the apps FileSearch and starts it
        val appsThread = Thread(apps, "Apps");
        appsThread.start();

        // Creates a thread to run the documents FileSearch and starts it
        val documentsThread = Thread(documents, "Documents");
        documentsThread.start();

        try {
            systemThread.join();
            appsThread.join();
            documentsThread.join();
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        println("Terminated: ${phaser.isTerminated}");
    }
}