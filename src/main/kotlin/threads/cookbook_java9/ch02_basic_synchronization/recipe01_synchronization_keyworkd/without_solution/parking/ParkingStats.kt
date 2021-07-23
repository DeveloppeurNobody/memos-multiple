package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution.parking

data class ParkingStats(var cash: ParkingCash,
                        var numberCars: Long = 0,
                        var numberMotorcycles: Long = 0) {

    fun carComeIn() {
        numberCars++;
    }

    fun carGoOut() {
        numberCars--;
        cash.vehiclePay();
    }

    fun motoComeIn() {
        numberMotorcycles++;
    }

    fun motoGoOut() {
        numberMotorcycles--;
        cash.vehiclePay();
    }
}
