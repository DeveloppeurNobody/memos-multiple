package aop.aspects;

import aop.metier.Compte;
import aop.metier.MetierBanqueImpl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class PatchRetraiAspect {
    
    @Pointcut("execution (* aop.metier.MetierBanqueImpl.retirer(Long, double))")
    public void pcRetirer() {}

    /** 
     * 
     * /!\ 
     *     NEED TO PUT ProceedingJointPoint 
     *     BEFORE argument Long code and double montant 
     * /!\
     * 
     * @param proceedingJoinPoint
     * @param joinPoint
     * @return Object to avoid changing singature of called method some of 
     * method can return an Object other void, we just catch it and return it 
     * as a result without use it
     * @throws Throwable
     */
    @Around("pcRetirer() && args(code, montant)")
    public Object autour(ProceedingJoinPoint proceedingJoinPoint, Long code, double montant) throws Throwable {
        System.out.println("*************** RETIRER ************************");
        MetierBanqueImpl metierBanque = (MetierBanqueImpl) proceedingJoinPoint.getTarget();
        Compte compte = metierBanque.consulter(code);

        if (compte.getSolde() < montant) throw new RuntimeException("Solde Insuffisant");
        return proceedingJoinPoint.proceed();
    }
}
