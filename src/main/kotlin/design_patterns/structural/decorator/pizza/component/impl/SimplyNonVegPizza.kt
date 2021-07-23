package design_patterns.structural.decorator.pizza.component.impl

import design_patterns.structural.decorator.pizza.component.Pizza

class SimplyNonVegPizza : Pizza {
    override fun getDesc(): String {
        return "SimplyNonVegPizza (350)";
    }

    override fun getPrice(): Double {
        return 350.0;
    }
}