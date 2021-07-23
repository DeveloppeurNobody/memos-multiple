package design_patterns.creational.objects.abstract_factory.parser.tw

import design_patterns.creational.objects.abstract_factory.parser.XMLParser

class TWErrorXMLParser : XMLParser {
    override fun parse(): String {
        return "TWError parsing...";
    }
}