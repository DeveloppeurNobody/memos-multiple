package design_patterns.creational.objects.abstract_factory.parser.factory

import design_patterns.creational.objects.abstract_factory.parser.XMLParser
import design_patterns.creational.objects.abstract_factory.parser.tw.TWErrorXMLParser
import design_patterns.creational.objects.abstract_factory.parser.tw.TWOrderXMLParser

class TWFactory : AbstractParserFactory() {
    override fun getParserInstance(parserType: String): XMLParser {
        when(parserType) {
            "TWOrder" -> return TWOrderXMLParser();
            "TWError" -> return TWErrorXMLParser();
            else -> return TWOrderXMLParser();
        }
    }
}