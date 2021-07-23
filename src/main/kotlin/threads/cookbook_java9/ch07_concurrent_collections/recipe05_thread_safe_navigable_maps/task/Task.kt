package threads.cookbook_java9.ch07_concurrent_collections.recipe05_thread_safe_navigable_maps.task

import threads.cookbook_java9.ch07_concurrent_collections.recipe05_thread_safe_navigable_maps.util.Contact
import java.util.concurrent.ConcurrentSkipListMap

class Task(val map: ConcurrentSkipListMap<String, Contact>,
           val id: String) : Runnable {

    override fun run() {
        repeat(1000) {
            val contact = Contact(id, "${it + 1000}");
            map.put("${id}${contact.phone}", contact);
        }
    }
}

