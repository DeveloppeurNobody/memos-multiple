package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.logger

import java.util.*
import java.util.logging.Formatter
import java.util.logging.LogRecord

/**
 * This class is used to format the log messages. The Logger class call the format() method
 * to get the formatted log message that it's going to write. The format method receives a
 * LogRecord object as parameter. That object has all the information about the message.
 *
 */
class MyFormatter : Formatter() {

    /**
     * Method that formats the log message. It's declared as abstract in the
     * Formatter class. It's called by the Logger class. It receives a LogRecord
     * object as parameter with all the information of the log message
     */
    override fun format(record: LogRecord?): String {
        // Create a string buffer to construct the message.
        val sb = StringBuilder();

        // Add the parts ofthe message with the desired format.
        with(sb) {
            append("[${record?.level}] - ");
            append("${Date(record?.millis!!)} : ");
            append("${record.sourceClassName}.${record.sourceMethodName}:");
            append("${record.message}\n");
        }

        return sb.toString();
    }
}