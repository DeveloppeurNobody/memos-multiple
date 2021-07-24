package apps_spring.aop.metier_spring;

/**
 * to Simulate a Security context 
 * that can used as a service of security in AutorizationAspect
 */
public class SecurityContext {
    private static String username = "";
    private static String password = "";
    private static String[] roles = {};

    public static void authenticate(String usernameArg, String passwordArg, String[] rolesArg) {
        if (usernameArg.equals("root") && passwordArg.equals("1234")) {
            username = usernameArg;
            password = passwordArg;
            roles = rolesArg;
        } else {
            throw new RuntimeException("Access Denied!");
        }
    }

    public static boolean hasRoles(String roleArg) {
        for (String role : roles) {
            if (role.equals(roleArg)) return true;
        }

        return false;
    }
}
