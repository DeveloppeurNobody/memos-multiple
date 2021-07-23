package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe11_implementing_your_own_stream_generator.core

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe11_implementing_your_own_stream_generator.splititerator.Item
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe11_implementing_your_own_stream_generator.splititerator.MySplitIterator
import java.util.stream.StreamSupport

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val items = Array(10) { arrayOfNulls<Item>(10)}

        repeat(10) { i->
            repeat(10) {j ->
                val item = Item();
                item.row = i;
                item.column = j;
                item.name = "Item $i $j";
                items[i][j] = item;
            }
        }

        val mySplitIterator = MySplitIterator(items, 0, items.size);

        StreamSupport.stream(mySplitIterator, true)
            .forEach { item -> println("${Thread.currentThread().name}: ${item.name}") }

    }
}