package design_patterns.creational.objects.abstract_factory.producer

import design_patterns.creational.objects.abstract_factory.parser.factory.AbstractParserFactory
import design_patterns.creational.objects.abstract_factory.parser.factory.NYParserFactory
import design_patterns.creational.objects.abstract_factory.parser.factory.TWFactory
import java.lang.AssertionError

class ParserFactoryProducer {
    constructor() {
        throw AssertionError();
    }

//    companion object {
//        fun getFactory(factoryType: String): AbstractParserFactory {
//            when(factoryType) {
//                "NYFactory" -> return NYParserFactory();
//                "TWFactory" -> return TWFactory();
//                else -> TWFactory();
//            }
//        }
//    }
}

