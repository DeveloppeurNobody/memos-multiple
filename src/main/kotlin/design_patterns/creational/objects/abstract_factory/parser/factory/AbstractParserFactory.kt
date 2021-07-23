package design_patterns.creational.objects.abstract_factory.parser.factory

import design_patterns.creational.objects.abstract_factory.parser.XMLParser

abstract class AbstractParserFactory {
    abstract fun getParserInstance(parserType: String): XMLParser;
}