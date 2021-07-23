package design_patterns.structural.decorator.pizza.component

interface Pizza {
    fun getDesc(): String;
    fun getPrice(): Double;
}