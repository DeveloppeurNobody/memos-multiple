package applications_spring_kt.aop.metier.impl

import applications_spring_kt.aop.aspects.annotation.Log
import applications_spring_kt.aop.aspects.annotation.SecuredByAspect
import applications_spring_kt.aop.metier.IMetierAop
import org.springframework.stereotype.Component

@Component
class MetierAopImpl : IMetierAop {

    @Log
    @SecuredByAspect(roles = ["USER", "ADMIN"])
    override fun process() {
        println("Business Process...");
    }

    @SecuredByAspect(roles = ["ADMIN"])
    override fun compute(): Double {
        val data = 78.0;
        println("Business Computing and returning");
        return data;
    }
}