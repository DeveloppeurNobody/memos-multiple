package design_patterns.creational.classes.factory_method

class OrderXMLParser : XMLParser {
    override fun parse(): String {
        return "Order XML Parser ...";
    }
}