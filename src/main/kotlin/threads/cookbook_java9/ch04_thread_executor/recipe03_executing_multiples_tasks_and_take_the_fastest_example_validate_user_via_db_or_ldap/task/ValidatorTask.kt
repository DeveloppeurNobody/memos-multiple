package threads.cookbook_java9.ch04_thread_executor.recipe03_executing_multiples_tasks_and_take_the_fastest_example_validate_user_via_db_or_ldap.task

import java.lang.Exception
import java.util.concurrent.Callable

class ValidatorTask(val validator: UserValidator,
                    val user: String,
                    val password: String) : Callable<String> {

    /**
     * Core method of the Callable interface. Tries to validate the user using the user
     * validation system. If the user is validated, returns the name of the validation system.
     * If not, throws and Exception
     *
     * @return The name of the user validation system.
     * @throws Exception An exception when the user is not validated
     */
    @Throws(Exception::class)
    override fun call(): String {
        if (!validator.validate(user, password)) {
            println("${validator.name}: The user has not been found");
            throw Exception("Error validating user");
        }
        println("${validator.name}: The user has been found");
        return validator.name;
    }
}