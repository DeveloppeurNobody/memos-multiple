package threads.labo

class Task(var name: String = "MyTask") : Runnable {

    override fun run() {
        println("START -->>>>> ${Thread.currentThread().name}  --- #name: $name");
        Thread.sleep(1000);
        println("END -->>>>> ${Thread.currentThread().name}  --- #name: $name");
        Thread.sleep(2000);
    }
}
