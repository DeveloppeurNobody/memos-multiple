package threads.cookbook_java9.ch07_concurrent_collections.recipe10_using_variable_handles_new_feature_java9.task

import threads.cookbook_java9.ch07_concurrent_collections.recipe10_using_variable_handles_new_feature_java9.data.Account
import java.lang.invoke.MethodHandles
import java.lang.invoke.VarHandle

class Incrementer(private val account: Account) : Runnable {
    override fun run() {
        var handler: VarHandle
        try {
            handler = MethodHandles.lookup().`in`(Account::class.java).findVarHandle(
                Account::class.java, "amount",
                Double::class.javaPrimitiveType
            )
            for (i in 0..9999) {
                handler.getAndAdd(account, 100)
                account.unsafeAmount += 100
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}
