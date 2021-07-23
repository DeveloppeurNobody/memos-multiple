package design_patterns.creational.objects.builder.example_car

interface CarBuilder {
    fun buildBodyStyle();
    fun buildPower();
    fun buildEngine();
    fun buildBreaks();
    fun buildSeats();
    fun buildWindows();
    fun buildFuelType();

    fun getCar(): Car;
}