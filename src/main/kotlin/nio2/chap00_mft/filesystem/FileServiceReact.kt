package nio2.chap00_mft.filesystem
//
//import nio2.chap00_mft.filesystem.impl.FileServiceImpl
//import reactor.core.publisher.Mono
//import java.nio.file.Files
//import java.nio.file.LinkOption
//import java.nio.file.Path
//import java.nio.file.StandardCopyOption
//
///**
// * service that take all tasks I/O files,
// * as non blocking operations
// */
//// TODO make interface and Log in Mono
//class FileServiceReact(var fileService: FileServiceImpl) {
//
//    fun copyFile(from: Path, target: Path): Mono<Boolean> {
//        return Mono.fromCallable {
//            // updated to string call
//            fileService.copyFile(from.toString(), target.toString());
//        }
//    }
//
//    fun renameFile(path: Path, newName: String): Mono<Boolean> {
//        return Mono.fromCallable {
//            fileService.renameFile(path, newName);
//        }
//    }
//
//    fun moveFile(pathFrom: Path, pathTo: Path): Mono<Any> {
//        return Mono.fromCallable {
//            fileService.moveFile(pathFrom, pathTo);
//        }
//    }
//
//    fun deleteFile(target: Path): Mono<Boolean> {
//        return Mono.fromCallable {
//            fileService.deleteFile(target);
//        }
//    }
//
//    fun isFileExists(target: Path): Mono<Boolean> {
//        return Mono.fromCallable {
//            fileService.isFileExists(target);
//        }
//    }
//
//    fun isFileHidden(target: Path): Mono<Boolean> {
//        return Mono.fromCallable {
//            fileService.isFileHidden(target);
//        }
//    }
//
//
//}