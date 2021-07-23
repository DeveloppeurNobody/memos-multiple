package threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.task

class Bank(val account: Account) : Runnable {

    override fun run() {
        repeat(100) {
            println("- subtracting amount of 1000")
            account.substractAmount(1000);
        }
    }
}