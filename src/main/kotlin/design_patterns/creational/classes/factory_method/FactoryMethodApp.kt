package design_patterns.creational.classes.factory_method

object FactoryMethodApp {
    @JvmStatic
    fun main(args: Array<String>) {
        var service: DisplayService = FeedbackXMLParserService();
        service.display();

        service = OrderXMLParserService();
        service.display();

    }
}