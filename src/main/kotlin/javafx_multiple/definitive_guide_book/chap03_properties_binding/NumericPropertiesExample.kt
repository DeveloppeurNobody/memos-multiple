package javafx_multiple.definitive_guide_book.chap03_properties_binding

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty

object NumericPropertiesExample {
    @JvmStatic
    fun main(args: Array<String>) {
        var i = SimpleIntegerProperty(null, "i", 1024);
        var l = SimpleLongProperty(null, "l", 0L);
        var f = SimpleFloatProperty(null, "f", 0.0F);
        var d = SimpleDoubleProperty(null, "d", 0.0);

        println("""
            Constructed numerical properties i, l, f, d.
            i.get() ${i.get()}
            l.get() ${l.get()}
            f.get() ${f.get()}
            d.get() ${d.get()}
        """.trimIndent());

        l.bind(i);
        f.bind(l);
        d.bind(f);
        println("Bound l to i, f to l, d to f");

        println("""
            i.get() ${i.get()}
            l.get() ${l.get()}
            f.get() ${f.get()}
            d.get() ${d.get()}
        """.trimIndent());

        println("Calling i.set(2048).");
        i.set(2048);

        println("""
            i.get() ${i.get()}
            l.get() ${l.get()}
            f.get() ${f.get()}
            d.get() ${d.get()}
        """.trimIndent());

        d.unbind();
        f.unbind();
        l.unbind();
        l.unbind();
        println("Unbound l to i, f to l, d to f.");

        f.bind(d);
        l.bind(f);
        i.bind(l);
        println("Bound f to d, l to f, i to l.");

        println("Calling d.set(10000000000L).");
        d.set(10000000000.0);

        println("""
            i.get() ${i.get()}
            l.get() ${l.get()}
            f.get() ${f.get()}
            d.get() ${d.get()}
        """.trimIndent());
    }
}