package design_patterns.structural.decorator.ftp

import design_patterns.structural.decorator.ftp.component.impl.BaseUserPI
import design_patterns.structural.decorator.ftp.decorator.DefaultUserPI
import design_patterns.structural.decorator.ftp.decorator.UserDTP

object DecoratorApp {
    @JvmStatic
    fun main(args: Array<String>) {
        println(">> ONLY UserPI")
        val baseUserPI = BaseUserPI();
        val defaultUserPI = DefaultUserPI(baseUserPI);
        defaultUserPI.exec();

        println("\n----------------------------------------\n");
        println(">> ONLY UserPI WITH UserDTP")

        val userDTP = UserDTP(baseUserPI);
        userDTP.exec();
    }
}