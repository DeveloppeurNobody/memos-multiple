package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution.parking


class ParkingStats(cash: ParkingCash) {
    /**
     * This two variables store the number of cars and motorcycles in the parking
     */
    private var numberCars: Long = 0
    private var numberMotorcycles: Long = 0

    /**
     * Two objects for the synchronization. ControlCars synchronizes the
     * access to the numberCars attribute and controlMotorcycles synchronizes
     * the access to the numberMotorcycles attribute.
     */
    private val controlCars: Any = Any()
    private val controlMotorcycles: Any = Any()

    private val cash: ParkingCash = cash

  //  @Synchronized
    fun getNumberCars(): Long {
        synchronized(controlCars) { return numberCars }
    }

    fun getNumberMotorcycles(): Long {
        synchronized(controlMotorcycles) { return numberMotorcycles }
    }



    fun carComeIn() {
        synchronized(controlCars) { numberCars++ }
    }

    fun carGoOut() {
        synchronized(controlCars) { numberCars-- }
        cash.vehiclePay()
    }

    fun motoComeIn() {
        synchronized(controlMotorcycles) { numberMotorcycles++ }
    }

    fun motoGoOut() {
        synchronized(controlMotorcycles) { numberMotorcycles-- }
        cash.vehiclePay()
    }


}
