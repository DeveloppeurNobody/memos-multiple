package design_patterns.structural.adapter

data class XpayToPayDAdapter(val xpay: Xpay) : PayD() {
    init {
        this.cardOwnerName = this.xpay.customerName;
        this.custCardNo = this.xpay.creaditCardNo;
        this.cardExpMonthDate = "${this.xpay.cardExpMonth}/${this.xpay.cardExpYear}";
        this.CVVNo = this.xpay.cardCVVNo;
        this.totalAmount = this.xpay.amount;
    }
}