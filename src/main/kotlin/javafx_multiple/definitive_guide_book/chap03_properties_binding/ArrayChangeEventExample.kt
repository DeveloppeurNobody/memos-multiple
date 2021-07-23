package javafx_multiple.definitive_guide_book.chap03_properties_binding

import javafx.collections.FXCollections
import javafx.collections.ObservableIntegerArray

object ArrayChangeEventExample {

    @JvmStatic
    fun main(args: Array<String>) {
        val ints: ObservableIntegerArray = FXCollections.observableIntegerArray(10, 20);

        ints.addListener { observableArray, sizeChanged, from, to ->
            val sb = StringBuilder("\tObservable Array = ")
                .append(observableArray)
                .append("\n")
                .append("\t\tsizeChanged = ")
                .append(sizeChanged).append("\n")
                .append("\t\tfrom = ")
                .append(from).append("\n")
                .append("\t\tto = ")
                .append(to).append("\n")

            println(sb.toString());
        }

        ints.ensureCapacity(20);

        println("Calling addAll(30, 40): ");
        ints.addAll(30, 40);

        val src: Array<Int> = arrayOf(50, 60, 70);
        println("Calling addAll(src, 1, 2)");
        ints.addAll(src.toIntArray(), 1, 2);

        println("Calling set(0, src, 0, 1)");
        ints.set(0, src.toIntArray(), 0, 1)

        ints.trimToSize();

        val ints2 = FXCollections.observableIntegerArray();
        ints2.resize(ints.size())

        println("Calling copyTo(0, ints2, 0, ints.size()");

        ints.copyTo(0, ints2, 0, ints.size())

        println("\tDestination = ints2");
    }
}