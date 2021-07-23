package myjunit.chap03


interface Controller {
    fun processRequest(request: Request?): Response?
    fun addHandler(request: Request?, requestHandler: RequestHandler?)
}
