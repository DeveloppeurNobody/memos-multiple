package nio2.chap02_attrs

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributeView

object AttrsApp05SupportedViews {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        // list all the supported views in the current file system.
        var fileSystem: FileSystem = FileSystems.getDefault();
        var views: Set<String> = fileSystem.supportedFileAttributeViews();

        views.forEach(::println);

        // test if a all file store supports a particular view - in this case, basic view
        fileSystem.fileStores
            .filter { it -> it.supportsFileAttributeView(BasicFileAttributeView::class.java) }
            .forEach { println("${it.name()} --- supported") }

        fileSystem.fileStores
            .filter { !it.supportsFileAttributeView(BasicFileAttributeView::class.java) }
            .forEach { println("${it.name()} *** not supported") };

        // check view on a file store where a particular file resides - in this case, basic view.
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");
        try {
            var store: FileStore = Files.getFileStore(path);
            var supported: Boolean = store.supportsFileAttributeView("basic");
            println("${store.name()} --- $supported");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}