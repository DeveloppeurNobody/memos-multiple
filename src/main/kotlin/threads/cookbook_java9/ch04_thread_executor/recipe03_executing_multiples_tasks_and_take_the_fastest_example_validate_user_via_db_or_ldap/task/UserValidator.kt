package threads.cookbook_java9.ch04_thread_executor.recipe03_executing_multiples_tasks_and_take_the_fastest_example_validate_user_via_db_or_ldap.task

import java.util.concurrent.TimeUnit

/**
 * This class implements a simulation of a user validation system.
 * It suspend the Thread a random period of time and then returns a random boolean value.
 * We consider that it returns the true value when the user is validated and the false
 * value when it's not.
 */
data class UserValidator(val name: String) {

    /**
     * Method that validates a user
     *
     * @param name Name of the user
     * @param password Password of the user
     * @return true if the user is validated and false if not
     */
    fun validate(name: String, password: String): Boolean {
        // Create a new Random objects generator
        val random = java.util.Random();

        // Sleep the thread during a randon period of time
        try {
            val duration = (Math.random() * 10).toLong();
            println("Validator $name: Validating a user during $duration");
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
            return false;
        }

        // Return a random boolean value
        return random.nextBoolean();
    }
}