package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe01_customizing_thread_pool_executor.executor

import java.util.*
import java.util.concurrent.*

class MyExecutor(corePoolSize: Int,
                 maximumPoolSize: Int,
                 keepAliveTime: Long,
                 unit: TimeUnit,
                 workQueue: BlockingQueue<Runnable>
                 ) : ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) {

    val startTimes = ConcurrentHashMap<Runnable, Date>();

    override fun shutdown() {
        println("""
            MyExecutor: Going to shutdown.
            MyExecutor: Executed tasks: $completedTaskCount
            MyExecutor: Running tasks: $activeCount
            MyExecutor: Pending tasks: ${queue.size}
        """.trimIndent());
        super.shutdown();
    }

    override fun shutdownNow(): MutableList<Runnable> {
        println("""
            MyExecutor: Going to immediately shutdown.
            MyExecutor: Executed tasks: $completedTaskCount
            MyExecutor: Running tasks: $activeCount
            MyExecutor: Pending tasks: ${queue.size}
        """.trimIndent());
        return super.shutdownNow()
    }

    override fun beforeExecute(t: Thread?, r: Runnable?) {
        println("MyExecutor: A tasks is beginning: ${t?.name} : ${r.hashCode()}");
        if (r != null) {
            startTimes.put(r, Date())
        };
    }

    override fun afterExecute(r: Runnable?, t: Throwable?) {
        val result = r as Future<*>?;
        try {
            println("""
                *****************************************
                MyExecutor: A task is finishing.
                MyExecutor: Result: ${result?.get()}
            """.trimIndent());
            val startDate: Date = r?.let { startTimes.remove(it) }!!;
            val finishDate: Date = Date();
            val diff = finishDate.time - startDate.time;
            println("""
                MyExecutor: Duration $diff
                *****************************************
            """.trimIndent());
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } catch (ee: ExecutionException) {
            System.err.println(ee)
        }
    }
}

