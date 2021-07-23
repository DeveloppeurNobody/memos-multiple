package nio2.chap00_mft.filesystem.impl

import java.nio.file.Paths
//
//object ValidatorFilesServiceImpl {
//
//    fun checkPathSource(pathString: String): Boolean {
//        var result: Boolean = false;
//
//        var pathEnum: PathEnum = ValidatorPath.getPathEnum(pathString);
//        var validatorData = getValidatorData(pathEnum);
//
//        if (validatorData.isValid) {
//            try {
//                return Files.exists(Paths.get(pathString));
//            } catch (e: Exception) {
//                System.err.println("--- [ checkPathSource ] failed | error: $e")
//            }
//        }
//
//        return result;
//    }
//
//    fun getValidatorData(pathEnum: PathEnum): ValidatorData = when (pathEnum) {
//        PathEnum.PATH_IS_NULL -> {
//            ValidatorData(false, pathEnum.toString());
//        }
//        PathEnum.PATH_IS_EMPTY -> {
//            ValidatorData(false, pathEnum.toString());
//        }
//        PathEnum.PATH_IS_INVALID -> {
//            ValidatorData(false, pathEnum.toString());
//        }
//        PathEnum.PATH_IS_VALID -> {
//            ValidatorData(true, pathEnum.toString());
//        }
//    }
//
//    fun checkPathArgument(pathString: String): ValidatorData {
//        var pathEnum: PathEnum = ValidatorPath.getPathEnum(pathString);
//
//        return when (pathEnum) {
//            PathEnum.PATH_IS_VALID -> {
//                ValidatorData(true, pathEnum.toString());
//            }
//            PathEnum.PATH_IS_INVALID -> {
//                ValidatorData(false, pathEnum.toString());
//            }
//            PathEnum.PATH_IS_EMPTY -> {
//                ValidatorData(false, pathEnum.toString());
//            }
//            PathEnum.PATH_IS_NULL -> {
//                ValidatorData(false, pathEnum.toString());
//            }
//        }
//    }
//
//    fun checkPathFromAndPathToArguments(pathFrom: String, pathTo: String): ValidatorData {
//        var validatorPathFrom: ValidatorData = checkPathArgument(pathFrom);
//        var validatorPathTo: ValidatorData = checkPathArgument(pathTo);
//        var validatorResult: ValidatorData = ValidatorData();
//
//        // validator has false as initialised value
//        if (validatorPathFrom.isValid) {
//            if (validatorPathTo.isValid) {
//                validatorResult.isValid = true;
//            } else {
//                validatorResult.messageError = validatorPathTo.messageError;
//            }
//        } else {
//            validatorResult.messageError = validatorPathFrom.messageError;
//        }
//
//        return validatorResult;
//    }
//
//    fun isFileExistsUnderValidator(pathString: String): Boolean {
//        var result: Boolean = false;
//
//        try {
//            // before checking if file exists, we check all possibilites
//            // with argument null, etc
//            var validatorData = checkPathArgument(pathString);
//
//            if (validatorData.isValid) {
//                result = Files.exists(Paths.get(pathString));
//            }
//        } catch (e: Exception) {
//            System.err.println("--- [ isFileExists ] failed | error: $e");
//        }
//        return result;
//    }
//
//
//
//
//    // TODO for checkPaths for vararg ...
//
//    //    fun checkPathsArguments(vararg pathStrings: String): MutableList<ValidatorFile> {
////        var mapValidatorFiles: MutableMap<String, ValidatorFile> = mutableMapOf();
////
////        pathStrings.forEach {
////            var pathEnum: PathEnum = ValidatorPath.getPathEnumAccordingToStringValue(it);
////            mapValidatorFiles[it] = getValidatorDataAccordingToPathEnum(pathEnum);
////        }
////
////        return mutableListOf();
////    }
//
//}