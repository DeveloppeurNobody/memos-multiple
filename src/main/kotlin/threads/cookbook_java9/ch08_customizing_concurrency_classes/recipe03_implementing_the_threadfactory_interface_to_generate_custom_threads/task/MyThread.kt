package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe03_implementing_the_threadfactory_interface_to_generate_custom_threads.task

import java.util.*

class MyThread(target: Runnable,
               name: String
) : Thread(target, name) {

    val creationDate = Date();

    lateinit var startDate: Date;
    lateinit var finishDate: Date;

    override fun run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    @Synchronized fun setStartDate() {
        startDate = Date();
    }

    @Synchronized fun setFinishDate() {
        finishDate = Date();
    }

    @Synchronized fun getExecutionTime(): Long = finishDate.time - startDate.time;

    @Synchronized
    override fun toString(): String {
        val buffer = StringBuilder();
        buffer.append(name);
        buffer.append(": ");
        buffer.append(" Creation Date: ");
        buffer.append(creationDate);
        buffer.append(" : Running time: ");
        buffer.append(getExecutionTime());
        buffer.append(" Milliseconds.");
        return buffer.toString();
    }


}