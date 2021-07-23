package design_patterns.structural.decorator.ftp.decorator

import design_patterns.structural.decorator.ftp.component.UserPI

abstract class UserPIDecorator(private val userPI: UserPI) : UserPI {

}

class DefaultUserPI(val userPI: UserPI) : UserPIDecorator(userPI) {
    override fun exec(args: MutableList<String>) {
        println("DefaultUserPI.exec() => calling exec in userPI")
        userPI.exec();
    }
}

class UserDTP(val userPI: UserPI) : UserPIDecorator(userPI) {
    override fun exec(args: MutableList<String>) {
        runAsActive();
        println("UserDTP.exec() => calling exec in userPI");
        userPI.exec();
    }

    fun runAsActive() {
        println("runAsActiveConnection()");
    }

    fun runAsPassive() {
        println("runAsPassiveConnection()");
    }
}