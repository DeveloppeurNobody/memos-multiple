package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe07_customizing_tasks_running_in_fork_join_framework.task

class Task(name: String,
           val array: Array<Int>,
           val start: Int,
           val end: Int) : MyWorkerTask(name) {

    companion object {
        const val serialVersionUID = 1L;
    }


    override fun compute() {
        if ((end - start) > 100) {
            val mid = (end + start) / 2;
            val task1 = Task("$name 1", array, start, mid);
            val task2 = Task("$name 2", array, mid, end);
            invokeAll(task1, task2);
        } else {
            (start until end).forEach {
                array[it]++;
            }
            try {
                Thread.sleep(50);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        }
    }

    override fun setRawResult(value: Void?) {

    }
}

