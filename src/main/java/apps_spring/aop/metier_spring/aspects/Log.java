package apps_spring.aop.metier_spring.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used as marker instead of 'execution' in aspect
 * 
 * Retention: to specify interpretation in compile time or runtime
 * Target: to specify target (method, class, package, etc);
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    
}
