package threads.cookbook_java9.ch07_concurrent_collections.recipe10_using_variable_handles_new_feature_java9

import threads.cookbook_java9.ch07_concurrent_collections.recipe10_using_variable_handles_new_feature_java9.data.Account
import threads.cookbook_java9.ch07_concurrent_collections.recipe10_using_variable_handles_new_feature_java9.task.Decrementer
import threads.cookbook_java9.ch07_concurrent_collections.recipe10_using_variable_handles_new_feature_java9.task.Incrementer

fun main() {
    val account = Account();

    val threadIncrementer = Thread(Incrementer(account));
    val threadDecrementer = Thread(Decrementer(account));

    threadIncrementer.start();
    threadDecrementer.start();

    try {
        threadIncrementer.join();
        threadDecrementer.join();
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

    println("""
        Safe amount: ${account.amount}
        Unsage amount: ${account.unsafeAmount}
    """.trimIndent())
}