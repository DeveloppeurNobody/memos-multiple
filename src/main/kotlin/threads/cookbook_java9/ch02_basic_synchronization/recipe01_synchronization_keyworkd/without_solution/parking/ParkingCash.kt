package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution.parking

data class ParkingCash(var cash: Long = 0) {
    var cost: Int = 2;

    fun vehiclePay() {
        cash += cost;
    }

    fun close() {
        println("Closing accounting");
        var totalAmount = cash;
        cash = 0;
        println("The total amount is : $totalAmount");
    }
}