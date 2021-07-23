package nio2.chap04_filesandfolders

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.nio.file.*
import java.time.LocalTime
import java.util.*

object FilesAndFoldersObservable {
    @JvmStatic
    fun main(args: Array<String>) {
        var copyFrom01: Path = Paths.get("/home/ryu/Downloads", "waarp-demovm.ova");
        var copyTo01: Path = Paths.get("/home/ryu/raf", copyFrom01.fileName.toString());

        try {

            println("StartTime ${LocalTime.now()}")
            var o = Observable.fromCallable { Files.copy(copyFrom01, copyTo01, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS)  }
            printMsg("")
            o.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe { printMsg("fromSubscribe ${LocalTime.now()}") }
            printMsg("afterSubcribe");

            var i = 0;
            while (i < 100) {
                Thread.sleep(1000);
                println("sleeping 1s");
                i++
            }

        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }

    fun printMsg(msg: String) {
        println("work done! $msg")
    }
}