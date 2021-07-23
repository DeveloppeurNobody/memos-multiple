package design_patterns.structural.decorator.pizza.component.impl

import design_patterns.structural.decorator.pizza.component.Pizza

class SimplyVegPizza : Pizza {
    override fun getDesc(): String {
        return "SimplyVegPizza (230)";
    }

    override fun getPrice(): Double {
        return 230.0;
    }
}