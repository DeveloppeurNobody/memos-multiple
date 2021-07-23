package design_patterns.creational.objects.builder.example_form

internal class Car {
    var name: String
    var price = 0.0

    // Constructor with 1 parameter
    constructor(name: String) {
        this.name = name
        println("1 parameters constructor is called")
        println("Car name :" + this.name)
    }

    // Constructor with 2 parameters
    constructor(name: String, price: Double) {
        this.name = name
        this.price = price
        println("2 parameters constructor is called")
        println("Car name :" + this.name + ", price:" + this.price)
    }

    // method
    fun run() {
        println("$name Car is running...")
    }
}
