package coroutines.coffee_shop

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

object CoffeeShopTalk03_wait_for_launch {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val orders = listOf(
            Menu.Cappuccino(CoffeeBean.Regular, Milk.Whole),
            Menu.Cappuccino(CoffeeBean.Premium, Milk.Breve),
            Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
            Menu.Cappuccino(CoffeeBean.Decaf, Milk.Whole),
            Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
            Menu.Cappuccino(CoffeeBean.Decaf, Milk.NonFat)
        );

        orders.forEach { println(it) }

        val time = measureTimeMillis {
            // replace forEach by coroutine

//            orders.forEach {
//                val groundBreans = grindCoffeeBeans(it.beans);
//                val espresso = pullEspressoShot(groundBreans);
//                val steamedMilk = steamMilk(it.milk);
//                val cappuccino = makeCappuccino(it, espresso, steamedMilk);
//                println("serve: $cappuccino");
//            }

            launch(CoroutineName("barista-1") + Dispatchers.IO) { processOrders(orders) }
            launch(CoroutineName("barista-2") + Dispatchers.IO) { processOrders(orders) }
        }
        println("==> time $time ms");
    }
}

private fun processOrders(orders: List<Menu.Cappuccino>) {
    orders.forEach {
        val groundBreans = grindCoffeeBeans(it.beans);
        val espresso = pullEspressoShot(groundBreans);
        val steamedMilk = steamMilk(it.milk);
        val cappuccino = makeCappuccino(it, espresso, steamedMilk);
        println("serve: $cappuccino");
    }
}

private fun grindCoffeeBeans(beans: CoffeeBean): CoffeeBean.GroundBeans {
    println("grinding coffee beans")
    sleep(100)
    return CoffeeBean.GroundBeans(beans)
}

private fun pullEspressoShot(groundBeans: CoffeeBean.GroundBeans): Espresso {
    println("pulling espresso shot")
    sleep(60)
    return Espresso(groundBeans)
}

private fun steamMilk(milk: Milk): Milk.SteamedMilk {
    println("steaming milk")
    sleep(30)
    return Milk.SteamedMilk(milk)
}

private fun makeCappuccino(
    order: Menu.Cappuccino,
    espresso: Espresso,
    steamedMilk: Milk.SteamedMilk
): Beverage.Cappuccino {
    println("making cappuccino")
    sleep(10)
    return Beverage.Cappuccino(order, espresso, steamedMilk)
}

