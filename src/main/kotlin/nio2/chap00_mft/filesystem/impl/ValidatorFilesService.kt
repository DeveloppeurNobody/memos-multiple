package nio2.chap00_mft.filesystem.impl

import nio2.chap00_mft.filesystem.enum.PathEnum
import nio2.chap00_mft.filesystem.model.ValidatorData
import nio2.chap00_mft.filesystem.validator.ValidatorPath
import java.nio.file.Files
import java.nio.file.Paths

class ValidatorFilesService(var validatorPath: ValidatorPath = ValidatorPath()) {

    /**
     * get pathEnum according to string value
     */
    fun checkPathArgument(pathString: String): PathEnum {
        return validatorPath.getPathEnum(pathString);
    }

    fun checkPathFromAndPathToArguments(pathFrom: String, pathTo: String): ValidatorData {
        val pathFromEnum = validatorPath.getPathEnum(pathFrom);
        val pathToEnum = validatorPath.getPathEnum(pathTo);

        val validatorPathFrom: ValidatorData = getValidatorData(pathFromEnum);
        val validatorPathTo: ValidatorData = getValidatorData(pathToEnum);

        var validatorResult: ValidatorData = ValidatorData();

        // validator has false as initialised value
        if (validatorPathFrom.isValid) {
            if (validatorPathTo.isValid) {
                validatorResult.isValid = true;
            } else {
                validatorResult.messageError = validatorPathTo.messageError;
            }
        } else {
            validatorResult.messageError = validatorPathFrom.messageError;
        }

        return validatorResult;
    }

    fun getValidatorData(pathEnum: PathEnum): ValidatorData = when (pathEnum) {
        PathEnum.PATH_IS_VALID -> {
            ValidatorData(true, pathEnum.toString());
        }
        PathEnum.PATH_IS_INVALID -> {
            ValidatorData(false, pathEnum.toString());
        }
        PathEnum.PATH_IS_EMPTY -> {
            ValidatorData(false, pathEnum.toString());
        }
        PathEnum.PATH_IS_NULL -> {
            ValidatorData(false, pathEnum.toString());
        }
    }

    fun isFileExistsUnderValidator(pathString: String): Boolean {
        try {
            // before checking if file exists, we check all possibilites
            // with argument null, etc
            val pathEnum = checkPathArgument(pathString);
            val validatorData = getValidatorData(pathEnum)

            if (validatorData.isValid) {
                return Files.exists(Paths.get(pathString));
            } else {
                return false;
            }
        } catch (e: Exception) {
            System.err.println("--- [ isFileExists ] failed | error: $e");
            return false;
        }
    }

}