package threads.java_threads_concurrency_utilities.ch01

import java.math.BigDecimal
import java.math.BigDecimal.ROUND_HALF_EVEN

object Appv3 {
    val FOUR = BigDecimal.valueOf(4);

    const val roundingMode = ROUND_HALF_EVEN;
    lateinit var result: BigDecimal;

    @JvmStatic
    fun main(args: Array<String>) {
        var r: Runnable = Runnable {
            result = computePi(50000);
        }

        var t: Thread = Thread(r);
        t.start();

        try {
            t.join();
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        println(result);
    }

    fun computePi(digits: Int): BigDecimal {
        val scale = digits + 5
        val arctan1_5 = arctan(5, scale)
        val arctan1_239 = arctan(239, scale)
        val pi = arctan1_5.multiply(FOUR).subtract(arctan1_239).multiply(FOUR)
        return pi.setScale(digits, BigDecimal.ROUND_HALF_UP)
    }

    /*
     * Compute the value, in radians, of the arctangent of the inverse of
     * the supplied integer to the specified number of digits after the
     * decimal point. The value is computed using the power series
     * expansion for the arc tangent:
     *
     * arctan(x) = x-(x^3)/3+(x^5)/5-(x^7)/7+(x^9)/9 ...
     */

    /*
     * Compute the value, in radians, of the arctangent of the inverse of
     * the supplied integer to the specified number of digits after the
     * decimal point. The value is computed using the power series
     * expansion for the arc tangent:
     *
     * arctan(x) = x-(x^3)/3+(x^5)/5-(x^7)/7+(x^9)/9 ...
     */
    fun arctan(inverseX: Int, scale: Int): BigDecimal {
        var result: BigDecimal
        var numer: BigDecimal
        var term: BigDecimal
        val invX = BigDecimal.valueOf(inverseX.toLong())
        val invX2 = BigDecimal.valueOf(inverseX * inverseX.toLong())
        numer = BigDecimal.ONE.divide(invX, scale, roundingMode)
        result = numer
        var i = 1
        do {
            numer = numer.divide(invX2, scale, roundingMode)
            val denom = 2 * i + 1
            term = numer.divide(
                BigDecimal.valueOf(denom.toLong()), scale,
                roundingMode
            )
            result = if (i % 2 != 0) result.subtract(term) else result.add(term)
            i++
        } while (term.compareTo(BigDecimal.ZERO) != 0)
        return result
    }
}