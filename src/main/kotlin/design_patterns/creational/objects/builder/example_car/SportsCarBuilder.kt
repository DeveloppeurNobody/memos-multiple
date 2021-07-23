package design_patterns.creational.objects.builder.example_car

class SportsCarBuilder : CarBuilder {
    private val car: Car = Car("SPORTS");


    override fun buildBodyStyle() {
        car.bodyStyle = "External dimensions: overall length (inches): 192.3," +
                " overall width (inches): 75.5, overall height (inches): 54.2, wheelbase (inches): 112.3," +
                " front track (inches): 63.7, rear track (inches): 64.1 and curb to curb turning circle (feet): 37.7"
    }

    override fun buildPower() {
        car.power = "323 hp @ 6,800 rpm; 278 ft lb of torque @ 4,800 rpm"
    }

    override fun buildEngine() {
        car.engine = "3.6L V 6 DOHC and variable valve timing"
    }

    override fun buildBreaks() {
        car.breaks =
            "Four-wheel disc brakes: two ventilated. Electronic brake distribution. StabiliTrak stability control"
    }

    override fun buildSeats() {
        car.seats =
            "Driver sports front seat with one power adjustments manual height, front passenger seat sports front seat with one power adjustments"
    }

    override fun buildWindows() {
        car.windows = "Front windows with one-touch on two windows"
    }

    override fun buildFuelType() {
        car.fuelType = "Gasoline 17 MPG city, 28 MPG highway, 20 MPG combined and 380 mi. range"
    }

    override fun getCar(): Car {
        return car;
    }
}