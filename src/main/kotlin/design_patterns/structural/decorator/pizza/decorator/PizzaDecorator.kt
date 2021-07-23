package design_patterns.structural.decorator.pizza.decorator

import design_patterns.structural.decorator.pizza.component.Pizza

abstract class PizzaDecorator : Pizza {
    override fun getDesc(): String {
        return "Toppings";
    }
}

class Broccoli(private val pizza: Pizza) : PizzaDecorator() {
    override fun getDesc(): String {
        return "${pizza.getDesc()}, Broccoli (9.25)";
    }

    override fun getPrice(): Double {
        return pizza.getPrice() + 9.25;
    }
}

class Cheese(private val pizza: Pizza) : PizzaDecorator() {
    override fun getDesc(): String {
        return "${pizza.getDesc()}, Cheese (20.72)";
    }

    override fun getPrice(): Double {
        return pizza.getPrice() + 20.72;
    }
}

class Chicken(private val pizza: Pizza) : PizzaDecorator() {
    override fun getDesc(): String {
        return "${pizza.getDesc()}, Chicken (12.75)";
    }

    override fun getPrice(): Double {
        return pizza.getPrice() + 12.75;
    }
}

