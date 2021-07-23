package design_patterns.creational.classes.factory_method

abstract class DisplayService {
    fun display() {
        var parser: XMLParser = getParser();
        var msg: String = parser.parse();
        println(msg);
    }

    abstract fun getParser(): XMLParser;
}