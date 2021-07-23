package design_patterns.creational.objects.abstract_factory.parser.factory

import design_patterns.creational.objects.abstract_factory.parser.XMLParser
import design_patterns.creational.objects.abstract_factory.parser.ny.NYErrorXMLParser
import design_patterns.creational.objects.abstract_factory.parser.ny.NYOrderXMLParser

class NYParserFactory : AbstractParserFactory() {
    override fun getParserInstance(parserType: String): XMLParser {
        when(parserType) {
            "NYOrder" -> return NYOrderXMLParser();
            "NYError" -> return NYErrorXMLParser();
            else -> return NYOrderXMLParser();
        }
    }
}