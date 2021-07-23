package coroutines.coffee_shop

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

object CoffeShopTalk05_Solution_Wait_For_Launch {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val orders = listOf(
            Menu.Cappuccino(CoffeeBean.Regular, Milk.Whole),
            Menu.Cappuccino(CoffeeBean.Premium, Milk.Breve),
            Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
            Menu.Cappuccino(CoffeeBean.Decaf, Milk.Whole),
            Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
            Menu.Cappuccino(CoffeeBean.Decaf, Milk.NonFat)
        )

        orders.forEach { log(it) }

        val time = measureTimeMillis {
            // create and start a child coroutine
            coroutineScope {
                launch(CoroutineName("barista-1")) { processOrders(orders) }
                launch(CoroutineName("barista-2")) { processOrders(orders) }
            }
        }
        log("time: $time ms")
    }

    private suspend fun processOrders(orders: List<Menu.Cappuccino>) {
        orders.forEach {
            val groundBeans = grindCoffeeBeans(it.beans())
            val espresso = pullEspressoShot(groundBeans)
            val steamedMilk = steamMilk(it.milk())
            val cappuccino = makeCappuccino(it, espresso, steamedMilk)
            log("serve: $cappuccino")
        }
    }

    private suspend fun grindCoffeeBeans(beans: CoffeeBean): CoffeeBean.GroundBeans {
        log("grinding coffee beans")
        delay(100)
        return CoffeeBean.GroundBeans(beans)
    }

    private suspend fun pullEspressoShot(groundBeans: CoffeeBean.GroundBeans): Espresso {
        log("pulling espresso shot")
        delay(60)
        return Espresso(groundBeans)
    }

    private suspend fun steamMilk(milk: Milk): Milk.SteamedMilk {
        log("steaming milk")
        delay(30)
        return Milk.SteamedMilk(milk)
    }

    private suspend fun makeCappuccino(
        order: Menu.Cappuccino, espresso: Espresso,
        steamedMilk: Milk.SteamedMilk
    ): Beverage.Cappuccino {
        log("making cappuccino")
        delay(10)
        return Beverage.Cappuccino(order, espresso, steamedMilk)
    }
}