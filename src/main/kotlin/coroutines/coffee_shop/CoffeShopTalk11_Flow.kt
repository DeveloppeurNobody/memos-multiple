package coroutines.coffee_shop

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

@ExperimentalCoroutinesApi
fun main() = runBlocking {
    val orders = listOf(
        Menu.Cappuccino(CoffeeBean.Regular, Milk.Whole),
        Menu.Cappuccino(CoffeeBean.Premium, Milk.Breve),
        Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
        Menu.Cappuccino(CoffeeBean.Decaf, Milk.Whole),
        Menu.Cappuccino(CoffeeBean.Regular, Milk.NonFat),
        Menu.Cappuccino(CoffeeBean.Decaf, Milk.NonFat)
    )

    orders.forEach { log(it) }

    val espressoMachine = EspressoMachine(this)

    val ordersFlow: Flow<Menu.Cappuccino> = orders.asFlow()
    val time = measureTimeMillis {
        flowOf(
            processOrders(ordersFlow, espressoMachine),
            processOrders(ordersFlow, espressoMachine)
        )
            .flattenMerge()
            .collect { cappuccino ->
                log("serve: $cappuccino")
            }
    }
    log("time: $time ms")
    espressoMachine.destroy()
}

private suspend fun processOrders(
    orders: Flow<Menu.Cappuccino>,
    espressoMachine: EspressoMachine
): Flow<Beverage.Cappuccino> =
    orders.map {
        val groundBeans = grindCoffeeBeans(it.beans())
        coroutineScope {
            val espressoDeferred = async { espressoMachine.pullEspressoShot(groundBeans) }
            val steamedMilkDeferred = async { espressoMachine.steamMilk(it.milk()) }
            makeCappuccino(it, espressoDeferred.await(), steamedMilkDeferred.await())
        }
    }

private suspend fun grindCoffeeBeans(beans: CoffeeBean): CoffeeBean.GroundBeans {
    log("grinding coffee beans")
    delay(1000)
    return CoffeeBean.GroundBeans(beans)
}

private suspend fun makeCappuccino(order: Menu.Cappuccino, espresso: Espresso,
                                   steamedMilk: Milk.SteamedMilk): Beverage.Cappuccino {
    log("making cappuccino")
    delay(100)
    return Beverage.Cappuccino(order, espresso, steamedMilk)
}