package applications_spring_kt.aop.aspects.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


/**
 * Used as marker instead of 'execution' in aspect
 *
 * Retention: to specify interpretation in compile time or runtime
 * Target: to specify target (method, class, package, etc);
 *
 * in java   => @Target(ElementType.METHOD)
 * in kotlin => @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
 *
 * in java   => public @interface SecuredByAspect
 * in kotlin => annotation class SecuredByAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class SecuredByAspect(
    /**
     * @SecuredByAspect(roles=["USER", "ADMIN"])
     * to use argument in annotation we add this method
     */
    val roles: Array<String>
)
