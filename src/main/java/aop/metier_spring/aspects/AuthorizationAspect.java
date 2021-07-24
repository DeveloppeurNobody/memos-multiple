package aop.metier_spring.aspects;

import aop.metier_spring.SecurityContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Aspect used for all methods 
 * that use @SecuredByAspect annotation 
 * 
 * [SecuredByAspect] target methods in classes by @SecurityByAspect annotation/marker.
 * [SecurityContext] a context, a class utility to facilite a simulation of security
 * [AuthorizationAspect] a core aspect that contains the technical code to use.
 *
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class AuthorizationAspect {
    
    // /!\ @annotation(securedByAspect not SecuredByAspect) target argument in signature method
    @Around(value = "@annotation(securedByAspect)",
            argNames = "proceedingJoinPoint, securedByAspect")
    public Object secure(ProceedingJoinPoint proceedingJoinPoint, SecuredByAspect securedByAspect) throws Throwable {
        /*
         *  *** in MetierImpl we have ***
         *  @SecuredByAspect(roles={"USER", "ADMIN"})
         *  MetierImpl.process()
         *  
         *  @SecuredByAspect(roles={"ADMIN"})
         *  MetierImpl.compute()
         */
        String[] roles = securedByAspect.roles();
        boolean authorized = false;
        
        for (String role : roles) {
            if (SecurityContext.hasRoles(role)) {
                authorized = true;
                break;
            }
        }

        if (authorized) {
            Object result = proceedingJoinPoint.proceed();
            return result;
        }

        throw new RuntimeException("Unauthorized access 403");
    }
}
