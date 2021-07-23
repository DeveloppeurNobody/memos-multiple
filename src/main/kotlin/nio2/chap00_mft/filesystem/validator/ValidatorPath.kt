package nio2.chap00_mft.filesystem.validator

import nio2.chap00_mft.filesystem.enum.PathEnum
import nio2.chap00_mft.filesystem.enum.StringEnum
import java.nio.file.Paths

class ValidatorPath(var validatorString: ValidatorString = ValidatorString()) {

    @Throws(Exception::class)
    fun getPathEnum(pathString: String): PathEnum {

        when (validatorString.getStringEnum(pathString)) {
            StringEnum.STRING_IS_NULL -> { return PathEnum.PATH_IS_NULL }
            StringEnum.STRING_IS_EMPTY -> { return PathEnum.PATH_IS_EMPTY }
            StringEnum.STRING_IS_BLANK -> { return PathEnum.PATH_IS_EMPTY }
            StringEnum.STRING_IS_NOT_VALID -> { return PathEnum.PATH_IS_INVALID }
        }

        try {
            // throws InvalidPath only on windows
            // in linux/unix we can create a file with non alphabet
            Paths.get(pathString);
        } catch (e : Exception) {
            System.err.println("--- [ getPathEnumAccordintToStringValue ] failed: [ $pathString ] | error: $e");
            return PathEnum.PATH_IS_INVALID;
        }

        return PathEnum.PATH_IS_VALID;
    }

    fun checkPathEnumIsValid(pathEnum: PathEnum): Boolean = when(pathEnum) {
        PathEnum.PATH_IS_VALID -> true
        else -> false;
    }

}