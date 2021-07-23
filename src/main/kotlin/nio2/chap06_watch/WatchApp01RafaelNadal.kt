package nio2.chap06_watch

import java.io.IOException
import java.lang.Exception
import java.nio.file.*

object WatchApp01RafaelNadal {

    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class,
        InterruptedException::class
    )
    fun main(args: Array<String>) {
        val path: Path = Paths.get("/home/ryu/raf");
        var watch: WatchRafaelNadal = WatchRafaelNadal();

        try {
            watch.watchRNDir(path);
        } catch (e: Exception) {
            when (e) {
                is InterruptedException -> {
                    System.err.println("InterruptedException: $e")
                }
                is IOException -> {
                    System.err.println("I/OException: $e")
                }
                else -> {
                    System.err.println("Exception: $e")
                }
            }
        }
    }

    class WatchRafaelNadal {
        @Throws(
            IOException::class,
            InterruptedException::class
        )
        fun watchRNDir(path: Path) {
            try {
                FileSystems.getDefault()
                    .newWatchService()
                    .use { watchService ->
                        path.register(
                            watchService,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_MODIFY,
                            StandardWatchEventKinds.ENTRY_DELETE
                        );

                        // start an infinite loop
                        while (true) {

                            // retrieve and remove the next watch key.
                            val key: WatchKey = watchService.take();

                            // get list of pending events for the watch key.
                            key.pollEvents()
                                .forEach { watchEvent ->
                                    // get the kind of event (create, modify, delete)
                                    val kind = watchEvent.kind();

                                    // handle OVERFLOW event
                                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                                        // in java we use: continue;
                                        // in kotlin we use: (implicit label) return@forEach
                                        return@forEach;
                                    }

                                    // get the filename for the event
                                    val watchEventPath: WatchEvent<Path> = watchEvent as WatchEvent<Path>;
                                    val filename: Path = watchEventPath.context();

                                    // print out
                                    println("$kind -> $filename");
                                }

                            // reset the key
                            var valid: Boolean = key.reset();

                            // exit loop if the key is not valid (if the directory is deleted, per example)
                            if (!valid) {
                                break;
                            }
                        }
                    }
            } catch (e: Exception) {
                System.err.println(e);
            }
        }
    }
}