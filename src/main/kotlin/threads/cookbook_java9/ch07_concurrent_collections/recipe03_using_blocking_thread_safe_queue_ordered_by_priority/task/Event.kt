package threads.cookbook_java9.ch07_concurrent_collections.recipe03_using_blocking_thread_safe_queue_ordered_by_priority.task

data class Event(val thread: Int,
                 val priority: Int) : Comparable<Event> {

    override fun compareTo(other: Event): Int {
        return when {
            priority > other.priority -> {
                -1;
            }
            priority < other.priority -> {
                1;
            }
            else -> {
                0;
            }
        }
    }
}