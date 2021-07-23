package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.data

import java.util.*

data class News (var title: String = "",
                 var content: String = "",
                 var date: Date = Date())