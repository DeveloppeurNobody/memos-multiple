package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe02_implementing_priority_based_executor

import java.util.concurrent.TimeUnit

class MyPriorityTask(val name: String, val priority: Int) : Runnable, Comparable<MyPriorityTask> {

    override fun compareTo(other: MyPriorityTask): Int = other.priority.compareTo(this.priority);

    override fun run() {
        println("MyPriorityTask: $name : $priority");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
            Thread.currentThread().interrupt();
        }
    }

}