package threads.cookbook_java9.ch07_concurrent_collections.recipe06_thread_safe_hash_maps.util

import java.util.*

data class Operation(var user: String = "",
                     var operation: String = "",
                     var time: Date = Date())