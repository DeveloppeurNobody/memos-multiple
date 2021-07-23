package nio2.chap00_mft.filesystem

import java.lang.Exception
import java.nio.file.Path

// TODO implements all tasks of an FileSystem
interface FileService {
//    fun delete(path: Path): Boolean;
//    fun isFileExists(path: Path): Boolean;


    //fun copyFile(fromPath: Path, toPath: Path): Boolean;
    fun copyFile(pathFromString: String, pathToString: String): Boolean;


    fun renameFile(pathTargetString: String, newName: String): Boolean;
//    fun moveFile(pathFrom: Path, pathTo: Path): Boolean;
//    fun deleteFile(path: Path): Boolean;
//    fun isFileExists(path: Path): Boolean;
//    fun isFileHidden(path: Path): Boolean;
}