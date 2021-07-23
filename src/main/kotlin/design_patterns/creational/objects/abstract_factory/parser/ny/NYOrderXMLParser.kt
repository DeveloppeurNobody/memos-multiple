package design_patterns.creational.objects.abstract_factory.parser.ny

import design_patterns.creational.objects.abstract_factory.parser.XMLParser

class NYOrderXMLParser : XMLParser {
    override fun parse(): String {
        return "NYOrder parsing...";
    }
}

