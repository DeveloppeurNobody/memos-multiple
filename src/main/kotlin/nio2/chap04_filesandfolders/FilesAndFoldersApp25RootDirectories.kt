package nio2.chap04_filesandfolders

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Path
import java.util.*


object FilesAndFoldersApp25RootDirectories {

    @JvmStatic
    @Throws(
     Exception::class,
     IOException::class
    )
    fun main(args: Array<String>) {
        var dirs: Iterable<Path> = FileSystems.getDefault()
            .rootDirectories;
        var mutableList: MutableList<Path> = mutableListOf();

        dirs.forEach { mutableList.add(it) };

        var arr: Array<Path?> = arrayOfNulls<Path>(mutableList.size);
        mutableList.forEachIndexed { index, path -> arr[index] = path }

        arr.forEach(::println);


        // JAVA CODE
        /*
          Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        ArrayList<Path> list = new ArrayList<Path>();
        for (Path name : dirs) {
            //System.out.println(name);
            list.add(name);
        }
        Path[] arr = new Path[list.size()];
        list.toArray(arr);

        for (Path path : arr) {
            System.out.println(path);
        }
         */
    }
}