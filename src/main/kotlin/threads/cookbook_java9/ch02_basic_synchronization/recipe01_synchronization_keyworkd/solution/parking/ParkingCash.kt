package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution.parking

data class ParkingCash(var cash: Long = 0) {
    val cost: Int = 2;

    @Synchronized fun vehiclePay() {
        cash += cost;
    }

    fun close() {
        println("Closing accounting");

        synchronized(this){
            val totalAmount = cash;
            cash = 0;
            println("The total amount is $totalAmount");
        }
    }
}