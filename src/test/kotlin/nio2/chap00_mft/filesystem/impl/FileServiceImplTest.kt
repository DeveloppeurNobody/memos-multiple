package nio2.chap00_mft.filesystem.impl

import nio2.chap00_mft.filesystem.FileService
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test FileService")
@Tag("TestFileServiceImpl")
internal class FileServiceImplTest {
    // TODO file.separator() ?

    private lateinit var tempDirectory: Path;
    private lateinit var tempFileFrom: Path;

    //
    private val tempDirectoryPrefix: String = "tmp_irsal";
    private var isTempDirectoryExists: Boolean = false;

    //
    private val fileService: FileService = FileServiceImpl();

    /**
     * we create a tempDirectory under 'user.dir' named 'workspace'
     * before doing any I/O operation.
     * we can't go further if tempDirectory is not created
     */
    @BeforeAll
    fun `set up and check if tempDirectory is created before going further`() {
        tempDirectory = Files.createTempDirectory(Paths.get(System.getProperty("user.dir"), "workspace"), tempDirectoryPrefix);
        println(Files.exists(tempDirectory));
        println("path of tempDirectory: $tempDirectory");
        tempFileFrom = Files.createTempFile(tempDirectory, "tempFile", ".txt");
        // if tempDirectory does not exists, we can't go further
        assertTrue(Files.exists(tempDirectory));
        //assertTrue(Files.exists(Paths.get("/home/ryu/ryu")))
    }

    @Nested
    @DisplayName("tests copyFile method")
    inner class CopyFileTests {
        @Test
        fun `test expected exception when copying with first argument empty`() {
            assertThrows<Exception> { fileService.copyFile("", tempFileFrom.toString()); }
        }

        @Test
        fun `test expected exception when copying with second argument empty`() {
            assertThrows<Exception> { fileService.copyFile(tempFileFrom.toString(), ""); }
        }

        @Test
        fun `test expected exception when copying with all empty arguments`() {
            assertThrows<Exception> { fileService.copyFile("", ""); }
        }

        @DisabledOnOs(OS.LINUX)
        @Test
        fun `test expected exception when copying with first argument as illegal argument`() {
            assertThrows<Exception> { fileService.copyFile(1.toString(), tempFileFrom.toString()); }
        }

        @DisabledOnOs(OS.LINUX)
        @Test
        fun `test expected exception when copying with second argument as illegal argument`() {
            assertThrows<Exception> { fileService.copyFile(tempFileFrom.toString(), 1.toString()); }
        }

        @DisabledOnOs(OS.LINUX)
        @Test
        fun `test expected exception when copying with all arguments as illegal arguments`() {
            assertThrows<Exception> { fileService.copyFile(1.toString(), 1.toString()); }
        }

        @Test
        fun `test copying file`() {
            var tempFileTo = Paths.get(tempDirectory.toString(), "tempFileCopy.txt");
            fileService.copyFile(tempFileFrom.toString(), tempFileTo.toString());

            assertTrue(Files.exists(tempFileFrom));
            assertTrue(Files.exists(tempFileTo));
        }

        /**
         * That not work in linux because, we can create a file
         * with non alphabetic arguments
         */
        @DisabledOnOs(OS.LINUX)
        @Test
        fun `test expected exception with invalid path`() {
            var tempFile = "$tempDirectory\\\\tempFileCopyZZ????..txt";
            assertThrows<Exception> { fileService.copyFile(tempFile, tempFile); }
        }
    }

//    @Test
//    fun renameFile() {
//       // assertTrue(fileService.renameFile(tempFileFrom, "wiki_renamed.txt"));
//    }
//
//    @Test
//    fun moveFile() {
//
//    }
//
//    @Test
//    fun deleteFile() {
//    }
//
//    @Test
//    fun isFileExists() {
//    }
//
//    @Test
//    fun isFileHidden() {
//    }

//    @AfterAll
//    fun tearDown() {
//        try {
//            println("tempDirectory: $tempDirectory");
//            println("Attempt to delete all temporaries files and directories ...")
//            Runtime.getRuntime()
//                    .addShutdownHook(object: Thread(){
//                        override fun run() {
//                            println("Deleting the temporary folder ...");
//                            try {
//                                Files.newDirectoryStream(tempDirectory)
//                                        .use {
//                                            it.forEach(Files::delete)
//                                            Files.delete(tempDirectory);
//                                        }
//                            } catch (ioe: IOException) {
//                                System.err.println("--- [ run ] failed | error: $ioe");
//                            }
//                            println("Shutdown-hook completed ...")
//                        }
//                    });
//            // operations done
//            println("operations done !");
//
//        } catch (e: Exception) {
//            when (e) {
//                is InterruptedException -> println("Interrupted Exception: $e");
//                is IOException -> println("An I/O exception: $e");
//                else -> println("Exception: $e");
//            }
//        }
//
//        assertTrue(Files.exists(tempDirectory));
//    }

}