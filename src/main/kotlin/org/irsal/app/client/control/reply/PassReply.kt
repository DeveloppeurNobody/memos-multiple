package org.irsal.app.client.control.reply

object PassReply {

    fun checkResponse(msg: String): Boolean {
        /*
         * 202 FTP response code, 230 FTP response code
         * 332 FTP response code
         * 421 FTP response code
         * 500 FTP response code, 501 FTP response code, 503 FTP response code, 530 FTP response code
         */

        // check for others codes reply

        return (msg.contains(FtpReply.REPLY_230_USER_LOGGED_IN));
    }

}