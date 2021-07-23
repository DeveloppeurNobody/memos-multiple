package design_patterns.creational.objects.abstract_factory.parser.ny

import design_patterns.creational.objects.abstract_factory.parser.XMLParser

class NYErrorXMLParser : XMLParser {
    override fun parse(): String {
        return "NYError...";
    }
}