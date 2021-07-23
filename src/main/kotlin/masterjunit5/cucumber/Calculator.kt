package masterjunit5.cucumber

import java.util.*
import java.util.Arrays.asList


class Calculator {
    private val stack: Deque<Number?> = LinkedList()

    fun push(arg: Any?) {
        if (OPS.contains(arg)) {
            val y = stack.removeLast()!!
            val x = (if (stack.isEmpty()) 0 else stack.removeLast())!!
            var value: Double? = null
            if (arg == "-") {
                value = x.toDouble() - y.toDouble()
            } else if (arg == "+") {
                value = x.toDouble() + y.toDouble()
            } else if (arg == "*") {
                value = x.toDouble() * y.toDouble()
            } else if (arg == "/") {
                value = x.toDouble() / y.toDouble()
            }
            push(value)
        } else {
            stack.add(arg as Number?)
        }
    }

    fun value(): Number? {
        return stack.last
    }

    companion object {
        private val OPS: List<String> = mutableListOf("-", "+", "*", "/")
    }
}