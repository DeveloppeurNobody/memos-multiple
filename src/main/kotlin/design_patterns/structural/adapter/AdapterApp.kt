package design_patterns.structural.adapter

object AdapterApp {
    @JvmStatic
    fun main(args: Array<String>) {
        // Object for Xpay
        var xpay: Xpay = Xpay();
        xpay.creaditCardNo = "4789565874102365";
        xpay.customerName = "Max Warner";
        xpay.cardExpMonth = "09";
        xpay.cardExpYear = "25";
        xpay.cardCVVNo = 235;
        xpay.amount = 2565.23;

        var payD: PayD = XpayToPayDAdapter(xpay);
        testPayD(payD);
    }

    fun testPayD(payD: PayD) {
        println(payD);
    }
}