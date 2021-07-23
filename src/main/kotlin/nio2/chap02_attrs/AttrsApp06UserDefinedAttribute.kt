package nio2.chap02_attrs

import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.FileStore
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.UserDefinedFileAttributeView

object AttrsApp06UserDefinedAttribute {
    @JvmStatic
    @Throws(
        Exception::class,
        IOException::class
    )
    fun main(args: Array<String>) {
        var path: Path = Paths.get("/home/ryu/raf/ts/2009", "BNP.txt");

        // check if your file system implementation support user defined file attributes.
        try {
            var store: FileStore = Files.getFileStore(path);
            if (!store.supportsFileAttributeView(UserDefinedFileAttributeView::class.java)) {
                println("The user defined attributes are not supported on: $store");
            }
            else {
                println("The user defined attributes are supported on $store");
            }
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // use the UserDefinedAttributeView
        var userDefinedFileAttributeView: UserDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView::class.java);

        // set a user defined attribute
        try {
            userDefinedFileAttributeView.write("file.description", Charset.defaultCharset().encode("This file contains private information!"))
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // list the available user file attributes
        try {
            userDefinedFileAttributeView.list()
                .forEach { println("${userDefinedFileAttributeView.size(it)}  *** $it")};
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // get the value of an user defined attribute
        try {
            var size: Int = userDefinedFileAttributeView.size("file.description");
            var buffer: ByteBuffer = ByteBuffer.allocate(size);
            userDefinedFileAttributeView.read("file.description", buffer);
            buffer.flip();
            println(Charset.defaultCharset()
                .decode(buffer)
                .toString());
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }

        // Delete a file's user defined attribute
        try {
            userDefinedFileAttributeView.delete("file.description");
        } catch (ioe: IOException) {
            System.err.println(ioe);
        }
    }
}