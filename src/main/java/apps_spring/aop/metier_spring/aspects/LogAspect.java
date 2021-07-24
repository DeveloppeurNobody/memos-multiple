package apps_spring.aop.metier_spring.aspects;


import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @Component => is added, because SpringAOP is dynamique
 * not for compilation, that mean LogAspect need to be instantiate
 * to be used
 * 
 * @EnableAspectJAutoProxy => ask spring to generate this class as 
 * an Proxy, who is in charge to do all methods of Aspect
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class LogAspect {
    Logger LOG = Logger.getLogger(LogAspect.class.getName());

    //======================================================================
    // ==== TARGET ONE METHOD
    // @Before("execution(public void process()))")
    //
    //======================================================================
    // ==== TWO WAYS TO TARGET 
    // ==== ALL SUB_PACKAGES, CLASSES AND METHODS 
    // ==== UNDER A SPECIFIC PACKAGE
    //
    // @Before("execution(* apps_spring.aop.metier_spring..*(..))")
    //
    // @Before("within(apps_spring.aop.metier_spring..*)")
    //======================================================================

    //@Around("execution(* apps_spring.aop.metier_spring..*(..))")

    /**
     * all methods that use @Log (aspect), we call this method for them
     */
    @Around("@annotation(apps_spring.aop.metier_spring.aspects.Log)")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long t1 = System.currentTimeMillis();
        LOG.info("From Logging Aspectes... Before | " + proceedingJoinPoint.getSignature());
        
        // do method of metier
        Object result = proceedingJoinPoint.proceed();
        
        LOG.info("From Logging Aspectes... After | " + proceedingJoinPoint.getSignature());
        long t2 = System.currentTimeMillis();
        LOG.info("Duration: " + (t2 - t1));

        return result;
    }
}
