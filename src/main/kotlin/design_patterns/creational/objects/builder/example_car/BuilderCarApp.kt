package design_patterns.creational.objects.builder.example_car

object BuilderCarApp {
    @JvmStatic
    fun main(args: Array<String>) {
        var carBuilder: CarBuilder = SedanCardBuilder();
        var director: CardDirector = CardDirector(carBuilder);

        director.build();
        var car: Car = carBuilder.getCar();
        println(car);

        carBuilder = SportsCarBuilder();
        director = CardDirector(carBuilder);

        director.build();
        car = carBuilder.getCar();
        println(car);
    }
}