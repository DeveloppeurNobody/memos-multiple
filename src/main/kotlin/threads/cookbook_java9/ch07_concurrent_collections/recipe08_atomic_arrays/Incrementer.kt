package threads.cookbook_java9.ch07_concurrent_collections.recipe08_atomic_arrays

import java.util.concurrent.atomic.AtomicIntegerArray

class Incrementer(val vector: AtomicIntegerArray) : Runnable {

    override fun run() {
        (0 until vector.length()).forEach {
            vector.getAndIncrement(it);
        }
    }
}