package coroutines.coffee_shop

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.system.measureTimeMillis

object CoffeShopTalk09_Produce_With_CompletableDeferred {

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

        val ordersChannel = produce(CoroutineName("cashier")) {
            orders.forEach { send(it) }
        }

        val espressoMachine = EspressoMachine(this)
        val time = measureTimeMillis {
            coroutineScope {
                launch(CoroutineName("barista-1")) { processOrders(ordersChannel, espressoMachine) }
                launch(CoroutineName("barista-2")) { processOrders(ordersChannel, espressoMachine) }
            }
        }
        log("time: $time ms")

        espressoMachine.destroy()
    }

    // Adding Espresso
    private suspend fun processOrders(orders: ReceiveChannel<Menu.Cappuccino>, espressoMachine: EspressoMachine) {
        orders.consumeEach {
            val groundBeans = grindCoffeeBeans(it.beans())
            val espresso = espressoMachine.pullEspressoShot(groundBeans)
            val steamedMilk = espressoMachine.steamMilk(it.milk())
            val cappuccino = makeCappuccino(it, espresso, steamedMilk)
            log("serve: $cappuccino")
        }
    }

    private suspend fun grindCoffeeBeans(beans: CoffeeBean): CoffeeBean.GroundBeans {
        log("grinding coffee beans")
        delay(100)
        return CoffeeBean.GroundBeans(beans)
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