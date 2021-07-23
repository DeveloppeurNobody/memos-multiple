package netty.telnet.executor



import io.reactivex.rxjava3.schedulers.Schedulers
import netty.telnet.client.ControlConnection
import netty.telnet.client.TelnetClientService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class ControlConnectionService {

    companion object {
        private val executor = Executors.newSingleThreadExecutor(ThreadFactory { it -> Thread(it, "CC") });
    }

    init {
        println("[ ${Thread.currentThread().name} ] ControlConnectionService() #newInstance");
        println("[ ${Thread.currentThread().name} ] attempt to start a control connection");

        executor.submit(ControlConnection());

        ControlConnectionEventBus
            .COMMAND_STATE
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.from(executor))
            .subscribe(
                {
                    println("[ ${Thread.currentThread().name} ] COMMAND_STATE #it: $it");
                    printMessage();
                },
                {
                    println("[ ${Thread.currentThread().name} ] COMMAND_STATE #ex: ${it.printStackTrace()}");
                }
            )

    }

    fun printMessage() {
        println("[ ${Thread.currentThread().name} ] printMessage()");
    }

}