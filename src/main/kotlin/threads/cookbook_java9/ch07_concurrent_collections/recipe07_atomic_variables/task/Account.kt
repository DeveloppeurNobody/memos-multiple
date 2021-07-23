package threads.cookbook_java9.ch07_concurrent_collections.recipe07_atomic_variables.task

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.DoubleAccumulator
import java.util.concurrent.atomic.LongAdder
import java.util.function.DoubleBinaryOperator

class Account {
    var balance = AtomicLong();
    var operations = LongAdder();
    var commission = DoubleAccumulator(DoubleBinaryOperator { x, y -> x+y * 0.2}, 0.0);


    fun getBalance(): Long = balance.get();

    fun setBalance(balance: Long) {
        this.balance.set(balance);
        operations.reset();
        commission.reset();
    }

    fun addAmount(amount: Long) {
        this.balance.getAndAdd(amount);
        this.operations.increment();
        this.commission.accumulate(amount.toDouble());
    }

    fun substractAmount(amount: Long) {
        balance.getAndAdd(-amount);
        operations.increment();
        commission.accumulate(amount.toDouble());
    }

    fun getOperations(): Long = operations.toLong();

    fun getCommission(): Double = commission.get();

}