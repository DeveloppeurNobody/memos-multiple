package threads.cookbook_java9.ch07_concurrent_collections.recipe06_thread_safe_hash_maps.task

import threads.cookbook_java9.ch07_concurrent_collections.recipe06_thread_safe_hash_maps.util.Operation
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

class HashFiller(val userHash: ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>>) : Runnable {

    override fun run() {
        val randomGenerator = java.util.Random();
        repeat(100) {
            val operation = Operation();
            val user = "USER ${randomGenerator.nextInt(100)}";
            operation.user = user;
            val action = "OP ${randomGenerator.nextInt(10)}";
            operation.operation = action;
            operation.time = Date();

            addOperationToHash(userHash, operation);
        }
    }

    private fun addOperationToHash(
        userHash: ConcurrentHashMap<String, ConcurrentLinkedDeque<Operation>>,
        operation: Operation
    ) {
        val opList = userHash.computeIfAbsent(operation.user) { ConcurrentLinkedDeque(); }
        opList.add(operation);
    }
}