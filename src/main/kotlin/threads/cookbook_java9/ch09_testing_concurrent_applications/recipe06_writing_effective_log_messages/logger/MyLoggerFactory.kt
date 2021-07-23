package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.logger

import java.lang.Exception
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class is used to create a Logger object with the configuration
 * you want. In this case, you're going to write all the log messages generated
 * in the application to the recipe8.log file and with the format specified in the
 * MyFormatter class. It uses the Logger class to create the Logger object. This class
 * creates a Logger object per name that is passed as parameter.
 *
 */
class MyLoggerFactory {
    companion object {
        // Handler to control that the log messages are written in the recipe6.log file
        var handler: Handler? = null;

        /**
         * Static method that returns the log object associated with the name received
         * as parameter. If it's a new Logger object, this method configures it with your
         * configuration.
         * @param name Name of the Logger object you want to obtain.
         * @return The Logger object generated.
         */
        fun getLogger(name: String): Logger {
            // Get the logger
            val logger = Logger.getLogger(name);

            // Set the level to show all the messages
            logger.level = Level.ALL;
            try {
                /*
                 * If the Handler object is null, we create one to
                 * write the log messages in the recipe6.log file
                 * with the format specified by the MyFormatter class
                 */
                if (handler == null) {
                    val formater: Formatter = MyFormatter();
                    handler = FileHandler("recipe6.log").also { it.formatter = formater }
                }

                if (logger.handlers.isEmpty()) {
                    logger.addHandler(handler);
                }
            } catch (e: Exception) {
                System.err.println(e);
            }

            return logger;
        }

    }
}