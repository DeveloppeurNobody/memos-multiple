package aop.aspects;



import java.io.IOException;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class LoggingAspect {
    Logger LOG = Logger.getLogger(LoggingAspect.class.getName());


    public LoggingAspect() throws SecurityException, IOException {
        //// LOG in xml file
        // LOG.addHandler(new FileHandler("log.xml"));
        // LOG.setUseParentHandlers(false);
    }
    
    //long t1, t2;

    /*
     * execution: pointCut are added in metierImpl 
     * 
     * call: pointCut are added in DemoApplication.start() method,
     * before metierBanque.addCompte(new Compte(code, solde)); 
     * not supported in Spring AOP
     */
    //@Pointcut("execution(* aop.metier.MetierBanqueImpl.*(..))")
    @Pointcut("within(aop.metier..*)")
    public void pcLog() {}

    // @Before("pcLog()")
    // public void beforeMetier(JoinPoint joinPoint) {
    //     t1 = System.currentTimeMillis();
    //     LOG.info("-----------------------------------------------------------");
    //     LOG.info("Avant l'exécution de la méthode " + joinPoint.getSignature());
       
    // }

    // @After("pcLog()")
    // public void afterMetier(JoinPoint joinPoint) {
    //     LOG.info("-----------------------------------------------------------");
    //     LOG.info("Après l'exécution de la méthode " + joinPoint);
    //     t2 = System.currentTimeMillis();
    //     LOG.info("Durée d'exécution de la méthode " + (t2 - t1));
      
    // }

    /**
     * 
     * @param proceedingJoinPoint
     * @param joinPoint
     * @return Object to avoid changing singature of called method some of 
     * method can return an Object other void, we just catch it and return it 
     * as a result without use it
     * @throws Throwable
     */
    @Around("pcLog()")
    public Object autour(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long t1 = System.currentTimeMillis();
        LOG.info("-----------------------------------------------------------");
        LOG.info("Avant l'exécution de la méthode ");

        // call method
        Object result = proceedingJoinPoint.proceed();

        LOG.info("-----------------------------------------------------------");
        LOG.info("Après l'exécution de la méthode ");
        long t2 = System.currentTimeMillis(); 
        LOG.info("Durée d'exécution de la méthode " + (t2 - t1));
    
        return result;
    }

}
