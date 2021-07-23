package myjunit.chap03

import java.lang.Exception

interface RequestHandler {
    @Throws(Exception::class)
    fun process(request: Request): Response;
}