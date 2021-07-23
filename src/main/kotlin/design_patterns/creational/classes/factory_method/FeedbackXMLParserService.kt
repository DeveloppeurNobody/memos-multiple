package design_patterns.creational.classes.factory_method

class FeedbackXMLParserService : DisplayService() {
    override fun getParser(): XMLParser {
        return FeedbackXMLParser();
    }
}