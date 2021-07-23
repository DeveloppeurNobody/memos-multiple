package nio2.chap06_watch

import java.io.IOException
import java.lang.Exception
import java.nio.file.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object WatchApp02SecurityWatch {
    @Throws(
        Exception::class,
        InterruptedException::class,
        IOException::class
    )
    @JvmStatic
    fun main(args: Array<String>) {
        //val path = Paths.get("C:/security")
        val path = Paths.get("/home/ryu/security-raf")
        val watch = SecurityWatch()
        try {
            watch.watchVideoCamera(path)
        } catch (ex: IOException) {
            System.err.println(ex)
        } catch (ex: InterruptedException) {
            System.err.println(ex)
        }
    }
}


internal class SecurityWatch {
    lateinit var watchService: WatchService;

    @Throws(IOException::class)
    private fun register(path: Path, kind: WatchEvent.Kind<Path>) {
        //register the directory with the watchService for Kind<Path> event
        path.register(watchService, kind)
    }

    @Throws(IOException::class, InterruptedException::class)
    fun watchVideoCamera(path: Path) {
        watchService = FileSystems.getDefault().newWatchService()
        register(path, StandardWatchEventKinds.ENTRY_CREATE)

        //start an infinite loop
        OUTERMOST@ while (true) {

            //retrieve and remove the next watch key
            val key = watchService.poll(11, TimeUnit.SECONDS)
            if (key == null) {
                println("The video camera is jammed - security watch system is canceled!")
                break
            } else {

                //get list of events for the watch key
                for (watchEvent in key.pollEvents()) {

                    //get the kind of event (create, modify, delete)
                    val kind: WatchEvent.Kind<Path> = watchEvent.kind() as WatchEvent.Kind<Path>

                    //handle OVERFLOW event
                    if (kind === StandardWatchEventKinds.OVERFLOW) {
                        continue
                    }
                    if (kind === StandardWatchEventKinds.ENTRY_CREATE) {

                        //get the filename for the event
                        val watchEventPath =
                            watchEvent as WatchEvent<Path>
                        val filename = watchEventPath.context()
                        val child = path.resolve(filename)
                        if (Files.probeContentType(child) == "image/jpeg") {

                            //print it out the video capture time
                            val dateFormat = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss")
                            println(
                                "Video capture successfully at: " +
                                        dateFormat.format(Date())
                            )
                        } else {
                            println("The video camera capture format failed! This could be a virus!")
                            break@OUTERMOST
                        }
                    }
                }

                //reset the key
                val valid = key.reset()

                //exit loop if the key is not valid (if the directory was deleted, per example)
                if (!valid) {
                    break
                }
            }
        }
        watchService.close()
    }
}



//object WatchApp02SecurityWatch {
//
//    @JvmStatic
//    @Throws(
//        Exception::class,
//        IOException::class,
//        InterruptedException::class
//    )
//    fun main(args: Array<String>) {
//        val path: Path = Paths.get("/home/ryu/security-raf");
//        var watch: SecurityWatch = SecurityWatch();
//
//        try {
//            watch.watchVideoCamera(path);
//        } catch (e: Exception) {
//            when (e) {
//                is InterruptedException -> {
//                    System.err.println("InterruptedException: $e")
//                }
//                is IOException -> {
//                    System.err.println("I/OException: $e")
//                }
//                else -> {
//                    System.err.println("Exception: $e")
//                }
//            }
//        }
//    }
//
//
//    class SecurityWatch {
//        lateinit var watchService: WatchService;
//
//        @Throws(IOException::class)
//        private fun register(path: Path, kind: WatchEvent.Kind<Path>) {
//            path.register(watchService, kind);
//        }
//
//        @Throws(
//            IOException::class,
//            InterruptedException::class
//        )
//        fun watchVideoCamera(path: Path) {
//            watchService = FileSystems.getDefault()
//                .newWatchService();
//            register(path, StandardWatchEventKinds.ENTRY_CREATE);
//
//            // start an infinit loop
//            OUTERMOST@
//            while (true) {
//                // retrieve and remove the next watch key.
//                val key: WatchKey = watchService.poll(11, TimeUnit.SECONDS);
//
//                if (key == null) {
//                    println("The video camera is jammed - security watch system is canceled!");
//                    return@OUTERMOST;
//                }
//                else {
//                    // get list of events for the watch key
//                    key.pollEvents()
//                        .forEach { watchEvent->
//                            // get the kind of event (create, modify, delete)
//                            val kind = watchEvent.kind();
//
//                            // handle OVERFLOW event
//                            if (kind == StandardWatchEventKinds.OVERFLOW) {
//                                return@forEach
//                            }
//
//                            if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
//
//                                // get the filename for the event
//                                val watchEventPath: WatchEvent<Path> = watchEvent as WatchEvent<Path>;
//                                val filename: Path = watchEventPath.context();
//                                val child: Path = path.resolve(filename);
//
//                                if (Files.probeContentType(child).equals("image/jpeg")) {
//
//                                    // print out the video capture image
//                                    var dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    println("Video capture successfully at! ${dateFormat.format(Date())}");
//                                }
//                                else {
//                                    println("The video camera capture format failed! This could be a virus!");
//                                    return@OUTERMOST;
//                                }
//                            }
//                        }
//
//                    // reset the key
//                    var valid: Boolean = key.reset();
//
//                    // exit loop if the key is not valid (if the directory was deleted, per example).
//                    if (!valid) {
//                        return@OUTERMOST;
//                    }
//                }
//            }
//
//            watchService.close();
//        }
//    }
//}