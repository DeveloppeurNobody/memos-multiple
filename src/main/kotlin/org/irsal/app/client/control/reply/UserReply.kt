package org.irsal.app.client.control.reply

object UserReply {

    fun checkResponse(msg: String): Boolean {
        // 230 User logged in process.
        // 232 User logged in, authorized by security data exchange.

        // 331 User name okay, need password.
        // 332 Need account for login.

        // 421 Service not available, closing control connection.
        // --- This may be a reply to any command if the service knows it must shut down.

        // 500 Syntax error, command unrecognized.
        // 501 Syntax error in parameters or arguments.
        // 502 Command not implemented.

        // check for others codes reply

        return (msg.contains(FtpReply.REPLY_230_USER_LOGGED_IN)
                || msg.contains("232")
                || msg.contains(FtpReply.REPLY_331_USER_NAME_OKAY_NEED_PASSWORD)
                || msg.contains(FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN))
    }
}