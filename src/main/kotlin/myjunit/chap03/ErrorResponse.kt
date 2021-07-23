package myjunit.chap03

import java.lang.Exception

class ErrorResponse(var request: Request, var e: Exception) : Response {
    fun getOriginalRequest(): Request {
        return this.request;
    }

    fun getOriginalException(): Exception {
        return this.e;
    }
}
