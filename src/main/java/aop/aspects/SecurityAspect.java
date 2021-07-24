package aop.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@EnableAspectJAutoProxy
public class SecurityAspect {

    /* === WORKS, commented to avoid a prompt of username and password at start
    
    @Pointcut("execution (* aop.DemoAopApplication.run(..))")
    public void startAppPointCut() {}

    @Around("startAppPointCut()")
    public void autoutStart(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Username: ");
        String username = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();

        if (username.equals("root") && password.equals("1234")) {
            proceedingJoinPoint.proceed();
        } else {
            System.out.println("ACCES DENIED !");
            System.exit(0);
        }
    }

    */
}
