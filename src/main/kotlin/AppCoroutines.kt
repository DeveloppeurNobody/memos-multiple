import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

fun main() {
    // Coroutines scopes or Coroutines context => a way to organize coroutines in to grouping
    // grouping a bunch of jobs together
    // example: if a scope is cancelled then all jobs in this context are cancelled

    // IO,
    // Main,
    // Default for heavy computation work example large list etc...


    CoroutineScope(IO).launch {
        fakeApiRequest();
    }

//    CoroutineScope(Dispatchers.IO).launch { fakeApiRequest() }
}

suspend fun fakeApiRequest() {
    val result1 = getResult1FromApi();
    println("fakeApi: $result1");
}

// suspend mean that can be used in coroutines
suspend fun getResult1FromApi(): String {
    logThread("getResult1FromApi")

    // coroutines call
    delay(1000);
    println("heeeere");
    return "Result #1";
}

fun logThread(methodName: String) {
    println("debug $methodName")
}

