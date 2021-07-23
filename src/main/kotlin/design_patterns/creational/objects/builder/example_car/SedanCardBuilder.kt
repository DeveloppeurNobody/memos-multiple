package design_patterns.creational.objects.builder.example_car

class SedanCardBuilder : CarBuilder {
    private val car: Car = Car("Sedan");

    override fun buildBodyStyle() {
        car.bodyStyle = "External dimensions: overall length (inches): 202.9, " +
                "overall width (inches): 76.2, overall height (inches): 60.7, wheelbase (inches): 112.9," +
                " front track (inches): 65.3, rear track (inches): 65.5 and curb to curb turning circle (feet): 39.5"
    }

    override fun buildPower() {
        car.power = "285 hp @ 6,500 rpm; 253 ft lb of torque @ 4,000 rpm"
    }

    override fun buildEngine() {
        car.engine = "3.5L Duramax V 6 DOHC"
    }

    override fun buildBreaks() {
        car.breaks = "Four-wheel disc brakes: two ventilated. Electronic brake distribution";
    }

    override fun buildSeats() {
        car.seats = "Front seat center armrest.Rear seat center armrest.Split-folding rear seats"
    }

    override fun buildWindows() {
        car.windows = "Laminated side windows.Fixed rear window with defroster"
    }

    override fun buildFuelType() {
        car.fuelType = "Gasoline 19 MPG city, 29 MPG highway, 23 MPG combined and 437 mi. range"
    }

    override fun getCar(): Car {
        return this.car;
    }
}