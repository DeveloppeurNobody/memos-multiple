package threads.cookbook_java9.ch07_concurrent_collections.recipe06_thread_safe_hash_maps.core

import threads.cookbook_java9.ch07_concurrent_collections.recipe06_thread_safe_hash_maps.task.HashFiller
import threads.cookbook_java9.ch07_concurrent_collections.recipe06_thread_safe_hash_maps.util.Operation
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

fun main() {
    val userHash: ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>> = ConcurrentHashMap();
    val hashFiller = HashFiller(userHash);

    val threads = arrayOfNulls<Thread>(10);
    repeat(10) {
        threads[it] = Thread(hashFiller);
        threads[it]?.start();
    }

    // Wait until all threads are finished
    threads.forEach {
        try {
            it?.join();
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
    }

    println("Size: ${userHash.size}");

    userHash.forEach(10) { user: String, list: ConcurrentLinkedDeque<Operation> ->
        println("${Thread.currentThread().name} $user ${list.size}");
    }

    userHash.forEachEntry(10) { entry -> println("${Thread.currentThread().name}: ${entry.key}: ${entry.value.size}"); }

    val op = userHash.search(10) { user, list ->
        list.filter { it.operation.endsWith("1") }
            .get(0);
    }

    println("The operation we have found is ${op.user}, ${op.operation}, ${op.time}");

    val operations = userHash.search(10) { user, list ->
        if (list.size > 10) {
            list;
        } else {
            null;
        }
    }

    println("The user we have found is ${operations?.first?.user}: ${operations?.size}");

    val totalSize = userHash.reduce(10,
        { _: String, list: ConcurrentLinkedDeque<Operation> -> list.size; },
        { n1, n2 -> n1 + n2 });

    println("The total size: $totalSize");





}