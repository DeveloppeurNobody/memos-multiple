package org.irsal.app.client

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object GenerateLogger {

    init {
      //  LogManager.
    }

    fun getLoggerForClass(targetClass: Any): Logger {

        //println("getLoggerForClass: ${targetClass.javaClass.simpleName}")
        val LOG: Logger = LogManager.getLogger(targetClass.javaClass.simpleName);
        return LOG;
    }
}