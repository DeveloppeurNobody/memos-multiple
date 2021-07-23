package threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.core

import threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.task.Account
import threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.task.Bank
import threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.task.Company

fun main() {
    // Create a new account
    val account = Account();

    // an initialize its balance to 1000
    account.setBalance(1000);

    // Create a new Company and a Thread to run its task
    val company = Company(account);
    val companyThread = Thread(company);

    // Create a new Bank and a Thread to run its task
    val bank = Bank(account);
    val bankThread = Thread(bank);

    // Prints the initial balance
    println("Account: Initial Balance: ${account.getBalance()}");

    // Start the Threads
    companyThread.start();
    bankThread.start();

    try {
        // Wait for the finalization of the threads
        companyThread.join();
        bankThread.join();

        // Print the final balance
        println("""
            Account: Final Balance: ${account.getBalance()}
            Account: Number of Operations: ${account.getOperations()}
            Account: Accumulated commissions: ${account.commission}
        """.trimIndent());
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

}