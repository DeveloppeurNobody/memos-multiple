package design_patterns.structural.adapter

/**
 * new implementation of cb
 */
open class PayD(open var custCardNo: String = "",
                open var cardOwnerName: String = "",
                open var cardExpMonthDate: String = "",
                open var CVVNo: Int = 0,
                open var totalAmount: Double = 0.0)