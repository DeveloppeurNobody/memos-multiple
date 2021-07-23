package nio2.chap02_attrs

import java.io.IOException
import java.nio.file.*
import java.nio.file.attribute.FileStoreAttributeView

object AttrsApp04Stores {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        // get information for all the stores in the default file system.
        var fs: FileSystem = FileSystems.getDefault();
        fs.fileStores
            .forEach { store ->
                try {
                    var totalSpace: Long = store.totalSpace / 1024;
                    var usedSpace: Long = (store.totalSpace - store.unallocatedSpace) / 1024;
                    var availableSpace: Long = store.usableSpace;
                    var isReadOnly: Boolean = store.isReadOnly;

                    println("--- ${store.name()} --- ${store.type()}");
                    println("Total space: $totalSpace");
                    println("Used space: $usedSpace");
                    println("Available space: $availableSpace");
                    println("Is read Only: $isReadOnly");
                } catch (ioe: IOException) {
                    System.err.println(ioe);
                }
            }

        // get information about a file store where a particular file resides.
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");
        try {
            var store: FileStore = Files.getFileStore(path);

            var totalSpace: Long = store.totalSpace / 1024;
            var usedSpace: Long = (store.totalSpace - store.unallocatedSpace) / 1024;
            var availableSpace: Long = store.usableSpace;
            var isReadOnly: Boolean = store.isReadOnly;

            println("--- ${store.name()} --- ${store.type()}");
            println("Total space: $totalSpace");
            println("Used space: $usedSpace");
            println("Available space: $availableSpace");
            println("Is read Only: $isReadOnly");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // get the FileStoreAttributeView
        fs.fileStores
            .forEach { store ->
                var fileStoreAttributeView: FileStoreAttributeView = store.getFileStoreAttributeView(FileStoreAttributeView::class.java)
                println("fsav: $fileStoreAttributeView")
            }
    }
}