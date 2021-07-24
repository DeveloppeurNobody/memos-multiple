package applications_spring_kt.aop

import applications_spring_kt.aop.metier.IMetierAop
import applications_spring_kt.aop.metier.context.SecurityContext
import applications_spring_kt.aop.metier.impl.MetierAopImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class AopDemo(val metier: IMetierAop) {

    init {
        SecurityContext.authenticate(
            "root",
            "1234",
            arrayOf("USER", "ADMIN"));
    }

    fun doAspectSpring() {
        try {

            metier.process();
            println("doAspectSpring() #result of computation: ${metier.compute()}");

        } catch (ex: Exception) {
            println(ex.message);
            exitProcess(0);
        }
    }
}