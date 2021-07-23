package nio2.chap06_watch

import java.io.IOException
import java.nio.file.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


object WatchApp03Print {
    @Throws(
        Exception::class,
        InterruptedException::class,
        IOException::class
    )
    @JvmStatic
    fun main(args: Array<String>) {
        val path: Path = Paths.get("home/ry/raf/printertray");
        var watch: WatchPrinterTray = WatchPrinterTray();

        try {
            watch.watchTray(path);
        } catch (ex: IOException) {
            System.err.println(ex)
        } catch (ex: InterruptedException) {
            System.err.println(ex)
        }

    }
}

internal class Print(var doc: Path) : Runnable {
    override fun run() {
        try {
            // sleep a random number of seconds for simulating dispaching and printing.
            Thread.sleep(20000L + Random.nextInt(30000));
            println("Printing: $doc");
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
    }
}

internal class WatchPrinterTray(val threads: MutableMap<Thread, Path> = mutableMapOf()) {
    @Throws(
        IOException::class,
        InterruptedException::class
    )
    fun watchTray(path: Path) {
        try {
            FileSystems.getDefault()
                .newWatchService()
                .use { watchService ->
                    path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);

                    // start an infinite loop
                    while (true) {
                        // retrieve and remove the next watch key
                        val key: WatchKey = watchService.poll(10, TimeUnit.SECONDS);

                        // get list of events for the watch key
                        key?.pollEvents()
                            ?.forEach { watchEvent ->

                                // get the filename for the event
                                val watchEventPath = watchEvent as WatchEvent<Path>;
                                val filename: Path = watchEventPath.context();

                                // get the kind of event (create, modify, delete)
                                val kind = watchEvent.kind();

                                // handle OVERFLOW event
                                if (kind == StandardWatchEventKinds.OVERFLOW) {
                                    return@forEach;
                                }

                                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                    println("Sending the document to print -> $filename");

                                    var task: Runnable = Print(path.resolve(filename));
                                    var worker: Thread = Thread(task);

                                    // we can set the name of the thread
                                    worker.name = path.resolve(filename).toString();

                                    // store the thread and the path
                                    threads.put(worker, path.resolve(filename));

                                    // start the thread, never call method run() direct
                                    worker.start();
                                }

                                if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                    println("$filename was successfully printed!");
                                }
                            }

                        // reset the key
                        var valid: Boolean = key.reset();

                        // exit loop if the key is not valid (if directory was deleted, per example)
                        if (!valid) {
                            threads.clear();
                            break;
                        }
                    }

                    if (threads.isNotEmpty()) {
                        val it: MutableIterator<Map.Entry<Thread, Path>> =
                            threads.entries.iterator()
                        while (it.hasNext()) {
                            val entry = it.next()
                            if (entry.key.state == Thread.State.TERMINATED) {
                                Files.deleteIfExists(entry.value)
                                it.remove()
                            }
                        }
                    }

//                    if (threads.isNotEmpty()) {
//                        threads.entries
//                            .filter { it.key.state == Thread.State.TERMINATED }
//                            .forEach { Files.deleteIfExists(it.value) }
//                    }
                }

        } catch (ex: IOException) {
            System.err.println(ex)
        } catch (ex: InterruptedException) {
            System.err.println(ex)
        }
    }
}