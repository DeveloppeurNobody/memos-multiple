package design_patterns.creational.classes.factory_method

class FeedbackXMLParser : XMLParser {
    override fun parse(): String {
        return "feedback XML Server";
    }
}