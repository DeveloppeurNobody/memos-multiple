package netty.telnet.executor

import io.reactivex.rxjava3.subjects.BehaviorSubject


object ControlConnectionEventBus {
    val COMMAND_STATE: BehaviorSubject<Boolean> = BehaviorSubject.create();
}