package threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.task

class Company(val account: Account) : Runnable {

    override fun run() {
        repeat(100) {
            println("+ adding amount of 1000")
            account.addAmount(1000);
        }
    }
}