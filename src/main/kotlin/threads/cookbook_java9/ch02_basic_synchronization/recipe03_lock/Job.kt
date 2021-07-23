package threads.cookbook_java9.ch02_basic_synchronization.recipe03_lock

/**
 * This class simulates a job that send a document to print
 */
class Job(private val printQueue: PrintQueue) : Runnable {

    override fun run() {
        println("${Thread.currentThread().name}: Going to print a document");

        printQueue.printJob(Object());

        println("${Thread.currentThread().name}: The document has been printed");
    }
}