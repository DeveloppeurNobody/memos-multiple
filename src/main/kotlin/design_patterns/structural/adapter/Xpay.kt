package design_patterns.structural.adapter

/**
 * old implementation of cb
 */
data class Xpay(var creaditCardNo: String = "",
           var customerName: String = "",
           var cardExpMonth: String = "",
           var cardExpYear: String = "",
           var cardCVVNo: Int = 0,
           var amount: Double = 0.0)