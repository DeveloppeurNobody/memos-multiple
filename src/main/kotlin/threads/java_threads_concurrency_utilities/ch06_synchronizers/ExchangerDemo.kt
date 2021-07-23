package threads.java_threads_concurrency_utilities.ch06_synchronizers

import java.util.concurrent.Exchanger

/**
 * An exchanger provides a synchronization point where threads can swap objects.
 * Each thread presents some object on entry to the exchanger’s exchange() method,
 * matches with a partner thread, and receives its partner’s object on return.
 */
object ExchangerDemo {
   // val exchanger: Exchanger<DataBuffer>
}

class DataBuffer(val prefix: String = "") {
    private val MAXITEMS: Int = 10;
    val items: MutableList<String> = mutableListOf();

//    init {
//        for ()
//    }
}