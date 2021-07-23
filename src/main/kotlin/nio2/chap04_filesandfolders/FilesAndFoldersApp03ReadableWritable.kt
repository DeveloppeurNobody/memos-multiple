package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path

object FilesAndFoldersApp03ReadableWritable {
    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = FileSystems.getDefault()
            .getPath("/home/ryu/raf/ts/2009", "AEGON.txt");

        // method 1
        var isReadable: Boolean = Files.isReadable(path);
        var isWritable: Boolean = Files.isWritable(path);
        var isExecutable: Boolean = Files.isExecutable(path);
        var isRegular: Boolean = Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);

        if ( isReadable && isWritable && isExecutable && isRegular ) {
            println("The checked file is accessbile!");
        }
        else {
            println("It's not accessbile!");
            println("isReadable: $isReadable | isWritable: $isWritable | isExecutable: $isExecutable | isRegular: $isRegular");
        }

        // method 2
        var isAccessible: Boolean = Files.isRegularFile(path)
                && Files.isReadable(path)
                && Files.isExecutable(path)
                && Files.isWritable(path);

        if (isAccessible) {
            println("The checked file is accessible!");
        }
        else {
            println("The checked file is not accessible!");
        }
    }
}