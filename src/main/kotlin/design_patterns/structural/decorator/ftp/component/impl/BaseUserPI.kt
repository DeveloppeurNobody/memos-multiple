package design_patterns.structural.decorator.ftp.component.impl

import design_patterns.structural.decorator.ftp.component.UserPI

class BaseUserPI : UserPI{
    override fun exec(args: MutableList<String>) {
        println("BaseUserPI.exec()")
    }
}