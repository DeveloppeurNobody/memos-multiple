package threads.cookbook_java9.ch04_thread_executor.recipe03_executing_multiples_tasks_and_take_the_fastest_example_validate_user_via_db_or_ldap

import threads.cookbook_java9.ch04_thread_executor.recipe03_executing_multiples_tasks_and_take_the_fastest_example_validate_user_via_db_or_ldap.task.UserValidator
import threads.cookbook_java9.ch04_thread_executor.recipe03_executing_multiples_tasks_and_take_the_fastest_example_validate_user_via_db_or_ldap.task.ValidatorTask
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

/**
 * This is the main class of the example. Creates two user validation systems and execute
 * them in an Executor using the invokeAny() method. If the user is validated by one of the
 * user validation system, then it shows a message. If both system don't validate the user,
 * the application proccess the ExecutionException throwed by the method.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Initializes the parameters of the user
        val username = "test";
        val password = "test";

        // Create two user validation objects
        val ldapValidation = UserValidator("LDAP");
        val dbValidator = UserValidator("Database");

        // Create two tasks for the user validation objects
        val ldapTask = ValidatorTask(ldapValidation, username, password);
        val dbTask = ValidatorTask(dbValidator, username, password);

        // Add the two tasks to a list of tasks
        val taskList = mutableListOf<ValidatorTask>();
        taskList.add(ldapTask);
        taskList.add(dbTask);

        // Create a new Executor
        val executor = Executors.newCachedThreadPool();
        var result: String;

        try {
            // Send the list of tasks to the executor and waits for the result of the first task
            // that finish without throw an Exception. If all tasks throw an exception,
            // the method throws an ExecutionException.
            result = executor.invokeAny(taskList);
            println("Main: Result: $result");
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } catch (ee: ExecutionException) {
            System.err.println(ee);
        }

        // Shutdown the Executor
        executor.shutdown();
        println("Main: End of the Execution");
    }
}

