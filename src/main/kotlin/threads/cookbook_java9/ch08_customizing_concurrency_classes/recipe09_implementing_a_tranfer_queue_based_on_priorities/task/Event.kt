package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task

data class Event(val thread: String,
                 val priority: Int) : Comparable<Event> {

    override fun compareTo(other: Event): Int  = other.priority.compareTo(this.priority);
}