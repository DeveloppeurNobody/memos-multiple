package applications_spring_kt.aop.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.stereotype.Component
import java.util.logging.Logger

/**
 * @Component => is added, because SpringAOP is dynamic
 * not for compilation, that mean LogAspect need to be instantiate
 * to be used
 *
 * @EnableAspectJAutoProxy => ask spring to generate this class as
 * an Proxy, who is in charge to do all methods of Aspect
 */
@Component
@Aspect
@EnableAspectJAutoProxy
class LogAspect {
    val LOG = Logger.getLogger(LogAspect::class.java.name);


    //======================================================================
    // ==== TARGET ONE METHOD
    // @Before("execution(public void process()))")
    //
    //======================================================================
    // ==== TWO WAYS TO TARGET
    // ==== ALL SUB_PACKAGES, CLASSES AND METHODS
    // ==== UNDER A SPECIFIC PACKAGE
    //
    // @Before("execution(* com.mycompany.demo_aop.metier_spring..*(..))")
    //
    // @Before("within(com.mycompany.demo_aop.metier_spring..*)")
    //======================================================================

    //@Around("execution(* com.mycompany.demo_aop.metier_spring..*(..))")

    /**
     * all methods that use @Log (aspect), we call this method for them
     */
    @Around("@annotation(applications_spring_kt.aop.aspects.annotation.Log)")
    fun log(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val t1 = System.currentTimeMillis();
        LOG.info("[ Log Aspect ] <<<< Before | ${proceedingJoinPoint.signature}");

        // do method of metier
        val result = proceedingJoinPoint.proceed();

        LOG.info("[ Log Aspect ] >>>> After | ${proceedingJoinPoint.signature}");
        val t2 = System.currentTimeMillis();
        LOG.info("[ Log Aspect ] #duration: ${t2 - t1}");

        return result;
    }
}