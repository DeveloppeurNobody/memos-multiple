package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe11_implementing_your_own_stream_generator.splititerator

import java.util.*
import java.util.Spliterator.*
import java.util.function.Consumer

class MySplitIterator(
    val items: Array<Array<Item?>>,
    val start: Int,
    var end: Int): Spliterator<Item> {

    var current = start;

    override fun estimateSize(): Long = (end - current).toLong();

    override fun characteristics(): Int {
        return ORDERED or SIZED or SUBSIZED
    }

    override fun tryAdvance(action: Consumer<in Item>?): Boolean {
        println("MySpliterator.tryAdvance.start: $start, $end, $current");
        if (current < end) {
            (items[current].indices).forEach {
                action?.accept(items[current][it]!!);
            }
            current++;
            println("MySPliterator.tryAdvance.end: true\n");
            return true;
        }
        println("MySpliterator.tryAdvance.end: false\n");
        return false;
    }

    override fun forEachRemaining(action: Consumer<in Item>?) {
        println("MySpliterator.forEachRemaining.start\n");
        var ret: Boolean;
        do {
            ret = tryAdvance(action);
        } while (ret);
        println("MySpliterator.forEachReamining.end\n");
    }

    override fun trySplit(): Spliterator<Item>? {
        println("MySpliterator.trySplit.start\n");

        if((end - start) <= 2) {
            println("MySplitterator.trySplit.end\n");
            return null;
        }

        val mid = start + ((end - start) / 2);
        val newStart = mid;
        val newEnd = end;
        end = mid;
        println("MySPliterator.trySplit.end: $start, $mid, $end, $newStart, $newEnd, $current");

        return MySplitIterator(items, newStart, newEnd);
    }
}