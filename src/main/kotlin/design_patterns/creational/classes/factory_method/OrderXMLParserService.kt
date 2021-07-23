package design_patterns.creational.classes.factory_method

class OrderXMLParserService : DisplayService() {
    override fun getParser(): XMLParser {
        return OrderXMLParser();
    }
}