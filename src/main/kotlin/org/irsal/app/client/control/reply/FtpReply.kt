package org.irsal.app.client.control.reply


// TODO write a DefaultFtpReply with service at release?
interface FtpReply {

    var code: Int;
    var message: String;
    var sentTime: Long;
    var positive: Boolean;

    companion object {
        /**
         * 110 Restart marker reply. In this case, the text is exact and not left to
         * the particular implementation; it must read: MARK yyyy =mmmm Where yyyy
         * is User-process data stream marker, and mmmm server's equivalent marker
         * (note the spaces between markers and = "").
         */
        const val REPLY_110_RESTART_MARKER_REPLY = "110";

        /**
         * 120 Service ready in nnn minutes.
         */
        const val REPLY_120_SERVICE_READY_IN_NNN_MINUTES = "120";

        /**
         * 125 Data connection already open; transfer starting.
         */
        const val REPLY_125_DATA_CONNECTION_ALREADY_OPEN = "125";

        /**
         * 150 File status okay; about to open data connection.
         */
        const val REPLY_150_FILE_STATUS_OKAY = "150";

        /**
         * 200 Command okay.
         */
        const val REPLY_200_COMMAND_OKAY = "200";

        /**
         * 202 Command not implemented, superfluous at this site.
         */
        const val REPLY_202_COMMAND_NOT_IMPLEMENTED = "202";

        /**
         * 211 System status, or system help reply.
         */
        const val REPLY_211_SYSTEM_STATUS_REPLY = "211";

        /**
         * 212 Directory status.
         */
        const val REPLY_212_DIRECTORY_STATUS = "212";

        /**
         * 213 File status.
         */
        const val REPLY_213_FILE_STATUS = "213";

        /**
         * 214 Help message. On how to use the server or the meaning of a particular
         * non-standard command. This reply is useful only to the human user.
         */
        const val REPLY_214_HELP_MESSAGE = "214";

        /**
         * 215 NAME system type. Where NAME is an official system name from the list
         * in the Assigned Numbers document.
         */
        const val REPLY_215_NAME_SYSTEM_TYPE = "215";

        /**
         * 220 Service ready for new user.
         */
        const val REPLY_220_SERVICE_READY = "220";

        /**
         * Service closing control connection. Logged out if appropriate.
         */
        const val REPLY_221_CLOSING_CONTROL_CONNECTION = "221";

        /**
         * 225 Data connection open; no transfer in progress.
         */
        const val REPLY_225_DATA_CONNECTION_OPEN_NO_TRANSFER_IN_PROGRESS = "225";

        /**
         * Closing data connection. Requested file action successful (for example,
         * file transfer or file abort).
         */
        const val REPLY_226_CLOSING_DATA_CONNECTION = "226";

        /**
         * 227 Entering Passive Mode (h1,h2,h3,h4,p1,p2).
         */
        const val REPLY_227_ENTERING_PASSIVE_MODE = "227";

        /**
         * 230 User logged in, proceed.
         */
        const val REPLY_230_USER_LOGGED_IN = "230";

        /**
         * 250 Requested file action okay, completed.
         */
        const val REPLY_250_REQUESTED_FILE_ACTION_OKAY = "250";

        /**
         * 257 "PATHNAME" created.
         */
        const val REPLY_257_PATHNAME_CREATED = "257";

        /**
         * 331 User name okay, need password.
         */
        const val REPLY_331_USER_NAME_OKAY_NEED_PASSWORD = "331";

        /**
         * 332 Need account for login.
         */
        const val REPLY_332_NEED_ACCOUNT_FOR_LOGIN = "332";

        /**
         * 350 Requested file action pending further information.
         */
        const val REPLY_350_REQUESTED_FILE_ACTION_PENDING_FURTHER_INFORMATION = "350";

        /**
         * 421 Service not available, closing control connection. This may be a
         * reply to any command if the service knows it must shut down.
         */
        const val REPLY_421_SERVICE_NOT_AVAILABLE_CLOSING_CONTROL_CONNECTION = "421";

        /**
         * 425 Can't open data connection.
         */
        const val REPLY_425_CANT_OPEN_DATA_CONNECTION = "425";

        /**
         * 426 Connection closed; transfer aborted.
         */
        const val REPLY_426_CONNECTION_CLOSED_TRANSFER_ABORTED = "426";

        /**
         * 450 Requested file action not taken. File unavailable (e.g., file busy).
         */
        const val REPLY_450_REQUESTED_FILE_ACTION_NOT_TAKEN = "450";

        /**
         * 451 Requested action aborted: local error in processing.
         */
        const val REPLY_451_REQUESTED_ACTION_ABORTED = "451";

        /**
         * 452 Requested action not taken. Insufficient storage space in system.
         */
        const val REPLY_452_REQUESTED_ACTION_NOT_TAKEN = "452";

        /**
         * 500 Syntax error, command unrecognized. This may include errors such as
         * command line too long.
         */
        const val REPLY_500_SYNTAX_ERROR_COMMAND_UNRECOGNIZED = "500";

        /**
         * 501 Syntax error in parameters or arguments.
         */
        const val REPLY_501_SYNTAX_ERROR_IN_PARAMETERS_OR_ARGUMENTS = "501";

        /**
         * 502 Command not implemented.
         */
        const val REPLY_502_COMMAND_NOT_IMPLEMENTED = "502";

        /**
         * 503 Bad sequence of commands.
         */
        const val REPLY_503_BAD_SEQUENCE_OF_COMMANDS = "503";

        /**
         * 504 Command not implemented for that parameter.
         */
        const val REPLY_504_COMMAND_NOT_IMPLEMENTED_FOR_THAT_PARAMETER = "504";

        /**
         * 530 Not logged in.
         */
        const val REPLY_530_NOT_LOGGED_IN = "530";

        /**
         * 532 Need account for storing files.
         */
        const val REPLY_532_NEED_ACCOUNT_FOR_STORING_FILES = "532";

        /**
         * 550 Requested action not taken. File unavailable (e.g., file not found,
         * no access).
         */
        const val REPLY_550_REQUESTED_ACTION_NOT_TAKEN = "550";

        /**
         * 551 Requested action aborted: page type unknown.
         */
        const val REPLY_551_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN = "551";

        /**
         * 552 Requested file action aborted. Exceeded storage allocation (for
         * current directory or dataset).
         */
        const val REPLY_552_REQUESTED_FILE_ACTION_ABORTED_EXCEEDED_STORAGE = "552";

        /**
         * 553 Requested action not taken. File name not allowed.
         */
        const val REPLY_553_REQUESTED_ACTION_NOT_TAKEN_FILE_NAME_NOT_ALLOWED = "553";
    }

    /**
     * The reply code
     *
     * @return The reply code
     */
    //abstract fun getCode(): Int

    /**
     * The reply message, might be multiple lines
     *
     * @return The reply message
     */
    // fun getMessage(): String?

    /**
     * Returns the timestamp (in milliseconds since the epoch time) when this
     * reply was sent.
     *
     * @return the timestamp (in milliseconds since the epoch time) when this
     * reply was sent.
     */
    // abstract fun getSentTime(): Long

    /**
     * Must implement toString to format the reply as described in the RFC. Most
     * important is the handling of multi-line replies.
     *
     * @return The formated reply
     */
    //abstract override fun toString(): String

    /**
     * Tells whether or not this reply indicates a positive completion.
     * @return `true`, if this reply is a positive completion or
     * positive intermediate reply; `false`, otherwise.
     */
    // abstract fun isPositive(): Boolean
}