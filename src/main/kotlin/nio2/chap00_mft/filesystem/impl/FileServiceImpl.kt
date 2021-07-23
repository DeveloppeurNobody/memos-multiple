package nio2.chap00_mft.filesystem.impl

import nio2.chap00_mft.filesystem.FileService
import nio2.chap00_mft.filesystem.validator.ValidatorString

//import org.springframework.stereotype.Service
import java.nio.file.*

/**
 * Service for all tasks in domain of FileSystem
 * all operations are blocking I/O
 */
//@Service
class FileServiceImpl : FileService {

    var validatorFilesService: ValidatorFilesService = ValidatorFilesService();

    override fun copyFile(pathFromString: String, pathToString: String): Boolean {
        val validatorResult = validatorFilesService.checkPathFromAndPathToArguments(pathFromString, pathToString);

        if (validatorResult.isValid) {
            try {
                // before copying we need to check if the source exists
                if (validatorFilesService.isFileExistsUnderValidator(pathFromString)) {

                    // all arguments are correct we can process
                    // for getting pathFrom and pathTo
                    var fromPath: Path = Paths.get(pathFromString);
                    var pathTo: Path = Paths.get(pathToString);

                    // attempt to copy
                    Files.copy(
                        fromPath,
                        pathTo,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES,
                        LinkOption.NOFOLLOW_LINKS
                    );

                    // after copying we check if file exists
                    return Files.exists(pathTo);

                } else {
                    throw Exception("--- [ copyFile ] failed | pathFrom does not exists!")
                }
            } catch (e: Exception) {
                throw Exception("--- [ copyFile ] failed | error: $e");
            }
        } else {
            throw Exception("--- [ copyFile ] failed | messageException: ${validatorResult.messageError}")
        }
    }

    /**
     * Attempt to copy file from origine to destination
     * if one or both arguments are not correct we throw an exception.
     * if the operation succeed we return true
     * if we returns false that means arguments are correct
     * but the copying file in destination doest not exists
     */
//    override fun copyFile(pathFromString: String, pathToString: String): Boolean {
//        var validatorResult = ValidatorFilesServiceImpl.checkPathFromAndPathToArguments(pathFromString, pathToString);
//
//        if (validatorResult.isValid) {
//            try {
//                // before copying we need to check if the source exists
//                if (ValidatorFilesServiceImpl.isFileExistsUnderValidator(pathFromString)) {
//
//                    var fromPath: Path = Paths.get(pathFromString);
//                    var pathTo: Path = Paths.get(pathToString);
//
//                    Files.copy(
//                        fromPath,
//                        pathTo,
//                        StandardCopyOption.REPLACE_EXISTING,
//                        StandardCopyOption.COPY_ATTRIBUTES,
//                        LinkOption.NOFOLLOW_LINKS
//                    );
//
//                    return Files.exists(pathTo);
//                    // after copying we check if file exists
//                } else {
//                    throw Exception("--- [ copyFile ] failed | pathFrom does not exists!")
//                }
//            } catch (e: Exception) {
//                throw Exception("--- [ copyFile ] failed | error: $e");
//            }
//        } else {
//            throw Exception("--- [ copyFile ] failed | messageException: ${validatorResult.messageError}")
//        }
//    }

    /**
     * rename file by using path.resolveSibling
     * @param path actual path of the original file
     * @param newName the name to use when renaming
     * @return if the file is renamed then return true else false
     */
    override fun renameFile(pathTargetString: String, newName: String): Boolean {
//        var canRename: Boolean = ValidatorFilesServiceImpl.checkPathSource(pathTargetString);
//
//        return if (canRename && ValidatorString.isStringValid(newName)) {
//            try {
//                var path: Path = Paths.get(pathTargetString);
//                var pathRenamed = Files.move(path, path.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
//
//                // check if the renamed file exists
//                ValidatorFilesServiceImpl.checkPathSource(pathRenamed.toString())
//
//            } catch (e: Exception) {
//                System.err.println("--- [ renameFile ] failed | error: $e");
//                false;
//            }
//        } else {
//            false
//        }

        return false;
    }
//
//    override fun moveFile(pathFrom: Path, pathTo: Path): Boolean {
//        try {
//            Files.move(
//                    pathFrom,
//                    pathTo.resolve(pathFrom.fileName),
//                    StandardCopyOption.REPLACE_EXISTING);
//        } catch (e: Exception) {
//            System.err.println("--- [ moveFile ] failed | error: $e");
//            return false;
//        }
//        return Files.exists(pathTo);
//    }
//
//    override fun deleteFile(path: Path): Boolean {
//        return try {
//            Files.deleteIfExists(path);
//        } catch (e: java.lang.Exception) {
//            System.err.println("--- [ deleteFile ] failed | error: $e");
//            false;
//        }
//    }
//
//    override fun isFileExists(path: Path): Boolean {
//        return try {
//            Files.exists(path);
//        } catch (e: Exception) {
//            System.err.println("--- [ deleteFile ] failed | error: $e");
//            false;
//        }
//    }
//
//    override fun isFileHidden(path: Path): Boolean {
//        return try {
//            Files.isHidden(path);
//        } catch (e: java.lang.Exception) {
//            System.err.println("--- [ isFileHidden] failed | error: $e");
//            false;
//        }
//    }
}





