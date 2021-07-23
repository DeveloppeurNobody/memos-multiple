package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe06_implementing_thread_factory_interface_to_generate_custom_threads_for_fork_join_framework.task

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ForkJoinWorkerThread

class MyWorkerThreadFactory : ForkJoinPool.ForkJoinWorkerThreadFactory {

    override fun newThread(pool: ForkJoinPool?): ForkJoinWorkerThread = MyWorkerThread(pool!!);

}