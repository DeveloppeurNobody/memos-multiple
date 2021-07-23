package design_patterns.creational.objects.builder.example_car

open class CardDirector(open var carBuilder: CarBuilder) {
    fun build() {
        carBuilder.buildBodyStyle();
        carBuilder.buildPower();
        carBuilder.buildEngine();
        carBuilder.buildBreaks();
        carBuilder.buildSeats();
        carBuilder.buildWindows();
        carBuilder.buildFuelType();
    }
}