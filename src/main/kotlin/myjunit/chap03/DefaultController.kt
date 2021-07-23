package myjunit.chap03

class DefaultController : Controller {
    var requestHandlers: MutableMap<String, RequestHandler> = mutableMapOf();

    fun getHandler(request: Request): RequestHandler? {
        if (!this.requestHandlers.containsKey(request.getName())) {
            var message: String = "Cannot find handler for request name [ ${request.getName()} ]";
            throw RuntimeException(message);
        }
        return this.requestHandlers[request.getName()];
    }

    override fun processRequest(request: Request?): Response? {
        val response: Response
        response = try {
            getHandler(request!!)!!.process(request)
        } catch (exception: Exception) {
            ErrorResponse(request!!, exception)
        }
        return response
    }

    override fun addHandler(request: Request?, requestHandler: RequestHandler?) {
        if (requestHandlers.containsKey(request?.getName())) {
            throw RuntimeException(
                "A request handler has "
                        + "already been registered for request name " + "["
                        + request?.getName() + "]"
            )
        } else {
            requestHandlers[request!!.getName()] = requestHandler!!
        }
    }


}