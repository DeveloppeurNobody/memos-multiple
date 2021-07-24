package applications_spring_kt.aop.metier.context

import kotlin.jvm.Throws

/**
 * a context to simulate a security context that can be used
 * as a service of security in AuthorizationAspect
 */
object SecurityContext {
    var username = "";
    var password = "";
    var roles: Array<String> = arrayOf("");

    @Throws(RuntimeException::class)
    fun authenticate(usernameArg: String = "", passwordArg: String = "", rolesArgs: Array<String> = arrayOf("")) {
        if (usernameArg.equals("root") && passwordArg.equals("1234")) {
            username = usernameArg;
            password = passwordArg;
            roles = rolesArgs;
        } else {
            throw RuntimeException("Access Denied !");
        }
    }

    fun hasRoles(roleArg: String = ""): Boolean {
        var result = false;
        roles.forEach {
            if (it.equals(roleArg)) {
                result = true;
                return@forEach
            }
        }

        return result;
    }


}