package nio2.chap06_watch

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

object WatchApp04WatchRecursiveRafaelNadal {
    @Throws(
        Exception::class,
        InterruptedException::class,
        IOException::class
    )
    @JvmStatic
    fun main(args: Array<String>) {
        val path: Path = Paths.get("/home/ryu/raf_watch04");
        var watch: WatchRecursiveRafaelNadal = WatchRecursiveRafaelNadal();

        try {
            watch.watchRNDir(path);
        } catch (ex: IOException) {
            System.err.println(ex)
        } catch (ex: InterruptedException) {
            System.err.println(ex)
        }

    }
}

class WatchRecursiveRafaelNadal {
    private lateinit var watchService: WatchService;
    private var directories: MutableMap<WatchKey, Path> = mutableMapOf();

    @Throws(IOException::class)
    private fun registerPath(path: Path) {
        // register the received path
        var key: WatchKey = path.register(watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_DELETE);

        // store the key and path
        directories[key] = path;
    }

    @Throws(IOException::class)
    fun registerTree(start: Path) {
        Files.walkFileTree(start, object: SimpleFileVisitor<Path>(){
            @Throws(IOException::class)
            override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                println("Registering: $dir");
                registerPath(dir!!);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Throws(
        InterruptedException::class,
        IOException::class
    )
    fun watchRNDir(start: Path) {
        watchService = FileSystems.getDefault()
            .newWatchService();

        registerPath(start);

        // start an infinite loop
        while (true) {

            // retrieve and remove the next watch key
            val key = watchService.take();

            // get list of events for the watch key
            key.pollEvents()
                .forEach { watchEvent ->

                    // get the kind of event (create, modify, delete)
                    val kind = watchEvent.kind();

                    // get filename for the event
                    val watchEventPath = watchEvent as WatchEvent<Path>;
                    val filename: Path = watchEventPath.context();

                    // handle OVERFLOW event
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        return@forEach;
                    }

                    // handle CREATE event
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        val directoryPath: Path = directories[key]!!;
                        val child: Path = directoryPath.resolve(filename);

                        if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                            registerTree(child);
                        }
                    }

                    // print it out
                    println("$kind -> $filename");
                }

            // reset the key
            var valid: Boolean = key.reset();

            // remove the key if it is not valid
            if (!valid) {
                directories.remove(key);

                // there are no more keys registred
                if (directories.isEmpty()) {
                    break;
                }
            }
        }
        watchService.close();
    }
}