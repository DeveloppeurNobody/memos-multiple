package apps_spring.aop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class SecondAspect {


    // /**
    //  *
    //  */
    // private static final String EXECUTION_COM_MYCOMPANY_DEMO_AOP_MY_MESSAGE_SHOW_MESSAGE = "execution(* apps_spring.aop.MyMessage.showMessage(..))";

    // @Before(EXECUTION_COM_MYCOMPANY_DEMO_AOP_MY_MESSAGE_SHOW_MESSAGE)
    // public void beforeShowMessage() {
    //     System.out.println("*****************************************");
    //     System.out.println("Before Main From Aspect with AspectJS");
    //     System.out.println("*****************************************");
    // }

    // @After(EXECUTION_COM_MYCOMPANY_DEMO_AOP_MY_MESSAGE_SHOW_MESSAGE)
    // public void afterShowMessage() {
    //     System.out.println("SAVE to DB AFTER DONE !!!!!");
    // }

   
    /*
     *
     *======================= MEMOS ============================= 
     * 
     */

    // all classes and its methods under specific package
    // -------------------------------equals to => .metier.class.method(..))")
    //@Pointcut("execution(* apps_spring.aop.metier.*.*(..))")

    //  /*
    //  * one specific class
    //  */
    // @Pointcut("within(apps_spring.aop.message.MyMessage)")
    // public void myMessage() {}

    // /*
    //  * Target all class that implements BarDao
    //  */ 
    // @Pointcut("target(com.baeldung.pointcutadvice.dao.BarDao)")
    // public void myBarDao() {}
    

    // /*
    //  * cut in all methods for all classes 
    //  * in packages and subpackage under package message
    //  */
    // @Pointcut("within(apps_spring.aop.message..*)")
    // public void pc1() {}

   
    @Pointcut("within(apps_spring.aop.message.MyMessage)")
    public void pc1() {}


    // @Before("pc1()")
    // public void beforeShowMessage() {
    //     System.out.println("*****************************************");
    //     System.out.println("Before Main From Aspect with AspectJS");
    //     System.out.println("*****************************************");
    // }

    @Around("pc1()")
    public void aroundShowMessage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("*****************************************");
        System.out.println("BEFORE Around Main From Aspect with AspectJS");
        System.out.println("*****************************************");

        // Execute method
        proceedingJoinPoint.proceed();

        System.out.println("*****************************************");
        System.out.println("AFTER Around Main From Aspect with AspectJS");
        System.out.println("*****************************************");
    }


}
