package aop.aspects

import aop.aspects.annotation.SecuredByAspect
import aop.metier.context.SecurityContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.stereotype.Component
import kotlin.jvm.Throws


/**
 * Aspect used for all methods that use @SecuredByAspect annotation
 *
 * 3 elements are needed
 * [ SecuredByAspect ] - annotation - target methods by @SecurityByAspect annotation/marker
 * [ SecurityContext ] - context - a class utility to facilitate a simulation of security
 * [ AuthorizationAspect ] - aspect - a core aspect that contains the technical code to use
 */
@Component
@Aspect
@EnableAspectJAutoProxy
class AuthorizationAspect {

    // /!\ @annotation(securedByAspect not SecuredByAspect) target argument in signature method
    @Around(
        value = "@annotation(securedByAspect",
        argNames = "proceedingJoinPoint, securedByAspect")
    fun secure(proceedingJoinPoint: ProceedingJoinPoint, securedByAspect: SecuredByAspect): Any {
        /*
         * **** in MetierImpl we have ****
         *
         * @SecuredByAspect(roles = ["USER", "ADMIN"])
         * metierImpl.process()
         *
         * @SecuredByAspect(roles = ["ADMIN"])
         * metierImpl.compute()
         */
        val roles = securedByAspect.roles;
        var authorized = false;

        roles.forEach {
            if (SecurityContext.hasRoles(it)) {
                authorized = true;
                return@forEach
            }
        }

        if (authorized) {
            val result = proceedingJoinPoint.proceed();
            return result;
        }

        throw RuntimeException("Unauthorized access 403");
    }
}